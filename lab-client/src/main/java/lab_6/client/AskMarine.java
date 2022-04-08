package lab_6.client;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import lab_6.common.data.AstartesCategory;
import lab_6.common.data.Chapter;
import lab_6.common.data.Coordinates;
import lab_6.common.data.SpaceMarine;
import lab_6.common.exception.IncorrectData;
import lab_6.common.exception.IncorrectDataOfFileException;

public class AskMarine {
    private final IOManager ioManager;

    public AskMarine(IOManager ioManager) {
        this.ioManager = ioManager;
    }

    public String askName() throws IOException, IncorrectDataOfFileException {
        String name = asker(arg -> arg, 
                         arg -> ((String) arg).length() > 0,  
                         "Enter name (String)",
                         "The string must not be empty.", false,
                         ioManager.getFileMode());
        return name;
    }

    public Coordinates askCoordinates() throws IOException, IncorrectDataOfFileException, IncorrectData {
        double coordinateX = asker(Double::parseDouble, 
                                arg -> true, 
                                "Enter coordinates: X (double)", 
                                "Incorrect input. X must be double.", 
                                false,
                                ioManager.getFileMode());
        Long coordinateY = asker(Long::parseLong, 
                                arg -> true, 
                                "Enter coordinates: Y (Long)", 
                                "Incorrect input. Y must be Long and not null.", 
                                false,
                                ioManager.getFileMode());
        Coordinates cor = new Coordinates(coordinateX, coordinateY);
        return cor;
    }

    public Integer askHealth() throws IOException, IncorrectDataOfFileException{
        Integer health = asker(Integer::parseInt, 
                           arg -> ((Integer) arg) > 0, 
                           "Enter the level of health (Integer)",
                           "Health must be Integer, not null and greater than zero.", 
                           false,
                           ioManager.getFileMode());
        return health;
    }

    public Integer askHeartCount() throws IOException, IncorrectDataOfFileException{
        Integer heartCount = asker(Integer::parseInt, 
                               arg -> 1 <= ((Integer) arg) && arg <= 3, 
                               "Enter heart count: from 1 to 3 (Integer)",
                               "Heartcount must be form 1 to 3 (Integer)", 
                               false,
                               ioManager.getFileMode());
        return heartCount;
    }

    public Boolean askLoyal() throws IOException, IncorrectDataOfFileException {
        Boolean loyal = asker(arg -> {
                    if (!(arg.equals("false") || arg.equals("true") || arg.equals(""))) {
                        throw new NumberFormatException();
                    }
                    return Boolean.parseBoolean(arg);
                                    }, 
                              arg -> true, 
                              "Enter loyal: true, false or null - empty line.",
                              "Incorrect input - loyal is only true, false or null - empty line.",
                              true,
                              ioManager.getFileMode());
        return loyal;
    }

    public AstartesCategory askCategory() throws IOException, IncorrectDataOfFileException {
        AstartesCategory category = asker(arg -> AstartesCategory.valueOf(arg.toUpperCase()), 
                                 arg -> true, 
                                 "Enter category: " + AstartesCategory.listOfCategory(), 
                                 "The category is not in the list.", 
                                 false,
                                 ioManager.getFileMode());
        return category;
    }

    public Chapter askChapter() throws IOException, IncorrectDataOfFileException, IncorrectData {
        String name = asker(arg -> arg, arg -> true, "Enter name of chapter, empty line if chapter is null", 
                         "", true, ioManager.getFileMode());
            if (!Objects.equals(name, null)) {
                String parentLegion = asker(arg -> arg, arg -> true, "Enter parent Legion of chapter", 
                                        "", false, ioManager.getFileMode());
                Long marinesCount = asker(Long::parseLong, arg -> 0 < ((Long) arg) && ((Long) arg) <= 1000, 
                                     "Enter marines count of chapter: from 1 to 1000 (Integer)", 
                                     "Marines count must be Integer, not null and from 1 to 1000.", false,
                                     ioManager.getFileMode());
                String world = asker(x -> x, arg -> ((String) arg).length() > 0,  "Enter name (String)",
                              "The string must not be empty.", false,
                              ioManager.getFileMode());
                Chapter chapter = new Chapter(name, parentLegion, marinesCount, world);
                return chapter;
            } else {
                return null;
            }
    }

    public  SpaceMarine askMarine() throws IOException, IncorrectDataOfFileException, IncorrectData {
        String name = askName();
        Coordinates coordinates = askCoordinates();
        Integer health = askHealth();
        Integer heartCount = askHeartCount();
        Boolean loyal = askLoyal();
        AstartesCategory category = askCategory();
        Chapter chapter = askChapter();
        return new SpaceMarine(name, coordinates, LocalDateTime.now(), health, heartCount, loyal, category, chapter);
    }

    public <T> T asker(Function<String, T> function,
                       Predicate<T> predicate,  
                       String askField, 
                       String wrongValue, 
                       Boolean nullable,
                       Boolean fileMode) throws IOException, IncorrectDataOfFileException {
        String stringIn;
        T value;
        while (true) {
            ioManager.println(askField);
            ioManager.prompt();
            try {
                stringIn = ioManager.readLine().trim();
                if ("".equals(stringIn) && nullable) {
                    return null;
                }
                value = function.apply(stringIn);
            } catch (IllegalArgumentException e) {
                ioManager.printerr(wrongValue);
                if (fileMode) {
                    throw new IncorrectDataOfFileException();
                }
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                ioManager.printerr(wrongValue);
                if (fileMode) {
                    throw new IncorrectDataOfFileException();
                }
            }
        }
    }
}