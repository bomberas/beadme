package it.polimi.group03.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.polimi.group03.domain.Game;
import it.polimi.group03.domain.Bar;

/**
 * This class contains the allowed values for the position of a particular bar: INNER, CENTRAL or OUTER,
 * each position has an initial index that will be use to obtain the keys of the bar, and it is the initial point
 * from where to start obtaining the keys of the slots to be present on the board.
 *
 * <p>Is used when the bars are configured at the beginning of the game and after a move.
 *
 *
 * @see Game
 * @see Bar
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public enum BarPosition {
    INNER(0), CENTRAL(1), OUTER(2);

    private int initialSlot;

    BarPosition(int initialSlot) {
        this.initialSlot = initialSlot;
    }

    public int getInitialSlot() {
        return this.initialSlot;
    }

    public static BarPosition randomPosition()  {
        List<BarPosition> values = Collections.unmodifiableList(Arrays.asList(values()));

        return values.get(new Random().nextInt(values.size()));
    }

}
