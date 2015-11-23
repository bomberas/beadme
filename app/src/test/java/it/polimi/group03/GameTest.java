package it.polimi.group03;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;

/**
 * This class contains all the unit tests performed
 * on the code, in any case should be pass without
 * errors. Additionally could provide some coverage
 * statistics.
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
    public void testAddPlayer() {
        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);

        reConfigureBars(engine, BarOrientation.HORIZONTAL, new int[]{0, 1, 2, 0, 1, 2, 0});
        reConfigureBars(engine, BarOrientation.VERTICAL, new int[]{2, 1, 0, 2, 1, 0, 2});

//        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(new Player(0, "tatibloom", "PINK")));
//        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(new Player(1, "cecibloom", "PURPLE")));
//        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(new Player(2, "megi", "BLACK")));
//        Assert.assertEquals(Constant.STATUS_OK, engine.addPlayer(new Player(3, "tati", "WHITE")));
//        Assert.assertEquals(Constant.STATUS_ERR_NUMBER_PLAYERS, engine.addPlayer(new Player(4, "ceci", "YELLOW")));
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

}