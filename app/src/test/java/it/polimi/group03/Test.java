package it.polimi.group03;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;

/**
 *
 * This class was implemented for specific testing purposes established in the requirements of
 * the delivery of the project. It should be used <u>only</u> for tests based on strings. Some
 * minor validations has been made to avoid problems with the format of the strings entered.
 *
 * <p>This class is prepared to receive an input as follow:
 * <ul style="list-style-type:circle">
 * <li>[01] number of players +</li>
 * <li>[01] moving player +</li>
 * <li>[07] positions of the horizontal bars +</li>
 * <li>[07] positions of the vertical bars +</li>
 * <li>[49] beads in the grid +</li>
 * <li>[3*n] moves.</li>
 * </ul>
 *
 * For unit testing please see {@link GameTest}
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */
public class Test {

    private GameEngine engine = new GameEngine();
    private int numberOfPlayers;
    private String beadsInTheGrid;
    private int movingPlayer;
    private List<Bar> moves = new ArrayList<>();
    private StatusMessage message;

    public static void main(String args[]) {
        Test test = new Test();
        // The player puts a bead in a key. It will show an error.
        String input = "2"
                + "1"
                + "0120120"
                + "2101102"
                + "0100100"
                + "0020000"
                + "0000001"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "h3i"
                + "v2o";

        // Player 1 moves  the third bar horizontally inward. Player 2 moves the second vertical bar outward.

        input = "2"
                + "1"
                + "0120120"
                + "2101102"
                + "0000100"
                + "0020000"
                + "0000001"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "h3i"
                + "v2o";


/*
        // It is again player's 1 turn.
        input = "2"
                        + "1"
                        + "0110120"
                        + "2201102"
                        + "0000100"
                        + "0020000"
                        + "0000000"
                        + "2000000"
                        + "0000000"
                        + "0001000"
                        + "0000000"
                        + "h2o";

        //Player 2 tries to move the same bar that player 1 moved. It gives an error.
        input = "2"
                        + "2"
                        + "0210120"
                        + "2201102"
                        + "0000100"
                        + "0000000"
                        + "0000000"
                        + "2000000"
                        + "0000000"
                        + "0001000"
                        + "0000000"
                        + "h2o";

        // Since it gave an error, now player 2 has a second chance to drop a bead from player 1. The second horizontal bar now has outer position.
         //  No bead will be dropped but the position of the bar will change and it will be player's one turn.

        input = "2"
                        + "2"
                        + "0210120"
                        + "2201102"
                        + "0000100"
                        + "0000000"
                        + "0000000"
                        + "2000000"
                        + "0000000"
                        + "0001000"
                        + "0000000"
                        + "v4i";

        // Player 1 will tend to move a bar (the sixth vertical) inward which has already the position inner.
        input = "2"
                + "1"
                + "0210120"
                + "2200102"
                + "0000100"
                + "0000000"
                + "0000000"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "v6i";

        // Player 1 will move a bar which was moved once by him.
        input = "2"
                + "1"
                + "0210120"
                + "2200102"
                + "0000100"
                + "0000000"
                + "0000000"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "h2i";
        //Player 2 moves v2o.
        input = "2"
                        + "2"
                        + "0110120"
                        + "2200102"
                        + "0000100"
                        + "0000000"
                        + "0000000"
                        + "2000000"
                        + "0000000"
                        + "0001000"
                        + "0000000"
                        + "v2i";

        // Player 1 will move  the same bar for the third consecutive time.
        //This will be a a test for the new rule.
        input = "2"
                        + "1"
                        + "0110120"
                        + "2100102"
                        + "0000100"
                        + "0000000"
                        + "0000000"
                        + "2000000"
                        + "0000000"
                        + "0001000"
                        + "0000000"
                        + "h2i";

        //Player 1 does the last move which makes the player's 2 bead fall down.
        input = "2"
                + "1"
                + "0210120"
                + "2200102"
                + "0000100"
                + "0000000"
                + "0000000"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "v1i";

        //NEW CASE: The input will have 3 moves. In the second move the game will finish.
        //We have 3 beads left. One from player two, and two from player one. The second move of player one will cause the winning onf the game.

        input = "2"
                        + "1"
                        + "0210120"
                        + "2200102"
                        + "0000100"
                        + "0000000"
                        + "0000000"
                        + "2000000"
                        + "0000000"
                        + "0001000"
                        + "0000000"
                        + "h50" //player 1 non-winning move
                        + "v1i" // player two gets self-sacrificed.
                        + "h3i"; //player one keeps moving

        //Same case, with the difference that player 1 kills player 2. Here it gives an undefined message.

        input = "2"
                + "2"
                + "0210120"
                + "2200102"
                + "0000100"
                + "0000000"
                + "0000000"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "h50" //player 2 non-winning move
                + "v1i" // player 1 kills player 2
                + "h3i"; //player 2 keeps moving

        input = "2"
                + "2"
                + "0210120"
                + "2200102"
                + "0000100"
                + "0000000"
                + "0000000"
                + "2000000"
                + "0000000"
                + "0001000"
                + "0000000"
                + "h50" //player 2 non-winning move
                + "v1i" // player 1 kills player 2
                + "h3i"; //player 2 keeps moving

                */

        input = "4"
                + "1"
                + "0120120"
                + "2101102"

                + "3010120"
                + "0020010"
                + "0032001"
                + "2030301"
                + "0300004"
                + "0004004"
                + "4004020"
                + "v3o"
                + "v6o"
                + "h6i"
                + "h4o"
                + "v4o"
                + "h3i"
                + "h4i";//error movimiento repetido en ronda

        input = "4"
                + "1"
                + "0120120"
                + "2101102"

                + "3010120"
                + "0020010"
                + "0032001"
                + "2030301"
                + "0300004"
                + "0004004"
                + "4004020"
                + "v3o"//1
                + "v6o"//2
                + "h6i"//3
                + "h4o"//4
                + "v4o"//1
                + "h3i"//2
                + "h6i"//3
                + "h1o"//4
                + "v6o"//1
                + "h2i"//2
                + "v2o"//3 the so called papu play
                + "h1i"//4
                + "v5o"//1
                + "v7i"//2
                + "h1o"//4
                + "v7i"//2
                + "v3i"//4
                + "v1i"//2
        ;

        input = "4" +
                "4" +
                "1011100" +
                "1202220" +

                "0000000" +
                "0000000" +
                "0000000" +
                "0000004" +
                "0000000" +
                "0000000" +
                "0000002" +
                "v7o";

        String output = test.moveTest(input); // a move: type of bar, bar number, direction);

        System.out.println("input: " + input);
        System.out.println("output: " + output);

        test.printBoard(); // final status of the board
    }

    public String moveTest(String inputString) {
        boolean isValid = false;
        String errorMessage = isConfigurable(inputString);
        if ( CommonUtil.isEmpty(errorMessage) ) {
            int count = 0;
            for ( Bar move : moves ) {
                count++;
                Bar bar = engine.getGame().findBar(move.getId(), move.getOrientation());
                if ( (BarPosition.INNER.equals(bar.getPosition()) && BarPosition.OUTER.equals(move.getPosition())) ||
                        (BarPosition.OUTER.equals(bar.getPosition()) && BarPosition.INNER.equals(move.getPosition())) ) {
                    move.setPosition(BarPosition.CENTRAL);
                }

                Player playerInTurn = getPlayer(movingPlayer);
                if ( playerInTurn != null ) {
                    message = engine.makeMove(move.getId(), move.getOrientation(), move.getPosition(), playerInTurn);
                    if ( !Constant.STATUS_OK.equals(message.getCode()) ) {
                        errorMessage = getErrorMessage(message.getCode());
                        isValid = false;
                        break;
                    }

                    isValid = true;

                    if ( engine.isGameEndConditionReached() ) {
                        System.out.println("♒♒♒♒♒♒♒♒♒♒♒♒♒   W I N N E R   ♒♒♒♒♒♒♒♒♒♒♒♒♒");
                        System.out.println("♪♒♪☨♪❆★✿☆ ☛ ☛♔♔ " + engine.getWinner().getNickname() + " ♔♔ ☚☚☆✿★❆♪☨♪♒♪");
                        if ( count != moves.size() ) {
                            movingPlayer = engine.getGame().getNextPlayer() != null ? engine.getGame().getNextPlayer().getId() + 1 : engine.getGame().getLastPlayer().getId() + 1;
                            System.out.println("The game has finished, next (" + (moves.size() - count) + ") moves will be skipped.");
                            break;
                        }
                    }
                } else {
                    errorMessage = MessageFormat.format("error: Player {0} does not exist in the game.", movingPlayer);
                    isValid = false;
                    break;
                }
                movingPlayer = engine.getGame().getNextPlayer() != null ?  engine.getGame().getNextPlayer().getId() + 1 : engine.getGame().getLastPlayer().getId() + 1;
            }
        }

        if ( isValid ) {
            return getFinalStatus();
        }

        return errorMessage;
    }

    private String isConfigurable(String test) {
        numberOfPlayers = Integer.parseInt(test.substring(0, 1));
        beadsInTheGrid = test.substring(16, 65);

        engine.startGame();
        engine.getGame().setBoard(new SlotInfo[7][7]);

        String initialHorizontalBar = test.substring(2, 9);
        reConfigureBars(BarOrientation.HORIZONTAL, initialHorizontalBar.toCharArray());

        String initialVerticalBar = test.substring(9, 16);
        reConfigureBars(BarOrientation.VERTICAL, initialVerticalBar.toCharArray());

        String errorMessage = isPossibleInitialConfiguration();

        if ( CommonUtil.isEmpty(errorMessage) ) {
            movingPlayer = Integer.parseInt(test.substring(1, 2));
            errorMessage = isPossibleMovesConfiguration(test.substring(65, test.length()));
            if ( CommonUtil.isEmpty(errorMessage) ) {
                return "";
            }
        }

        return errorMessage;
    }

    private String isPossibleInitialConfiguration() {
        char beads[] = beadsInTheGrid.toCharArray();
        Set<Integer> playersOnBoard = new TreeSet<>();

        for ( char playersBead : beads ) {
            if ( playersBead == '1' || playersBead == '2' || playersBead == '3' || playersBead == '4' || playersBead == '5' ) {
                playersOnBoard.add(Character.getNumericValue(playersBead));
            } else if ( playersBead != '0' ) {
                return MessageFormat.format("error: Impossible to add player [{0}].", playersBead);
            }
        }

        if ( numberOfPlayers < playersOnBoard.size() ) {
            return "error: The number of players doesn't match with the beads on the board.";
        }

        for ( int id : playersOnBoard ) {
            Player player = new Player(id - 1, String.valueOf(id), "black");
            message = engine.addPlayer(player);
            if ( Constant.STATUS_OK.equals(message.getCode()) ) {
                for ( int x = 1; x <= beads.length; x++ ) {
                    if ( String.valueOf(id).equals(String.valueOf(beads[x - 1])) ) {
                        Bead bead = new Bead((x % 7 == 0 ? x / 7 : (x / 7) + 1) - 1, (x % 7 == 0 ? 7 : x % 7) - 1);
                        message = engine.addBeadToBoard(player, bead);
                        if ( !Constant.STATUS_OK.equals(message.getCode()) ) {
                            return getErrorMessage(message.getCode());
                        }
                    }
                }
            } else {
                return getErrorMessage(message.getCode());
            }
        }

        return "";
    }

    private String isPossibleMovesConfiguration(String givenMoves) {
        if ( CommonUtil.isEmpty(givenMoves) || givenMoves.length()%3 != 0 ) {
            return MessageFormat.format("error: Error configuring moves {0} ...", moves);
        }

        for ( int i = 0; i < givenMoves.length(); i += 3 ) {
            String move = givenMoves.substring(i, i + 3);
            if ( (CommonUtil.equalsIgnoreCase(move.substring(0, 1), "h") || CommonUtil.equalsIgnoreCase(move.substring(0, 1), "v")) &&
                    (CommonUtil.equalsIgnoreCase(move.substring(1, 2), "1") || CommonUtil.equalsIgnoreCase(move.substring(1, 2), "2")) || CommonUtil.equalsIgnoreCase(move.substring(1, 2), "3") || CommonUtil.equalsIgnoreCase(move.substring(1, 2), "4") || CommonUtil.equalsIgnoreCase(move.substring(1, 2), "5") || CommonUtil.equalsIgnoreCase(move.substring(1, 2), "6") || CommonUtil.equalsIgnoreCase(move.substring(1, 2), "7") &&
                    (CommonUtil.equalsIgnoreCase(move.substring(2, 3), "i") || CommonUtil.equalsIgnoreCase(move.substring(2, 3), "o"))) {
                Bar bar = new Bar(Integer.valueOf(move.substring(1, 2)) - 1, CommonUtil.equalsIgnoreCase(move.substring(0, 1), "H") ?
                        BarOrientation.HORIZONTAL : BarOrientation.VERTICAL, CommonUtil.equalsIgnoreCase(move.substring(2,3), "I") ?
                        BarPosition.INNER : BarPosition.OUTER); // for inwards and outwards
                moves.add(bar);
            } else {
                return MessageFormat.format("error: String configuration {0} is invalid ...", move);
            }
        }

        return "";
    }

    private String getBarPositions(BarOrientation orientation) {
        String position = "";
        for ( Bar bar : engine.getGame().getBars(orientation) ) {
            position += bar.getPosition().getInitialSlot();
        }
        return position;
    }

    private Player getPlayer(int id) {
        for ( Player player : engine.getGame().activePlayers() ) {
            if ( player.getId() + 1 == id ) {
                return player;
            }
        }
        return null;
    }

    private void reConfigureBars(BarOrientation orientation, char[] initialBars) {
        for ( int i = 0; i < 7; i++ ) {
            if ( ((Character)'0').equals(initialBars[i]) ) {
                engine.getGame().getBars(orientation).get(i).setPosition(BarPosition.INNER);
            } else if ( ((Character)'1').equals(initialBars[i]) ) {
                engine.getGame().getBars(orientation).get(i).setPosition(BarPosition.CENTRAL);
            } else {
                engine.getGame().getBars(orientation).get(i).setPosition(BarPosition.OUTER);
            }
        }
        engine.getGame().refreshBoard();
    }

    private String getFinalStatus() {
        String output = String.valueOf(numberOfPlayers);
        output += engine.getGame().getNextPlayer() != null ? engine.getGame().getNextPlayer().getNickname() : engine.getGame().getLastPlayer().getNickname();
        output += getBarPositions(BarOrientation.HORIZONTAL);
        output += getBarPositions(BarOrientation.VERTICAL);
        output += getBeads();

        return  output;
    }

    private String getBeads() {
        String beads[][] = new String[7][7];
        String output = "";

        for ( Player p : engine.getGame().activePlayers() ) {
            for ( Bead bead : p.activeBeads() ) {
                beads[bead.getPosition().getX()][bead.getPosition().getY()] = p.getNickname();
            }
        }

        for ( int i = 0; i < Constant.BOARD_INDEX; i++ ) {
            for ( int j = 0; j < Constant.BOARD_INDEX; j++ ) {
                if ( CommonUtil.isEmpty(beads[i][j]) ) {
                    beads[i][j] = "0";
                }
                output += beads[i][j];
            }
        }

        return output;
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
        if ( CommonUtil.equalsIgnoreCase(s,"1") ) s = "♥";
        if ( CommonUtil.equalsIgnoreCase(s,"2") ) s = "♦";
        if ( CommonUtil.equalsIgnoreCase(s,"3") ) s = "♣";
        if ( CommonUtil.equalsIgnoreCase(s,"4") ) s = "♠";
        return s == null ? "[ ]" : "[" + s + "]";
    }

    private String getErrorMessage(String code) {
        if ( Constant.STATUS_ERR_NUMBER_PLAYERS.equals(code) ) {
            return "error: Number of players out of bound";
        }
        if ( Constant.STATUS_ERR_BAR_POSITION.equals(code) ) {
            return "error: Cannot move the bar to the new position. Invalid movement";
        }
        if ( Constant.STATUS_ERR_BAR_SELECTED.equals(code) ) {
            return "error: Cannot move selected bar because it was moved in the previous round by one of your opponents";
        }
        if ( Constant.STATUS_ERR_BAR_CONSECUTIVE.equals(code) ) {
            return "error: Cannot move selected bar because it was moved in one of the two previous rounds";
        }
        if ( Constant.STATUS_ERR_SAME_PREVIOUS_PLAYER.equals(code) ) {
            return "error: You cannot move twice in a row";
        }
        if ( Constant.STATUS_ERR_PLACED_BEAD.equals(code) ) {
            return "error: Cannot placed the bead in this position. Invalid movement";
        }
        return "error: Something went wrong";
    }

}
