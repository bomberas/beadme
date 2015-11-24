package it.polimi.group03.engine;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Game;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;

/**
 *
 * This class consists exclusively of validation methods that operate on a {@link Game} object.
 *
 * <p>Each method is responsible for checking the compliance of a specific rule established for the game.
 *
 * <p>The methods of this class all return a {@link StatusMessage} object that indicates the final result of the
 * validation. The code s0x0 means there was no violation to none of the rules. Any other
 * code indicates that at least one violation to the rules of the game was committed, the message returned specifies the
 * exact violation.
 *
 * @see Game
 * @see StatusMessage
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 *
 */
public class GameValidator {

    private Game game;

    public GameValidator(Game game){
        //Needs an object game on which will perform all kind of validations
        this.game = game;
    }

    /**
     * This method checks if the number of player is valid i.e more than 1 and no more than 4.
     *
     * @see Game
     * @see StatusMessage
     *
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.<br/><br/>
     *         The {@code code} is <b>s0x0</b> if the number of players is allowed.<br/><br/>
     *                      And is <b>e0x1</b> in any other case.
     */
    public StatusMessage validateNumberOfPlayers(){

        StatusMessage statusMessage = new StatusMessage();

        if ( game.getPlayers() == null || game.getPlayers().size() < Constant.GAME_MAX_NUMBER_PLAYERS ){
            statusMessage.setCode(Constant.STATUS_OK);
            statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
        } else {
            statusMessage.setCode(Constant.STATUS_ERR_NUMBER_PLAYERS);
            statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
        }
        return statusMessage;
    }

    /**
     * This method checks whether the movement a player is trying to do complies with the rules established for the game.
     * Further information in the <i>Reference</i> section.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #validateCurrentPlayerDifferentFromPreviousPlayer(Player)}<br/>
     * {@link #validateBarPositionMove(Bar, BarPosition)}<br/>
     * {@link #validateSelectedBarMove(Bar)}<br/>
     * {@link #validateConsecutiveBarMove(Bar)}<br/>
     *
     * @see #validateCurrentPlayerDifferentFromPreviousPlayer(Player)
     * @see #validateBarPositionMove(Bar, BarPosition)
     * @see #validateSelectedBarMove(Bar)
     * @see #validateConsecutiveBarMove(Bar)
     * @see StatusMessage
     *
     * @param selectedBar the bar the player wants to move
     * @param targetPosition the target position of the bar
     * @param currentPlayer the player making the move
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.
     */
    public StatusMessage validateMove(Bar selectedBar, BarPosition targetPosition, Player currentPlayer){

        //checks if the movement of the bar is allowed
        StatusMessage statusMessage = validateCurrentPlayerDifferentFromPreviousPlayer(currentPlayer);

        if ( statusMessage.getCode().equals(Constant.STATUS_OK) ) {
            //checks is the final position is valid
            statusMessage = validateBarPositionMove(selectedBar, targetPosition);

            if ( statusMessage.getCode().equals(Constant.STATUS_OK) ) {
                //checks the bar against the previous moved bars
                statusMessage = validateSelectedBarMove(selectedBar);

                if ( statusMessage.getCode().equals(Constant.STATUS_OK) ) {
                    //if there are only two players left is necessary another validation

                    if (game.activePlayers().size() == 2) {
                        //checks the bar in the previous two consecutive turns
                        statusMessage = validateConsecutiveBarMove(selectedBar);

                        if (statusMessage.getCode().equals(Constant.STATUS_OK)) {
                            return statusMessage;
                        }
                    } else {
                        return statusMessage;
                    }
                }
            }
        }
        return statusMessage;
    }

    /**
     * This method checks that a player does not play two times in a row.
     *
     * @see Game
     * @see Player
     * @see StatusMessage
     *
     * @param currentPlayer player making the move
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.<br/><br/>
     *         The {@code code} is <b>s0x0</b> if the player is not making two consecutive moves.<br/><br/>
     *                      And is <b>e0x5</b> in any other case.
     */
    public StatusMessage validateCurrentPlayerDifferentFromPreviousPlayer(Player currentPlayer){

        StatusMessage statusMessage = new StatusMessage();

        if ( game.getLastPlayer() == null || ( game.getLastPlayer().getId() != currentPlayer.getId()) ) {
            statusMessage.setCode(Constant.STATUS_OK);
            statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
            return statusMessage;
        } else {
            statusMessage.setCode(Constant.STATUS_ERR_SAME_PREVIOUS_PLAYER);
            statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
            return statusMessage;
        }
    }

    /**
     * This method checks that a player places a bead in an allowable position; in order to do so
     * first checks the number of beads the player has already placed, then checks if the position of the new bead
     * is valid  i.e if the new bead is not placed in a holed slot or if there is already a bead in the selected slot.
     *
     * @see Game
     * @see Player
     * @see Bead
     * @see SlotInfo
     * @see StatusMessage
     *
     * @param currentPlayer player in turn trying to place a bead
     * @param newBead current bead
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.<br/><br/>
     *         The {@code code} is <b>s0x0</b> if the number of beads for the player is lower than 5 and
     *                                        if the position in which the bead was placed is BLUE or RED and
     *                                        if there isn't already a bead in the position selected.<br/><br/>
     *                      And is <b>e0x6</b> in any other case.
     */
    public StatusMessage validatePlacedBead(Player currentPlayer, Bead newBead) {

        StatusMessage statusMessage = new StatusMessage();

        //A player cannot add more than 5 bead per player
        if ( CommonUtil.isEmpty(currentPlayer.getBeads()) || currentPlayer.getBeads().size() < Constant.GAME_MAX_NUMBER_BEADS ) {
            //A player cannot place a bead above an empty slot
            if ( !SlotInfo.BLACK.equals(game.getBoard()[newBead.getPosition().getX()][newBead.getPosition().getY()]) ) {
                for ( Player player : game.getPlayers() ) {
                    for ( Bead bead : player.getBeads() ) {
                        //A player cannot add a bead on a slot already taken
                        if ( bead.getPosition().getX() == newBead.getPosition().getX() &&
                                bead.getPosition().getY() == newBead.getPosition().getY() ) {
                            statusMessage.setCode(Constant.STATUS_ERR_PLACED_BEAD);
                            statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
                            return statusMessage;
                        }
                    }
                }
                statusMessage.setCode(Constant.STATUS_OK);
                statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
                return statusMessage;
            }
        }
        statusMessage.setCode(Constant.STATUS_ERR_PLACED_BEAD);
        statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
        return statusMessage;
    }

    /**
     * This method checks the intended movement of the bar with respect of the final position. A bar cannot be slide
     * directly from the inner to the outer position or vice versa. In order to check this rule, the method needs the
     * information of the bar that the player is pretending to move, this way is possible recognize the bar in the game; is also
     * necessary the target position to check whether the movement is permitted or not.
     *
     * @see Game
     * @see Player
     * @see StatusMessage
     *
     * @param selectedBar the bar the player wants to move
     * @param targetPosition the target position of the bar
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.
     *         The {@code code} is <b>s0x0</b> if the bar is being moved correctly.<br/><br/>
     *                      And is <b>e0x2</b> in any other case.
     */
    private StatusMessage validateBarPositionMove(Bar selectedBar, BarPosition targetPosition){

        Bar sourceBar = game.findBar(selectedBar.getId(),selectedBar.getOrientation());
        StatusMessage statusMessage = new StatusMessage();
        statusMessage.setCode(Constant.STATUS_OK);
        statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));

        if ( targetPosition.equals(BarPosition.CENTRAL) && (sourceBar.getPosition().equals(BarPosition.INNER)
             || sourceBar.getPosition().equals(BarPosition.OUTER)) ) {
             return statusMessage;
        } else if ( targetPosition.equals(BarPosition.INNER) && sourceBar.getPosition().equals(BarPosition.CENTRAL) ) {
             return statusMessage;
        } else if ( targetPosition.equals(BarPosition.OUTER) && sourceBar.getPosition().equals(BarPosition.CENTRAL) ) {
             return statusMessage;
        }

        statusMessage.setCode(Constant.STATUS_ERR_BAR_POSITION);
        statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
        return statusMessage;
    }

    /**
     * This method checks that the bar selected by the player was not slid by any of his/her opponents in the previous turn.
     * In order to check this rule, the method needs the information of the bar the player is pretending to move, this way is
     * possible to recognize the bar in the game and determine whether the bar was moved in a previous turn of the current round.
     *
     * @see Game
     * @see StatusMessage
     *
     * @param selectedBar the bar the player wants to move
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.<br/><br/>
     *         The {@code code} is <b>s0x0</b> if the player is not moving a bar that was moved in the round for one of his/her opponents.<br/><br/>
     *                      And is <b>e0x3</b> in any other case.
     */
    private StatusMessage validateSelectedBarMove(Bar selectedBar){

        StatusMessage statusMessage = new StatusMessage();

        for ( Bar bar : game.getMovedBarsInCurrentRound() ) {
            if ( bar.getId() == selectedBar.getId() && bar.getOrientation().equals(selectedBar.getOrientation()) ) {
                 statusMessage.setCode(Constant.STATUS_ERR_BAR_SELECTED);
                 statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
                 return statusMessage;
            }
        }

        statusMessage.setCode(Constant.STATUS_OK);
        statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
        return statusMessage;
    }

    /**
     * This method checks the movement that a player tries to do when there are only two players left in the game, in order to do it
     * validates the selected bar against the bars slid in the two previous turns of the player.
     * In order to check this rule, the method needs the information of the bar that the player is pretending to move, this way is possible to recognize
     * the bar in the game and determine whether the bar was moved.
     *
     * @see Game
     * @see Bar
     * @see StatusMessage
     *
     * @param selectedBar the bar the player wants to move.
     * @return {@code statusMessage} indicating the result of the validation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.<br/><br/>
     *         The {@code code} is <b>s0x0</b> if the player is not moving the same bar he/she moved in the previous two turns.<br/><br/>
     *                      And is <b>e0x4</b> in any other case.
     */
    private StatusMessage validateConsecutiveBarMove(Bar selectedBar){

        StatusMessage statusMessage = new StatusMessage();

        //This validation checks if there are at least two rounds played for the 2 players left in the game (4 movements in total)
        if ( !CommonUtil.isEmpty(game.getMovedBarsInTwoPreviousRounds()) && game.getMovedBarsInTwoPreviousRounds().size() >= 4 ){
            Bar bar = game.findBar(selectedBar.getId(),selectedBar.getOrientation());
            //size-2 represents the first previous turn for the current player
            //size-4 represents the second previous turn for the current player
            //in the occurrence of the equivalence of moved bars with intended bar, the condition is not met
            if( bar.equals(game.getMovedBarsInTwoPreviousRounds().get(game.getMovedBarsInTwoPreviousRounds().size() - 2 ) ) &&
                bar.equals(game.getMovedBarsInTwoPreviousRounds().get(game.getMovedBarsInTwoPreviousRounds().size() - 4 )) ){
                statusMessage.setCode(Constant.STATUS_ERR_BAR_CONSECUTIVE);
                statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
                return statusMessage;
            }
        }

        statusMessage.setCode(Constant.STATUS_OK);
        statusMessage.setMessage(CommonUtil.getMessageDescription(statusMessage.getCode()));
        return statusMessage;
    }
}