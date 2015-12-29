package it.polimi.group03;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;
import it.polimi.group03.domain.StatusMessage;

/**
 *
 * This class contains all the unit tests performed
 * on the code. All shall pass without any unknown/unhandled errors and along
 * {@link it.polimi.group03.Test} covers all the possible scenarios.
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
        engine.getGame().refreshBoard();
        printBoard();
        printBars();
    }

    @Test
    public void testAddPlayerAndBeads() throws Exception {
        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);

        //set the horizontal and vertical bars, represented by an array of integers.
        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{0, 1, 2, 0, 1, 2, 0});
        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});
        engine.getGame().refreshBoard();

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
        engine.getGame().refreshBoard();

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

    @Test
    public void testEntireGame() {
        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);//we have to reset the board, cause we'll reset the bars.
        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{1, 2, 2, 1, 0, 1, 1});
        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 2, 1, 1, 1, 0, 0});
        engine.getGame().refreshBoard();

        Player one = new Player(0, "tatibloom", "PINK");
        engine.addPlayer(one);
        Player two = new Player(1, "cecibloom", "PURPLE");
        engine.addPlayer(two);
        Player three = new Player(2, "megi", "white");
        engine.addPlayer(three);
        Player four = new Player(3, "jamesy boy", "four");
        engine.addPlayer(four);

        engine.addBeadToBoard(one, new Bead(0,1));engine.addBeadToBoard(one, new Bead(1, 2));engine.addBeadToBoard(one, new Bead(2, 1));engine.addBeadToBoard(one, new Bead(3,6));engine.addBeadToBoard(one, new Bead(6,1));
        engine.addBeadToBoard(two, new Bead(0,3));engine.addBeadToBoard(two, new Bead(1,4));engine.addBeadToBoard(two, new Bead(2,6));engine.addBeadToBoard(two, new Bead(3,3));engine.addBeadToBoard(two, new Bead(4,0));
        engine.addBeadToBoard(three, new Bead(0,4));engine.addBeadToBoard(three, new Bead(1,6));engine.addBeadToBoard(three, new Bead(2,3));engine.addBeadToBoard(three, new Bead(3,1));engine.addBeadToBoard(three, new Bead(5,4));
        engine.addBeadToBoard(four, new Bead(0,5));engine.addBeadToBoard(four, new Bead(0,6));engine.addBeadToBoard(four, new Bead(1,1));engine.addBeadToBoard(four, new Bead(2, 2));engine.addBeadToBoard(four, new Bead(3, 0));

        engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.OUTER, one);//1- fallen beads: player 1, player 2
        Assert.assertEquals(4, one.activeBeads().size());
        Assert.assertEquals(4, two.activeBeads().size());
        engine.makeMove(0, BarOrientation.VERTICAL, BarPosition.CENTRAL, two);//2- fallen beads: player 4
        Assert.assertEquals(4, four.activeBeads().size());
        engine.makeMove(6, BarOrientation.HORIZONTAL, BarPosition.OUTER, three);//3- fallen beads:
        engine.makeMove(5, BarOrientation.HORIZONTAL, BarPosition.OUTER, four);//4- fallen beads: player 3
        Assert.assertEquals(4, three.activeBeads().size());
        engine.makeMove(4, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, one);//1-
        engine.makeMove(0, BarOrientation.VERTICAL, BarPosition.OUTER, two);//2- fallen beads: player 2
        Assert.assertEquals(3, two.activeBeads().size());
        engine.makeMove(2, BarOrientation.VERTICAL, BarPosition.OUTER, three);//3- fallen beads: player 1
        Assert.assertEquals(3, one.activeBeads().size());
        engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, four);//4-
        Assert.assertEquals(0, engine.getGame().getNextPlayer().getId());
        engine.makeMove(6, BarOrientation.VERTICAL, BarPosition.CENTRAL, one);//1- fallen beads: player 4, player 1
        Assert.assertEquals(2, one.activeBeads().size());
        Assert.assertEquals(3, four.activeBeads().size());
        Assert.assertEquals(1, engine.getGame().getNextPlayer().getId());
        engine.makeMove(4, BarOrientation.VERTICAL, BarPosition.OUTER, two);//2- fallen beads: player 3
        Assert.assertEquals(3, three.activeBeads().size());
        Assert.assertEquals(2, engine.getGame().getNextPlayer().getId());
        engine.makeMove(1, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, three);//3- fallen beads: player 4, player 2, player 3
        Assert.assertEquals(2, two.activeBeads().size());
        Assert.assertEquals(2, three.activeBeads().size());
        Assert.assertEquals(2, four.activeBeads().size());
        Assert.assertEquals(3, engine.getGame().getNextPlayer().getId());
        Assert.assertEquals(Constant.STATUS_ERR_BAR_SELECTED, engine.makeMove(6, BarOrientation.VERTICAL, BarPosition.INNER, four).getCode());
        Assert.assertEquals(3, engine.getGame().getNextPlayer().getId());
        engine.makeMove(2, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, four);//4- fallen beads: player 4
        Assert.assertEquals(1, four.activeBeads().size());
        engine.makeMove(5, BarOrientation.VERTICAL, BarPosition.CENTRAL, one);
        engine.makeMove(1, BarOrientation.VERTICAL, BarPosition.CENTRAL, two);//2- fallen beads: player 1
        Assert.assertEquals(1, one.activeBeads().size());
        engine.makeMove(6, BarOrientation.HORIZONTAL, BarPosition.CENTRAL, three);//3- fallen beads: player 1
        Assert.assertEquals(0, one.activeBeads().size());
        Assert.assertEquals(false, one.isActive());
        Assert.assertEquals(3, engine.getGame().activePlayers().size());
        engine.makeMove(3, BarOrientation.VERTICAL, BarPosition.OUTER, four);//4-
        engine.makeMove(5, BarOrientation.VERTICAL, BarPosition.OUTER, two);//2-
        engine.makeMove(0, BarOrientation.HORIZONTAL, BarPosition.OUTER, three);//3- fallen beads: player 4
        Assert.assertEquals(0, four.activeBeads().size());
        Assert.assertEquals(false, four.isActive());
        Assert.assertEquals(2, engine.getGame().activePlayers().size());
        Assert.assertEquals(1, engine.getGame().getNextPlayer().getId());
        engine.makeMove(3, BarOrientation.HORIZONTAL, BarPosition.INNER, two);//2- fallen bead player 2
        Assert.assertEquals(1, two.activeBeads().size());
        engine.makeMove(6, BarOrientation.VERTICAL, BarPosition.INNER, three);//3- suicide
        Assert.assertEquals(0, two.activeBeads().size());
        Assert.assertEquals(false, two.isActive());
        Assert.assertEquals(1, engine.getGame().activePlayers().size());
        //Assert.assertNull(engine.getGame().getNextPlayer()); nextplayer is not null, it always has a value
        Assert.assertNotNull(engine.getWinner());
        Assert.assertEquals(three, engine.getWinner());
    }

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
    }

    private void printBoard() {

        System.out.format("%n%n%n");
        System.out.println("Player 1[♥]   Player 2[♦]   Player 3[♣]   Player 4[♠]");

        System.out.format("+%-3s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%n", "---", "----------", "----------", "----------", "----------", "----------", "----------","----------");
        System.out.format("| %-1s |    %-1d     |    %-1d     |    %-1d     |    %-1d     |    %-1d     |    %-1d     |    %-1d     |%n","/",1,2,3,4,5,6,7);
        System.out.format("+%-3s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%n","---","----------","----------","----------","----------","----------","----------","----------");
        String[][] beadsOnBoard = engine.getGame().getPlayers() == null ?  new String[7][7] : getBeadsOnBoard() ;

        for ( int i = 0; i < 7; i++ ) {
            System.out.format("| %-1d | %-8s | %-8s | %-8s | %-8s | %-8s | %-8s | %-8s |%n",
                    i+1, engine.getGame().getBoard()[i][0].name() + format(beadsOnBoard[i][0]),
                    engine.getGame().getBoard()[i][1].name() + format(beadsOnBoard[i][1]), engine.getGame().getBoard()[i][2].name()+ format(beadsOnBoard[i][2]),
                    engine.getGame().getBoard()[i][3].name()+ format(beadsOnBoard[i][3]), engine.getGame().getBoard()[i][4].name()+ format(beadsOnBoard[i][4]),
                    engine.getGame().getBoard()[i][5].name()+ format(beadsOnBoard[i][5]), engine.getGame().getBoard()[i][6].name()+ format(beadsOnBoard[i][6]));
            System.out.format("+%-3s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%-10s+%n","---","----------","----------","----------","----------","----------","----------","----------");
        }

    }

    private void printBars() {
        String reds = "";
        String blues = "";

        for ( int i = 0; i < 7; i++){
            reds += engine.getGame().getBars(BarOrientation.HORIZONTAL).get(i).getPosition().getInitialSlot();
            blues += engine.getGame().getBars(BarOrientation.VERTICAL).get(i).getPosition().getInitialSlot();
        }
        System.out.println(reds);
        System.out.println(blues);
    }

    private String[][] getBeadsOnBoard() {
        String[][] beadsOnBoard = new String[7][7];

        for (Player player : engine.getGame().activePlayers()) {
            for (Bead bead : player.activeBeads()) {
                beadsOnBoard[bead.getPosition().getX()][bead.getPosition().getY()] = String.valueOf(player.getId());
            }
        }
        return beadsOnBoard;
    }

    private String format(String s) {
        if ( CommonUtil.equalsIgnoreCase(s,"0") ) s = "♥";
        if ( CommonUtil.equalsIgnoreCase(s,"1") ) s = "♦";
        if ( CommonUtil.equalsIgnoreCase(s,"2") ) s = "♣";
        if ( CommonUtil.equalsIgnoreCase(s,"3") ) s = "♠";
        return s == null ? "[ ]" : "[" + s + "]";
    }

}