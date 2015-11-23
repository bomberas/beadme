package it.polimi.group03.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class
 *
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */
// TODO: 22/11/2015 Tati escribe la doc de la clase

public enum BarPosition {
    INNER(0, 'i'), CENTRAL(1,'c'), OUTER(2,'o');

    private int initialSlot;
    private char shortcut;

    BarPosition(int initialSlot, char shortcut) {
        this.initialSlot = initialSlot;
        this.shortcut = shortcut;
    }

    public int getInitialSlot() {
        return this.initialSlot;
    }

    public char getShortcut() {
        return this.shortcut;
    }

    public static BarPosition randomPosition()  {
        List<BarPosition> values = Collections.unmodifiableList(Arrays.asList(values()));

        return values.get(new Random().nextInt(values.size()));
    }

}
