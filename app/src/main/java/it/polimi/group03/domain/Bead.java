package it.polimi.group03.domain;

/**
 * This class represents a bead in the matrix (board),
 * when a bead is added to a player, its status is set to <b>active</b>
 * by default. A bead cannot be moved from one position to another, in the eventual
 * case a player loses his beads these will be set as <b>inactive</b>.
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
     * Indicates whether if a bead is present <tt>[active]</tt> on the board or not <tt>[inactive]</tt>.
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
