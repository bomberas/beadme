package it.polimi.group03.domain;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;

/**
 * Created by tatibloom on 08/11/2015.
 */
public class Bar {

    private int id;
    private BarPosition position;
    private BarOrientation orientation;
    private int[] keys = new int[9];

    public Bar(int id, BarOrientation orientation, int[] keys) {
        this.id = id;
        this.position = BarPosition.randomPosition();
        this.orientation = orientation;
        this.keys = keys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Position: Inner, Outer, Central
     **/
    public BarPosition getPosition() {
        return position;
    }

    public void setPosition(BarPosition position) {
        this.position = position;
    }

    /**
     * Orientation: V for vertical and H for Horizontal
     **/
    public BarOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(BarOrientation orientation) {
        this.orientation = orientation;
    }

    /**
     * keys: Fixed configuration for the seven slots that
     * a Bar has: 0 for empty and 1 for covered
     **/
    public int[] getKeys() {
        return keys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

}