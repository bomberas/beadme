package it.polimi.group03.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;

import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.engine.GameValidator;

/**
 * This class represents the whole concept of the game which includes players,
 * bars, beads and the board itself. Is seen as a snapshot of the game that shows the current status,
 * i.e the latest and most updated values for all the information hold in the game.
 *
 * <p>This class is capable to retrieve the current situation of a particular game in any time, because
 * stores useful information obtained during the development of the game e.g. <i>lastBarMoved, lastPlayer,
 * nextPlayer, round, turn</i> and so on.
 * To achieve this key behaviour the class performs methods such as <i>refreshBoard(), refreshBead() </i> etc.
 *
 * <p>It is used by {@link GameEngine} class and {@link GameValidator} class.
 *
 * @see Bar
 * @see Player
 * @see SlotInfo
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */
public class Game {

    /**
     * List of bar in the board. 7 horizontal and 7 vertical.
     */
    private List<Bar> bars;
    /**
     * List of player playing the current game.
     */
    private List<Player> players;
    /**
     * Grid representing the board.
     */
    private SlotInfo[][] board;
    /**
     * Used to retrieve the last bar moved in the game.
     */
    private Bar lastBarMoved;
    /**
     * Used to retrieve the player who performed the last move. Possibly read from the UI.
     */
    private Player lastPlayer;
    /**
     * Used to retrieve the next player in turn. Possibly read from the UI.
     */
    private Player nextPlayer;
    /**
     * Used for statistics.
     * This attribute is updated in the makeMove(...) method
     */
    private int round;
    /**
     * Used for statistics.
     * This attribute is updated in the makeMove(...) method
     */
    private int turn;
    /**
     * Used to retrieve the loser or losers after a move. Possibly read from the UI.
     * This attribute is updated in the refreshBeads(Bar bar) method.
     */
    private List<Player> losersAfterTurn;
    /**
     * List of the bars move in the round.
     * Used when validating the rule of the bars moved by the opponents in the previous round.
     */
    private List<Bar> movedBarsInCurrentRound;
    /**
     * List of the bars move in the two previous rounds.
     * Used when validating the rule of the bars moved in the two consecutive turns by a player
     * when there are only two remaining players in the game.
     */
    private List<Bar> movedBarsInTwoPreviousRounds;

    /**
     * List of the bars move in the round.
     * Used when validating the rule of the bars moved by the opponents in the previous round.
     */
    private List<Move> movedBarsByOpponents;

    /**
     * This method will setup the initial state (<b>screenshot</b>) of the game: <i>bars,
     * players, beads and the board itself</i> and some additional attributes that help to have the most
     * accurate info of the board in order to manage the game and some statistics.
     *
     * <p>The method uses {@link #configureBar(BarOrientation)} to set up the composition of the
     * horizontal and vertical bars.
     *
     * @see SlotInfo
     */
    public void init(){
        //Initializing the board itself
        this.board = new SlotInfo[Constant.BOARD_INDEX][Constant.BOARD_INDEX];
        //setting-up the fixed composition for the horizontal Bars
        configureBar(BarOrientation.HORIZONTAL);
        //setting-up the fixed composition for the vertical Bars
        configureBar(BarOrientation.VERTICAL);
    }

    /**
     * This method sets new values for each slot in the board (column or row),
     * if it's a horizontal move (<b>RED</b> bars), updates only the value in the slot (to <b>RED</b> or <b>BLACK</b>), if the previous one
     * it's not blue (<b>BLUE</b> covers all the possible colors behind it). If it's a vertical move (<b>BLUE</b> bars)
     * updates the value of the slot, if the previous one it's not <b>RED</b> but if it is <b>RED</b>, does not update it if the current move
     * generates a <b>BLACK</b> slot but yes if generates a <b>BLUE</b> one.
     *
     * @see Bar
     * @see SlotInfo
     *
     * @param bar Only the slots corresponding to this bar will be updated.
     */
    public void refreshBoard(Bar bar){
        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
            if ( BarOrientation.HORIZONTAL.equals(bar.getOrientation()) ) {
                board[bar.getId()][i] = !SlotInfo.BLUE.equals(board[bar.getId()][i]) ?
                        bar.getKeys()[bar.getPosition().getInitialSlot() + i] : board[bar.getId()][i];
            } else {
                board[i][bar.getId()] = !SlotInfo.RED.equals(board[i][bar.getId()]) ? bar.getKeys()[bar.getPosition().getInitialSlot() + i] :
                        SlotInfo.BLACK.equals(bar.getKeys()[bar.getPosition().getInitialSlot() + i]) ?
                                board[i][bar.getId()] : bar.getKeys()[bar.getPosition().getInitialSlot() + i];
            }
        }
    }

    /**
     * This method <u>updates the status</u> of all the affected beads once a bar has been moved,
     * based on its coordinates. If the bar moved is <i>vertical</i>, checks
     * only all beads for which the <b>Y</b> coordinate is the same as the id of the bar, and the
     * <b>X</b> coordinate if it's a horizontal bar. The <u>updated status</u> will be <i>INACTIVE</i> for
     * all the beads allocated on a slot in the board which the current value is <i>BLACK</i> or <i>EMPTY</i>.
     *
     * <p>After updating the status of all the beads of a player, the status of the player <i>will be updated</i> too in such way
     * that, if the player doesn't have any active bead, the user will be <i>INACTIVE</i> too and it will be added
     * to the list of the looser players.
     *
     * <p>The method uses the {@link #activePlayers()} method to retrieve the current list of active players.
     * The {@link #addPlayerToListOfLosersAfterTurn(Player)} method to add a player to the list of losers if after
     * the current move the player has lost.
     *
     * @see Bar
     * @see Player
     * @see Bead
     * @see SlotInfo
     *
     * @param bar in which the last move has been made.
     */
    public void refreshBeads(Bar bar) {
        for ( Player player : activePlayers() ) {
            for ( Bead bead : player.activeBeads() ) {
                if ( BarOrientation.VERTICAL.equals(bar.getOrientation()) ) {//checking only Y coordinate
                    if ( bar.getId() == bead.getPosition().getY() ) {
                        //if the corresponding slot on the board is BLACK or EMPTY we should de-activate the bead
                        bead.setActive(!SlotInfo.BLACK.equals(board[bead.getPosition().getX()][bead.getPosition().getY()]));
                    }
                } else {//checking only X coordinate
                    if ( bar.getId() == bead.getPosition().getX() ) {
                        //if the corresponding slot on the board is BLACK or EMPTY we should de-activate the bead
                        bead.setActive(!SlotInfo.BLACK.equals(board[bead.getPosition().getX()][bead.getPosition().getY()]));
                    }
                }

            }
            //if there aren't active beads for the player, the player won't be active too
            if ( CommonUtil.isEmpty(player.activeBeads()) ){
                player.setActive(false);
                addPlayerToListOfLosersAfterTurn(player);
            }
        }
    }

    /**
     * This method returns all the bars present in the game for a given
     * <b>orientation</b>.
     *
     * @see Bar
     *
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     * @return {@code bar}
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
     * This method returns the list of the current active players in the game. A player is consider active
     * if and only if still has beads on the board.
     *
     * @see Player
     *
     * @return List of {@code active players} (at least 1).
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
     * This method finds a bar inside the <b>Board Game</b> in base of the id and the orientation.
     * Is used to retrieve an specific bar for reviewing the current state of the bar and perform
     * validations and other kind of operations.
     *
     * <p>The method uses the {@link Game#getBars(BarOrientation)} to retrieve all the bars in the board.
     *
     * @see Bar
     *
     * @param id From <i>0</i> to <i>6</i>
     * @param orientation <i>vertical</i> or <i>horizontal</i>
     * @return {@code bar} corresponding to the given parameters or
     *         {@code null} if such bar doesn't exist.
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
     * This method sets up the initial composition of every bar in the board.
     * Should be called just once per game. After calling this method a bar will be composed
     * of slots (<b>RED/BLUE</b> when it's cover or <b>BLACK</b> when it's not).
     * The method will configure all bars for the given <b>orientation</b>.
     *
     * <p>The method uses the {@link #getKeys(int, BarOrientation)} method for retrieving the fixed composition
     * of the bars, and the {@link #refreshBoard(Bar)} method for resetting the values of all slots in the game.
     *
     * @see Bar
     *
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     */
    private void configureBar(BarOrientation orientation) {
        this.bars = CommonUtil.isEmpty(this.bars) ?  new ArrayList<Bar>() : this.bars;

        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
            Bar bar = new Bar(i, orientation, getKeys(i, orientation));
            //initializing each slot in the board (RED, BLUE, EMPTY)
            //Log.d("Setup", MessageFormat.format("Bar[{0}-{1},{2}]", bar.getOrientation(),bar.getId(),bar.getPosition()));
            refreshBoard(bar);
            this.bars.add(bar);
        }
    }

    /**
     * This method is in charge of setting the composition of each slot in a bar,
     * this configuration <b>won't change</b> during the game, it will always be the same.
     *
     * @see SlotInfo
     *
     * @param id From <i>0</i> to <i>6</i>
     * @param orientation <i>horizontal</i> or <i>vertical</i>
     * @return {@code keys} for the given bar
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

    /**
     * This method initializes the list of bars moved by the opponents in the previous round.
     */
    public void resetMovedBarsInCurrentRound() {
        this.movedBarsInCurrentRound = new ArrayList<>();
    }

    /**
     * This method initializes the list of bars moved in two previous rounds.
     */
    public void resetMovedBarsInTwoPreviousRounds() {
        this.movedBarsInTwoPreviousRounds = new ArrayList<>();
    }

    /**
     * This method initializes the list of bars moved by all opponents.
     */
    public void resetMovedBarsByOpponents() {
        this.movedBarsByOpponents = new ArrayList<>();
    }

    /**
     * This method initializes the list of losers after a turn.
     */
    public void resetLosersAfterTurn(){
        this.losersAfterTurn = new ArrayList<>();
    }

    /**
     * This method adds a bar to the list of bars moved by the opponents in the previous round.
     *
     * @param bar current bar
     */
    public void addBarToListOfMovedBarsInRound(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInCurrentRound) ) {
             this.movedBarsInCurrentRound = new ArrayList<>();
        }
        this.movedBarsInCurrentRound.add(bar);
    }

    /**
     * This method adds a bar to the list of bars moved in two previous rounds.
     *
     * @param bar current bar
     */
    public void addBarToListOfMovedBarsInTwoPreviousRounds(Bar bar){
        if ( CommonUtil.isEmpty(this.movedBarsInTwoPreviousRounds) ) {
            this.movedBarsInTwoPreviousRounds = new ArrayList<>();
        }
        this.movedBarsInTwoPreviousRounds.add(bar);
    }

    /**
     * This method adds a player to the list of loser after a turn.
     *
     * @param player player to be removed from the game
     */
    private void addPlayerToListOfLosersAfterTurn(Player player) {
        if ( CommonUtil.isEmpty(this.losersAfterTurn) ) {
            this.losersAfterTurn = new ArrayList<>();
        }
        this.losersAfterTurn.add(player);
    }

    /**
     * This method adds a player to the list of moved bars.
     */
    public void addBarToListOfMovedBarsByOpponents(Player player, Bar bar) {
        if ( CommonUtil.isEmpty(this.movedBarsByOpponents) ) {
            this.movedBarsByOpponents = new ArrayList<>();
        }
        this.movedBarsByOpponents.add(new Move(player, bar));
    }

    /**
     * This method moves the current round of the game to the next i.e adds one to
     * the current value of <tt>round</tt>.
     */
    public void moveRound(){
        this.round += 1;
    }

    /**
     * This method sets the value of the <tt>turn</tt> to "1" again. Should be called only when the round has finished.
     */
    public void restartTurn() {
        this.turn = 1;
    }

    /**
     * This method moves the turn from one user to the next. Does not retrieve the next user in turn, just
     * adds one to the current value of <tt>turn</tt>.
     */
    public void moveTurn() {
        this.turn += 1;
    }

    /**
     *
     * This method adds a player to the current list of players in the game.
     *
     * @param player to be added to the list.
     */
    public void addPlayer(Player player) {
        if ( CommonUtil.isEmpty(players) ) {
            players = new ArrayList<>();
        }
        players.add(player);
    }

    //From here, just constructors, getters and setters

    public Game() {
        this.round = 1;
        this.turn = 1;
    }

    public SlotInfo[][] getBoard() {
        return board;
    }

    public void setBoard(SlotInfo[][] board) {
        this.board = board;
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

    public List<Bar> getMovedBarsInTwoPreviousRounds() {
        return movedBarsInTwoPreviousRounds;
    }

    public List<Move> getMovedBarsByOpponents() {
        return movedBarsByOpponents;
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
