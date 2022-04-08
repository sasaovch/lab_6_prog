package lab_6.common.commands;

import java.util.HashMap;
import java.util.Map;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public class GroupCountingByNameCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
            if (collection.getSize() == 0) {
                return new CommandResult("The collection is empty.", true);
            }
            HashMap<String, Integer> outHashMap = collection.groupCountingByField(SpaceMarine::getName);
            String mess = "";
            for (Map.Entry<String, Integer> entry : outHashMap.entrySet()) {
                mess += "Name: " + entry.getKey() + ". Number of elements: " + entry.getValue() + "\n";
            }
            return new CommandResult(mess, true);
    }
}