package components.board;

import java.util.Objects;

public class Coordinate {
    private final int q;
    private final int r;

    // Constructor
    public Coordinate(int q, int r) {
        this.q = q;
        this.r = r;
    }

    // Getters
    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    // Override equals and hashCode methods to ensure proper functioning in a HashMap
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return q == that.q && r == that.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }
}
