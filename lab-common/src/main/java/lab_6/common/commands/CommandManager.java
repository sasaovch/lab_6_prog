package lab_6.common.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, Command> commandsMap;

    public CommandManager() {
        commandsMap = new HashMap<String, Command>();
    }

    public void addCommand(String key, Command com) {
        commandsMap.put(key, com);
    }

    public Map<String, Command> getMap() {
        return commandsMap;
    }
}
