package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class RemoveByIdCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        Long id = (Long) data;
        if (collection.removeIf(spaceMar -> spaceMar.getID().equals(id))) {
            return new CommandResult("remove_by_id", "Space Marine has been successfully deleted.", true);
        } else {
            return new CommandResult("remove_by_id", "Uknown Id.", false);
        }
    }
}
