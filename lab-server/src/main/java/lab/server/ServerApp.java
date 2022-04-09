package lab.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;

import lab.common.commands.Command;
import lab.common.commands.CommandManager;
import lab.common.commands.CommandResult;
import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;
import lab.common.util.Message;
import lab.server.util.ParsingJSON;


public class ServerApp {
    private SpaceMarineCollection collection;
    private final CommandManager commands;
    private final Logger logger;
    private final Scanner scanner;
    private byte[] bufr;
    private byte[] bufs;
    private ByteBuffer sendBuffer;
    private ByteBuffer receiveBuffer;
    private SocketAddress client;
    private SocketAddress address;
    private DatagramChannel channel;
    private ParsingJSON pars;
    private File fileOfApp;
    private boolean isWorkState;
    private final int defaultBufferSize = 2048;

    public ServerApp(CommandManager commands, InetAddress addr, int port) {
        this.commands = commands;
        address = new InetSocketAddress(addr, port);
    }

    {
        isWorkState = true;
        logger = Logger.getLogger("Server");
        scanner = new Scanner(System.in);
        bufr = new byte[defaultBufferSize];
        receiveBuffer = ByteBuffer.wrap(bufr);
    }

    public void start() throws IOException, ClassNotFoundException {
        try (DatagramChannel datachannel = DatagramChannel.open()) {
            this.channel = datachannel;
            logger.info("Open datagram channel. Server started working");
            channel.configureBlocking(false);
            channel.bind(address);
            Message mess;
            CommandResult result;
            while (isWorkState) {
                checkCommands();
                mess = receiveMessage();
                if (Objects.isNull(mess)) {
                    continue;
                }
                if (mess.getCommand().contains(".json")) {
                    parsing(mess.getCommand());
                    continue;
                }
                logger.info("Receive command from client: " + mess.getCommand());
                if (mess.getCommand().equals("exit")) {
                    logger.info("Client disconnected");
                    continue;
                }
                result = execute(mess);
                sendCommResult(result);
                logger.info("Send result of command to client: " + result.getMessage());
            }
        }
    }

    public Message deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Message mess = (Message) is.readObject();
        return mess;
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        byte[] outMess =  out.toByteArray();
        return outMess;
    }

    public CommandResult execute(Message mess) {
        Command command = commands.getMap().get(mess.getCommand());
        Object data = mess.getData();
        SpaceMarine spMar = mess.getSpacMar();
        return command.run(data, spMar, collection);
    }

    public String readfile(File file) throws FileNotFoundException, IOException {
        StringBuilder strData = new StringBuilder();
        String line;
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                while ((line = bufferedReader.readLine()) != null) {
                    strData.append(line);
                }
            }
        }
        return strData.toString();
    }

    public void checkCommands() throws IOException {
        if (System.in.available() > 0) {
            String line = scanner.nextLine();
            if ("save".equals(line)) {
                try {
                    if (pars.serialize(collection, fileOfApp)) {
                        logger.info("The collection has been saved");
                    } else {
                        logger.info("The collection hasn't been saved.");
                    }
                } catch (FileNotFoundException | NullPointerException e) {
                    logger.info("File isn't exist or invalid user rights.");
                }
            }
            if ("exit".equals(line)) {
                logger.info("Server finished working");
                isWorkState = false;
            }
        }
    }

    public void sendCommResult(CommandResult result) throws IOException {
        bufs = serialize(result);
        sendBuffer = ByteBuffer.wrap(bufs);
        channel.send(sendBuffer, client);
        logger.info("Send message to client that the collection hasn't been created");
        sendBuffer.clear();
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        client = channel.receive(receiveBuffer);
        if (Objects.nonNull(client)) {
            Message mess = deserialize(bufr);
            receiveBuffer.clear();
            return mess;
        } else {
            return null;
        }
    }

    public void parsing(String filename) throws JsonSyntaxException, FileNotFoundException, IOException {
        logger.info("Received file path for creating collection");
        CommandResult result;
        try {
            pars = new ParsingJSON();
            fileOfApp = new File(filename);
            String fileline = readfile(fileOfApp);
            collection = pars.deSerialize(fileline);
        } catch (JsonSyntaxException | FileNotFoundException e) {
            logger.info("Something with parsing went wrong. Check data in file and rights of file.");
            result = new CommandResult("Something with parsing went wrong. Check data in file and rights of file.", false);
            sendCommResult(result);
            isWorkState = false;
            logger.info("Finish work.");
            return;
        }
        if (Objects.equals(collection, null)) {
            result = new CommandResult("Incorrect data in file for parsing.", false);
            sendCommResult(result);
            isWorkState = false;
            logger.info("Finish work.");
        } else {
            result = new CommandResult("Everything is ready to work.", true);
            sendCommResult(result);
        }
    }
}
