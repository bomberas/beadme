package it.polimi.group03.domain;

/**
 * @author tatibloom
 * Created by tatibloom on 11/11/2015.
 */

public class Bead {

    private String color;
    private Position position;
    private boolean active;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
