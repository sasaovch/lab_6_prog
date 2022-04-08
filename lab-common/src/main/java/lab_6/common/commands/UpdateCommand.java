package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public class UpdateCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        Long id = (Long) data;
        if (collection.getSize() == 0) {
            return new CommandResult("There are no such element in the collection.", false);
        }
        SpaceMarine changeMarine = collection.findByID(id);
        if (changeMarine == null) {
            return new CommandResult("Id is not correct.", false);
        }
        collection.updateSpaceMarine(changeMarine, spMar);
        return new CommandResult("Marine has been successfully updated.", true);
    }
}