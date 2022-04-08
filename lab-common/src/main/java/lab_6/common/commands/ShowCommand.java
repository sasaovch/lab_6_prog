package lab_6.common.commands;

import java.util.Comparator;
import java.util.TreeSet;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;

public class ShowCommand extends Command {

    @Override
    public CommandResult run(Object data, SpaceMarine spMar, SpaceMarineCollection collection) {
        if (collection.getSize() == 0) {
            return new CommandResult("The collection is empty.", true);
        }
        String mess = "";
        Comparator<SpaceMarine> comparator = new Comparator<SpaceMarine>() {

            @Override
            public int compare(SpaceMarine o1, SpaceMarine o2) {
                if (o1.getCoordinates().getX() == o2.getCoordinates().getX()) {
                    int i = Math.toIntExact(o1.getCoordinates().getY() - o2.getCoordinates().getY());
                    return i;
                } else {
                    double res = o1.getCoordinates().getX() - o2.getCoordinates().getX();
                    int i = (int) res ;
                    return i;
                }
            }
            
        };
        TreeSet<SpaceMarine> set = new TreeSet<>(comparator);
        set.addAll(collection.getCollection());
        for (SpaceMarine spaceMarine : set) {
            mess += spaceMarine;
        }
        return new CommandResult(mess, true);
    }
}