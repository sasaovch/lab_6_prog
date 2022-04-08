package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;

public class ClearCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        collection.clearCollection();
        return new CommandResult("The collection is cleared.", true);
    }
}
