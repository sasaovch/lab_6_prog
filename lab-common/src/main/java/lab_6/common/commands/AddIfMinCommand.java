package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;


public class AddIfMinCommand extends Command {


    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        if (collection.addIfMin(spMar)) {
            return new CommandResult(spMar.getName() + "has been successfully added.", true);
        } else {
            return new CommandResult("Element is bigger than minimal.", false);
        }
    }
}