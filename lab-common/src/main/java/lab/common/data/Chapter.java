package lab.common.data;

import java.io.Serializable;
import java.util.Objects;

import lab.common.exception.IncorrectData;


/**
 * Chapter with Marines.
 */
public class Chapter implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private long marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле не может быть null
    private final Integer maxMarinesCount = 1000;

    public Chapter(String name, String parentLegion, long marinesCount, String world) throws IncorrectData {
        this.setName(name);
        this.setParentLegion(parentLegion);
        this.setMarinesCount(marinesCount);
        this.setWorld(world);
    }

    public Chapter() {
    }

    /**
     * Set name of Chapter.
     * @param name A name of Chapter.
     * @throws IncorrectData
     */
    public void setName(String name) throws IncorrectData {
        if ((name == null) || (name.trim().equals(""))) {
            throw new IncorrectData();
        }
        this.name = name;
    }

    /**
     * Set Marines Count of Chapter.
     * @param marinesCount Marines Count of Chapter.
     * @throws IncorrectData
     */
    public void setMarinesCount(long marinesCount) throws IncorrectData {
        if (!((1 <= marinesCount) && (marinesCount <= maxMarinesCount))) {
            throw new IncorrectData();
        }
        this.marinesCount = marinesCount;
    }

    /**
     * Set Parent Legion of Chapter.
     * @param parentLegion Parent Legion of Chapter.
     */
    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    /**
     * Set World of Chapter.
     * @param world World of Chapter.
     * @throws IncorrectData
     */
    public void setWorld(String world) throws IncorrectData {
        if (world == null) {
            throw new IncorrectData();
        }
        this.world = world;
    }

    /**
     * @return World of Chapter.
     */
    public String getWorld() {
        return world;
    }

    /**
     * @return Marines Count of Chapter.
     */
    public long getMarinesCount() {
        return marinesCount;
    }

    /**
     * @return Parent Legion of Chapter.
     */
    public String getParentLegion() {
        return parentLegion;
    }

    /**
     * @return Name of Chapter.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "   Name: " + name + "\n   ParentLegion: " + parentLegion + "\n   MarinesCount: " + marinesCount
                + "\n   World: " + world;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, parentLegion, marinesCount, world);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Chapter compChap = (Chapter) object;
        return (Objects.equals(name, compChap.getName())) && (Objects.equals(parentLegion, compChap.getParentLegion()))
            && (Objects.equals(marinesCount, compChap.getMarinesCount())) && (Objects.equals(world, compChap.getWorld()));
    }
}
