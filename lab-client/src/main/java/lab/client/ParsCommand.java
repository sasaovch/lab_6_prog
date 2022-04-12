package lab.client;

import java.io.IOException;

import lab.common.data.SpaceMarine;
import lab.common.exception.IncorrectData;
import lab.common.exception.IncorrectDataOfFileException;
import lab.common.util.Message;

public class ParsCommand {
    private final IOManager ioManager;
    private final AskMarine asker;

    public ParsCommand(IOManager ioMan, AskMarine askr) {
        ioManager = ioMan;
        asker = askr;
    }

    public Message addComm(String value) {
        SpaceMarine spMar = asker.askMarine();
        Message message = new Message("add", null, spMar);
        return message;
    }

    public Message updateComm(String value) {
        Long id;
                try {
                    id = Long.parseLong(value);
                } catch (NumberFormatException e) {
                    ioManager.printerr("Incorrect data");
                    return null;
                }
                SpaceMarine spMar = asker.askMarine();
                Message message = new Message("update", id, spMar);
                return message;
    }

    public Message removeByIdComm(String value) {
        Long id;
        try {
            id = Long.parseLong(value);
        } catch (NumberFormatException e) {
            ioManager.printerr("Incorrect data");
            return null;
        }
        Message message = new Message("remove_by_id", id, null);
        return message;
    }

    public Message addIfMinComm(String value) {
        SpaceMarine spMar = asker.askMarine();
        Message message = new Message("add_if_min", null, spMar);
        return message;
    }

    public Message removeGreaterComm(String value) {
        try {
            Integer health = asker.askHealth();
            Integer heart = asker.askHeartCount();
            SpaceMarine spMar = new SpaceMarine();
            spMar.setHealth(health);
            spMar.setHeartCount(heart);
            Message message = new Message("remove_greater", null, spMar);
            return message;
        } catch (IOException | IncorrectDataOfFileException | IncorrectData e) {
            return null;
        }
    }

    public Message removeLowerComm(String value) {
        try {
            Integer health = asker.askHealth();
            Integer heart = asker.askHeartCount();
            SpaceMarine spMar = new SpaceMarine();
            spMar.setHealth(health);
            spMar.setHeartCount(heart);
            Message message = new Message("remove_lower", null, spMar);
            return message;
        } catch (IOException | IncorrectDataOfFileException | IncorrectData e) {
            return null;
        }
    }

    public Message countByLoyalComm(String value) {
        Boolean loyal;
        if ("".equals(value)) {
            loyal = null;
        } else if (!("true".equals(value) || "false".equals(value))) {
            ioManager.printerr("The value of Loyal isn't correct (true, false, null - empty line)");
            return null;
        } else {
            loyal = Boolean.parseBoolean(value);
        }
        Message message = new Message("count_by_loyal", loyal, null);
        return message;
    }

    public Message executeScriptComm(String value) {
        ioManager.turnOnFileMode(value);
        return new Message("execute", null, null);
    }

    public Message helpComm(String value) {
        return new Message("help", null, null);
    }

    public Message infoComm(String value) {
        return new Message("info", null, null);
    }

    public Message showComm(String value) {
        return new Message("show", null, null);
    }

    public Message clearComm(String value) {
        return new Message("clear", null, null);
    }

    public Message exitComm(String value) {
        return new Message("exit", null, null);
    }

    public Message groupCountingByNameComm(String value) {
        return new Message("group_counting_by_name", null, null);
    }

    public Message printDescendingComm(String value) {
        return new Message("print_descending", null, null);
    }
}
