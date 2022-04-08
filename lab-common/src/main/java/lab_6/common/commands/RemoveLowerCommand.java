package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public class RemoveLowerCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        Integer specifiedHealth = spMar.getHealth();
        Integer specifiedHeart = spMar.getHeartCount();
        if (collection.removeIf(spaceMar -> 
            {
                if (spaceMar.getHealth() < specifiedHealth) {
                    return true;
                }
                return (spaceMar.getHealth() == specifiedHealth) && (spaceMar.getHeartCount() < specifiedHeart);
            })) {
            return new CommandResult("All items have been successfully deleted.", true);
        } else {
            return new CommandResult("No element has been deleted.", true);
        }
    }
}