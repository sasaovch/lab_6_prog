package lab.common.commands;

import java.io.Serializable;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class ShowCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        if (collection.getSize() == 0) {
            return new CommandResult("The collection is empty.", true);
        }
        return new CommandResult((Serializable) collection.sortByCoordinates(), true);
    }
}
