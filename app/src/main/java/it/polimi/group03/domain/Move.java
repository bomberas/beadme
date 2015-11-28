package it.polimi.group03.domain;

/**
 * This class holds the movement performed by a player. This "movement" basically consists of a
 * player - bar pair. This class should be used when checking the validations
 * and rules established for the game e.g a bar already moved in a previous round.
 *
 * @see Player
 * @see Bar
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 16/11/2015.
 */
public class Move {

    private Player player;
    private Bar bar;

    public Move(Player player, Bar bar) {
        this.player = player;
        this.bar = bar;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
