package lab.common.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import lab.common.data.SpaceMarine;


public interface CollectionManager {
    boolean addElement(SpaceMarine spMar);
    boolean addIfMin(SpaceMarine spMar);
    void clearCollection();
    <T> int countBySomeThing(Function<SpaceMarine, T> getter, T value);
    int getSize();
    <R> Map<R, List<SpaceMarine>> groupByField(Function<SpaceMarine, R> funct);
    LocalDateTime getTime();
    ArrayList<SpaceMarine> sortCollection();
    boolean removeIf(Predicate<SpaceMarine> predicate);
    HashSet<SpaceMarine> getCollection();
    SpaceMarine findByID(Long id);
    boolean updateSpaceMarine(SpaceMarine changeSpaceMarine, SpaceMarine oldSpaceMarine);
    List<SpaceMarine> sortByCoordinates();
}
