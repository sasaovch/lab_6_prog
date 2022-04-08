package lab.common.commands;

import java.util.List;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;

public class PrintDescendingCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        if (collection.getSize() == 0) {
            return new CommandResult("The collection is empty.", true);
        }
        List<SpaceMarine> list = collection.sortCollection();
        String mess = "";
        for (int i = list.size() - 1; i >= 0; i--) {
            mess += list.get(i);
        }
        return new CommandResult(mess, true);
    }
}
