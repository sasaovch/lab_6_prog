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
import java.io.Serializable;
import java.net.BindException;
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
import lab.common.util.Message;
import lab.server.util.ParsingJSON;
import lab.server.util.SpaceMarineCollection;


public class ServerApp {
    private SpaceMarineCollection collection;
    private final CommandManager commands;
    private final Logger logger;
    private final Scanner scanner;
    private SocketAddress client;
    private SocketAddress address;
    private DatagramChannel channel;
    private ParsingJSON pars;
    private File fileOfApp;
    private boolean isWorkState;
    private final int defaultBufferSize = 2048;
    private final int defaultSleepTime = 500;

    public ServerApp(CommandManager commands, InetAddress addr, int port) {
        this.commands = commands;
        address = new InetSocketAddress(addr, port);
    }

    {
        isWorkState = true;
        logger = Logger.getLogger("Server");
        scanner = new Scanner(System.in);
    }

    public void start(String filename) throws IOException, ClassNotFoundException, InterruptedException {
        try (DatagramChannel datachannel = DatagramChannel.open()) {
            this.channel = datachannel;
            logger.info("Open datagram channel. Server started working.");
            parsing(filename);
            channel.configureBlocking(false);
            try {
                channel.bind(address);
            } catch (BindException e) {
                logger.info("Cannot assign requested address.");
                isWorkState = false;
            }
            Message mess;
            CommandResult result;
            while (isWorkState) {
                checkCommands();
                mess = receiveMessage();
                if (Objects.isNull(mess)) {
                    continue;
                } else if ("error".equals(mess.getCommand())) {
                    logger.info("Something with data went wrong.");
                    sendCommResult(new CommandResult("Something with data went wrong. Try again.", false));
                    continue;
                }
                logger.info("Receive command from client: " + mess.getCommand());
                if (mess.getCommand().equals("exit")) {
                    logger.info("Client disconnected.");
                    continue;
                }
                result = execute(mess);
                sendCommResult(result);
            }
        }
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

    public Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Serializable mess = (Serializable) is.readObject();
        in.close();
        is.close();
        return mess;
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        byte[] outMess = out.toByteArray();
        out.close();
        os.close();
        return outMess;
    }

    public void sendCommResult(CommandResult result) throws IOException {
        byte[] bufs = serialize(result);
        byte[] bufSendSize = serialize(bufs.length);
        ByteBuffer sendBufferSize = ByteBuffer.wrap(bufSendSize);
        channel.send(sendBufferSize, client);
        sendBufferSize.clear();
        ByteBuffer sendBuffer = ByteBuffer.wrap(bufs);
        channel.send(sendBuffer, client);
        sendBuffer.clear();
        logger.info("Send result of command to client: " + result.getData());
    }

    public Message receiveMessage() throws IOException, ClassNotFoundException, InterruptedException {
        byte[] bufReceiveSize = new byte[defaultBufferSize];
        ByteBuffer receiveBufferSize = ByteBuffer.wrap(bufReceiveSize);
        client = channel.receive(receiveBufferSize);
        Message mess;
        if (Objects.nonNull(client)) {
            Serializable receiveMess = deserialize(bufReceiveSize);
            if (receiveMess.getClass().equals(Integer.class)) {
                int size = (int) receiveMess;
                Thread.sleep(defaultSleepTime);
                byte[] bufr = new byte[size];
                ByteBuffer receiveBuffer = ByteBuffer.wrap(bufr);
                channel.receive(receiveBuffer);
                receiveMess = deserialize(bufr);
                mess = (Message) receiveMess;
            } else {
                mess = (Message) receiveMess;
            }
            return mess;
        } else {
            return null;
        }
    }

    public void parsing(String filename) throws JsonSyntaxException, FileNotFoundException, IOException {
        logger.info("Creating collection.");
        try {
            pars = new ParsingJSON();
            fileOfApp = new File(filename);
            String fileline = readfile(fileOfApp);
            collection = pars.deSerialize(fileline);
            logger.info("The collection has been created.");
        } catch (JsonSyntaxException | FileNotFoundException e) {
            logger.info("Something with parsing went wrong. Check data in file and rights of file.");
            isWorkState = false;
            logger.info("Finish work.");
            return;
        }
    }
}
