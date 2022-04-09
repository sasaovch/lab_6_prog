package lab.client;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import lab.common.commands.CommandResult;
import lab.common.exception.IncorrectData;
import lab.common.exception.IncorrectDataOfFileException;
import lab.common.util.Message;


public class Console {
    private final Map<String, Function<String, Message>> parsingList;
    private final IOManager ioManager;
    private final ServerWork server;
    private final Integer lengthOfCommand = 3;
    private Message message;
    private Boolean isWorkState = true;

    public Console(Map<String, Function<String, Message>> parsingList, IOManager ioManager, ServerWork server) {
        this.parsingList = parsingList;
        this.ioManager = ioManager;
        this.server = server;
    }

    public void run() throws IOException, IncorrectData, ClassNotFoundException, IncorrectDataOfFileException {
        String filePath = System.getenv("filePath");
        if (Objects.equals(filePath, null)) {
            ioManager.printerr("There is no such variable \"filePath\"");
            return;
        }
        server.sendMessage(new Message(filePath, null, null));
        checkPrintResult(server.reciveMessage());
        String[] command = new String[lengthOfCommand];
        String line = "";
        String name;
        String value;
        String lastPartComm;
        while (isWorkState) {
            if (!ioManager.getFileMode()) {
                ioManager.prompt();
            }
            line = ioManager.readLine();
            if (!checkLine(line)) {
                continue;
            }
            command = (line.trim() + " " + " ").split(" ", lengthOfCommand);
            name = command[0];
            value = command[1];
            lastPartComm = command[2];
            if (!checkCommand(name, lastPartComm)) {
                continue;
            }
            if (parsCommand(name, value)) {
                server.sendMessage(message);
                checkPrintResult(server.reciveMessage());
            }
        }
        ioManager.println("Good Buy!\n\\(?_?)/");
    }

    public boolean parsCommand(String name, String value) {
            Function<String, Message> func = parsingList.get(name);
            message = func.apply(value);
            if (Objects.isNull(message)) {
                if (ioManager.getFileMode()) {
                    ioManager.turnOffFileMode();
                    return false;
                } else {
                    return false;
                }
            } else if ("execute".equals(message.getCommand())) {
                return false;
            } else if ("exit".equals(message.getCommand())) {
                isWorkState = false;
            }
            return true;
    }

    public boolean checkLine(String line) {
        if (Objects.equals(line, null) && !ioManager.getFileMode()) {
            return false;
        } else if (Objects.equals(line, null)) {
            ioManager.turnOffFileMode();
            return false;
        }
        return true;
    }

    public boolean checkCommand(String name, String lastPartComm) {
        boolean resultcheck = true;
        if ("".equals(name) && !ioManager.getFileMode()) {
            resultcheck = false;
        } else if ("".equals(name)) {
            ioManager.turnOffFileMode();
            resultcheck = false;
        }
        if (Objects.isNull(parsingList.get(name))) {
            if (!ioManager.getFileMode()) {
                ioManager.printerr("Unknown commands. Print help for getting info about commands");
                resultcheck = false;
            } else {
                ioManager.printerr("Unknow command in file.");
                ioManager.turnOffFileMode();
                resultcheck = false;
            }
        }
        if (!"".equals(lastPartComm.trim())) {
            ioManager.printerr("Incorrect input.");
            resultcheck = false;
        }
        return resultcheck;
    }

    public void checkPrintResult(CommandResult result) {
        if (Objects.isNull(result)) {
            ioManager.println("Server disconnected.");
            isWorkState = false;
        } else {
            ioManager.println(result.getMessage());
        }
    }
}
