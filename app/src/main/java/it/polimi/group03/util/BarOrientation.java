package it.polimi.group03.util;

/**
 * This class
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */
// TODO: 22/11/2015 Tati escribe la doc de la clase

public enum BarOrientation {
    VERTICAL('v'), HORIZONTAL('h');

    private char shortcut;

    BarOrientation(char shortcut) {
        this.shortcut = shortcut;
    }

    public char getShortcut() {
        return shortcut;
    }

}
