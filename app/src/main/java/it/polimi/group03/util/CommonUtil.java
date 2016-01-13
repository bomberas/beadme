package it.polimi.group03.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

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
     * Clones the given array and creates another array exactly like the given one, in all forms and shapes.
     *
     * @param source array to be cloned
     * @return {@code array} cloned after method
     */
    public static int[][] cloneArray( int[][] source) {
        int[][] dest = new int[source.length][source.length];
        for ( int col = 0; col < source.length; col++) {
            for ( int row = 0; row < source.length; row++) {
                dest[row][col] = source[row][col];
            }
        }
        return dest;
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
        myToast.setGravity(Gravity.CENTER , 0, 400);
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

    /**
     *
     * Returns the corresponding view id of the summary image. This method checks the id of the player
     * so it can return the view id
     * related to the bead.
     *
     * @param playerId The id of the player for whom the image view id is needed.
     * @return {@code id} The id of the view representing the image on the screen.
     */
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

    /**
     *
     * Returns the corresponding view id of the given bar. This method checks the  orientation and location
     * given so it can return the proper view id.
     *
     * @param orientation orientation of the given bar
     * @param id position - number of bar from 0 to 6
     * @return {@code id} The id of the view representing the bar on the screen.
     */
    public static int getBarId(BarOrientation orientation, int id){

        switch (id) {
            case 0:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH1;
                }else {
                    return R.id.imgViewV1;
                }
            case 1:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH2;
                }else {
                    return R.id.imgViewV2;
                }
            case 2:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH3;
                }else {
                    return R.id.imgViewV3;
                }
            case 3:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH4;
                }else {
                    return R.id.imgViewV4;
                }
            case 4:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH5;
                }else {
                    return R.id.imgViewV5;
                }
            case 5:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH6;
                }else {
                    return R.id.imgViewV6;
                }
            case 6:
                if (orientation.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH7;
                }else {
                    return R.id.imgViewV7;
                }
            default:
                return -1;
        }
    }

    /**
     *
     * This method converts the given coordinates into a valid row or column on the board.
     * Both rows and columns go from 0 to 6.
     *
     * @param x coordinate x or y that represents the position of the finger on the screen
     * @param offset the offset either on x or y coordinate.
     * @return the corresponding {@code row} or {@code column} on the board.
     */
    public static int convertXYToRowColumn(int x, int offset, int width){
        return (int)Math.floor((x - offset)/width) - 4;
    }

    /**
     *
     * This method converts the given row or column into a valid position on the screen.
     *
     * @param i the row or column in which the bead has been placed.
     * @param offset the offset either on x or y coordinate.
     * @return the corresponding {@code x coordinate} or {@code y coordinate} in which the bead will be placed.
     */
    public static int convertRowColumnToXY(int i, int offset, int width){
        return (i + 3)*width + offset;
    }
}