package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public class ClearCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        collection.clearCollection();
        return new CommandResult("The collection is cleared.", true);
    }
}