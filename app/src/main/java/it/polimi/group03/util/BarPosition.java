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
