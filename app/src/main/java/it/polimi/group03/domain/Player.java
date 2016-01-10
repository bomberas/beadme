package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.CommonUtil;

import it.polimi.group03.engine.GameValidator;
import it.polimi.group03.util.Constant;

/**
 * This class defines and holds the basic information of a player in the game.
 * Being that a player has up to 5 beads, the class references the {@link Bead} class
 * and uses the {@link Player#addBead(Bead)} method to set the beads to the player and then
 * add them to the game.
 *
 * The player remains in the game til the end unless all his/her beads have fallen,
 * in such case, the status of the player is set to <tt>inactive</tt>.
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

    /**
     * Indicates whether if a player is Mr. Roboto or not.
     */
    private boolean isMrRoboto;

    public Player(int id, String nickname, String color) {
        this.id = id;
        this.nickname = nickname;
        this.color = color;
        this.beads = new ArrayList<>();
        this.active = true;
        this.isMrRoboto = false;
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

    public boolean isMrRoboto() {
        return isMrRoboto;
    }

    public void setIsMrRoboto(boolean isMrRoboto) {
        this.isMrRoboto = isMrRoboto;
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
     * @return {@code List} of all active beads for the current player.<br/>
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

    public int getRemainingBeadsToPlace(){
        int count = Constant.GAME_MAX_NUMBER_BEADS;

        for ( int i = 0; i < this.beads.size(); i++ ) {
            if ( this.beads.get(i).isPlaced() ) {
                count --;
            }
        }

        return count;
    }
}