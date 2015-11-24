package it.polimi.group03.domain;

import java.util.Date;

/**
 * This class contains all the information related
 * to the statistics collected during the game.
 * It will be used also to hold the stats sent and
 * retrieved from the database.
 * <p>The game will show in the Statistic section all kind of
 * history such as, but not limited to:
 *
 * <ul style="list-style-type:circle">
 * <li>Number of games played.</li>
 * <li>Top 10 players.</li>
 * <li>Bottom 10 players.</li>
 * <li>Number of victories per player.</li>
 * <li>Number of defeats per player.</li>
 * <li>Number of rounds in last game.</li>
 * <li>Number of turns in last game.</li>
 * <li>Average rounds in a game.</li>
 * <li>Average turns in a game.</li>
 * <li>Number of unconcluded games.</li>
 * <li>Average time of a game.</li>
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class Statistic {

    /**
     * Date in which the game was played.
     */
    private String date;
    /**
     * <code>true</code> if there was a winner.
     * <code>false</code> if not.
     */
    private boolean winner;
    /**
     * Name of the winner.
     */
    private String winnerName;
    /**
     * Color picked by the winner. Could be used for "Which is your lucky color?"
     */
    private String winnerColor;
    /**
     * Number of rounds played in the game until someone won.
     */
    private int rounds;
    /**
     * Number of turns played in the game until someone won.
     */
    private int turns;
    /**
     * Name of one of the losers.
     */
    private String loser1Name;
    /**
     * Name of one of the losers.
     */
    private String loser2Name;
    /**
     * Name of one of the losers.
     */
    private String loser3Name;
    /**
     * Time in which the game started. Possibly used for retrieving the average time of the game.
     */
    private Date startTime;
    /**
     * Time in which the game finished. Possibly used for retrieving the average time of the game.
     */
    private Date endTime;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getWinnerColor() {
        return winnerColor;
    }

    public void setWinnerColor(String winnerColor) {
        this.winnerColor = winnerColor;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public String getLoser1Name() {
        return loser1Name;
    }

    public void setLoser1Name(String loser1Name) {
        this.loser1Name = loser1Name;
    }

    public String getLoser2Name() {
        return loser2Name;
    }

    public void setLoser2Name(String loser2Name) {
        this.loser2Name = loser2Name;
    }

    public String getLoser3Name() {
        return loser3Name;
    }

    public void setLoser3Name(String loser3Name) {
        this.loser3Name = loser3Name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}