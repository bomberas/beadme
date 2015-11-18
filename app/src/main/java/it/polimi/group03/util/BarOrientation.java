package it.polimi.group03.util;

/**
 * @author tatibloom
 * Created by tatibloom on 11/11/2015.
 */
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
