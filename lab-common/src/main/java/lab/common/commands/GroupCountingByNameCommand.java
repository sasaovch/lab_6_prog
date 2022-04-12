package lab.common.commands;

import java.util.TreeMap;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class GroupCountingByNameCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
            if (collection.getSize() == 0) {
                return new CommandResult("The collection is empty.", true);
            }
            TreeMap<String, Integer> outMap = new TreeMap<>(collection.groupCountingByField(SpaceMarine::getName));
            return new CommandResult(outMap, true);
    }
}
