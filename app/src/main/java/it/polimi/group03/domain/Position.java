package it.polimi.group03.domain;

/**
 * This class holds the position of a bead in the matrix (board),
 * using coordinates X (from 0 to 6) and Y (from 0 to 6).
 *
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class Position {

    /**
     * Coordinate X.
     */
    private int x;
    /**
     * Coordinate Y.
     */
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
