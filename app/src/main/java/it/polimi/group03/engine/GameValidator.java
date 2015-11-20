package it.polimi.group03.engine;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Game;
import it.polimi.group03.domain.MessageError;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;

/**
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 */
public class GameValidator {

    private Game game;
    private MessageError messageError;

    public GameValidator(Game game){
        this.game = game;
        this.messageError = new MessageError();
    }

    /**
     * This method checks if the number of player is no more than 4.
     *
     * @return {@code true} if the number of players is less or equal than 4
     *         {@code false} if not.
     */
    public boolean isNumberOfPlayersValid(){
        if ( game.getPlayers() != null && game.getPlayers().size() > 1 &&
                game.getPlayers().size() <= 4 ){
            return true;
           // this.messageError.setCode("01");
            //this.messageError.setMessage("");
            //return messageError;
        }
        //return messageError;
        return false;
    }

    /**
     * This method checks whether the player movement complies with the rules established for the game. Further information in the <i>Reference</i> section.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #isPreviousPlayerDifferentFromCurrentPlayer(Player)}<br/>
     * {@link #isPositionMoveValid(Bar, BarPosition)}<br/>
     * {@link #isSelectedBarMoveValid(Bar)}<br/>
     * {@link #isSelectedBarMoveValidTwoPlayers(Bar)}<br/>
     * @see #isPreviousPlayerDifferentFromCurrentPlayer(Player)
     * @see #isPositionMoveValid(Bar, BarPosition)
     * @see #isSelectedBarMoveValid(Bar)
     * @see #isSelectedBarMoveValidTwoPlayers(Bar)
     *
     * @param selectedBar the bar the player wants to move
     * @param targetPosition the target position of the bar
     * @param currentPlayer the player making the move
     * @return {@code true} if the movement is allowed and
     *         {@code false} if not.
     */
    public boolean isMoveValid(Bar selectedBar, BarPosition targetPosition, Player currentPlayer){
        //check if the movement of the bar is allowed
        if ( isPreviousPlayerDifferentFromCurrentPlayer(currentPlayer) ) {
            if ( isPositionMoveValid(selectedBar, targetPosition) ) {
                if ( isSelectedBarMoveValid(selectedBar) ) {
                    //if there are only two players left is necessary another validation
                    if (game.activePlayers().size() == 2) {
                        if (isSelectedBarMoveValidTwoPlayers(selectedBar)) {
                          //  return messageError;
                        }
                    } else {
                        //return messageError;
                    }
                }
            }
        }
        //return messageError;
        return false;
    }

    /**
     * This method checks the intended movement of the bar with respect of the final position. A player can’t slide a bar
     * directly from the inner to the outer position or vice versa. In order to check this rule, the method needs the
     * information of the bar that the player is pretending to move, this way is possible the bar in the game; is also
     * necessary the target position to check whether the movement is permitted or not.
     *
     * @param selectedBar the bar the player wants to move
     * @param targetPosition the target position of the bar
     * @return {@code true} if the movement is allowed and
     *         {@code false} if not.
     */
    private boolean isPositionMoveValid(Bar selectedBar, BarPosition targetPosition){
        Bar sourceBar = game.findBar(selectedBar.getId(),selectedBar.getOrientation());

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
     * of the bar that the player is pretending to move, this way is possible to recognize the bar in the game and determine
     * whether the bar was moved in a previous turn of the current round.
     *
     * @param selectedBar the bar the player wants to move
     * @return {@code true} if the movement is allowed and
     *         {@code false} if not.
     */
    private boolean isSelectedBarMoveValid(Bar selectedBar){
        for ( Bar bar : game.getMovedBarsInCurrentRound() ) {
            if ( bar.getId() == selectedBar.getId() && bar.getOrientation().equals(selectedBar.getOrientation()) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method checks the intended movement of the bar with respect of the selected bar when there are only 2 players in the game.
     * When only two players are left, a player cannot slide the same bar for more than two consecutive turns. In order to check
     * this rule, the method needs the information of the bar that the player is pretending to move, this way is possible to recognize
     * the bar in the game and determine whether the bar was moved in the two previous turns of the player.
     *
     * @param selectedBar the bar the player wants to move.
     * @return {@code true} if the movement is allowed and
     *         {@code false} if not.
     */
    private boolean isSelectedBarMoveValidTwoPlayers(Bar selectedBar){
        //This validation means there are at least two rounds played for the 2 players left in the game (4 movements in total)
        if ( !CommonUtil.isEmpty(game.getMovedBarsInTwoPreviousRound()) && game.getMovedBarsInTwoPreviousRound().size() >= 4 ){
            Bar bar = game.findBar(selectedBar.getId(),selectedBar.getOrientation());
            //size-2 represents to the first previous turn for the current player
            //size-4 represents to the second previous turn for the current player
            //in the occurrence of the equivalence of moved bars with intended bar, the condition is not met
            if( bar.equals(game.getMovedBarsInTwoPreviousRound().get(game.getMovedBarsInTwoPreviousRound().size() -2 ) ) &&
                bar.equals(game.getMovedBarsInTwoPreviousRound().get(game.getMovedBarsInTwoPreviousRound().size() -4 )) ){
                return false;
            }
        }

        return true;
    }

    /**
     * This method checks that a player doesn't slide a bar two consecutive times in the same round.
     *
     * @param currentPlayer player making the move
     * @return {@code true} if the player making the move is different from the one that made the move in the previous turn
     *         {@code false} if not.
     */
    private boolean isPreviousPlayerDifferentFromCurrentPlayer(Player currentPlayer){
        if (game.getLastPlayer() == null || ( game.getLastPlayer() != null && game.getLastPlayer().getId() != currentPlayer.getId()) ) {
            return true;
        }

        return false;
    }

}