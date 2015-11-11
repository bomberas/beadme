package it.polimi.group03.engine;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.Constant;

/**
 * Created by tatibloom on 11/11/2015.
 */
public class GameEngine {

    private Board board;
    private GameValidator validator;

    /**
     * @author cecibloom
     * @author megireci
     * @author tatibloom
     * This method should be called when the game start in order to initialize board,
     * players, beads and other initial configurations for the class Bar
     **/
    public void startGame(Board board) {
        this.board = board;
        configureBar(board, BarOrientation.HORIZONTAL);//setting-up the fixed positions for the horizontal Bars
        configureBar(board, BarOrientation.VERTICAL);//setting-up the fixed positions for the vertical Bars

    }

    /**
     * @author cecibloom
     * @author megireci
     * @author tatibloom
     * This method should be called when a player try to make a move,
     * before refresh the state of the board, it will perform a validation
     * according to the rules stablish for the game itself
     **/
    public void makeMove() {
        //TODO
    }

    /**
     * @author cecibloom
     * @author megireci
     * @author tatibloom
     * This method should will be called when only a player left, in order to save some
     * statistics and destroy (not necessary) all the remain objects.
     **/
    public void finishGame() {

    }

    private void configureBar(Board board, BarOrientation orientation) {
        for ( int i = 1; i <= Constant.BAR_SLOTS; i++ ) {
            board.addBar(new Bar(i, orientation, getKeys(i, orientation)));
        }
    }

    private int[] getKeys(int id, BarOrientation orientation) {
        int[] keys;

        switch ( id ) {
            case 1:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,1,0,1,0,1,0,1} : new int[] {1,0,0,0,0,1,0,1,1};
                break;
            case 2:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,1,0,0,1,0,1} : new int[] {1,0,0,0,1,1,0,0,1};
                break;
            case 3:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,0,1,0,0,0,1} : new int[] {1,0,1,0,0,1,0,1,1};
                break;
            case 4:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,1,0,1,0,1,0,1} : new int[] {1,0,0,1,1,0,0,0,1};
                break;
            case 5:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,0,0,0,0,0,1} : new int[] {1,1,0,0,0,1,0,1,1};
                break;
            case 6:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,1,0,0,0,1,0,1,1} : new int[] {1,1,0,0,0,0,0,1,1};
                break;
            case 7:
                keys = BarOrientation.HORIZONTAL.equals(orientation) ?
                        new int[] {1,0,0,1,0,1,0,1,1} : new int[] {1,0,0,1,0,0,1,0,1};
                break;
            default:
                keys = null;
        }
        return keys;
    }
}