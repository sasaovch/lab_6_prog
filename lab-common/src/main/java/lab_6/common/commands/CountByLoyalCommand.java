package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public class CountByLoyalCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        return new CommandResult("Number of elements: " + collection.countBySomeThing(SpaceMarine::getLoyal, (Boolean) data), true);
    }
}