package lab.common.util;

import java.io.Serializable;

import lab.common.data.SpaceMarine;

public class Message implements Serializable {
    private final String command;
    private final Object data;
    private final SpaceMarine spMar;

    public Message(String command, Object data, SpaceMarine spMar) {
        this.command = command;
        this.data = data;
        this.spMar = spMar;
    }

    public String getCommand() {
        return command;
    }

    public Object getData() {
        return data;
    }

    public SpaceMarine getSpacMar() {
        return spMar;
    }
}
