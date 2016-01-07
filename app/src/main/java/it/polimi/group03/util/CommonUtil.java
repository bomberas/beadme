package it.polimi.group03.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import it.polimi.group03.R;

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

    /**
     *
     * Creates a toast message on the screen displaying a customized <tt>text</tt>text
     *
     * @param context The context in which the Toast message should be created
     * @param msg The message display in the Toast message
     * @param duration The duration of the message on the screen. It's either
     *              {@link Toast#LENGTH_LONG} or {@link Toast#LENGTH_SHORT}
     */
    public static void showToastMessage(Context context, View view, TextView text, String msg, int duration){

        text.setText(msg);

        Toast myToast = new Toast(context);
        myToast.setView(view);
        myToast.setDuration(duration);
        myToast.setGravity(Gravity.CENTER , 0, 0);
        myToast.show();
    }

    /**
     *
     * Returns the corresponding view id of the given bead. This method checks the id of the player
     * and the position of the bead in the list of beads for the given player so it can return the view id
     * related to the bead.
     *
     * @param playerId The id of the player who owns the bead.
     * @param beadPosition The position of the bead inside the list of beads for the given player.
     * @return {@code id} The id of the view representing the bead on the screen.
     */
    public static int getBeadId(int playerId, int beadPosition){
        switch (playerId) {
            case 0: {
                switch (beadPosition) {
                    case 0:
                        return R.id.p1b1;
                    case 1:
                        return R.id.p1b2;
                    case 2:
                        return R.id.p1b3;
                    case 3:
                        return R.id.p1b4;
                    case 4:
                        return R.id.p1b5;
                }
            }
            case 1:{
                switch (beadPosition) {
                    case 0:
                        return R.id.p2b1;
                    case 1:
                        return R.id.p2b2;
                    case 2:
                        return R.id.p2b3;
                    case 3:
                        return R.id.p2b4;
                    case 4:
                        return R.id.p2b5;
                }
            }
            case 2: {
                switch (beadPosition) {
                    case 0:
                        return R.id.p3b1;
                    case 1:
                        return R.id.p3b2;
                    case 2:
                        return R.id.p3b3;
                    case 3:
                        return R.id.p3b4;
                    case 4:
                        return R.id.p3b5;
                }
            }
            case 3: {
                switch (beadPosition) {
                    case 0:
                        return R.id.p4b1;
                    case 1:
                        return R.id.p4b2;
                    case 2:
                        return R.id.p4b3;
                    case 3:
                        return R.id.p4b4;
                    case 4:
                        return R.id.p4b5;
                }
            }
            default:
                return -1;
        }
    }

    public static int getPlayerSummaryImageId(int playerId) {
        switch (playerId) {
            case 0:
                return R.id.summaryP1;
            case 1:
                return R.id.summaryP2;
            case 2:
                return R.id.summaryP3;
            case 3:
                return R.id.summaryP4;
            case 4:
                return R.id.summaryP5;
            default:
                return -1;
        }
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public static String convertDateToString(Date dateNow)
    {
        SimpleDateFormat dateConvertion = new SimpleDateFormat("yyyyMMddHHmmss");
        String date_to_string = dateConvertion.format(dateNow);
        return date_to_string;

    }


    public static Date convertStringToDate(String string) throws ParseException {
        String str = "January 2, 2010";
        SimpleDateFormat format = new SimpleDateFormat("yyyMMddHHmmss");
        Date date = format.parse(str);
        return date;
    }
}