package it.polimi.group03.domain;

/**
 * This class represents a bead in the matrix (board),
 * when a bead is added to a player, it's status is <b>active</b>
 * by default. A bead can not change it's position, in any case if
 * a player lose his bead it will be set it as <b>inactive</b>.
 *
 *
 * @see Position
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class Bead {

    /**
     * Position of the bead in the matrix (board).
     */
    private Position position;
    /**
     * Indicates whether if a bead is present in the matrix (board) or not.
     */
    private boolean active;

    public Bead(int x, int y) {
        this.active = true;
        this.position = new Position(x,y);
    }

    public Position getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
