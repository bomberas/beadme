package it.polimi.group03.engine;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.SlotInfo;

/**
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 */
public class GameValidator {

    private Board board;

    public GameValidator(Board board){
        this.board = board;
    }

    /**
     * This method checks if the number of player is no more than 4.
     *
     * @return <tt>true</tt> if the number of players is less or equal than 4
     *         <tt>false</tt> if not.
     */
    public boolean isMaxNumberOfPlayersValid(){
        if (board.getPlayers().size() <= 4 ){
            return true;
        }
        return false;
    }

    /**
     * This method checks whether the player movement complies with the rules established for the game. Further information in the
     * methods <b>isPreviousPlayerDifferentFromCurrentPlayer()</b>, <b>isPositionMoveValid()</b>, <b>isSelectedBarMoveValid()</b> and
     * <b>isSelectedBarMoveValidTwoPlayers()</b>
     *
     * @param selectedBar the bar the player wants to move
     * @param targetPosition the target position of the bar
     * @param currentPlayer the player making the move
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    public boolean isMoveValid(Bar selectedBar, BarPosition targetPosition, Player currentPlayer){
        //check if the movement of the bar is allowed
        if ( isPreviousPlayerDifferentFromCurrentPlayer(currentPlayer) ) {
            if ( isPositionMoveValid(selectedBar, targetPosition) ) {
                if ( isSelectedBarMoveValid(selectedBar) ) {
                    //if there are only two players left is necessary another validation
                    if (board.activePlayers().size() == 2) {
                        if (isSelectedBarMoveValidTwoPlayers(selectedBar)) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method will check if the player can place a bead on the the board; in order to do so
     * first it will check the number of beads for the player, then
     * if the position of the new bead is valid.
     * @param currentPlayer player who is trying to place a bead
     * @param newBead current bead
     * @return <tt>true</tt> <u>if the number of beads for the player is lower than 5</u> and
     *                       <u>if the position in which the bead was placed is BLUE or RED</u> and
     *                       <u>if there isn't already a bead in the position selected</u>.<br/>
     *         <tt>false</tt> in any other case.
     */
    public boolean isBeadValid(Player currentPlayer, Bead newBead) {
        //You can not add more than 5 bead per player
        if ( CommonUtil.isEmpty(currentPlayer.getBeads()) || currentPlayer.getBeads().size() < 5 ) {
            //You can not place a bead above an empty slot
            if ( !SlotInfo.BLACK.equals(board.getGrid()[newBead.getPosition().getX()][newBead.getPosition().getY()]) ) {
                boolean hasTaken = false;
                for ( Player player : board.getPlayers() ) {
                    for ( Bead bead : player.getBeads() ) {
                        //You can not add a bead on a slot already taken
                        if ( bead.getPosition().getX() == newBead.getPosition().getX() &&
                             bead.getPosition().getY() == newBead.getPosition().getY() ) {
                            hasTaken = true;
                        }
                    }
                }
                return !hasTaken;
            }
        }

        return false;
    }
    /**
     * This method checks the intended movement of the bar with respect of the final position. A player can’t slide a bar
     * directly from the inner to the outer position or vice versa. In order to check this rule, the method needs the
     * information of the bar that the player is pretending to move, this way is possible the bar in the board; is also
     * necessary the target position to check whether the movement is permitted or not.
     *
     * @param selectedBar the bar the player wants to move
     * @param targetPosition the target position of the bar
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    private boolean isPositionMoveValid(Bar selectedBar, BarPosition targetPosition){
        Bar sourceBar = board.findBar(selectedBar.getId(),selectedBar.getOrientation());

        if ( targetPosition.equals(BarPosition.CENTRAL) && (sourceBar.getPosition().equals(BarPosition.INNER)
            || sourceBar.getPosition().equals(BarPosition.OUTER)) ) {
                return true;
        } else if ( targetPosition.equals(BarPosition.INNER) && sourceBar.getPosition().equals(BarPosition.CENTRAL) ) {
            return true;
        } else if ( targetPosition.equals(BarPosition.OUTER) && sourceBar.getPosition().equals(BarPosition.CENTRAL) ) {
            return true;
        }

        return false;
    }

    /**
     * This method checks the intended movement of the bar with respect of the selected bar. A player can’t slide a bar that
     * was slid in the previous turn by one of his/her opponents. In order to check this rule, the method needs the information
     * of the bar that the player is pretending to move, this way is possible to recognize the bar in the board and determine
     * whether the bar was moved in a previous turn of the current round.
     *
     * @param selectedBar the bar the player wants to move
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    private boolean isSelectedBarMoveValid(Bar selectedBar){
        for ( Bar bar : board.getMovedBarsInCurrentRound() ) {
            if ( bar.getId() == selectedBar.getId() && bar.getOrientation().equals(selectedBar.getOrientation()) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method checks the intended movement of the bar with respect of the selected bar when there are only 2 players in the game.
     * When only two players are left, a player cannot slide the same bar for more than two consecutive turns. In order to check
     * this rule, the method needs the information of the bar that the player is pretending to move, this way is possible to recognize
     * the bar in the board and determine whether the bar was moved in the two previous turns of the player.
     *
     * @param selectedBar the bar the player wants to move.
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    private boolean isSelectedBarMoveValidTwoPlayers(Bar selectedBar){
        //This validation means there are at least two rounds played for the 2 players left in the game (4 movements in total)
        if ( !CommonUtil.isEmpty(board.getMovedBarsInTwoPreviousRound()) && board.getMovedBarsInTwoPreviousRound().size() >= 4 ){
            Bar bar = board.findBar(selectedBar.getId(),selectedBar.getOrientation());
            //size-2 represents to the first previous turn for the current player
            //size-4 represents to the second previous turn for the current player
            //in the occurrence of the equivalence of moved bars with intended bar, the condition is not met
            if( bar.equals(board.getMovedBarsInTwoPreviousRound().get(board.getMovedBarsInTwoPreviousRound().size() -2 ) ) &&
                bar.equals(board.getMovedBarsInTwoPreviousRound().get(board.getMovedBarsInTwoPreviousRound().size() -4 )) ){
                return false;
            }
        }

        return true;
    }

    /**
     * This method checks that a player doesn't slide a bar two consecutive times in the same round.
     *
     * @param currentPlayer player making the move
     * @return <tt>true</tt> if the player making the move is different from the one that made the move in the previous turn
     *         <tt>false</tt> if not.
     */
    private boolean isPreviousPlayerDifferentFromCurrentPlayer(Player currentPlayer){
        if (board.getLastPlayer() == null || ( board.getLastPlayer() != null && board.getLastPlayer().getId() != currentPlayer.getId()) ) {
            return true;
        }

        return false;
    }

}