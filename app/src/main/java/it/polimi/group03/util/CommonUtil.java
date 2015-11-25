package it.polimi.group03.util;

import java.util.Collection;
import java.util.Properties;

/**
 * This is an utility class.
 *
 * <p>All methods of this class are helpers of the project.
 * <p>Each method is responsible to perform some minor action non related to the mechanics of the game
 * but helpful and used in various instances.
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */
public class CommonUtil {

    /**
     * Checks whether a collection is empty or not.
     *
     * @param collection collection that wants to be validated.
     * @return  {@code true} if the collection is empty.
     *          {@code true} if not.
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * Checks whether a string is empty or not.
     *
     * @param string string that wants to be validated.
     * @return  {@code true} if the given string is empty.
     *          {@code true} if not.
     */
    public static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }

    /**
     * Checks whether two strings are equal without regarding the case.
     *
     * @param s1 string to compare.
     * @param s2 string to compare.
     * @return [null,null] {@code true} <br/>
     *         ["  123  ","123"] {@code true} <br/>
     *         ["ABC","abc"] {@code true} <br/>
     *         {@code false} in any other case.
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if ( s1 == null ) return s2 == null;
        if ( s2 == null ) return false;
        if ( s1.trim().equals("") ) return s2.trim().equals("");
        return !s2.trim().equals("") && s1.toLowerCase().trim().equals(s2.toLowerCase().trim());
    }

}