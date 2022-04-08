package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;


public class AddCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        collection.addElement(spMar);
        return new CommandResult(spMar.getName() + " has been successfuly added.", true);
    }
}
