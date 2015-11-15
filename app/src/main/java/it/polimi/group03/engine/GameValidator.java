package it.polimi.group03.engine;

import java.util.List;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;

/**
 * Created by tatibloom on 11/11/2015.
 */
public class GameValidator {

    /**
     * @author cecibloom
     * @author megireci
     * @author tatibloom
     * This method will validate the initial constraint for starting a <b>new</> game.
     **/

    private Board board;

    public GameValidator(Board board){
        this.board = board;
    }

    /**
     * This method check whether the player movement complies with the rules established for the game. Further information in the
     * methods isPositionMoveValid() isSelectedBarMoveValid() and isSelectedBarMoveValidTwoPlayers()
     * @param id the id of the bar the player wants to move
     * @param orientation the orientation of the bar (horizontal or vertical)
     * @param targetPosition the target position of the bar
     * @param currentPlayer the player making the move
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    public boolean isMoveValid(int id, BarOrientation orientation, BarPosition targetPosition, Player currentPlayer){
        //check if the movement of the bar is allowed
        if (isPositionMoveValid(id, orientation, targetPosition)){
            //if there are only two players left is necessary another validation
            if (board.getActiveNumberOfPlayers() == 2 ){
                if(isSelectedBarMoveValidTwoPlayers(id,orientation)){
                    return true;
                }else {
                    return false;
                }
            }else {
                return true;
            }
        }
        return false;

    }

    /**
     * This method check the intended movement of the bar with respect of the final position. A player can’t slide a bar
     * directly from the inner to the outer position or vice versa. In order to check this rule, the method needs the id of
     * the bar the player wants to move and the orientation of this bar: Horizontal or Vertical, this way is possible to recognize
     * the bar in the board; is also necessary the target position to check whether the movement is permitted or not.
     * @param id the id of the bar the player wants to move
     * @param orientation the orientation of the bar (horizontal or vertical)
     * @param targetPosition the target position of the bar
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    private boolean isPositionMoveValid(int id, BarOrientation orientation, BarPosition targetPosition){
        Bar sourceBar = board.findBar(id,orientation);

        if(targetPosition.equals(BarPosition.CENTRAL) && (sourceBar.getPosition().equals(BarPosition.INNER)
            || sourceBar.getPosition().equals(BarPosition.OUTER))){
                return true;
        }else if(targetPosition.equals(BarPosition.INNER) && sourceBar.getPosition().equals(BarPosition.CENTRAL)){
            return true;
        }else {if(targetPosition.equals(BarPosition.OUTER) && sourceBar.getPosition().equals(BarPosition.CENTRAL)){
            return true;
        }

            return false;
        }
    }

    /**
     * This method check the intended movement of the bar with respect of the selected bar. A player can’t slide a bar that
     * was slid in the previous turn by one of your opponents. In order to check this rule, the method needs the id of
     * the bar the player wants to move and the orientation of this bar: Horizontal or Vertical, this way is possible to recognize
     * the bar in the board and determined whether the bar was moved in a previous turn of the current round.
     * @param id the id of the bar the player wants to move
     * @param orientation the orientation of the bar (horizontal or vertical)
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    private boolean isSelectedBarMoveValid(int id, BarOrientation orientation){
        for ( Bar bar : board.getMovedBarsInCurrentRound() ) {
            if ( bar.getId() == id && orientation.equals(bar.getOrientation())) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method check the intended movement of the bar with respect of the selected bar when there are only 2 players in the game.
     * When only two players are left, a player cannot slide the same bar for more than two consecutive turns.In order to check
     * this rule, the method needs the id of the bar the player wants to move and the orientation of this bar: Horizontal or Vertical,
     * this way is possible to recognize the bar in the board and determined whether the bar was moved in the two previous turns of
     * the player.
     * @param id the id of the bar the player wants to move
     * @param orientation the orientation of the bar (horizontal or vertical)
     * @return <tt>true</tt> if the movement is allowed and
     *         <tt>false</tt> if not.
     */
    private boolean isSelectedBarMoveValidTwoPlayers(int id, BarOrientation orientation){
        //This validation means there at least two rounds played for the 2 players left in the game (4 movements in total)
        if (!CommonUtil.isEmpty(board.getMovedBarsInTwoPreviousRound()) && board.getMovedBarsInTwoPreviousRound().size() >= 4){
            Bar bar = board.findBar(id,orientation);
            //size-2 represents to the first previous turn for the current player
            //size-4 represents to the second previous turn for the current player
            //in the occurence of the equivalence of moved bars with intended bar, the condition is not met
            if( bar.equals(board.getMovedBarsInTwoPreviousRound().get(board.getMovedBarsInTwoPreviousRound().size() -2 )) &&
                bar.equals(board.getMovedBarsInTwoPreviousRound().get(board.getMovedBarsInTwoPreviousRound().size() -4 ))){
                return false;
            }
        }

        return true;
    }
}
