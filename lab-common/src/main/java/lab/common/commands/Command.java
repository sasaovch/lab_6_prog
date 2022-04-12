package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;


public abstract class Command {
    public abstract CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection);
}
