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
 * <li>Number of victories per player.</li>
 * <li>Number of rounds in last game.</li>
 * <li>Duration of the last game played. </li>
 * <li>The longest game.</li>
 * <li>The shortest game. </li>
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class Statistic {

    /**
     * <code>true</code> if there was a winner.
     * <code>false</code> if not.
     */
    public static boolean defeats;
    public static int victories;
    public static boolean winner;
    public int getVictory()
    {
        while (winner=true){
            victories++;
            break;
        }
        return victories;
    }

    private int playerID;
    public void setPlayerID(int playerID)
    {
        this.playerID=playerID;
    }

    public int getPlayerID ()
    {
        return playerID;
    }

    private int gameID;
    public void setGameID(int playerID)
    {
        this.gameID=gameID;
    }

    public int getGameID ()
    {
        return gameID;
    }

    private String winnerName;
    /**
     * Color picked by the winner. Could be used for "Which is your lucky color?"
     */
    private String winnerColor;
    /**
     * Number of rounds played in the game until someone won.
     */
    private static int rounds;
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

    private int gamecount;

    public void setGameCount (int gamecount)
    {
        this.gamecount=gamecount;
    }

    /**
     * Time in which the game started. Possibly used for retrieving the average time of the game.
     */
    private Date startTime;
    /**
     * Time in which the game finished. Possibly used for retrieving the average time of the game.
     */
    private Date endTime;

    /**
     *  Returns the possible winner.
     */
    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getWinnerName() {
// just to test
        return winnerName;

    }
    private int nr_vict;

    public void setVictories (int nr_vict)
    {
        this.nr_vict=nr_vict;
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
    public static int getRounds() {
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
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * The time when the game ends.
     */
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static Date getDuration()
    {
        //
        return new Date();
    }


}