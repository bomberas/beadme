package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.CommonUtil;

/**
 * Created by cecibloom on 11/11/2015.
 */
public class Board {

    private Bar lastBarMoved; //maybe this can derivate from a method
    private Player lastPlayer; //maybe this can derivate from a method
    private int activeNumberOfPlayers; //maybe this can derivate from a method

    private List<Bar> bars;
    private List<Player> players;

    private Bar[] hBars = new Bar[7];
    private Bar[] vBars = new Bar[7];
    private String[][] state = new String[7][7];

    public Board() {

    }

    public Board(Bar[] hBars, Bar[] vBars, List<Player> players) {
        super();
        this.hBars = hBars;
        this.vBars = vBars;
        this.players = players;
    }


    public void refreshBoardState(Move m){
        //Given bars refresh the state of the board
        //After a move, set lastplayer, las barmoved, active numberof Player
    }

    public Player getLastPlayer(){
        return lastPlayer;
    }

    public Player getCurrentPlayer(){
        Player p = new Player();
        return p;
    }

    public Bar getLastBarMoved(){
        return lastBarMoved;
    }

    public void addBar(Bar bar) {
        if ( CommonUtil.isEmpty(bars) ) {
            bars = new ArrayList<Bar>();
        }
        bars.add(bar);
    }

    public void addPlayer(Player player) {
        if ( CommonUtil.isEmpty(players) ) {
            players = new ArrayList<Player>();
        }
        players.add(player);
    }

    public List<Bar> getHorizontalBars() {
        List<Bar> hBars = new ArrayList<Bar>();
        for ( Bar bar : bars ) {
            if ( BarOrientation.HORIZONTAL.equals(bar.getOrientation()) ) {
                hBars.add(bar);
            }
        }
        return hBars;
    }

    public List<Bar> getVerticalBars() {
        List<Bar> vBars = new ArrayList<Bar>();
        for ( Bar bar : bars ) {
            if ( BarOrientation.VERTICAL.equals(bar.getOrientation()) ) {
                vBars.add(bar);
            }
        }
        return vBars;
    }

    public int getActiveNumberOfPlayers() {
        int count = 0;
        for ( Player player : players ) {
            if ( player.isActive() ) {
                count++;
            }
        }
        return count;
    }

}
