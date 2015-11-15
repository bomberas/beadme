package it.polimi.group03.engine;

import java.util.List;
import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;

/**
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * Created on 11/11/2015.
 */
public class GameEngine {

    private Board board;
    private GameValidator validator;

    /**

     * This method should be called when the game start, in order to initialize board,
     * players, beads and other initial configurations for the class Bar
     **/
    public void startGame(List<Player> players) {
        this.board = new Board(players);
        this.board.init();
    }

    /**
     * This method should be called when a player try to make a move,
     * before refresh the state of the board, it will perform a validation
     * according to the rules stablish for the game itself
     **/
    public void makeMove() {
        //TODO
    }

    /**
     * This method should will be called when is only a player left, in order to save some
     * statistics and destroy (not necessary) all the remain objects.
     **/
    public void finishGame() {

    }

    private boolean isGameEndConditionReached() {
        return true;
    }
}