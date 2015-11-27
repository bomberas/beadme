package it.polimi.group03;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;
import it.polimi.group03.domain.StatusMessage;

/**
 *
 * This class contains all the unit tests performed
 * on the code. All shall pass without any error.
 *
 *
 * @see GameEngine
 * @see BarOrientation
 * @see BarPosition
 * @see Constant
 * @see Bead
 * @see Player
 * @see SlotInfo
 * @see StatusMessage
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 16/11/2015.
 */
public class GameTest {

    private GameEngine engine;

    // Initializes the game.
    @Before
    public void setup() {
        engine = new GameEngine();
        Assert.assertNull(engine.getGame());
    }

    @Test
    public void testStartGame() {
        engine.startGame();
        Assert.assertNotNull(engine.getGame());

        //This just for managing the bars (random positions by default)
        engine.getGame().setBoard(new SlotInfo[7][7]);
        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{0, 1, 2, 0, 1, 2, 0});

        //Checks if the bars are set correctly.
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.HORIZONTAL).get(0).getPosition(), BarPosition.INNER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.HORIZONTAL).get(2).getPosition(), BarPosition.OUTER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.HORIZONTAL).get(4).getPosition(), BarPosition.CENTRAL);

        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.VERTICAL).get(0).getPosition(), BarPosition.OUTER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.VERTICAL).get(2).getPosition(), BarPosition.INNER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.VERTICAL).get(4).getPosition(), BarPosition.CENTRAL);
    }

    /*
     * This tests the adding of players (id, nickname,color of the beads) and beads (active or not, position).
     */
    @Test
    public void testAddPlayerAndBeads() throws Exception {
        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);

        //set the horizontal and vertical bars, represented by an array of integers.
        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{0, 1, 2, 0, 1, 2, 0});
        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});

        //printBoard();

        //Adding the players.
        Player one = new Player(0, "tatibloom", "PINK");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(one).getCode());
        Player two = new Player(1, "cecibloom", "PURPLE");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(two).getCode());
        Player three = new Player(2, "megi", "BLACK");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(three).getCode());
        Player four = new Player(3, "tati", "WHITE");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(four).getCode());

        // The fifth player shall produce an error.
        Player five = new Player(4, "ceci", "YELLOW");
        Assert.assertEquals(Constant.STATUS_ERR_NUMBER_PLAYERS, engine.addPlayer(five).getCode());

        //Checkes if the beads are correctly set in the board.
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(0, 0)).getCode());

        //This will give error since that place is occupied by another bead
        Assert.assertEquals(Constant.STATUS_ERR_PLACED_BEAD, engine.addBeadToBoard(two, new Bead(0, 0)).getCode());

        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(0, 2)).getCode());

        // In this position there is a ky and the bead cannot be placed.
        Assert.assertEquals(Constant.STATUS_ERR_PLACED_BEAD, engine.addBeadToBoard(three, new Bead(1, 0)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(three, new Bead(3, 1)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(four, new Bead(4, 1)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(five, new Bead(2, 6)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(3, 4)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(3, 2)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(6, 4)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(6, 6)).getCode());
        Assert.assertEquals(Constant.STATUS_ERR_PLACED_BEAD, engine.addBeadToBoard(one, new Bead(6, 0)).getCode());

        Assert.assertNull(engine.getWinner());
    }

    @Test
    public void testMoves() {
        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);//we have to reset the board, cause we'll reset the bars.
        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{0, 1, 2, 0, 1, 2, 0});
        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});
        //printBoard();

        Player one = new Player(0, "tatibloom", "PINK");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(one).getCode());
        Player two = new Player(1, "cecibloom", "PURPLE");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(two).getCode());

        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(0, 0)).getCode());Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(0, 2)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(3, 1)).getCode());Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(4, 1)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(2, 6)).getCode());Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(3, 4)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(3, 2)).getCode());Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(6, 4)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(6, 6)).getCode());Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(6, 0)).getCode());

        Assert.assertEquals(Constant.STATUS_ERR_BAR_POSITION, engine.makeMove(1, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, one).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, one).getCode());
        Assert.assertEquals(Constant.STATUS_ERR_BAR_SELECTED, engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.INNER, two).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.makeMove(1, BarOrientation.VERTICAL, BarPosition.INNER, two).getCode());

        //Same previous bar for player one
        Assert.assertEquals(Constant.STATUS_OK, engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.OUTER, one).getCode());
        //You can not keep the turn
        Assert.assertEquals(Constant.STATUS_ERR_SAME_PREVIOUS_PLAYER, engine.makeMove(6, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, one).getCode());

        //Same previous bar for player two
        Assert.assertEquals(Constant.STATUS_OK, engine.makeMove(1, BarOrientation.VERTICAL, BarPosition.CENTRAL, two).getCode());

        //You can not move the same bar more than 2 consecutive times
        Assert.assertEquals(Constant.STATUS_ERR_BAR_CONSECUTIVE, engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, one).getCode());

        Assert.assertEquals(Constant.STATUS_OK, engine.makeMove(5, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, one).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, two).getCode());

    }

    /*
     *  This method resets the position of bars with fixed positions
     */
    private void reConfigureBars(GameEngine engine, BarOrientation orientation, int[] initialBars) {
        for ( int i = 0; i < 7; i++ ) {
            if ( 0 == initialBars[i] ) {
                engine.getGame().getBars(orientation).get(i).setPosition(BarPosition.INNER);
            } else if ( 1 == initialBars[i] ) {
                engine.getGame().getBars(orientation).get(i).setPosition(BarPosition.CENTRAL);
            } else {
                engine.getGame().getBars(orientation).get(i).setPosition(BarPosition.OUTER);
            }
        }
        engine.getGame().refreshBoard();
    }

    private void printBoard() {
        System.out.format("%n%n%n");
        System.out.format("+%-3s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%n","---","----------","----------","----------","----------","----------","----------","----------");
        System.out.format("| %-1s |    %-1d     |    %-1d     |    %-1d     |    %-1d     |    %-1d     |    %-1d     |    %-1d     |%n","/",1,2,3,4,5,6,7);
        System.out.format("+%-3s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%n","---","----------","----------","----------","----------","----------","----------","----------");
        String[][] beadsOnBoard = getBeadsOnBoard();
        for ( int i = 0; i < 7; i++ ) {
            System.out.format("| %-1d | %-8s | %-8s | %-8s | %-8s | %-8s | %-8s | %-8s |%n",
                    i+1, engine.getGame().getBoard()[i][0].name() + format(beadsOnBoard[i][0]),
                    engine.getGame().getBoard()[i][1].name() + format(beadsOnBoard[i][1]), engine.getGame().getBoard()[i][2].name()+ format(beadsOnBoard[i][2]),
                    engine.getGame().getBoard()[i][3].name()+ format(beadsOnBoard[i][3]), engine.getGame().getBoard()[i][4].name()+ format(beadsOnBoard[i][4]),
                    engine.getGame().getBoard()[i][5].name()+ format(beadsOnBoard[i][5]), engine.getGame().getBoard()[i][6].name()+ format(beadsOnBoard[i][6]));
            System.out.format("+%-3s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%n","---","----------","----------","----------","----------","----------","----------","----------");
        }

    }

    private String[][] getBeadsOnBoard() {
        String[][] beadsOnBoard = new String[7][7];

        for (Player player : engine.getGame().activePlayers()) {
            for (Bead bead : player.activeBeads()) {
                beadsOnBoard[bead.getPosition().getX()][bead.getPosition().getY()] = player.getNickname();
            }
        }
        return beadsOnBoard;
    }

    private String format(String s) {
        return s == null ? "[ ]" : "[" + s + "]";
    }

}