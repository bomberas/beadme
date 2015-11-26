package it.polimi.group03.domain;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.SlotInfo;

import static it.polimi.group03.util.Constant.*;

/**
 * This class holds the configuration for all the bars present on the board, based on
 * the IDs and Orientations (unique key).
 *
 * <p>It's used when a game has started and when a move is made. The initial position of
 * each bar is set randomly.
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

    /**
     * This is the default constructor, meant for the regular game (setting the initial position randomly).
     *
     * @param id from 0 to 6
     * @param orientation Vertical Horizontal
     * @param keys Array containing the values for the slots.
     */
    public Bar(int id, BarOrientation orientation, SlotInfo[] keys) {
        this.id = id;
        this.position = BarPosition.randomPosition();
        this.orientation = orientation;
        this.keys = keys;
    }

    /**
     * This constructor will set a fixed position. Used for performing the moves (on tests).
     *
     * @param id from 0 to 6
     * @param orientation Vertical - Horizontal
     * @param position INNER - OUTER - CENTRAL
     */
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