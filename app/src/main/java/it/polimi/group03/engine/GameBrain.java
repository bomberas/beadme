package it.polimi.group03.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;
import it.polimi.group03.util.SlotInfo;

/**
 * This class holds the logic to play a game against the AI, the algorithm used here allow us to place
 * the beads in the safest positions as well as make the smartest moves, taking account not only the other
 * players dropped beads, but their own kamikaze movements.
 */
public class GameBrain {

    private int[][] board;
    private int[][] neighbors;

    public GameBrain() {
        this.board = new int[Constant.BOARD_INDEX][Constant.BOARD_INDEX];
        this.neighbors = new int[Constant.BOARD_INDEX][Constant.BOARD_INDEX];
    }

    /**
     * Retrieve a Bead (with the selected position) in which the AI player should place its bead.
     */
    public Bead placeBead(GameEngine engine) {
        //refresh neighborhood after placing a bead
        refreshNeighborhood(engine);

        //local copy
        int[][] neighbors = CommonUtil.cloneArray(this.neighbors);

        boolean keepLooking = true;
        int r = 0;
        int c = 0;
        int sum = 3;//indicates a blue hole over a red one

        //till we found the best place to put our bead
        while ( keepLooking ) {
            int max = -1;
            boolean found = false;

            for ( int row = 0; row < Constant.BOARD_INDEX; row++) {
                for ( int col = 0; col < Constant.BOARD_INDEX; col++) {
                    if ( board[row][col] == sum && neighbors[row][col] != 0 && neighbors[row][col] > max) {
                        //select the best place according to the neighborhood
                        max = neighbors[row][col];
                        r = row;
                        c = col;
                        found = true;
                    }
                }
            }

            //if it can not find a blue over a red slot, find a blue slot, if no, find a red slot
            if ( !found ) {
                sum--;
            } else {
                //validate if the selected position is valid, if no, nullify this position for the next calculations
                if ( !isValidSlot(r, c, engine) ) {
                    neighbors[r][c] = 0;
                } else {//if the new position is valid, break the loop
                    keepLooking = false;
                }
            }
        }

        return new Bead(r, c);
    }

    /**
     * Retrieve a bar with the new position selected as the preferred movement.
     */
    public Bar move(GameEngine engine) {
        Map<String, Object> map;
        Bar bestBar = null;
        int defeats = 0;

        int level = 0;//0 for counting the defeats, 1 for placing the beads on red slots, 2 for placing the beads on blue slots.

        //Obtaining players
        Player me = engine.getGame().getPlayers().get(0).isMrRoboto() ? engine.getGame().getPlayers().get(0) : engine.getGame().getPlayers().get(1);
        Player theOtherGuy = engine.getGame().getPlayers().get(0).isMrRoboto() ? engine.getGame().getPlayers().get(1) : engine.getGame().getPlayers().get(0);

        //Keep doing this unless a good move has been found
        while ( defeats <= 0 && level <= 2 ) {

            map = getBestBarToMove(engine, me, theOtherGuy, level);
            bestBar = (Bar) map.get("bar");
            defeats = Integer.valueOf(String.valueOf(map.get("defeats")));

            level++;// if it can not kill, then put the beads on red slots, if it can not do that, put the bead on blue slots.
        }

        //if we can find a proper bar, we will make a defensive move.
        if ( defeats <= 0 ) {
            return getBestDefensiveBar(engine, me);
        } else {
           return bestBar;
        }
    }

    /**
     * If the algorithm wasn't able to find a proper bar to move and defeats the opponents' beads,
     * nor place at least one of his/her beads on a cover slot,
     * the next move will be a defensive one, which means that will retrieve a new position
     * that will cover most of are own beads with both bars without committing suicide.
     */
    private Bar getBestDefensiveBar(GameEngine engine, Player me) {
        Bar selectedBar = null;

        List<Bar> bars = getBarsOfBeads(engine, me.activeBeads());

        int suicides = 1;
        int max = -1;
        int defeats = -1;

        for ( Bar bar : bars ) {
            List<Bead> myBeadsOnBar = getBeadsOnBar(me.activeBeads(), bar);

            if ( BarPosition.INNER.equals(bar.getPosition()) ||
                    BarPosition.OUTER.equals(bar.getPosition())) {
                bar.setPosition(BarPosition.CENTRAL);
                max = getDefeatsOnBar(engine, myBeadsOnBar, bar, 3);
                suicides = getDefeatsOnBar(engine, myBeadsOnBar, bar, 0);
            } else {
                bar.setPosition(BarPosition.INNER);
                int defeatedBeadsInner = getDefeatsOnBar(engine, myBeadsOnBar, bar, 3);
                int suicidedBeadsInner = getDefeatsOnBar(engine, myBeadsOnBar, bar, 0);

                bar.setPosition(BarPosition.OUTER);
                int defeatedBeadsOuter = getDefeatsOnBar(engine, myBeadsOnBar, bar, 3);
                int suicidedBeadsOuter = getDefeatsOnBar(engine, myBeadsOnBar, bar, 0);

                if ( defeatedBeadsInner > defeatedBeadsOuter && suicidedBeadsInner == 0 ) {
                    max = defeatedBeadsInner;
                    suicides = suicidedBeadsInner;
                    bar.setPosition(BarPosition.INNER);
                } else if ( suicidedBeadsOuter == 0 ) {
                    max = defeatedBeadsOuter;
                    suicides = suicidedBeadsOuter;
                    bar.setPosition(BarPosition.OUTER);
                }
            }

            if ( isConsecutiveBarMoveValid(engine, me, bar) && isSelectedBarMoveValid(engine, me, bar) && suicides == 0 ) {
                if ( max > defeats ) {
                    selectedBar = bar;
                    defeats = max;
                }
            }
        }

        return selectedBar != null ? selectedBar : getRandomBar(engine, me, bars);
    }

    /**
     * Murphy Law, if it isn't possible to find a proper bar to move, will return
     * the first bar allowed.
     */
    private Bar getRandomBar(GameEngine engine, Player me, List<Bar> bars) {
        List<Bar> allBars = engine.getGame().getBars(BarOrientation.VERTICAL);
        allBars.addAll(engine.getGame().getBars(BarOrientation.HORIZONTAL));
        boolean isValid = false;

        for ( Bar bar : allBars ) {
            boolean isUsed = false;

            for ( Bar usedBar : bars ) {
                if ( bar.getId() == usedBar.getId() && bar.getOrientation() == usedBar.getOrientation() && bar.getPosition() == usedBar.getPosition() ) {
                    isUsed = true;
                    break;
                }
            }

            if ( !isUsed ) {
                if ( BarPosition.INNER.equals(bar.getPosition()) || BarPosition.OUTER.equals(bar.getPosition()) ) {
                    bar.setPosition(BarPosition.CENTRAL);
                    isValid = isConsecutiveBarMoveValid(engine, me, bar) && isSelectedBarMoveValid(engine, me, bar);
                } else if ( BarPosition.CENTRAL.equals(bar.getPosition()) ) {
                    bar.setPosition(BarPosition.INNER);
                    isValid = isConsecutiveBarMoveValid(engine, me, bar) && isSelectedBarMoveValid(engine, me, bar);
                    if ( !isValid ) {
                        bar.setPosition(BarPosition.OUTER);
                        isValid = isConsecutiveBarMoveValid(engine, me, bar) && isSelectedBarMoveValid(engine, me, bar);
                    }
                }

                if ( isValid ) {
                    return bar;
                }
            }

        }

        return null;
    }

    /**
     * For both orientations (horizontal or vertical), the algorithm will search for the bar
     * and the position in which most of the opponent's beads will fall, by counting the defeats
     * and validating according to the game rules if the movement is valid, if no, will retrieve
     * the next best bar/position.
     */
    private Map<String,Object> getBestBarToMove(GameEngine engine, Player me, Player theOtherGuy, int level) {
        List<Bar> bars = getBarsOfBeads(engine, theOtherGuy.activeBeads());
        Bar selectedBar = null;

        int suicides;
        int max;
        int defeats = -1;

        //we need to validate the number of suicides too, to choose the next move
        for ( Bar bar : bars ) {
            List<Bead> theOtherGuyBeadsOnBar = getBeadsOnBar(theOtherGuy.activeBeads(), bar);
            List<Bead> myBeadsOnBar = getBeadsOnBar(me.activeBeads(), bar);

            if ( BarPosition.INNER.equals(bar.getPosition()) ||
                    BarPosition.OUTER.equals(bar.getPosition())) {
                bar.setPosition(BarPosition.CENTRAL);
                max = getDefeatsOnBar(engine, theOtherGuyBeadsOnBar, bar, level);
                suicides = getDefeatsOnBar(engine, myBeadsOnBar, bar, 0);
            } else {
                bar.setPosition(BarPosition.INNER);
                int defeatedBeadsInner = getDefeatsOnBar(engine, theOtherGuyBeadsOnBar, bar, level);
                int suicidedBeadsInner = getDefeatsOnBar(engine, myBeadsOnBar, bar, 0);

                bar.setPosition(BarPosition.OUTER);
                int defeatedBeadsOuter = getDefeatsOnBar(engine, theOtherGuyBeadsOnBar, bar, level);
                int suicidedBeadsOuter = getDefeatsOnBar(engine, myBeadsOnBar, bar, 0);

                if ( defeatedBeadsInner - (2 * suicidedBeadsInner) > defeatedBeadsOuter  - (2 * suicidedBeadsOuter) ) {
                    max = defeatedBeadsInner;
                    suicides = suicidedBeadsInner;
                    bar.setPosition(BarPosition.INNER);
                } else {
                    max = defeatedBeadsOuter;
                    suicides = suicidedBeadsOuter;
                    bar.setPosition(BarPosition.OUTER);
                }
            }

            if ( isConsecutiveBarMoveValid(engine, me, bar) && isSelectedBarMoveValid(engine, me, bar) ) {
                if ( theOtherGuy.activeBeads().size() - max == 0 ) { //Kamikaze move
                    selectedBar = bar;
                    defeats = 100;
                    break;
                } else if ( (max - (2 * suicides)) > defeats ) {
                    selectedBar = bar;
                    defeats = max;
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("bar", selectedBar);
        map.put("defeats", defeats);

        return map;
    }

    /**
     * Retrieves all the bars in which the beads provided are present.
     */
    private List<Bar> getBarsOfBeads(GameEngine engine, List<Bead> beads) {
        List<Bar> bars = new ArrayList<>();

        for (Bead bead : beads) {
            Bar horizontal = engine.getGame().findBar(bead.getPosition().getX(), BarOrientation.HORIZONTAL);
            bars.add(new Bar(horizontal.getId(), horizontal.getOrientation(), horizontal.getPosition()));
            Bar vertical = engine.getGame().findBar(bead.getPosition().getY(), BarOrientation.VERTICAL);
            bars.add(new Bar(vertical.getId(), vertical.getOrientation(), vertical.getPosition()));
        }

        return bars;
    }

    /**
     * Retrieves all the beads present on the selected bar.
     */
    private List<Bead> getBeadsOnBar(List<Bead> beads, Bar bar) {
        List<Bead> beadsOnBar = new ArrayList<>();

        for ( Bead bead : beads ) {
            if ( BarOrientation.HORIZONTAL.equals(bar.getOrientation()) && bead.getPosition().getX() == bar.getId() ) {
                beadsOnBar.add(bead);
            }
            if ( !BarOrientation.HORIZONTAL.equals(bar.getOrientation()) && bead.getPosition().getY() == bar.getId() ) {
                beadsOnBar.add(bead);
            }
        }

        return beadsOnBar;
    }

    /**
     * Retrieves the number of fallen beads in the selected bar after "performing"
     * the movement if the level is 0, if no, will retrieve a "strategic" count based on
     * how many RED or BLUE (depending on the level) slots are discovered by moving
     * the selected bar.
     */
    private int getDefeatsOnBar(GameEngine engine, List<Bead> beadsOnBar, Bar bar, int level) {
        List<Bar> horizontals = engine.getGame().getBars(BarOrientation.HORIZONTAL);
        List<Bar> verticals = engine.getGame().getBars(BarOrientation.VERTICAL);

        int defeats = 0;
        SlotInfo infoBlue, infoRed;

        for ( Bead bead : beadsOnBar ) {
            int x,y;

            //x and y denotes the slots we need to validate after "perform" the move
            if ( BarOrientation.HORIZONTAL.equals(bar.getOrientation()) ) {
                x = bead.getPosition().getY() + bar.getPosition().getInitialSlot();
                y = bead.getPosition().getX() + verticals.get(bead.getPosition().getY()).getPosition().getInitialSlot();
            } else {
                x = bead.getPosition().getY() + horizontals.get(bead.getPosition().getX()).getPosition().getInitialSlot();
                y = bead.getPosition().getX() + bar.getPosition().getInitialSlot();
            }

            infoRed = horizontals.get(bead.getPosition().getX()).getKeys()[x];
            infoBlue = verticals.get(bead.getPosition().getY()).getKeys()[y];

            //By assigning weights we take the best move into account
            int sum = (infoBlue.equals(SlotInfo.BLUE) ? 2 : 0) +
                    (infoRed.equals(SlotInfo.RED) ? 1 : 0);

            if ( sum == level ) { // level will drop bead or find better move
                defeats++;
            }
        }
        return defeats;
    }

    /**
     * This method will refresh the neighborhood matrix after each movement. The neighborhood
     * matrix will be used when the AI intends to place a bead, for which, based on the
     * quality of the neighborhood will be able to deduct how risky is the position selected.
     *
     * {@link GameBrain#countQualityOfNeighbors(int, int)}<br/>
     */
    private void refreshNeighborhood(GameEngine engine) {
        List<Bar> horizontals =  engine.getGame().getBars(BarOrientation.HORIZONTAL);
        List<Bar> verticals = engine.getGame().getBars(BarOrientation.VERTICAL);

        for ( int r = 0; r < Constant.BOARD_INDEX; r++) {
            for ( int c = 0; c < Constant.BOARD_INDEX; c++) {
                SlotInfo infoBlue = verticals.get(c).getKeys()[r + verticals.get(c).getPosition().getInitialSlot()];
                SlotInfo infoRed = horizontals.get(r).getKeys()[c + horizontals.get(r).getPosition().getInitialSlot()];
                board[r][c] = (infoBlue.equals(SlotInfo.BLUE) ? 2 : 0) +
                        (infoRed.equals(SlotInfo.RED) ? 1 : 0);
            }
        }

        //update quality of neighborhood
        for ( int r = 0; r < Constant.BOARD_INDEX; r++) {
            for ( int c = 0; c < Constant.BOARD_INDEX; c++) {
                neighbors[r][c] = countQualityOfNeighbors(r, c);
            }
        }

    }

    /**
     * This method will be used when the quality of the neighborhood needs to be updated. For each position in the
     * matrix 7*7 this method will assign a number depending on the quality of the next values,
     * this number will be the result of the sum of the neighbors, i.e if the quality of the neighborhood
     * for the position (2,3) is 24 means that no matter the move made on both bars (horizontals an verticals)
     * the bead placed in this position will never fall down, however if the quality of neighborhood for the same
     * position is low, this bead is on risk to fall down in the next move(s); this <i>it's just a
     * matter of probabilities.</i>
     */
    private int countQualityOfNeighbors(int row, int column) {
        int count = board[row][column];

        switch ( row ) {
            case 0:
                count += board[row + 1][column];
                count += board[row + 2][column];
                break;
            case 1:
                count += board[row - 1][column];
                count += board[row + 1][column];
                count += board[row + 2][column];
                break;
            case 5:
                count += board[row - 1][column];
                count += board[row - 2][column];
                count += board[row + 1][column];
                break;
            case 6:
                count += board[row - 1][column];
                count += board[row - 2][column];
                break;
            default:
                count += board[row + 2][column];
                count += board[row + 1][column];
                count += board[row - 1][column];
                count += board[row - 2][column];
                break;
        }

        switch ( column ) {
            case 0:
                count += board[row][column + 1];
                count += board[row][column + 2];
                break;
            case 1:
                count += board[row][column - 1];
                count += board[row][column + 1];
                count += board[row][column + 2];
                break;
            case 5:
                count += board[row][column - 1];
                count += board[row][column - 2];
                count += board[row][column + 1];
                break;
            case 6:
                count += board[row][column - 1];
                count += board[row][column - 2];
                break;
            default:
                count += board[row][column + 2];
                count += board[row][column + 1];
                count += board[row][column - 1];
                count += board[row][column - 2];
                break;
        }

        return count;
    }

    /**
     * This method checks that a player places a bead in an allowable position; in order to do so
     * first checks if the position of the new bead is valid  i.e if the new bead is been placed
     * in a holed slot or if there is already a bead in the selected slot.
     */
    private boolean isValidSlot(int row, int col, GameEngine engine) {
        //A player cannot place a bead on top an empty slot
        if ( neighbors[row][col] != 0 ) {
            for ( Player player : engine.getGame().getPlayers() ) {
                for ( Bead bead : player.getBeads() ) {
                    //A player cannot add a bead on a slot already taken
                    if ( bead.getPosition().getX() == row &&
                            bead.getPosition().getY() == col ) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    /**
     * This method checks that the bar selected by the player has not been slid by any of his/her opponents in the previous turn.
     * In order to check this rule, the method needs the information of the bar the player is pretending to move, this way is
     * possible to recognize the bar in the game and determine whether the bar was moved in a previous turn of the current round.
     */
    private boolean isSelectedBarMoveValid(GameEngine engine, Player player, Bar selectedBar) {
        for ( int i = engine.getGame().getMovedBarsByOpponents().size() - 1; i >= 0; i-- ){
            if ( engine.getGame().getMovedBarsByOpponents().get(i).getPlayer().getId() == player.getId() ) {
                // Validating until the same player is reached.
                break;
            } else if ( engine.getGame().getMovedBarsByOpponents().get(i).getBar().getId() == selectedBar.getId() &&
                    engine.getGame().getMovedBarsByOpponents().get(i).getBar().getOrientation().equals(selectedBar.getOrientation()) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks the movement that a player tries to do when there are only two players left in the game, in order to do it
     * validates the selected bar against the the list of moved bars, and checks if this particular player has two consecutive moves,
     * if so compares the bar with those two moves, if it is the same bar that the one that was moved before returns a negative response,
     * if not returns a positive response.
     */
    private boolean isConsecutiveBarMoveValid(GameEngine engine, Player player, Bar selectedBar){
        if ( !CommonUtil.isEmpty(engine.getGame().getMovedBarsByOpponents()) && engine.getGame().getMovedBarsByOpponents().size() >= 4 ){
            int myself = 0;
            int index1 = 0;
            int index2 = 0;

            for ( int i = engine.getGame().getMovedBarsByOpponents().size() - 1; i >= 0; i-- ){
                if ( engine.getGame().getMovedBarsByOpponents().get(i).getPlayer().getId() == player.getId() ) {
                    // If it's the same user it means it has reached the previous round
                    myself++;
                    if ( myself == 1 ) index1 = i; //Is the index of the first previous turn/move
                    if ( myself == 2 ){
                        index2 = i;
                        break; //Is the index of the second previous turn/move, so it's not necessary to continue with the iteration
                    }
                }
            }

            if ( myself == 2 ) { // If we found 2 consecutive moves for the player
                if ( engine.getGame().getMovedBarsByOpponents().get(index1).getBar().getId() == selectedBar.getId() &&
                        engine.getGame().getMovedBarsByOpponents().get(index2).getBar().getId() == selectedBar.getId() &&
                        engine.getGame().getMovedBarsByOpponents().get(index1).getBar().getOrientation() == selectedBar.getOrientation() &&
                        engine.getGame().getMovedBarsByOpponents().get(index2).getBar().getOrientation() == selectedBar.getOrientation() ) {

                    // In the occurrence of the equivalence of moved bars with intended bar, the condition is not met
                    // It means the bar was moved by the player two consecutive times
                    return false;
                }
            }
        }

        return true;
    }

}