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
    private Player nextPlayer; //this way the UI can call next player
    private int round; //Useful also for statistics
    private int turn; //Useful also for statistics
    private List<Player> losersAfterTurn; //this way the UI can show the loser after a turn, this property must be updated in the refreshBeads method
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
                grid[bar.getId()][i] = !SlotInfo.BLUE.equals(grid[bar.getId()][i]) ?
                        bar.getKeys()[bar.getPosition().getInitialSlot() + i] : grid[bar.getId()][i];
            } else {
                grid[i][bar.getId()] = !SlotInfo.RED.equals(grid[i][bar.getId()]) ? bar.getKeys()[bar.getPosition().getInitialSlot() + i] :
                        SlotInfo.BLACK.equals(bar.getKeys()[bar.getPosition().getInitialSlot() + i]) ?
                                grid[i][bar.getId()] : bar.getKeys()[bar.getPosition().getInitialSlot() + i];
            }
        }
    }

    /**
     * This method will <u>update the status</u> of all the affected beads once a bar has been moved,
     * based on its coordinates. If the bar moved is <i>vertical</i>, we should only
     * check all beads for which the <b>Y</b> coordinate is the same as the id of the bar, and the
     * <b>X</b> coordinate if it's a horizontal bar. The <u>updated status</u> will be <i>INACTIVE</i> for
     * all the beads allocated on a slot in the grid which the current value is <i>BLACK</i> or <i>EMPTY</i>.
     * After updating the status of all the beads of a user, the status of the user <i>will be updated</i> too in such way
     * that, if the user doesn't have any active bead, the user will be <i>INACTIVE</i> too and it will be added
     * to the list of the looser players.
     * @param bar in which the last move has been done.
     */
    public void refreshBeads(Bar bar) {
        for ( Player player : activePlayers() ) {
            for ( Bead bead : player.activeBeads() ) {
                if ( BarOrientation.VERTICAL.equals(bar.getOrientation()) ) {//checking only Y coordinate
                    if ( bar.getId() == bead.getPosition().getY() ) {
                        //if the corresponding slot on the grid is BLACK or EMPTY we should de-activate the bead
                        bead.setIsActive(!SlotInfo.BLACK.equals(grid[bead.getPosition().getX()][bead.getPosition().getX()]));
                    }
                } else {//checking only X coordinate
                    if ( bar.getId() == bead.getPosition().getX() ) {
                        //if the corresponding slot on the grid is BLACK or EMPTY we should de-activate the bead
                        bead.setIsActive(!SlotInfo.BLACK.equals(grid[bead.getPosition().getX()][bead.getPosition().getX()]));
                    }
                }

            }
            //if there aren't active beads for the player, the player won't be active too
            player.setActive(!CommonUtil.isEmpty(player.activeBeads()));
            addPlayerToListOfLosersAfterTurn(player);
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
     * List of the current active players in the game. A player is consider active if and only if still has beads on the board.
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
     * This method will find a Bar inside the <b>Board</b> in base of the id and the orientation.
     * @param id From <i>0</i> to <i>6</i>
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

        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
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
     * @param id From <i>0</i> to <i>6</i>
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     * @return <tt>keys</tt> for the given bar
     */
    private SlotInfo[] getKeys(int id, BarOrientation orientation) {
        SlotInfo[] keys;

        switch ( id ) {
            case 0:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 1:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE};
                break;
            case 2:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 3:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE};
                break;
            case 4:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 5:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLUE};
                break;
            case 6:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new SlotInfo[] {SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.RED} : new SlotInfo[] {SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLUE,SlotInfo.BLACK,SlotInfo.BLUE};
                break;
            default:
                keys = new SlotInfo[] {SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.RED,SlotInfo.BLACK,SlotInfo.BLACK,SlotInfo.BLACK};
        }
        return keys;
    }

    public void resetMovedBarsInCurrentRound() {
        this.movedBarsInCurrentRound = new ArrayList<>();
    }

    public void resetLoserAfterTurn(){
        this.losersAfterTurn = new ArrayList<>();
    }

    public void addBarToListOfMovedBarsInRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInCurrentRound) ) {
             this.movedBarsInCurrentRound = new ArrayList<>();
        }
        if ( this.movedBarsInCurrentRound.size() <= 3 ){//a round can only hold a maximum of 4 moved bars
             this.movedBarsInCurrentRound.add(bar);
        } else {//in any other case a new round is started
             this.round += 1;
             this.movedBarsInCurrentRound = new ArrayList<>();
             this.movedBarsInCurrentRound.add(bar);
        }

    }

    public void addBarToListOfMovedBarsInTwoPreviousRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInTwoPreviousRound) ) {
            this.movedBarsInTwoPreviousRound = new ArrayList<>();
        }
        this.movedBarsInTwoPreviousRound.add(bar);
    }

    private void addPlayerToListOfLosersAfterTurn(Player player) {
        if ( CommonUtil.isEmpty(this.losersAfterTurn) ) {
            this.losersAfterTurn = new ArrayList<>();
        }
        this.losersAfterTurn.add(player);
    }

    public void moveRound(){
        this.round += 1;
    }

    public void moveTurn() {
        this.turn = this.movedBarsInCurrentRound.size();
    }

    //From here, just getters and setters

    public Board(List<Player> players) {
        this.players = players;
        this.round = 1;
        this.turn = 1;
    }

    public SlotInfo[][] getGrid() {
        return grid;
    }

    public void setLastBarMoved(Bar lastBarMoved) {
        this.lastBarMoved = lastBarMoved;
    }

    public List<Bar> getMovedBarsInCurrentRound() {
        return movedBarsInCurrentRound;
    }

    public List<Player> getLosersAfterTurn() {
        return losersAfterTurn;
    }

    public List<Bar> getMovedBarsInTwoPreviousRound() {
        return movedBarsInTwoPreviousRound;
    }

    public void setLastPlayer(Player lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public Player getLastPlayer(){
        return this.lastPlayer;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public Bar getLastBarMoved(){
        return this.lastBarMoved;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getRound() {
        return round;
    }

    public int getTurn() {
        return turn;
    }

}
