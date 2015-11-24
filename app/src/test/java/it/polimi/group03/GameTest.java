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

/**
 *
 * This class contains all the unit tests performed
 * on the code. All have passed without any error.
 *
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
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.HORIZONTAL).get(0).getPosition(), BarPosition.INNER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.HORIZONTAL).get(2).getPosition(), BarPosition.OUTER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.HORIZONTAL).get(4).getPosition(), BarPosition.CENTRAL);

        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.VERTICAL).get(0).getPosition(), BarPosition.OUTER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.VERTICAL).get(2).getPosition(), BarPosition.INNER);
        Assert.assertEquals(engine.getGame().getBars(BarOrientation.VERTICAL).get(4).getPosition(), BarPosition.CENTRAL);
    }

    @Test
    public void testAddPlayerAndBeads() throws Exception {
        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);

        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{0, 1, 2, 0, 1, 2, 0});
        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});

        printBoard();

        Player one = new Player(0, "tatibloom", "PINK");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(one).getCode());
        Player two = new Player(1, "cecibloom", "PURPLE");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(two).getCode());
        Player three = new Player(2, "megi", "BLACK");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(three).getCode());
        Player four = new Player(3, "tati", "WHITE");
        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(four).getCode());
        Player five = new Player(4, "ceci", "YELLOW");
        Assert.assertEquals(Constant.STATUS_ERR_NUMBER_PLAYERS, engine.addPlayer(five).getCode());

        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(0, 0)).getCode());
        Assert.assertEquals(Constant.STATUS_ERR_PLACED_BEAD, engine.addBeadToBoard(two, new Bead(0, 0)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(two, new Bead(0, 2)).getCode());
        Assert.assertEquals(Constant.STATUS_ERR_PLACED_BEAD, engine.addBeadToBoard(three, new Bead(1, 0)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(three, new Bead(3, 1)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(four, new Bead(4, 1)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(five, new Bead(2, 6)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(3, 4)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(3, 2)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(6, 4)).getCode());
        Assert.assertEquals(Constant.STATUS_OK, engine.addBeadToBoard(one, new Bead(6, 6)).getCode());
        Assert.assertEquals(Constant.STATUS_ERR_PLACED_BEAD, engine.addBeadToBoard(one, new Bead(6, 0)).getCode());
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
            engine.getGame().refreshBoard(engine.getGame().getBars(orientation).get(i));
        }
    }

    private void printBoard() {
        System.out.format("%n%n%n");
        System.out.format("+%-3s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%n","---","-------","-------","-------","-------","-------","-------","-------");
        System.out.format("| %-1s |   %-1d   |   %-1d   |   %-1d   |   %-1d   |   %-1d   |   %-1d   |   %-1d   |%n","/",1,2,3,4,5,6,7);
        System.out.format("+%-3s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%n","---","-------","-------","-------","-------","-------","-------","-------");
        for ( int i = 0; i < 7; i++ ) {
            System.out.format("| %-1d | %-5s | %-5s | %-5s | %-5s | %-5s | %-5s | %-5s |%n",
                    i+1, engine.getGame().getBoard()[i][0].name(),
                    engine.getGame().getBoard()[i][1].name(), engine.getGame().getBoard()[i][2].name(),
                    engine.getGame().getBoard()[i][3].name(), engine.getGame().getBoard()[i][4].name(),
                    engine.getGame().getBoard()[i][5].name(), engine.getGame().getBoard()[i][6].name());
            System.out.format("+%-3s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%-7s+%n", "---", "-------", "-------", "-------", "-------", "-------", "-------", "-------");
        }

    }

}