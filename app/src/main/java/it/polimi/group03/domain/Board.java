package it.polimi.group03.domain;

import android.util.Log;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;

/**
 * @author cecibloom
 * @author tatibloom
 * Created on 11/11/2015.
 */
public class Board {

    private List<Bar> bars;
    private List<Player> players;
    private SlotInfo[][] grid = new SlotInfo[Constant.BOARD_INDEX][Constant.BOARD_INDEX];
    private Bar lastBarMoved; //maybe this can be obtained from a method
    private Player lastPlayer; //maybe this can be obtained from a method
    private int round; //Useful also for statistics
    private int turn; //Useful also for statistics
    private List<Bar> movedBarsInCurrentRound;
    private List<Bar> movedBarsInTwoPreviousRound;

    /**
     * This method will setup the initial state (<b>screenshot</b>) of the board: <i>bars,
     * players, beads and the grid itself</i> and some additional attributes that help us to have the most
     * accurate info in order to manage the game and some statistics.
     */
    public void init(){
        configureBar(BarOrientation.HORIZONTAL);//setting-up the fixed positions for the horizontal Bars
        configureBar(BarOrientation.VERTICAL);//setting-up the fixed positions for the vertical Bars
    }

    /**
     * This method will set new values for each slot in the grid (column or row),
     * if it's a horizontal move (<b>RED</b> bars), we'll only update the value in the slot (to <b>RED</b> or <b>BLACK</b>), if the previous one
     * it's not blue (<b>BLUE</b> covers all the possible colors behind it). If it's a vertical move (<b>BLUE</b> bars)
     * we'll update the value of the slot, if the previous one it's not <b>RED</b> but if it is <b>RED</b>, we won't update it if the current move
     * generates a <b>BLACK</b> slot but yes if generates a <b>BLUE</b> one.
     * @param bar Only the grid corresponding to this bar will be updated.
     */
    public void refreshGrid(Bar bar){
        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
            if ( BarOrientation.HORIZONTAL.equals(bar.getOrientation()) ) {
                //bar.getId()-1 'cause bar ids starts on 1
                grid[bar.getId()-1][i] = !SlotInfo.BLUE.equals(grid[bar.getId()-1][i]) ?
                                          bar.getKeys()[bar.getPosition().getInitialSlot() + i] : grid[bar.getId()-1][i];
            } else {
                grid[i][bar.getId()-1] = !SlotInfo.RED.equals(grid[i][bar.getId()-1]) ? bar.getKeys()[bar.getPosition().getInitialSlot() + i] :
                                          SlotInfo.BLACK.equals(bar.getKeys()[bar.getPosition().getInitialSlot() + i]) ?
                                          grid[i][bar.getId()-1] : bar.getKeys()[bar.getPosition().getInitialSlot() + i];
            }
        }
    }

    /**
     * This method will return all the bars present in the board for a given
     * <b>orientation</b>.
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     * @return <tt>bar</tt>
     */
    public List<Bar> getBars(BarOrientation orientation) {
        List<Bar> bars = new ArrayList<>();
        for ( Bar bar : this.bars ) {
            if ( orientation.equals(bar.getOrientation()) ) {
                bars.add(bar);
            }
        }
        return bars;
    }

    /**
     * List of the current active players in the game.
     * @return List of <tt>active players</tt> (at least 1).
     */
    public List<Player> activePlayers() {
        List<Player> players = new ArrayList<>();

        for ( Player player : this.players ) {
            if ( player.isActive() ) {
                players.add(player);
            }
        }
        return players;
    }

    /**
     * This method will return all the affected beads once a bar has been moved,
     * based on its coordinates. If the bar moved is <i>vertical</i>, we should only
     * check all beads for which the <b>Y</b> coordinate is the same as the id of the bar, and the
     * <b>X</b> coordinate if it's a horizontal bar.
     * @param bar in which the last move has been done.
     * @return <tt>beads</tt> present in the <i>bar</i> or an
     *         <tt>empty collection</tt> if there weren't any active beads on the <i>bar</i>.
     */
    public List<Bead> findBeadsOnBar(Bar bar) {
        List<Bead> beadsInBar = new ArrayList<>();

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

    /**
     * This method will find a Bar inside the <b>Board</b> in base of the id and the orientation.
     * @param id From <i>1</i> to <i>7</i>
     * @param orientation <i>vertical</i> or <i>horizontal</i>
     * @return <tt>bar</tt> corresponding to the given parameters or
     *         <tt>null</tt> if such bar doesn't exist.
     */
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
     * slots (<b>RED/BLUE</b> when it's cover or <b>BLACK</b> when it's not) for the given <b>orientation</b>.
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     */
    private void configureBar(BarOrientation orientation) {
        this.bars = CommonUtil.isEmpty(this.bars) ?  new ArrayList<Bar>() : this.bars;

        for ( int i = 1; i <= Constant.BOARD_INDEX; i++ ) {
            Bar bar = new Bar(i, orientation, getKeys(i, orientation));
            //initializing each slot in the grid (RED, BLUE, EMPTY)
            Log.d("Setup", MessageFormat.format("Bar[{0}-{1},{2}]", bar.getOrientation(),bar.getId(),bar.getPosition()));
            refreshGrid(bar);
            this.bars.add(bar);
        }
    }

    /**
     * This method is in charge of setting the values of each slot in a bar,
     * this configuration <b>won't change</b>, it will be the same always.
     * @param id From <i>1</i> to <i>7</i>
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     * @return <tt>keys</tt> for the given bar
     */
    private SlotInfo[] getKeys(int id, BarOrientation orientation) {
        SlotInfo[] keys;

        switch ( id ) {
            case 1:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 2:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE};
                break;
            case 3:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 4:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE};
                break;
            case 5:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 6:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 7:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE};
                break;
            default:
                keys = new SlotInfo[] {SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK};
        }
        return keys;
    }

    public void addBarToListOfMovedBarsInRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInCurrentRound) ) {
            this.movedBarsInCurrentRound = new ArrayList<>();
        }
        if ( movedBarsInCurrentRound.size() <= 3 ){//a round can only hold a maximum of 4 moved bars
            movedBarsInCurrentRound.add(bar);
        } else {//in any other case a new round is started
            this.round += 1;
            this.movedBarsInCurrentRound = new ArrayList<>();
            movedBarsInCurrentRound.add(bar);
        }

    }

    public void addBarToListOfMovedBarsInTwoPreviousRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInTwoPreviousRound) ) {
            this.movedBarsInTwoPreviousRound = new ArrayList<>();
        }
        movedBarsInTwoPreviousRound.add(bar);
    }

    //From here, just getters and setters

    public Board(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public SlotInfo[][] getGrid() {
        return grid;
    }

    public void setGrid(SlotInfo[][] grid) {
        this.grid = grid;
    }

    public void setLastBarMoved(Bar lastBarMoved) {
        this.lastBarMoved = lastBarMoved;
    }

    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public int getRound(){
        return round;
    }

    public void setRound(int round){
        this.round = round;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
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

    public Player getLastPlayer(){
        return this.lastPlayer;
    }

    public Player getCurrentPlayer(){
        //TODO update with proper code
        return new Player();
    }

    public Bar getLastBarMoved(){
        return this.lastBarMoved;
    }

}
