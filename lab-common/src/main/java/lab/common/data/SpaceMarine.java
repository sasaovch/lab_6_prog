package lab.common.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import lab.common.exception.IncorrectData;


/**
 * Main character. Is stored in the collection by health.
 */
public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDateTime; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer health; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer heartCount; //Поле не может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 3
    private Boolean loyal; //Поле может быть null
    private AstartesCategory category; //Поле не может быть null
    private Chapter chapter; //Поле может быть null
    private final Integer maxHeart = 3;
    private final Integer minHeart = 1;

    public SpaceMarine(String name, Coordinates coordinates, Integer health, Integer heartCount, Boolean loyal, AstartesCategory category, Chapter chapter) throws IncorrectData {
        setName(name);
        setCoordinates(coordinates);
        setHealth(health);
        setHeartCount(heartCount);
        setLoyal(loyal);
        setCategory(category);
        setChapter(chapter);
    }

    public SpaceMarine() {
        id = 1L;
    }

    /**
     * @return Category of the Marine.
     */
    public AstartesCategory getCategory() {
        return category;
    }

    /**
     * @return Chapter of the Marine.
     */
    public Chapter getChapter() {
        return chapter;
    }

    /**
     * @return Coordinates of the Marine.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return creation time of the Marine.
     */
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * @return Heart Count of the Marine.
     */
    public Integer getHeartCount() {
        return heartCount;
    }

    /**
     * @return Name of the Marine.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Health of the Marine.
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return ID of the Marine.
     */
    public Long getID() {
        return id;
    }

    /**
     * @return Loyal of the Marine.
     */
    public Boolean getLoyal() {
        return loyal;
    }

    /**
     * Set name of Marine.
     * @param name A name of Marine.
     * @throws IncorrectData
     */
    public void setName(String name) throws IncorrectData {
        if ((name == null) || (name.trim().equals(""))) {
            throw new IncorrectData();
        }
        this.name = name;
    }

    /**
     * Set id of Marine.
     * @param id ID of Marine.
     * @throws IncorrectData
     */
    public void setID(Long setId) throws IncorrectData {
        if ((setId == null) || (setId <= 0)) {
            throw new IncorrectData();
        }
        this.id = setId;
    }

    /**
     * Set Coordinates of Marine.
     * @param cor Coordinates of Marine.
     * @throws IncorrectData
     */
    public void setCoordinates(Coordinates cor) throws IncorrectData {
        if (cor == null) {
            throw new IncorrectData();
        }
        coordinates = cor;
    }

    /**
     * Set creation time of Marine.
     * @param time Time of Marine.
     * @throws IncorrectData
     */
    public void setTime(LocalDateTime time) throws IncorrectData {
        if (time == null) {
            throw new IncorrectData();
        }
        creationDateTime = time;
    }

    /**
     * Set Health of Marine.
     * @param health Health of Marine.
     * @throws IncorrectData
     */
    public void setHealth(Integer health) throws IncorrectData {
        if ((health == null) || (health <= 0)) {
            throw new IncorrectData();
        }
        this.health = health;
    }

    /**
     * Set Heart Count of Marine.
     * @param heartCount Heart Count of Marine.
     * @throws IncorrectData
     */
    public void setHeartCount(Integer heartCount) throws IncorrectData {
        if (!((heartCount <= maxHeart) && (minHeart <= heartCount))) {
            throw new IncorrectData();
        }
        this.heartCount = heartCount;
    }

    /**
     * Set loyal of Marine.
     * @param loyal Loyal of Marine.
     */
    public void setLoyal(Boolean loyal) {
        this.loyal = loyal;
    }

    /**
     * Set Category of Marine.
     * @param cat Category of Marine.
     * @throws IncorrectData
     */
    public void setCategory(AstartesCategory cat) throws IncorrectData {
        if (cat == null) {
            throw new IncorrectData();
        }
        category = cat;
    }

    /**
     * Set Chapter of Marine.
     * @param chapter Chapter of Marine.
     */
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /**
     * Generate ID of Marine.
     * @return ID of Marine.
     */
    // public static Long generateNextId() {
    //     count += 1;
    //     return count;
    // }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, health, heartCount, loyal, category, chapter);
    }

    @Override
    public String toString() {
        return "------------" + "\nName: " + name + "\nId: " + id + "\nHealth: " + health + "\nHeartCount: "
                + heartCount + "\nLoyal: " + loyal + "\nInitialization time: "
                + creationDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + "\n" + "Chapter:\n" + chapter + "\n" + coordinates + "\nCategory: " + category + "\n";
    }

    @Override
    public int compareTo(SpaceMarine o) {
        int diffHealth = health - o.getHealth();
        if (diffHealth == 0) {
            int diffHearC = heartCount - o.getHeartCount();
            if (diffHearC == 0) {
                return name.length() - o.getName().length();
            }
            return diffHearC;
        }
        return diffHealth;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SpaceMarine compSpMar = (SpaceMarine) object;
        return (name.equals(compSpMar.getName())) && (coordinates.equals(compSpMar.getCoordinates()))
                && (health.equals(compSpMar.getHealth())) && (heartCount.equals(compSpMar.getHeartCount()))
                && (Objects.equals(loyal, compSpMar.getLoyal())) && (category.equals(compSpMar.getCategory()))
                && (Objects.equals(chapter, compSpMar.getChapter()));
    }
}
