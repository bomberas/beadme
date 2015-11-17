package it.polimi.group03;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Board;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.Position;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 * @author tatibloom
 * Created on 16/11/2015.
 */
public class GameEngineTest {

    /**
     * Please use the xml file TestScenarios for test data
     */
    private static Logger logger = Logger.getLogger(GameEngineTest.class.getName());

    @Test
    public void moveTest() throws Exception {
        String scenario = getScenario(0).getAttribute("input");
        logger.info(scenario);

        int numberOfPlayers = Integer.parseInt(scenario.substring(0, 1));
        String beadsInTheGrid = scenario.substring(16, 65);
        GameEngine engine = new GameEngine();
        engine.startGame(getPlayersConfig(numberOfPlayers, beadsInTheGrid));

        String initialHorizontalBar = scenario.substring(2, 9);
        String initialVerticalBar = scenario.substring(9, 16);
//        reConfigureBars(engine.getBoard(), BarOrientation.HORIZONTAL, initialHorizontalBar.toCharArray());
//        reConfigureBars(engine.getBoard(), BarOrientation.VERTICAL, initialVerticalBar.toCharArray());

        int movingPlayer = Integer.parseInt(scenario.substring(1, 2));
        logger.info(String.valueOf(movingPlayer));

        List<char[]> moves = setMoves(scenario.substring(65, scenario.length()));
        logger.info(moves.toString());

        for ( int i = 0; i < Constant.BOARD_INDEX; i++) {
            String row = "";
            for ( int j = 0; j < Constant.BOARD_INDEX; j++) {
                row += " " + engine.getBoard().getGrid()[i][j].name();
            }
            logger.info(row);
        }
    }

    private List<Player> getPlayersConfig(int numberOfPlayers, String initialGrid) {
        List<Player> players = new ArrayList<>();
        char beadsInTheGrid[] = initialGrid.substring(0, 49).toCharArray();

        for ( int i = 1; i <= numberOfPlayers; i++ ) {
            Player player = new Player();
            player.setNickname(String.valueOf(i));
            player.setActive(true);
            for ( int x = 0; x < beadsInTheGrid.length; x++ ) {
                if ( ((Character)((char)i)).equals(beadsInTheGrid[x]) ) {
                    Bead bead = new Bead();
                    bead.setIsActive(true);
                    bead.setPosition(new Position((x+1)%7 == 0 ? (x+1)/7 : ((x+1)/7)+1,(x+1)%7 == 0 ? 7 : (x+1)%7));
                    player.addBead(bead);
                }
            }
            setDefaultBeads(player);
            players.add(player);
        }

        return players;
    }

    private void setDefaultBeads(Player player) {
        int remainingBeads = CommonUtil.isEmpty(player.getBeads()) ? 5 : 5 - player.getBeads().size();
        for ( int x = 0; x < remainingBeads; x++) {
            Bead bead = new Bead();
            bead.setIsActive(true);
            bead.setPosition(new Position(0, 0));
            player.addBead(bead);
        }
    }

    private void reConfigureBars(Board board, BarOrientation orientation, char[] initialBars) {
        List<Bar> bars = board.getBars(orientation);
        for ( int i = 0; i < 7; i++ ) {
            Bar bar = bars.get(i);
            if ( ((Character)'0').equals(initialBars[i]) ) {
                bar.setPosition(BarPosition.INNER);
            } else if (  ((Character)'1').equals(initialBars[i]) ) {
                bar.setPosition(BarPosition.CENTRAL);
            } else {
                bar.setPosition(BarPosition.OUTER);
            }
        }
    }

    private List<char[]> setMoves(String moves) {
        List<char[]> givenMoves = new ArrayList<>();
        for( int i = 0; i < moves.length()/3; i += 3 ) {
            givenMoves.add(moves.substring(i, i+3).toCharArray());
        }
        return givenMoves;
    }

    private Element getScenario(int index) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                parse(new File("src/test/java/it/polimi/group03/TestScenarios.xml"));
        doc.normalize();

        return (Element) doc.getElementsByTagName("scenario").item(index);
    }

}