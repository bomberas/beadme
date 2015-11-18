package it.polimi.group03.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author tatibloom
 * Created by tatibloom on 11/11/2015.
 */
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
