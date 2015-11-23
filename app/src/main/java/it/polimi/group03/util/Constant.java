package it.polimi.group03.util;

/**
 * This class holds the constants used in the project
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */
public class Constant {

    /**
     * This constant holds the value of the fixed number of slots for a bar.
     */
    public static final int BAR_SLOTS = 9;
    /**
     * This constant holds the size of the board. In this case the board is 7x7.
     */
    public static final int BOARD_INDEX = 7;
    /**
     * This constant holds the value of the max number of players.
     */
    public static final int GAME_MAX_NUMBER_PLAYERS = 4;
    /**
     * This constant holds the value of the number of beads per player.
     */
    public static final int GAME_MAX_NUMBER_BEADS = 5;
    /**
     * This constant holds the the status code for success.
     */
    public static final String STATUS_OK = "s0x0";
    /**
     * This constant holds the error code when the number of players is out of bound.
     */
    public static final String STATUS_ERR_NUMBER_PLAYERS = "e0x1";
    /**
     * This constant holds the error code when the new position of the bar is not permitted.
     */
    public static final String STATUS_ERR_BAR_POSITION = "e0x2";
    /**
     * This constant holds the error code when the selected bar was already moved.
     */
    public static final String STATUS_ERR_BAR_SELECTED = "e0x3";
    /**
     * This constant holds the error code when the selected bar was already moved in one of the two previous turns of a player.
     */
    public static final String STATUS_ERR_BAR_CONSECUTIVE = "e0x4";
    /**
     * This constant holds the error code when the player played in the previous turn.
     */
    public static final String STATUS_ERR_SAME_PREVIOUS_PLAYER = "e0x5";
    /**
     * This constant holds the error code when the bead is placed in an invalid place.
     */
    public static final String STATUS_ERR_PLACED_BEAD = "e0x6";
}
