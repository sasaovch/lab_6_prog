package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;

public class RemoveByIdCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        Long id = (Long) data;
        if (collection.removeIf(spaceMar -> spaceMar.getID().equals(id))) {
            return new CommandResult("Space Marine has been successfully deleted.", true);
        } else {
            return new CommandResult("Uknown Id.", false);
        }
    }
}
