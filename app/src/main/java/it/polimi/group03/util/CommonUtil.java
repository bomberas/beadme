package it.polimi.group03.util;

import java.util.Collection;

/**
 * Created by tatibloom on 11/11/2015.
 */
public class CommonUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

}
