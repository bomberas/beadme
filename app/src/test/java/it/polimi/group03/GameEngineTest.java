package it.polimi.group03;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

    private GameEngine engine;
    List<Player> players;

    private int numberOfPlayers;
    private String beadsInTheGrid;
    private int movingPlayer;
    private List<Bar> moves;

    @Test
    public void moveTest() throws Exception {
        configureGame(0);
        for ( Bar move : moves ) {
            engine.makeMove(move.getId(), move.getOrientation(), move.getPosition(), players.get(movingPlayer));
        }
    }

    private void configureGame(int nTest) throws Exception {
        String test = getScenario(nTest).getAttribute("input");
        System.out.println("Given scenario: " + test);

        numberOfPlayers = Integer.parseInt(test.substring(0, 1));
        beadsInTheGrid = test.substring(16, 65);

        engine = new GameEngine();
        engine.startGame(getPlayersConfig());

        String initialHorizontalBar = test.substring(2, 9);
        System.out.println("Horizontal Bar: " + initialHorizontalBar);
        reConfigureBars(engine.getBoard(), BarOrientation.HORIZONTAL, initialHorizontalBar.toCharArray());

        String initialVerticalBar = test.substring(9, 16);
        System.out.println("Vertical Bar: " + initialVerticalBar);
        reConfigureBars(engine.getBoard(), BarOrientation.VERTICAL, initialVerticalBar.toCharArray());

        movingPlayer = Integer.parseInt(test.substring(1, 2));
        System.out.println("Moving player: " + String.valueOf(movingPlayer));

        moves = setMoves(test.substring(65, test.length()));

        printCurrentGrid();
    }

    private List<Player> getPlayersConfig() {
        players = new ArrayList<>();
        char beads[] = beadsInTheGrid.substring(0, 49).toCharArray();

        for ( int i = 1; i <= numberOfPlayers; i++ ) {
            Player player = new Player();
            player.setNickname(String.valueOf(i));
            player.setActive(true);
            for ( int x = 0; x < beads.length; x++ ) {
                if ( ((Character)((char)i)).equals(beads[x]) ) {
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
            System.out.println(MessageFormat.format("Bar[{0}-{1},{2}]", bar.getOrientation(), bar.getId(), bar.getPosition()));
        }
    }

    private List<Bar> setMoves(String moves) {
        List<Bar> givenMoves = new ArrayList<>();
        int count = 1;
        for( int i = 0; i < moves.length()/3; i += 3 ) {
            String move = moves.substring(i, i + 3);
            System.out.println("Move[" + count + "]: " + move);
            Bar bar = new Bar(Integer.valueOf(move.substring(1, 2)), CommonUtil.equalsIgnoreCase(move.substring(0, 1), "H") ?
                    BarOrientation.HORIZONTAL : BarOrientation.VERTICAL, CommonUtil.equalsIgnoreCase(move.substring(2,3), "I") ?
                    BarPosition.INNER : CommonUtil.equalsIgnoreCase(move.substring(2, 3), "C") ? BarPosition.CENTRAL : BarPosition.OUTER);
            givenMoves.add(bar);
            count++;
        }
        return givenMoves;
    }

    private void printCurrentGrid() {
        System.out.println("Printing current board ...");
        for ( int i = 0; i < Constant.BOARD_INDEX; i++) {
            String row = "";
            for ( int j = 0; j < Constant.BOARD_INDEX; j++) {
                row += CommonUtil.rPad(engine.getBoard().getGrid()[i][j].name(), 8);
            }
            System.out.println(row);
        }
    }

    private Element getScenario(int index) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                parse(new File("src/test/java/it/polimi/group03/TestScenarios.xml"));
        doc.normalize();

        return (Element) doc.getElementsByTagName("scenario").item(index);
    }

}