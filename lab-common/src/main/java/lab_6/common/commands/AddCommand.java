package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;



public class AddCommand extends Command {


    public AddCommand() {
    }

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        collection.addElement(spMar);
        return new CommandResult(spMar.getName() + " has been successfuly added.", true);
    }
}