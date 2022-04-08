package lab.common.commands;

import java.time.format.DateTimeFormatter;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;

public class InfoCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        return new CommandResult("Initialization time: "
            + collection.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            + "\nNumber of Marines: " + collection.getSize()
            + "\nType" + SpaceMarine.class, true);
    }
}
