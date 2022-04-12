package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class ClearCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        collection.clearCollection();
        return new CommandResult("The collection is cleared.", true);
    }
}
