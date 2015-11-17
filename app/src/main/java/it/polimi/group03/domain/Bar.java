package it.polimi.group03.domain;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.SlotInfo;

import static it.polimi.group03.util.Constant.*;

/**
 * @author tatibloom
 * Created by tatibloom on 08/11/2015.
 */
public class Bar {

    private int id;
    private BarPosition position;
    private BarOrientation orientation;
    private SlotInfo[] keys = new SlotInfo[BAR_SLOTS];

    public Bar(int id, BarOrientation orientation, SlotInfo[] keys) {
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
     * Orientation: Horizontal or Vertical
     **/
    public BarOrientation getOrientation() {
        return orientation;
    }

    /**
     * Keys: Red or Blue when it's covered nad Black when it's not.
     */
    public SlotInfo[] getKeys() {
        return keys;
    }

}