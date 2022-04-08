package lab.client;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

import lab.common.commands.CommandResult;
import lab.common.data.SpaceMarine;
import lab.common.exception.IncorrectData;
import lab.common.exception.IncorrectDataOfFileException;
import lab.common.util.Message;


public class Console {
    private final HashSet<String> commands;
    private final IOManager ioManager;
    private final AskMarine asker;
    private final ServerWork server;

    public Console (HashSet<String> list, IOManager ioManager, AskMarine asker, ServerWork server) {
        commands = list;
        this.ioManager = ioManager;
        this.asker = asker;
        this.server = server;
    }

    public void run() throws IOException, IncorrectDataOfFileException, IncorrectData, ClassNotFoundException, InterruptedException {
        String filePath = System.getenv("filePath");
        if (Objects.equals(filePath, null)){
            ioManager.printerr("There is no such variable \"filePath\"");
            return;
        }
        server.sendMessage(new Message(filePath, null, null));
        CommandResult res = server.reciveMessage();
        if (Objects.isNull(res)) {
            ioManager.printerr("Server has disconected");
            return;
        }
        ioManager.println(res.getMessage());
        String[] command = new String[3];
        String name, line, value;
        line = "";
        Message mess;
        while (true) {
            if (!ioManager.getFileMode()) ioManager.prompt();
            line = ioManager.readLine();
            if (Objects.equals(line, null) && !ioManager.getFileMode()) {
                continue;
            } else if (Objects.equals(line, null)) {
                ioManager.turnOffFileMode();
                continue;
            } else if ("exit".equals(line)) {
                break;
            }
            command = (line.trim() + " " + " ").split(" ", 3);
            name = command[0];
            value = command[1];
            if (name.equals("") && !ioManager.getFileMode()) {
                continue;
            } else if (name.equals("")) {
                ioManager.turnOffFileMode();
                continue;
            }
            if (!commands.contains(name)) {
                if (!ioManager.getFileMode()) {
                    ioManager.printerr("Unknown commands. Print help for getting info about commands");
                    continue;
                } else {
                    ioManager.printerr("Unknow command in file.");
                    ioManager.turnOffFileMode();
                    continue;
                }
            }
            if (!command[2].trim().equals("")) {
                    ioManager.printerr("Incorrect input.");
                    continue;
            }
            try {
                switch (name) {
                    case "add" : 
                        SpaceMarine spMar = asker.askMarine();
                        mess = new Message(name, null, spMar);
                        server.sendMessage(mess);
                        break;
                    case "update" :
                        Long id;
                        try {
                            id = Long.parseLong(value);
                        } catch (NumberFormatException e) {
                            ioManager.printerr("Incorrect data");
                            continue;
                        }
                        spMar = asker.askMarine();
                        mess = new Message(name, id, spMar);
                        server.sendMessage(mess);
                        break;
                    case "remove_by_id" :
                        try {
                            id = Long.parseLong(value);
                        } catch (NumberFormatException e) {
                            ioManager.printerr("Incorrect data");
                            continue;
                        }
                        mess = new Message(name, id, null);
                        server.sendMessage(mess);
                        break;
                    case "add_if_min" : 
                        spMar = asker.askMarine();
                        mess = new Message(name, null, spMar);
                        server.sendMessage(mess);
                        break;
                    case "remove_greater" :
                        Integer health = asker.askHealth();
                        Integer heart = asker.askHeartCount();
                        spMar = new SpaceMarine();
                        spMar.setHealth(health);
                        spMar.setHeartCount(heart);
                        mess = new Message(name, null, spMar);
                        server.sendMessage(mess);
                        break;
                    case "remove_lower" :
                        health = asker.askHealth();
                        heart = asker.askHeartCount();
                        spMar = new SpaceMarine();
                        spMar.setHealth(health);
                        spMar.setHeartCount(heart);
                        mess = new Message(name, null, spMar);
                        server.sendMessage(mess);
                        break;
                    case "count_by_loyal" :
                        Boolean loyal;
                        if (value.equals("")) {
                            loyal = null;
                        } else if (!(value.equals("true") || value.equals("false"))) {
                            ioManager.printerr("The value of Loyal isn't correct (true, false, null - empty line)");
                            continue;
                        } else {
                            loyal = Boolean.parseBoolean(value);
                        }
                        mess = new Message(name, loyal, null);
                        server.sendMessage(mess);
                        break;
                    case "execute_script" :
                        ioManager.turnOnFileMode(value);
                        continue;
                    default :
                        mess = new Message(name, null, null);
                        server.sendMessage(mess);
                    }
                    res = server.reciveMessage();
                    if (Objects.isNull(res)) {
                        ioManager.println("Server disconnected.");
                        break;
                    }
                    ioManager.println(res.getMessage());
            } catch (IncorrectDataOfFileException e) {
                ioManager.printerr("Incorrect data in file.");
            }
        }
        ioManager.println("Good Buy!\n\\(?_?)/");
    }
}
