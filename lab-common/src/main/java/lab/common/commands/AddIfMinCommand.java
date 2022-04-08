package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;


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