package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;

public class CountByLoyalCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        Integer count = collection.countBySomeThing(SpaceMarine::getLoyal, (Boolean) data);
        return new CommandResult(count, true);
    }
}
