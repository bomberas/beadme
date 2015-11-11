package it.polimi.group03.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by tatibloom on 11/11/2015.
 */
public enum BarPosition {
    INNER, OUTER, CENTRAL;

    public static BarPosition randomPosition()  {
        List<BarPosition> values = Collections.unmodifiableList(Arrays.asList(values()));

        return values.get(new Random().nextInt(values.size()));
    }

}
