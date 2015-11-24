package it.polimi.group03.domain;

/**
 * This class contains the set of game preferences established
 * by the player.
 * It will be used also to hold the preferences sent and
 * retrieved from the database.
 * <p>The game will show in the Settings section options
 * to configure:
 *
 * <ul style="list-style-type:circle">
 * <li>Sound.</li>
 * <li>Vibration.</li>
 * <li>Notification.</li>
 * <li>Number of Players.</li>
 * <li>Lock Orientation.</li>
 * <li>Language.</li>
 * <li>Theme.</li>
 * <li>Light.</li>
 * </ul>
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 23/11/2015.
 */
public class Settings {

    /**
     * A value from 0 to 100 to indicate the volume of the sound effects.
     * 0 means off.
     */
    private int sound;
    /**
     * <code>true</code> if vibration effects are set to on.
     * <code>false</code> if not.
     */
    private boolean vibration;
    /**
     * <code>true</code> if notifications are set to on.
     * <code>false</code> if not.
     */
    private boolean notification;
    /**
     * Set number of players.
     */
    private int numberOfPlayers;
    /**
     * <code>true</code> if notifications are set to on.
     * <code>false</code> if not.
     */
    private boolean lockOrientation;
    /**
     * Predefined language. EN=English, ES=Spanish, SQ=Albanian, IT=Italian
     */
    private String language;
    /**
     * Selected theme for the UI. Related with every or almost every UI aspect of the game i.e.
     * when selecting "Star Wars" or "Dr. Who" not only colors, icons but sound effects
     * according to the theme will be set. CHEWBACCA=Star Wars theme, WHO= Dr. Who theme
     * EXPELLIARMUS= Harry Potter theme, etc.
     */
    private String theme;
    /**
     * <code>true</code> if light effects are set to on.
     * <code>false</code> if not.
     */
    private boolean light;

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isLockOrientation() {
        return lockOrientation;
    }

    public void setLockOrientation(boolean lockOrientation) {
        this.lockOrientation = lockOrientation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isLight() {
        return light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }
}
