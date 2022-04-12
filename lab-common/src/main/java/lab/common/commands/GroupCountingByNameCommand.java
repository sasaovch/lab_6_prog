package lab.common.commands;

import java.util.List;
import java.util.TreeMap;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class GroupCountingByNameCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
            if (collection.getSize() == 0) {
                return new CommandResult("group_counting_by_name", "The collection is empty.", true);
            }
            TreeMap<String, List<SpaceMarine>> outMap = new TreeMap<>(collection.groupByField(SpaceMarine::getName));
            return new CommandResult("group_counting_by_name", outMap, true);
    }
}
