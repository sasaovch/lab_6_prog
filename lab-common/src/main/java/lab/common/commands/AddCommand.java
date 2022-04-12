package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;


public class AddCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        if (collection.addElement(spMar)) {
            return new CommandResult("add", spMar.getName() + " has been successfuly added.", true);
        } else {
            return new CommandResult("add", spMar.getName() + " hasn't been added.", false);
        }
    }
}
