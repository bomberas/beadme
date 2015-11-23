package it.polimi.group03.domain;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.SlotInfo;

import static it.polimi.group03.util.Constant.*;

/**
 * This class will hold the configuration for all bars on the board, based on
 * the IDs and Orientations (unique key).
 *
 * <p>It's used when a game has started, (the initial configuration for the positions is
 * random) and when a move is made.</p>
 *
 * @see BarPosition
 * @see BarOrientation
 * @see BarPosition
 * @see SlotInfo
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 08/11/2015.
 */

public class Bar {

    /**
     * ID of the bar: from 0 to 6.
     */
    private int id;
    /**
     * Position of the bar: HORIZONTAL or VERTICAL.
     */
    private BarPosition position;
    /**
     * Orientation of the bar: INNER, OUTER or CENTRAL.
     */
    private BarOrientation orientation;
    /**
     * Slots configuration: RED or BLACK for HORIZONTAL bars.
     * BLUE or BLACK for VERTICAL bars.
     */
    private SlotInfo[] keys = new SlotInfo[BAR_SLOTS];

    public Bar(int id, BarOrientation orientation, SlotInfo[] keys) {
        this.id = id;
        this.position = BarPosition.randomPosition();
        this.orientation = orientation;
        this.keys = keys;
    }

    public Bar(int id, BarOrientation orientation, BarPosition position) {
        this.id = id;
        this.position = position;
        this.orientation = orientation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BarPosition getPosition() {
        return position;
    }

    public void setPosition(BarPosition position) {
        this.position = position;
    }

    public BarOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(BarOrientation orientation) {
        this.orientation = orientation;
    }

    public SlotInfo[] getKeys() {
        return keys;
    }

}