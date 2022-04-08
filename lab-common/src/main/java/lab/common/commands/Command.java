package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;


public abstract class Command {
    public abstract CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection);
}
