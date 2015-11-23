package it.polimi.group03.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    private static Properties properties;

    /**
     * Checks whether a collection is empty or not.
     *
     * @param collection
     * @return  {@code true} if the collection is empty.
     *          {@code true} if not.
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * Checks whether a string is empty or not.
     *
     * @param string
     * @return  {@code true} if the given string is empty.
     *          {@code true} if not.
     */
    public static boolean isEmpty(String string) {
        return string == null || string == "";
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
        if ( s1.trim() == "" ) return s2.trim() == "";
        if ( s2.trim() == "" ) return false;
        if ( s1.toLowerCase().trim().equals(s2.toLowerCase().trim()) ) return true;
        return false;
    }

    /**
     * Formats a string adding blank spaces to the right until reach the given size. For example:
     * rpad("abc", 5) will return <i>"abc  "</i> (two blank spaces to the right).
     *
     * @param string text to format.
     * @param size number of characters to fit.
     * @return the new formatted string.
     */
    public static String rPad(String string, int size) {
        if ( CommonUtil.isEmpty(string) ) {
            return string;
        }

        int count = size - string.length();
        int i = 0;

        while (  i < count ) {
            string += " ";
            i++;
        }

        return string;
    }

    /**
     * Loads the <tt>properties.xml</tt> file located in <tt>app/src/main/java/it/polimi/group03/</tt> to access the
     * properties that has been configured.
     */
    private static void loadProperties() {
        try {
            properties = new Properties();
            properties.loadFromXML(new FileInputStream(new File("app/src/main/java/it/polimi/group03/properties.xml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    /**
     * Returns the value of a specific key.
     *
     * @param code property key.
     * @return {@code message} value of the property.
     */
    public static String getMessageDescription(String code){
        if ( properties == null || properties.isEmpty() ){
            loadProperties();
        }

        String message = properties.getProperty(code);

        if ( message == null || message.equals("")) {
            return "Undefined message";
        } else {
            return message;
        }
    }
}