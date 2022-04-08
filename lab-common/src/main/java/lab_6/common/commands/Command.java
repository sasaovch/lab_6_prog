package lab_6.common.commands;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public abstract class Command {
    abstract public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection);
}
