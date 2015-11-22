package it.polimi.group03;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Game;
import it.polimi.group03.domain.Player;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 * This class is prepared to receive a string and parse the input as follow:
 [1]  number of players
 + [1]  moving player
 + [7]  positions of the horizontal bars
 + [7]  positions of the vertical bars
 + [49]  beads in the grid
 + [3*n] moves
 */
public class Test {

    private GameEngine engine;
    List<Player> players;

    private int numberOfPlayers;
    private String beadsInTheGrid;
    private int movingPlayer;
    private List<Bar> moves;

    public static void main(String args[]) {
        System.out.print("Please enter the string configuration: ");
        Scanner s = new Scanner(System.in);
        Test test = new Test();
        if ( test.configureGame(s.next()) ) {
            for ( Bar move : test.moves ) {
                if ( !test.engine.makeMove(move.getId(), move.getOrientation(), move.getPosition(), test.players.get(test.movingPlayer)) ) {
                    test.printMoveError(move.getId(), move.getOrientation(), move.getPosition());
                    break;
                }
            }
            test.printStatus();
        } else {
            System.out.println("Error in configuration");
        }
    }

    private boolean configureGame(String test) {
        numberOfPlayers = Integer.parseInt(test.substring(0, 1));
        beadsInTheGrid = test.substring(16, 65);

        engine = new GameEngine();
        engine.init();
        String initialHorizontalBar = test.substring(2, 9);
        reConfigureBars(engine.getGame(), BarOrientation.HORIZONTAL, initialHorizontalBar.toCharArray());

        String initialVerticalBar = test.substring(9, 16);
        reConfigureBars(engine.getGame(), BarOrientation.VERTICAL, initialVerticalBar.toCharArray());

        boolean hasFullyConfigured = configurePlayers();

        if ( hasFullyConfigured ) {
            movingPlayer = Integer.parseInt(test.substring(1, 2));
            moves = setMoves(test.substring(65, test.length()));
            return true;
        }

        return false;
    }

    private boolean configurePlayers() {
        players = new ArrayList<>();
        char beads[] = beadsInTheGrid.toCharArray();

        for ( int i = 0; i < numberOfPlayers; i++ ) {
            Player player = new Player(i,String.valueOf(i), "black");
            if ( engine.addPlayer(player) ) {
                System.out.println("Error configuring player...");
                for (int x = 1; x <= beads.length; x++) {
                    if ( String.valueOf(i + 1).equals(String.valueOf(beads[x - 1])) ) {
                        Bead bead = new Bead((x % 7 == 0 ? x / 7 : (x / 7) + 1) - 1, (x % 7 == 0 ? 7 : x % 7) - 1);
                        if ( !engine.addBead(player, bead) ) {
                            System.out.println("Error configuring bead...");
                            return false;
                        }
                    }
                }
                setDefaultBeads(player);
            } else {
                return false;
            }
        }

        return true;
    }

    private void setDefaultBeads(Player player) {
        int remainingBeads = CommonUtil.isEmpty(player.getBeads()) ? 5 : 5 - player.getBeads().size();
        for ( int x = 0; x < remainingBeads; x++) {
            player.addBead(new Bead(0, 0));
        }
    }

    private void reConfigureBars(Game game, BarOrientation orientation, char[] initialBars) {
        List<Bar> bars = game.getBars(orientation);
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

    private void printBeads() {
        String beads[][] = new String[7][7];
        for ( Player p : players ) {
            for ( Bead bead : p.activeBeads() ) {
                beads[bead.getPosition().getX()][bead.getPosition().getY()] = p.getNickname();
            }
        }
        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
            for ( int j = 0; j < Constant.BOARD_INDEX; j++ ) {
                if ( beads[i][j] == null ) {
                    beads[i][j] = "0";
                }
            }
        }
    }

}
