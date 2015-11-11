package it.polimi.group03.domain;
import java.util.ArrayList;

/**
 * Created by tatibloom on 11/11/2015.
 */

public class Bead {

    private String color;
    private Position position;
    private boolean isActive;

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
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
