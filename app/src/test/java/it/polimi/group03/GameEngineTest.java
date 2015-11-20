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
import it.polimi.group03.domain.Game;
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
        configureGame("21012012021011020000100002000000000012000000000000000010000000000h3cv2o");
        makeMoves();
    }

    @Test
    public void move() throws Exception {
        configureGame(0);//from XML
        //TODO more scenarios
    }

    private void configureGame(int nTest) throws Exception {
        configureGame(getScenario(nTest).getAttribute("input"));
    }

    private void configureGame(String test) {
        System.out.println("Given scenario: " + test);

        numberOfPlayers = Integer.parseInt(test.substring(0, 1));
        beadsInTheGrid = test.substring(16, 65);

        engine = new GameEngine();
        engine.startGame(getPlayersConfig());

        String initialHorizontalBar = test.substring(2, 9);
        reConfigureBars(engine.getGame(), BarOrientation.HORIZONTAL, initialHorizontalBar.toCharArray());

        String initialVerticalBar = test.substring(9, 16);
        reConfigureBars(engine.getGame(), BarOrientation.VERTICAL, initialVerticalBar.toCharArray());

        movingPlayer = Integer.parseInt(test.substring(1, 2));
        System.out.println("Moving player: " + String.valueOf(movingPlayer));

        moves = setMoves(test.substring(65, test.length()));
    }

    private void makeMoves() {
        for ( Bar move : moves ) {
            System.out.println(MessageFormat.format("Move: {1}{0}{2}", move.getId(), move.getOrientation().getShortcut(), move.getPosition().getShortcut()));
            if ( !engine.makeMove(move.getId(), move.getOrientation(), move.getPosition(), players.get(movingPlayer)) ) {
                printMoveError(move.getId(), move.getOrientation(), move.getPosition());
                break;
            }
            printStatus();
        }
    }

    private List<Player> getPlayersConfig() {
        players = new ArrayList<>();
        char beads[] = beadsInTheGrid.toCharArray();

        for ( int i = 0; i < numberOfPlayers; i++ ) {
            Player player = new Player();
            player.setNickname(String.valueOf(i+1));
            player.setActive(true);
            for ( int x = 1; x <= beads.length; x++ ) {
                if ( String.valueOf(i+1).equals(String.valueOf(beads[x-1])) ) {
                    Bead bead = new Bead();
                    bead.setIsActive(true);
                    bead.setPosition(new Position((x % 7 == 0 ? x / 7 : (x / 7) + 1) - 1, (x % 7 == 0 ? 7 : x % 7) - 1));
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
            bead.setIsActive(false);
            bead.setPosition(new Position(0, 0));
            player.addBead(bead);
        }
    }

    private void reConfigureBars(Game game, BarOrientation orientation, char[] initialBars) {
        List<Bar> bars = game.getBars(orientation);
        String orientations = "";
        for ( int i = 0; i < 7; i++ ) {
            Bar bar = bars.get(i);
            if ( ((Character)'0').equals(initialBars[i]) ) {
                bar.setPosition(BarPosition.INNER);
            } else if (  ((Character)'1').equals(initialBars[i]) ) {
                bar.setPosition(BarPosition.CENTRAL);
            } else {
                bar.setPosition(BarPosition.OUTER);
            }
            orientations += bar.getPosition() + ",";
        }
        System.out.println(MessageFormat.format("{0}[{1}]", orientation, orientations));
    }

    private List<Bar> setMoves(String moves) {
        List<Bar> givenMoves = new ArrayList<>();
        for( int i = 0; i < moves.length(); i += 3 ) {
            String move = moves.substring(i, i + 3);
            Bar bar = new Bar(Integer.valueOf(move.substring(1, 2)), CommonUtil.equalsIgnoreCase(move.substring(0, 1), "H") ?
                    BarOrientation.HORIZONTAL : BarOrientation.VERTICAL, CommonUtil.equalsIgnoreCase(move.substring(2,3), "I") ?
                    BarPosition.INNER : CommonUtil.equalsIgnoreCase(move.substring(2, 3), "C") ? BarPosition.CENTRAL : BarPosition.OUTER);
            givenMoves.add(bar);
        }
        return givenMoves;
    }

    private String getBarPositions(BarOrientation orientation) {
        String position = "";
        for ( Bar bar : engine.getGame().getBars(orientation) ) {
            position += bar.getPosition().getInitialSlot();
        }
        return position;
    }

    private void printStatus() {
        System.out.println(engine.getGame().activePlayers().size()); //"Number of players"
        System.out.println(engine.getGame().getNextPlayer());//"Moving player"
        System.out.println(getBarPositions(BarOrientation.HORIZONTAL));//"Positions of the horizontal bars"
        System.out.println(getBarPositions(BarOrientation.VERTICAL));//"Positions of the vertical bars"
        printBeads();//beads
    }

    private void printMoveError(int id, BarOrientation orientation, BarPosition position) {
        Bar currentBar = engine.getGame().findBar(id, orientation);
        System.out.println("ERROR: One of these conditions has occurred:");
        System.out.println("Move from <" + currentBar.getPosition() + "> to <" + position + "> it's forbidden.");
        System.out.println("Bar<" + id + "> has already been slid in the previous round.");
        System.out.println("There are only 2 players, you can not slide consecutively the same Bar<" + id + "> more than twice.");
    }

    /*
    private void printGrid() {
        System.out.println("Printing current board ...");
        for ( int i = 0; i < Constant.BOARD_INDEX; i++) {
            String row = "";
            for ( int j = 0; j < Constant.BOARD_INDEX; j++) {
                row += CommonUtil.rPad(engine.getGame().getBoard()[i][j].name(), 8);
            }
            System.out.println(row);
        }
    }*/

    private void printBeads() {
        String beads[][] = new String[7][7];
        for ( Player p : players ) {
            for ( Bead bead : p.activeBeads() ) {
                beads[bead.getPosition().getX()][bead.getPosition().getY()] = p.getNickname();
            }
        }
        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
            String row = "";
            for ( int j = 0; j < Constant.BOARD_INDEX; j++ ) {
                if ( beads[i][j] == null ) {
                    beads[i][j] = "0";
                }
                row += beads[i][j];
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