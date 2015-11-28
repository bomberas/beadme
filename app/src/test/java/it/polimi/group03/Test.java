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
 * the delivery of the project. It should be used <u>only</u> for tests based on strings.
 * The principal method of the class is {@link #moveTest(String)} and it is the one that should be used for
 * testing. Some minor validations have been made to avoid problems with the format of the entered strings.
 *
 * <p>Please consider the following:
 *
 * <ul style="list-style-type:circle">
 * <li>When a Game has finished (without errors) the "moving player" character showed in the string returned by
 * the method {@link #moveTest(String)} will be the last player who made a move regardless if he/she has lost or not.</li>
 * <li>The class {@link GameEngine} performs a validation whenever a user wants to placed a bead
 * on the board, it states that if a bead is placed over an empty slot, it will return an error message.</li>
 * </ul>
 *
 * For unit tests please refer to {@link GameTest}
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

    /**
     * Method created not for delivery but for internal testing on this class and game functionality
     *
     * <p>For unit tests please refer to {@link GameTest}
     *
     * @param args args
     */
    public static void main(String args[]) {
        Test test = new Test();
        System.out.println("\n\n*************************General************************************\n");
        String input = "2" + "1" + "0120120" + "2101102" + "0100100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i" + "v2o";
        String output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("error: Cannot placed the bead in this position. Invalid movement"));
        test.printBoard();

        input = "2" + "1" + "0120120" + "2101102" + "0000100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i" + "v2o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("21011012022011020000100002000000000002000000000000000010000000000"));
        test.printBoard();

        System.out.println("\n\n*************************Megi***************************************\n");

        input = "2" + "1" + "0120120" + "2101102" + "0000100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i"  + "v2o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("21011012022011020000100002000000000002000000000000000010000000000"));
        test.printBoard();

        input = "2" + "1" + "0120120" + "2101102" + "0000100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i" + "v2o" + "h2o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("22021012022011020000100000000000000002000000000000000010000000000"));
        test.printBoard();

        //Player 2 tries to move the same bar that player 1 moved. It gives an error.
        input = "2" + "1" + "0120120" + "2101102" + "0000100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i" + "v2o" + "h2o" + "h2o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("error: Cannot move selected bar because it was moved in the previous round by one of your opponents"));
        test.printBoard();

        input =   "2" + "1" + "0120120" + "2101102" + "0000100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i" + "v2o" + "h2o" + "v4i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("21021012022001020000100000000000000002000000000000000010000000000"));
        test.printBoard();

        input =   "2" + "1" + "0120120" + "2101102" + "0000100" + "0020000" + "0000001" + "2000000" + "0000000" + "0001000" + "0000000" + "h3i" + "v2o" + "h3o" + "v4i" + "h3i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("error: Cannot move selected bar because it was moved in one of the two previous rounds"));
        test.printBoard();

        //Player 1 does the last move which makes the player's 2 bead fall down.
        input = "2" + "1" + "0211120" + "2200102" + "0000100" + "0000000" + "0000000" + "2000000" + "0000000" + "0001000" + "0000000" + "v1i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("21021112012001020000100000000000000000000000000000000010000000000"));
        test.printBoard();

        input = "2" + "1" + "0210120" + "2200102" + "0000100" + "0000000" + "0000000" + "2000000" + "0000000" + "0001000" + "0000000" + "h50" + "v1i" + "h3i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("22020022012001020000100000000000000002000000000000000010000000000"));
        test.printBoard();

        input = "2" + "2" + "0210120" + "2200102" + "0000100" + "0000000" + "0000000" + "2000000" + "0000000" + "0001000" + "0000000" + "h50" + "v1i" + "h3i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("21020022012001020000100000000000000002000000000000000010000000000"));
        test.printBoard();

        input = "2" + "2" + "0210120" + "2200102" + "0000100" + "0000000" + "0000000" + "2000000" + "0000000" + "0001000" + "0000000" + "h50" + "v1i" + "h3i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("21020022012001020000100000000000000002000000000000000010000000000"));
        test.printBoard();

        System.out.println("\n\n*************************Ceci***************************************\n");

        input = "4" + "1" + "0120120" + "2101102" + "3010120" + "0020010" + "0032001" + "2030301" + "0300004" + "0004004" + "4004020" + "v3o" + "v6o" + "h6i" + "h4o" + "v4o" + "h3i" + "h4i";//error movimiento repetido en ronda
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("error: Cannot move selected bar because it was moved in the previous round by one of your opponents"));
        test.printBoard();

        input = "4" + "1" + "0120120" + "2101102" + "3010120"+ "0020010" + "0032001" + "2030301" + "0300004" + "0004004" + "4004020" + "v3o" + "v6o"+ "h6i"+ "h4o"+ "v4o"+ "h3i"+ "h6i" + "h1o" + "v6o" + "h2i" + "v2o" + "h1i" + "v5o" + "v7i" + "h1o" + "v7i" + "v3i" + "v1i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("44101110012022200000000000000000020000000000000000000000004004020"));
        test.printBoard();

        input = "4" + "4" + "1011100" + "1202220" + "0000000" + "0000000" + "0000000" + "0000004" + "0000000" + "0000000" + "0000002" + "v7o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("44101110012022210000000000000000000000000000000000000000000000000"));//game finished: winner player 4
        test.printBoard();

        System.out.println("\n\n**********************POLIMI****************************************\n");

        input = "4" + "1" + "1111111" + "1111111" + "0102000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000034" + "h4i" + "h3i" + "h2i" + "h1i" + "h2o" + "h1o" + "h5o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("4" + "4" + "1100211" + "1111111" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000034"));
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0102000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000034" + "h4i" + "h3i" + "h2i" + "h1i" + "h2o" + "h1o" + "h2i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("error: Cannot move selected bar because it was moved in one of the two previous rounds"));
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0304000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012" + "h4i" + "h3i" + "h2i" + "h1i" + "h4o" + "h3o" + "h5o";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("4" + "2" + "0011211" + "1111111" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012"));
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0304000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012" + "h4i" + "h3i" + "h2i" + "h1i" + "h4o" + "h3o" + "h4i";
        output = test.moveTest(input);
        System.out.println(output);
        System.out.println(output.equals("error: Cannot move selected bar because it was moved in one of the two previous rounds"));
        test.printBoard();

        // Ceci Mars
        input = "4" + "1" + "1111111" + "1111111" + "0102000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000034" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 1 & 2*/  + "h2o" + "h2o"; // Error moved by one opponent
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0102000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000034" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 1 & 2*/  + "h2o" + "h1o" + "h2i"; // Error moved previous 2 consecutive rounds
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0102000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000034" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 1 & 2*/  + "h2o" + "h1o" + "h5o"; // Ok, out = 44110021111111110000000000000000000000000000000000000000000000034
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0304000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 3 & 4 */  + "h1o"; // Error moved by one opponent
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0304000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 3 & 4 */  + "h4o" + "h2o"; // Error moved by one opponent
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0304000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 3 & 4 */  + "h4o" + "h3o" + "h4i"; // Error moved in previous 2 consecutive rounds
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "1111111" + "1111111" + "0304000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000000" + "0000012" +
                "h4i" + "h3i" + "h2i" + "h1i" /* Player 4 kills 3 & 4 */  + "h4o" + "h3o" + "h5o"; // Ok, out = 42001121111111110000000000000000000000000000000000000000000000012
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "0000000" + "0000001" + "0000000" + "0000000" + "0000000" + "1020102" + "0000000" + "0000000" + "0000000" +
                "h4o" /* Player 1 kills both himself and 2 */ ; // Ok, out = 41000100000000010000000000000000000000000000000000000000000000000
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "0000000" + "0000001" + "0000000" + "0000000" + "0000202" + "1000100" + "0000000" + "0000000" + "0000000" +
                "h4o" /* Player 1 commits suicide */ ; // Ok, out = 41000100000000010000000000000000002020000000000000000000000000000
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "2" + "1" + "0000000" + "0000001" + "5000000" + "0000000" + "0000202" + "1000100" + "0000000" + "0000000" + "0000000" +
                "h4o"; // Error: The number of players doesn't match with the beads on the board
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "0000000" + "6000001" + "6000000" + "0000000" + "0000202" + "1000100" + "0000000" + "0000000" + "0000000" +
                "h4o"; // Error: Impossible to add player [6]. ?But I put a position 6, and what's the diff with previous?
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "0000000" + "6000001" + "0000000" + "0000000" + "0000202" + "1000100" + "0000000" + "0000000" + "0000000" +
                "h4o"; // No error output = 42000100020000010000000000000000002021000000000000000000000000000 ? But I put a position 6
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "0000000" + "0000001" + "0000000" + "0300304" + "0000200" + "1000200" + "0000000" + "0000000" + "0000000" +
                "v6o"; // [error: Cannot placed the bead in this position.] But didnt print the beads 3 and 4 that were correct
        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

        input = "4" + "1" + "2120100" + "2012122" + "3100400" + "0020000" + "0013003" + "3010201" + "0240000" + "4400000" + "2020431"
                + "v5o" + "v2o" + "h3i" + "v1i" + "v3i" + "v6i" + "h2i" + "h7o" + "h1i" + "h4o" + "v5i" + "h7o" + "v7i" + "h5o" + "v2i" + "h7i";

        output = test.moveTest(input);
        System.out.println(output);
        test.printBoard();

    }

    /**
     * This method performs several actions in order to test the game mechanics. It uses mainly the methods ands classes
     * created in the model and some others added to this class to perform validations to the input and returning the
     * results. This method is prepared to receive a string input that complies with the following pattern:
     *
     * <ul style="list-style-type:circle">
     * <li>[01] number of players +</li>
     * <li>[01] moving player +</li>
     * <li>[07] positions of the horizontal bars +</li>
     * <li>[07] positions of the vertical bars +</li>
     * <li>[49] beads in the grid +</li>
     * <li>[3*n] moves.</li>
     * </ul>
     *
     * @see GameEngine
     * @see GameTest
     *
     * @param inputString A <i>68+ character string</i> that contains the configurations and movements to be tested
     * @return {@code string}  A <i>65-character string</i> with the resulting state of the moves, in the same format as the first
     *                          65 characters of the input if it was possible to perform all the moves and configurations
     *                          established in the input, or an <i>error message</i> indicating what went wrong e.g “error: <explanation>”
     *
     */
    public String moveTest(String inputString) {
        engine = new GameEngine();
        numberOfPlayers = 0;
        beadsInTheGrid = "";
        movingPlayer = 0;
        moves = new ArrayList<>();
        message = null;


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
                        System.out.println("⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱   W I N N E R   ⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱");
                        System.out.println("♪ ♒ ♪ ☨ ♪ ❆ ★ ✿ ☆ ☛ ⌇⌘ ☇ ☃ ☃ ♨ ♔ ♛ ☄ ☄ ☄ ☄    Player " + engine.getWinner().getNickname() + "    ☄ ☄ ☄ ♛ ♔ ♨ ☃ ☃ ☃ ☇ ⌘ ⌇☚ ☆ ✿ ★ ❆ ♪ ☨ ♪ ♒ ♪");
                        System.out.println("⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱⋰⋆⋱\n\n");

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
        System.out.println("⋯⋯⋯⋯⋯Player 1[♥]⋯⋯⋯⋯⋯Player 2[♦]⋯⋯⋯⋯⋯Player 3[♣]⋯⋯⋯⋯⋯Player 4[♠]⋯⋯⋯⋯⋯\n");

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
        if ( CommonUtil.equalsIgnoreCase(s, "1") ) s = "♥";
        if ( CommonUtil.equalsIgnoreCase(s, "2") ) s = "♦";
        if ( CommonUtil.equalsIgnoreCase(s, "3") ) s = "♣";
        if ( CommonUtil.equalsIgnoreCase(s, "4") ) s = "♠";
        return s == null ? "[ ]" : "[" + s + "]";
    }

    private String getErrorMessage(String code) {
        if ( Constant.STATUS_ERR_NUMBER_PLAYERS.equals(code) ) {
            return "error: Number of players out of bound.";
        }
        if ( Constant.STATUS_ERR_BAR_POSITION.equals(code) ) {
            return "error: Cannot move this bar to the new position.";
        }
        if ( Constant.STATUS_ERR_BAR_SELECTED.equals(code) ) {
            return "error: Cannot move this bar because it was moved in the previous round by one of your opponents.";
        }
        if ( Constant.STATUS_ERR_BAR_CONSECUTIVE.equals(code) ) {
            return "error: Cannot move this bar because it was moved in one of your two previous consecutive turns.";
        }
        if ( Constant.STATUS_ERR_SAME_PREVIOUS_PLAYER.equals(code) ) {
            return "error: Cannot move twice in a row.";
        }
        if ( Constant.STATUS_ERR_PLACED_BEAD.equals(code) ) {
            return "error: Cannot placed the bead in this position.";
        }
        return "error: Something went wrong";
    }

}
