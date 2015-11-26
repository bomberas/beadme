package it.polimi.group03.domain;

/**
 * Created by cecibloom on 25/11/2015.
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

    public void setBar(Bar move) {
        this.bar = bar;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


}
