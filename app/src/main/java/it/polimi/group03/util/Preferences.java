package it.polimi.group03.util;

/**
 * This class contains the allowed preferences present in the game linked to their respective method,
 * called with reflection in order to avoid cyclomatic complexity on the rules compliance indicator.
 *
 * <p>Is used when a change is made withing the Preference fragment.
 *
 *
 * @see it.polimi.group03.activity.SettingsActivity
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 18/12/2015.
 */
public enum Preferences {

    key_sound("setSound"),
    key_vibration("setVibration"),
    key_notification("setNotification"),
    key_light("setLight"),
    key_players("setNumberOfPlayers"),
    key_themes("setTheme"),
    key_languages("setLanguage");

    private String method;

    Preferences(String method) {
        this.method = method;
    }

    /**
     * Get the method for the corresponding <i>key code</i>.
     *
     * @param name String with the <i>name</i> of the preference.
     * @return {@code method} String that represents the name of the method to be invoked.
     */
    public static Preferences getPreference(String name) {
        for ( Preferences preference : values() ) {
            if ( preference.name().equalsIgnoreCase(name) ) {
                return preference;
            }
        }
        return key_sound;//by default
    }

    public String getMethod() {
        return this.method;
    }
}
