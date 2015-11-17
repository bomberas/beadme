package it.polimi.group03.util;

import java.util.Collection;

/**
 * @author tatibloom
 * Created by tatibloom on 11/11/2015.
 */
public class CommonUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * @param s1 string to compare
     * @param s2 string to compare
     * @return [null,null] <tt>true</tt> <br/>
     *         ["  123  ","123"] <tt>true</tt> <br/>
     *         ["ABC","abc"] <tt>true</tt> <br/>
     *         <tt>false</tt> in any other case.
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        if ( s1 == null ) return s2 == null;
        if ( s2 == null ) return false;
        if ( s1.trim() == "" ) return s2.trim() == "";
        if ( s2.trim() == "" ) return false;
        if ( s1.toLowerCase().trim().equals(s2.toLowerCase().trim()) ) return true;
        return false;
    }

    public static String rPad(String s, int chars) {
        int count = chars-s.length();
        int i = 0;

        while (  i < count ) {
            s += " ";
            i++;
        }

        return s;
    }
}
