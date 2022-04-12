package lab.common.commands;

import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class InfoCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        TreeMap<String, Object> outMap = new TreeMap<>();
        outMap.put("Initialization time", collection.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        outMap.put("Number of Marines", collection.getSize());
        outMap.put("Type", SpaceMarine.class);
        return new CommandResult("info", outMap, true);
    }
}
