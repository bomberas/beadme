package it.polimi.group03.domain;

/**
 * @author tatibloom
 * Created by tatibloom on 11/11/2015.
 */

public class Bead {

    private Position position;
    private boolean isActive;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
