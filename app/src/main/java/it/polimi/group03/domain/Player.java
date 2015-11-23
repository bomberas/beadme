package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.CommonUtil;

import it.polimi.group03.engine.GameValidator;

/**
 * This class will define a player in the game and his beads added
 * (using {@link Player#addBead(Bead)} to the game. The player will
 * remain in the game till the end unless all it's beads have fallen,
 * in that case, the status will be set it as <i>inactive</i>.
 *
 *
 * @see Bead
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 08/11/2015.
 */

public class Player {

    /**
     * ID of the player, from 0 up to 3.
     */
    private int id;
    /**
     * Nickname of the player, used for statistics.
     */
    private String nickname;
    /**
     * Color of the players' beads.
     */
    private String color;
    /**
     * Beads that the player has placed on the board (always 5 beads).
     */
    private List<Bead> beads;
    /**
     *  Indicates whether if a player is present in the game or not.
     */
    private boolean active;

    public Player(int id, String nickname, String color) {
        this.id = id;
        this.nickname = nickname;
        this.color = color;
        this.beads = new ArrayList<>();
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public List<Bead> getBeads() {
        return beads;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This method will add a new bead to the board for the current player, if there
     * is no bead yet, it will initialize the list. The rules to add the bead are
     * in {@link GameValidator}.
     *
     * @param bead Bead to be placed on the board.
     */
    public void addBead(Bead bead) {
        if ( CommonUtil.isEmpty(beads) ) {
            beads = new ArrayList<>();
        }

        beads.add(bead);
    }

    public String getColor() {
        return color;
    }

    /**
     * This method will retrieve a list with all the active beads for the player.
     *
     * @return {@code List} off all active beads for the current player.<br/>
     *         {@code empty} In there is no active beads for the player.
     */
    public List<Bead> activeBeads() {
        List<Bead> beads = new ArrayList<>();

        for ( Bead bead : this.beads ) {
            if ( bead.isActive() ) {
                beads.add(bead);
            }
        }

        return beads;
    }

}