package it.polimi.group03.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;

/**
 * @author cecibloom
 * @author tatibloom
 * Created on 11/11/2015.
 */
public class GameEngine {

    public static Logger logger = Logger.getLogger(GameEngine.class.getName());

    private Board board;
    private GameValidator validator;
    private Properties properties;

    /**
     * This method should be called when the game start, in order to initialize the <i>board,
     * players, beads and other initial configurations</i>.
     **/
    public void startGame(List<Player> players) {
        this.board = new Board(players);
        this.validator = new GameValidator(this.board);
        this.board.init();
        properties = new Properties();

        try {
           properties.loadFromXML(new FileInputStream("src/main/res/values/strings.xml"));
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * This method should be called when a player try to make a move,
     * before refresh the state of the board, it will perform a validation
     * according to the rules established for the game itself
     **/
    public boolean makeMove(int id, BarOrientation orientation, BarPosition targetPosition, Player currentPlayer) {
        Bar bar = this.board.findBar(id,orientation);
        if ( !this.validator.isMoveValid(bar,targetPosition,currentPlayer) ) {
            logger.info("Invalid move!");
            return false;
        }
        logger.info("Player " + currentPlayer.getNickname() + " authorized to move " + bar.getOrientation().toString() + " bar number " + bar.getId() +
                "to " + targetPosition.toString());

        this.board.setLastPlayer(currentPlayer);
        this.board.setLastBarMoved(bar);

        //If the current turn equals the number of player remaining in the game it means a round is complete, the turn must be reset
        if ( this.board.getTurn() == this.board.activePlayers().size() ) {
            this.board.setTurn(1);
            this.board.setRound(this.board.getRound() + 1);
        } else {
            this.board.setTurn(this.board.getTurn() + 1);
        }

        this.board.findBeadsOnBar(bar);

        return true;
    }

    /**
     * This method should will be called when there is only a player left, in order to save some
     * statistics and destroy (not necessary) all the remain objects.
     **/
    public void finishGame() {

    }

    private boolean isGameEndConditionReached() {
        return true;
    }

    /**
     * Only for testing purposes.
     * @return current <tt>board</tt>.
     */
    public Board getBoard() {
        return this.board;
    }

}