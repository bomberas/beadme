package it.polimi.group03.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;

/**
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * Created on 11/11/2015.
 */
public class GameEngine {

    private Board board;
    private GameValidator validator;
    private Properties properties;

    /**

     * This method should be called when the game start, in order to initialize board,
     * players, beads and other initial configurations for the class Bar
     **/
    public void startGame(List<Player> players) {
        this.board = new Board(players);
        this.validator = new GameValidator(this.board);
        this.board.init();
        properties = new Properties();
        try {
            properties.loadFromXML(new FileInputStream("strings.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method should be called when a player try to make a move,
     * before refresh the state of the board, it will perform a validation
     * according to the rules stablish for the game itself
     **/
    public boolean makeMove(int id, BarOrientation orientation, BarPosition targetPosition, Player currentPlayer) {
        
        if ( !this.validator.isMoveValid(id,orientation,targetPosition,currentPlayer) ) {
            System.out.println("move invalid");
            return false;
        }

        //this.board.se
        return true;
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