package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 * Created by cecibloom on 11/11/2015.
 */
public class Board {

    private List<Bar> bars;
    private List<Player> players;
    private String[][] state = new String[Constant.BOARD_INDEX][Constant.BOARD_INDEX];
    private Bar lastBarMoved; //maybe this can be obtained from a method
    private Player lastPlayer; //maybe this can be obtained from a method
    private int activeNumberOfPlayers; //maybe this can be obtained from a method
    private int round; //Useful also for statistics
    private List<Bar> movedBarsInCurrentRound;
    private List<Bar> movedBarsInTwoPreviousRound;

    public Board(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getRound(){
        return round;
    }

    public void setRound(int round){
        this.round = round;
    }

    public List<Bar> getMovedBarsInCurrentRound() {
        return movedBarsInCurrentRound;
    }

    public void setMovedBarsInCurrentRound(List<Bar> movedBarsInCurrentRound) {
        this.movedBarsInCurrentRound = movedBarsInCurrentRound;
    }

    public List<Bar> getMovedBarsInTwoPreviousRound() {
        return movedBarsInTwoPreviousRound;
    }

    public void setMovedBarsInTwoPreviousRound(List<Bar> movedBarsInTwoPreviousRound) {
        this.movedBarsInTwoPreviousRound = movedBarsInTwoPreviousRound;
    }

    public void init(){
        configureBar(BarOrientation.HORIZONTAL);//setting-up the fixed positions for the horizontal Bars
        configureBar(BarOrientation.VERTICAL);//setting-up the fixed positions for the vertical Bars
        //TODO save also first snapshot of board
    }

    public void refreshBoardState(Move m){
        //Given bars refresh the state of the board
        //After a move, set lastplayer, las barmoved, active numberof Player
    }

    public Player getLastPlayer(){
        return this.lastPlayer;
    }

    public Player getCurrentPlayer(){
        Player p = new Player(); //update with proper code
        return p;
    }

    public Bar getLastBarMoved(){
        return this.lastBarMoved;
    }

    public List<Bar> getBars(BarOrientation orientation) {
        List<Bar> bars = new ArrayList<Bar>();
        for ( Bar bar : this.bars ) {
            if ( orientation.equals(bar.getOrientation()) ) {
                bars.add(bar);
            }
        }
        return bars;
    }

    public void addBarToListOfMovedBarsInRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInCurrentRound) ) {
            this.movedBarsInCurrentRound = new ArrayList<Bar>();
        }
        if ( movedBarsInCurrentRound.size() <= 3 ){//a round can only hold a maximum of 4 moved bars
            movedBarsInCurrentRound.add(bar);
        } else {//in any other case a new round is started
            this.movedBarsInCurrentRound = new ArrayList<Bar>();
            this.round += 1;
            movedBarsInCurrentRound.add(bar);
        }

    }

    public void addBarToListOfMovedBarsInTwoPreviousRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInTwoPreviousRound) ) {
            this.movedBarsInTwoPreviousRound = new ArrayList<Bar>();
        }
        movedBarsInTwoPreviousRound.add(bar);
    }

    public List<Player> activePlayers() {
        List<Player> players = new ArrayList<Player>();

        for ( Player player : this.players ) {
            if ( player.isActive() ) {
                players.add(player);
            }
        }
        return players;
    }

    /**
     * This method will return all the beads affected once a bar has been moved,
     * based on it's coordinates.
     * @param bar
     * @return bead
     */
    public List<Bead> findBeadsOnBar(Bar bar) {
        List<Bead> beadsInBar = new ArrayList<Bead>();

        for ( Player player : activePlayers() ) {
            for ( Bead bead : player.activeBeads() ) {
                if ( BarOrientation.VERTICAL.equals(bar.getOrientation()) ) {//checking only Y coordinate
                    if ( bar.getId() == bead.getPosition().getY() ) {
                        beadsInBar.add(bead);
                    }
                } else {//checking only X coordinate
                    if ( bar.getId() == bead.getPosition().getX() ) {
                        beadsInBar.add(bead);
                    }
                }
            }
        }

        return beadsInBar;
    }

    public Bar findBar(int id, BarOrientation orientation){
        List<Bar> targetBars = getBars(orientation);

        for ( Bar bar : targetBars ) {
            if ( bar.getId() == id ) {
                return bar;
            }
        }

        return null;
    }

    /**
     * Method called just once per game, will setup all the
     * slots (1 per covered or 0 not covered) for the given <b>orientation</b>.
     * @see BarOrientation
     * @param orientation
     */
    private void configureBar(BarOrientation orientation) {
        this.bars = new ArrayList<Bar>();

        for ( int i = 1; i <= Constant.BAR_SLOTS; i++ ) {
            this.bars.add(new Bar(i, orientation, getKeys(i, orientation)));
        }
    }

    private int[] getKeys(int id, BarOrientation orientation) {
        int[] keys;

        switch ( id ) {
            case 1:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,1,0,1,0,1,0,1} : new int[] {1,0,0,0,0,1,0,1,1};
                break;
            case 2:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,1,0,0,1,0,1} : new int[] {1,0,0,0,1,1,0,0,1};
                break;
            case 3:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,0,1,0,0,0,1} : new int[] {1,0,1,0,0,1,0,1,1};
                break;
            case 4:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,1,0,1,0,1,0,1} : new int[] {1,0,0,1,1,0,0,0,1};
                break;
            case 5:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,0,0,0,0,0,1} : new int[] {1,1,0,0,0,1,0,1,1};
                break;
            case 6:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,1,0,0,0,1,0,1,1} : new int[] {1,1,0,0,0,0,0,1,1};
                break;
            case 7:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,1,0,1,0,1,1} : new int[] {1,0,0,1,0,0,1,0,1};
                break;
            default:
                keys = null;
        }
        return keys;
    }

}
