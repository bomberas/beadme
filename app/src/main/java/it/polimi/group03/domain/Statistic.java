package it.polimi.group03.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class contains all the information related
 * to the statistics collected during the game.
 * It will be used also to hold the status sent and
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
 * <li>Duration of the last game played. </li>
 * <li>The longest game.</li>
 * <li>The shortest game. </li>
 * <li>The fastest elimination of the players. </li>
 * <li>The player who survived the longest time. </li>
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class Statistics {


    SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

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
     * Name of one of the losers. It may happen that there are no winners.
     */
    private String loser4Name;
    /**
     * Time in which the game started. Possibly used for retrieving the average time of the game.
     */
    private String startTime;
    /**
     * Time in which the game finished. Possibly used for retrieving the average time of the game.
     */
    private String endTime;

    /**
     * Get-Set for the date.
     */
    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    /**
     *  Returns the possible winner.
     */
    public boolean isWinner() {
        return winner;
    }
    /**
     * Get-Set for the winner.
     */
    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
    /**
     * Gets the winner's chosen bead's color.
     */
    public String getWinnerColor() {
        return winnerColor;
    }

    /**
     * Sets the winner's chosen bead's color.
     */
    public void setWinnerColor(String winnerColor) {
        this.winnerColor = winnerColor;
    }

    /**
     * Gets the round, player 1 makes his move again.
     */
    public int getRounds() {
        return rounds;
    }

    /**
     * Sets the round, player 1 makes his move again.
     */
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    /**
     * Gets the turn for the next player.
     */
    public int getTurns() {
        return turns;
    }

    /**
     * Sets the turn for the next player.
     */
    public void setTurns(int turns) {
        this.turns = turns;
    }

    /**
     * Get-Sets for the losers.
     */
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

    public String getLoser4Name() {
        return loser4Name;
    }

    public void setLoser4Name(String loser4Name) {
        this.loser4Name = loser4Name;
    }

    /**
     *  The time when the game starts.
     */
    public String getStartTime() {
        return startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * The time when the game ends.
     */
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getDuration()
    {
        Date d1 = null,d2 = null;
        try {
            d1 = format.parse(startTime);
            d2 = format.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;

        return diff;
    }

}