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
     * This constant holds the error code when the selected bar was already moved by one of the opponents.
     */
    public static final String STATUS_ERR_BAR_SELECTED = "e0x3";
    /**
     * This constant holds the error code when the selected bar was already moved in one of the two previous turns of a player.
     */
    public static final String STATUS_ERR_BAR_CONSECUTIVE = "e0x4";
    /**
     * This constant holds the error code when the player is trying to move two times in a row.
     */
    public static final String STATUS_ERR_SAME_PREVIOUS_PLAYER = "e0x5";
    /**
     * This constant holds the error code when the bead is placed in an invalid place.
     */
    public static final String STATUS_ERR_PLACED_BEAD = "e0x6";
    /**
     * This constant holds the key of the sound preference.
     */
    public static final String KEY_PREF_SOUND = "key_sound";
    /**
     * This constant holds the key of the vibration preference.
     */
    public static final String KEY_PREF_VIBRATION = "key_vibration";
    /**
     * This constant holds the key of the notification preference.
     */
    public static final String KEY_PREF_NOTIFICATION = "key_notification";
    /**
     * This constant holds the key of the light preference.
     */
    public static final String KEY_PREF_LIGHT = "key_light";
    /**
     * This constant holds the key of the number of players preference.
     */
    public static final String KEY_PREF_PLAYERS = "key_players";
    /**
     * This constant holds the key of the themes preference.
     */
    public static final String KEY_PREF_THEMES = "key_themes";
    /**
     * This constant holds the key of the configuration for the bars preference.
     */
    public static final String KEY_PREF_BARS = "key_bars";
    /**
     * This constant holds the default value of the sound-on preference.
     */
    public static final boolean PREF_SOUND_DEFAULT = true;
    /**
     * This constant holds the default value of the vibration-on preference.
     */
    public static final boolean PREF_VIBRATION_DEFAULT = true;
    /**
     * This constant holds the default value of the notification-on preference.
     */
    public static final boolean PREF_NOTIFICATION_DEFAULT = true;
    /**
     * This constant holds the default value of the light-on preference.
     */
    public static final boolean PREF_LIGHT_DEFAULT = true;
    /**
     * This constant holds the default value of the theme preference.
     */
    public static final String PREF_THEME_DEFAULT = "PINK";
    /**
     * This constant holds the default value of the number of players preference.
     */
    public static final String PREF_PLAYER_DEFAULT = "2";
    /**
     * This constant holds the default value for the configuration of the bars preference.
     */
    public static final String PREF_BARS_DEFAULT = "";
    /**
     * This constant holds the value of the star wars theme.
     */
    public static final String PREF_THEME_STAR_WARS = "CHEWBACCA";
    /**
     * This constant holds the default value of the harry potter theme.
     */
    public static final String PREF_THEME_HARRY_POTTER = "EXPELLIARMUS";
    /**
     * This constant holds the value of the number of cells on the board.
     */
    public static final int NUMBER_OF_CELLS = 13;
    /**
     * This constant holds the value of the number of cells on the board.
     */
    public static final String HEIGHT = "height";
    /**
     * This constant holds the value of the number of cells on the board.
     */
    public static final String WIDTH = "width";
    /**
     * This constant holds the value of the player name tag.
     */
    public static final String PLAYER_NAME = "player_name";
    /**
     * This constant holds the value of the player icon tag.
     */
    public static final String PLAYER_ICON = "player_icon";
    /**
     * This constant holds the value of the player icon tag.
     */
    public static final String PLAYER_HUMAN = "player_human";
    /**
     * This constant holds the key of the sound effect for moving a bar.
     */
    public static final String MOVE_SFX = "MOVE_SFX";
    /**
     * This constant holds the name of this game brain :)
     */
    public static final String MR_ROBOTO = "Mr. Roboto";
}
