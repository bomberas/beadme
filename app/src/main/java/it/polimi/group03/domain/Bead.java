package it.polimi.group03.domain;

/**
 * This class represents a bead in the matrix (board),
 * when a bead is added to a player, its status is set to <b>active</b>
 * by default. A bead cannot be moved from one position to another, in the eventual
 * case a player loses his beads these will be set as <b>inactive</b>.
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
     * Indicates whether a bead is present <tt>[active]</tt> on the board or not <tt>[inactive]</tt>.
     */
    private boolean active;

    /**
     * Indicates whether a bead is placed on the board or not.
     */
    private  boolean placed;

    public Bead(){
        this.position = new Position(-1,-1);
        this.active = true;
    }

    public Bead(int x, int y) {
        this.active = true;
        this.placed = false;
        this.position = new Position(x,y);
    }

    public void setPosition(int x, int y){
        this.position.setX(x);
        this.position.setY(y);
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

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
