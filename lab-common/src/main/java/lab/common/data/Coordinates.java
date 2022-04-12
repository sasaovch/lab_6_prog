package lab.common.data;

import java.io.Serializable;
import java.util.Objects;

import lab.common.exception.IncorrectData;


/**
 * X-Y coordinates of Space Marine.
 */
public class Coordinates implements Serializable, Comparable<Coordinates> {
    private double x;
    private Long y; //Поле не может быть null

    public Coordinates(double x, Long y) throws IncorrectData {
        setX(x);
        setY(y);
    }

    public Coordinates() {
    }

    /**
     * @return Y-coordinate.
     */
    public Long getY() {
        return y;
    }

    /**
     * @return X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Set Y-coordinate.
     * @param y Y-coordinate.
     * @throws IncorrectData
     */
    public void setY(Long y) throws IncorrectData {
        if (y == null) {
            throw new IncorrectData();
        }
        this.y = y;
    }

    /**
     * Set Y-coordinate.
     * @param x X-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Coordinates: X - " + x + " | Y - " + y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Coordinates compCoor = (Coordinates) object;
        return (Objects.equals(x, compCoor.getX())) && (Objects.equals(y, compCoor.getY()));
    }

    @Override
    public int compareTo(Coordinates o) {
        if (x == o.getX()) {
            return Math.toIntExact(y - o.getY());
        } else {
            double res = x - o.getX();
            int i = (int) res;
            return i;
        }
    }
}
