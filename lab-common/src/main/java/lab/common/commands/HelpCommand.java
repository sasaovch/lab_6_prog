package lab.common.commands;

import lab.common.data.SpaceMarine;
import lab.common.util.CollectionManager;


public class HelpCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, CollectionManager collection) {
        return new CommandResult("help : print info about all commands\n"
        + "info : print info about collection: type, initialization date, number of elements\n"
        + "show : print all elements of collection\n"
        + "add {element} : add new element in collection\n"
        + "add_if_min {element} : add element if its value is less than minimal value in collection (value is health)\n"
        + "update id {element} : update element info by it's id\n"
        + "remove_by_id id : delete element by it's id\n"
        + "clear : clear the collection\n"
        + "count_by_loyal loyal : print the number of elements whose value of the loyal field is equal to the specified\n"
        + "execute_script file_name : execute script\n"
        + "exit : finish work of client\n"
        + "group_counting_by_name : groups the elements of the collection by the value of the name field\n"
        + "remove_greater {element} : remove all items from collection that exceed the specified\n"
        + "remove_lower {element} : remove all items smaller than the specified one from the collection\n"
        + "print_descending : print all the elements of the collection in descending order", true);
    }
}
