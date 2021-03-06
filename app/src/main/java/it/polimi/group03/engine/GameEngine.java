package it.polimi.group03.engine;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Game;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.Constant;

/**
 *
 * This class is the one in charge of performing all related things to fulfill with the mechanics of the game. Should
 * be used when one of the following actions is required:
 *
 * <ul style="list-style-type:circle">
 * <li>Start a new game.</li>
 * <li>Add a player to a game.</li>
 * <li>Put a bead on the board.</li>
 * <li>Perform a move.</li>
 * <li>Obtain the winner.</li>
 * </ul>
 *
 * <p>This class fully depends on the {@link Game} class to store some values of the game such as: the players,
 * the movements, the beads, and the general state of the game and it makes use of the {@link GameValidator} for
 * checking the rules.
 *
 * @see Game
 * @see GameValidator
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class GameEngine {

    private Game game;
    private GameValidator validator;

    /**
     * This method initializes some attributes in order the prepare the game for the start. This method initializes the board,
     * configure the bars, also initializes the lists that will contain the bars move during the game.
     * Should be called whenever a new game is wanted.
     *
     * @see Game
     *
     **/
    public void startGame() {
        this.game = new Game();
        this.validator = new GameValidator(this.game);
        this.game.init();
        this.game.resetMovedBarsByOpponents();
        this.game.resetLosersAfterTurn();
    }

    /**
     * This method adds a new player to the game <u>if and only if</u>
     * it complies with the rules established by the Game.
     *
     * <p>Refer to the {@link GameValidator#validateNumberOfPlayers()} method for
     * further information about the validation and object returned.
     *
     * @see GameValidator
     * @see Player
     * @see StatusMessage
     *
     * @param player new player to be added to the game
     * @return {@code statusMessage} indicating the result of the operation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.
     */
    public StatusMessage addPlayer(Player player) {
        StatusMessage statusMessage = this.validator.validateNumberOfPlayers();

        if ( statusMessage.getCode().equals(Constant.STATUS_OK) ) {
            this.game.addPlayer(player);
        }

        this.game.setNextPlayer(this.game.getPlayers().get(0));

        return statusMessage;
    }

    /**
     * This method adds a new bead for the selected player <u>if and only if</u>
     * it complies with the rules established by the Game.
     *
     * <p>Refer to the {@link GameValidator#validatePlacedBead(Player, Bead)} method for
     * further information about the validation and object returned.
     *
     * @see GameValidator
     * @see Player
     * @see StatusMessage
     *
     * @param player the current player who is making the move
     * @param bead bead to be placed on the board
     * @return {@code statusMessage} indicating the result of the operation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.
     */
    public StatusMessage addBeadToBoard(Player player, Bead bead) {
        StatusMessage statusMessage = this.validator.validatePlacedBead(player, bead);

        if ( statusMessage.getCode().equals(Constant.STATUS_OK) ) {
            bead.setPlaced(true);
            player.addBead(bead);
        }

        return statusMessage;
    }

    /**
     * This method should be called when a player tries to make a move, before refreshing the state of the game,
     * it will perform a validation according to the rules established for the game. If the move is permitted
     * then some changes are made: the new position of the bar is set, the board is refresh with the new values after
     * the movement of the bar, the beads placed on the board are refresh in the case of any falling off the board,
     * the turn is moved and also the round if is the case.
     *
     * <p>Whenever the move is made without any problem the method returns a valid response so the controller can ask for
     * the result of the movement i.e check for any loser.
     *
     * <p>Refer to the {@link GameValidator#validateMove(Bar, BarPosition, Player)} method for
     * further information about the validation and object returned.
     *
     * @see Game
     * @see GameValidator
     * @see StatusMessage
     *
     * @param id Moved bar <b>id</b>
     * @param orientation Moved bar <b>orientation</b>
     * @param targetPosition Moved bar <b>position</b> (target position)
     * @param currentPlayer Player making move
     * @return {@code statusMessage} indicating the result of the operation. This object contains a
     *         {@code code} which specifies the code of the error <i>if any</i> and a
     *         {@code message} with a brief description.
     */
    public StatusMessage makeMove(int id, BarOrientation orientation, BarPosition targetPosition, Player currentPlayer) {
        Bar bar = this.game.findBar(id,orientation);
        StatusMessage statusMessage = this.validator.validateMove(bar, targetPosition, currentPlayer);

        if ( !statusMessage.getCode().equals(Constant.STATUS_OK) ) {
            //Log.i("GameEngine.makeMove","Invalid move!");
            return statusMessage;
        }
        //Log.i("GameEngine.makeMove", MessageFormat.format("Player {0} authorized to move {1} bar number {2} to {3}", currentPlayer.getNickname(), bar.getOrientation().toString(), bar.getId(),
        //        targetPosition.toString()));

        // Giving that the move is permitted is necessary to update the position of the selected bar
        bar.setPosition(targetPosition);

        // Is necessary reset the list of losers of the previous turn, so when the UI call "getLosersAfterTurn()" gets the updated list of loser of current turn
        this.game.resetLosersAfterTurn();

        this.game.setLastPlayer(currentPlayer);
        this.game.setLastBarMoved(bar);
        this.game.refreshBoard();
        this.game.refreshBeads(bar);

        // Only if the game is not finished with this move we'll perform some actions
        if ( !isGameEndConditionReached() ) {
            this.game.moveTurn(); //this is for the statistics
            this.game.setNextPlayer(getNextPlayer(currentPlayer));
            // In any case is necessary to update the list of moved bars in the current round putting the selected bar in the list
            // of moved bars so in the next turn the list will be updated for further validations
            refreshMovedBarsInRound(currentPlayer, bar);

            // If after the move made by the player the round is complete and the game is not finished is necessary to move the round
            // and restart the turn, these two attributes will be possibly use in the statistics
            if ( isRoundFinished(currentPlayer) ) {
                this.game.restartTurn(); //this is for statistics
                this.game.moveRound(); //this is for statistics
            }
        }
        return statusMessage;
    }

    /**
     *
     * This method checks if there is only one player active in the game after a move has been made, which indicates that the game
     * has reached the end. This method should be called from the UI to update the final result and show it to the players.
     *
     * @see Game
     *
     * @return {@code true} if the are only 1 or 0 players remaining in the game. <br/>
     *         {@code false} if there is more than one player in the game.
     */
    public boolean isGameEndConditionReached() {
        return this.game.activePlayers().size() <= 1;
    }

    /**
     *
     * This method checks if all players have placed the {@link Constant#GAME_MAX_NUMBER_BEADS} on the board which indicates that the game
     * has met the basic condition to start. This method should be called from the UI to update the mobility of the bars.
     *
     * @see Game
     *
     * @return {@code true} if all beads have been placed on the board.<br/>
     *         {@code false} if there is still beads that haven´t been placed.
     */
    public boolean isGameStartConditionReached() {
        int count = 0;
        for (Player player: this.game.getPlayers()) {
            for (Bead bead: player.getBeads()) {
                if (bead.isPlaced()) count++;
            }

        }
        return this.game.getPlayers().size() * Constant.GAME_MAX_NUMBER_BEADS == count;
    }

    /**
     * This method checks whether there is a winner or not, there is a winner only when the game has finished,
     * in this case the method retrieves the winning player.
     *
     * @see Game
     *
     * @return {@code player} the winner if there is any.
     *         {@code null} if there is no winner yet.
     */
    public Player getWinner(){
        if ( isGameEndConditionReached() ) {
            switch (this.game.activePlayers().size()) {
                case 0:
                    return this.game.getLastPlayer();
                case 1:
                    return this.game.activePlayers().get(0);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    /**
     * This method checks whether the current round has finished or not. It is necessary to give the current player as an input so the
     * validation can review if there is any other player active after the player in turn.
     *
     * @see Game
     * @see Player
     *
     * @param player the current player who is making the move
     * @return {@code true} if the round has finished.
     *         {@code false} if the round has not finished yet.
     */
    private boolean isRoundFinished(Player player){

        int position = 0;

        for ( Player p : this.game.activePlayers() ) {
            position++;
            if ( p.getId() == player.getId() ) {
                break;
            }
        }

        return position == this.game.activePlayers().size();
    }

    /**
     * This method retrieves the next player in the game. If there is only one player left in the game
     * the method will return a null value, because it'd mean that the game has finished.
     *
     * @see Game
     * @see Player
     *
     * @param player the current player who is making the move
     * @return {@code player} if there is any player left in the game besides the player making the move.
     *         {@code null} if there is no player next in turn.
     */
    public Player getNextPlayer(Player player){

        // First of all it's necessary to check if the player is the last player in the list of players
        if ( this.game.getPlayers().size() == player.getId() + 1 ){
            return this.game.activePlayers().get(0); // If so then return the first player in the list of <active> players
        } else {
            // Since the player is not the last player in the list of players it means there are players located
            // after the current player that could still be active in the game.
            // It is necessary to look up the active player among those located after the current player
            for ( int i = player.getId() + 1; i < this.game.getPlayers().size(); i++ ){
                if ( this.game.getPlayers().get(i).isActive() )  {
                    return this.game.getPlayers().get(i); // If an active player is found : the next player is located after current player.
                }
            }

            // If it continues to here it means none active player has been found in the previous iteration
            // It is necessary to look up the active player among those located before the current player
            for ( int i = 0; i < player.getId(); i++ ){
                if (this.game.getPlayers().get(i).isActive())  {
                    return this.game.getPlayers().get(i); // If an active player is found : the next player is located before current player.
                }
            }
        }
        // If there is none active player remaining in the game
        return null;
    }

    /**
     * This method adds the bar moved by the player to the list of bars that have been moved in the round {@link Game#movedBarsByOpponents}
     * <p>It also deletes the second previous move of the user in order to store just what it requires
     * to fulfill the game rules.
     *
     * @see it.polimi.group03.domain.Move
     *
     * @param player the current player who is making the move
     * @param bar the bar that has been moved by the player
     */
    private void refreshMovedBarsInRound(Player player, Bar bar){
        int myself = 0;
        int index = 0;

        for ( int i = this.game.getMovedBarsByOpponents().size() - 1; i >= 0; i--){
            if ( this.game.getMovedBarsByOpponents().get(i).getPlayer().getId() ==
                    player.getId() ) {
                // If it's the same user it means it has reached the previous round
                myself++;
                if ( myself == 2 ) {
                    index = i; //Is the index of the second previous turn/move
                    break;
                }
            }
        }

        // If the player has two consecutive turns delete the oldest
        if ( myself == 2 ) {
            this.game.getMovedBarsByOpponents().remove(index);
        }

        // Whether the player has consecutive turns or not, add the new move.
        this.game.addBarToListOfMovedBarsByOpponents(player, bar);
/*
        String print = "";
        for (Move move : this.game.getMovedBarsByOpponents()){
            print+="["+ move.getPlayer().getId() + " moved " + move.getBar().getOrientation().toString().substring(0,1) + move.getBar().getId() + "] - ";
        }
        System.out.println("After making a move: " + print);
        */
    }

    /**
     *
     * This method assigns the values specified with {@code initialBars} to all the bars that have the orientation
     * specified by {@code orientation}.
     *
     * <p>Using this method implies that the initial configuration will be changed completely and will no longer be random.
     *
     * @see Game
     * @see BarOrientation
     *
     * @param orientation the orientation of the bars that are going to be reconfigured
     * @param initialBars the new configuration to use with the bar
     */
    public void reConfigureBars(BarOrientation orientation, char[] initialBars) {

        for (int i = 0; i < 7; i++) {
            if (((Character) '0').equals(initialBars[i])) {
                this.game.getBars(orientation).get(i).setPosition(BarPosition.INNER);
            } else if (((Character) '1').equals(initialBars[i])) {
                this.game.getBars(orientation).get(i).setPosition(BarPosition.CENTRAL);
            } else if (((Character) '2').equals(initialBars[i])) {
                this.game.getBars(orientation).get(i).setPosition(BarPosition.OUTER);
            }
        }
    }

    public Game getGame() {
        return this.game;
    }

}