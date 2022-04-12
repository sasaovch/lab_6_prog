package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class RemoveGreaterCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        if (collection.removeIf(spaceMar -> {
            return (spaceMar.compareTo(spMar) > 0);
            })) {
            return new CommandResult("remove_greater", "All items have been successfully deleted.", true);
        } else {
            return new CommandResult("remove_greater", "No element has been deleted.", true);
        }
    }
}
