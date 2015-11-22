package it.polimi.group03.engine;

import android.util.Log;

import java.text.MessageFormat;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Game;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;

/**
 * @author cecibloom
 * @author tatibloom
 * Created on 11/11/2015.
 */
public class GameEngine {

    private Game game;
    private GameValidator validator;
    //private Properties properties;

    /**
     * This method should be called when the game start, in order to initialize the <i>board,
     * beads and other initial configurations</i>.
     **/
    public void init() {
        this.game = new Game();
        this.validator = new GameValidator(this.game);
        this.game.init();
        this.game.resetMovedBarsInCurrentRound();
        this.game.resetLoserAfterTurn();
    }

    /**
     * This method will add a new player to the game <u>if and only if</u>
     * it complies with the rules established by the Game.
     * @param player new player to be added to the game
     * @return <tt>true</tt> if the new player is accepted.<br/>
     *         <tt>false</tt> if not.
     */
    public boolean addPlayer(Player player) {
        if ( this.validator.isNumberOfPlayersValid() ) {
            this.game.addPlayer(player);
            return true;
        }

        return false;
    }

    /**
     * This method will add a new bead for the selected player <u>if and only if</u>
     * it complies with the rules established by the Game.
     * @param player new player to be added to the game
     * @return <tt>true</tt> if the new bead is accepted.<br/>
     *         <tt>false</tt> if not.
     */
    public boolean addBead(Player player, Bead bead) {
        if ( this.validator.isBeadValid(player, bead) ) {
            player.addBead(bead);
            return true;
        }

        return false;
    }

    /**
     * This method should be called when a player tries to make a move, before refreshing the state of theGame,
     * it will perform a validation according to the rules established for the game. If the move is valid is necessary
     * to make some changes to the current state of theGame, the number of active players, turns and rounds.
     * Whenever the move is made without any problem the method returns a valid response so the controller can ask for
     * the result of the movement i.e check for any loser.
     *
     * @param id Moved bar <b>id</b>
     * @param orientation Moved bar <b>orientation</b>
     * @param targetPosition Moved bar <b>position</b> (target position)
     * @param currentPlayer Player making move
     * @return <tt>true</tt> if the move is valid and made
     *         <tt>false</tt> if not
     */
    public boolean makeMove(int id, BarOrientation orientation, BarPosition targetPosition, Player currentPlayer) {
        Bar bar = this.game.findBar(id,orientation);
        if ( !this.validator.isMoveValid(bar,targetPosition,currentPlayer) ) {
             Log.i("GameEngine.makeMove","Invalid move!");
             return false;
        }
        Log.i("GameEngine.makeMove", MessageFormat.format("Player {0} authorized to move {1} bar number {2} to {3}", currentPlayer.getNickname(), bar.getOrientation().toString(), bar.getId(),
                targetPosition.toString()));

        // Giving that the move is permitted is necessary to update the position of the selected bar
        bar.setPosition(targetPosition);

        // If and only if there are only 2 players remaining in the game, is necessary to put the selected bar in the
        // list of moved bars.

        if ( this.game.activePlayers().size() == 2 ) {
            this.game.addBarToListOfMovedBarsInTwoPreviousRound(bar); // see the way to reset the list after "n" movements so it doesn't grow too much
        }

        // Is necessary reset the list of losers of the previous turn, so when the UI call "getLosersAfterTurn()" gets the updated list of loser of current turn

        this.game.resetLoserAfterTurn();

        this.game.setLastPlayer(currentPlayer);
        this.game.setLastBarMoved(bar);
        this.game.refreshBoard(bar);
        this.game.refreshBeads(bar);

        // If after the move made by the player the round is complete and the game is finished is necessary to move the round and reset the list of moved bars in the round
        // In any case is necessary to update the list of moved bars in the current round putting the selected bar in the list of moved bars.
        //the round is finished with the player in turn
        if ( isRoundFinished(currentPlayer) ) {
            this.game.resetMovedBarsInCurrentRound();
            if ( !isGameEndConditionReached() ) this.game.moveRound();
        }
        this.game.addBarToListOfMovedBarsInRound(bar);
        if ( !isGameEndConditionReached()) this.game.moveTurn();

        // Is necessary to update the next player after a move

        this.game.setNextPlayer(getNextPlayer(currentPlayer));

        return true;
    }

    /**
     * This method checks whether the current round has finished or not. It is necessary to five the current player as an input so the
     * validation can review is there any other player active after the current player,
     *
     * @param player the current player who is making the move
     * @return <tt>true</tt> if the round has finished.
     *         <tt>false</tt> if the round has not finished yet.
     */
    private boolean isRoundFinished(Player player){

        if ( player.getId() + 1 == this.game.getPlayers().size()) {
            return true;
        }

        for (int i= player.getId() + 1; i < this.game.getPlayers().size(); i++){
            if ( this.game.getPlayers().get(i).isActive() ){
                return false;
            }
        }
        return true;
    }

    /**
     * This method retrieves the next player in the game. Could be in the same round or in the next round
     *
     * @param player the current player who is making the move
     * @return <tt>player</tt> if there is any player left in the game besides the current player who is the next in turn.
     *         <tt>null</tt> if there is no player next in turn.
     */
    private Player getNextPlayer(Player player){

        if ( player.getId() + 1 != this.game.getPlayers().size()) {

            for (int i = player.getId() + 1; i < this.game.getPlayers().size(); i++) {
                if (this.game.getPlayers().get(i).isActive()) {
                    return this.game.getPlayers().get(i);
                }
            }
        }

        for (int i = 0; i < player.getId() - 1; i++){
            if ( this.game.getPlayers().get(i).isActive() ){
                return this.game.getPlayers().get(i);
            }
        }

        return null;
    }

    /**
     * This method checks if there is only 1 player active in the game after a move is been made, which indicates that the game
     * has reached the end. This method should be called from the UI to update the final result and show it to the players.
     *
     * @return <tt>true</tt> if the are only 1 or 0 players remaining in the game
     *         <tt>false</tt> if there is more than one player in the game.
     */
    public boolean isGameEndConditionReached() {
        return this.game.activePlayers().size() <= 1;
    }

    /**
     * This method checks whether is a winner or not, and retrieves the winner.
     *
     * @return <tt>player</tt> the winner player if there is any.
     *         <tt>null</tt> if there is no winner yet.
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

    public Game getGame() {
        return this.game;
    }

}