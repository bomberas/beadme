package it.polimi.group03.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
 * <li>Top 3 players.</li>
 * <li>Bottom 3 players.</li>
 * <li>Number of victories per player.</li>
 * <li>Number of defeats per player.</li>
 * <li>Number of rounds in last game.</li>
 * <li>Average rounds in a game.</li>
 * <li>Number of unconcluded games.</li>
 * <li>Average time of a game.</li>
 *
 * @author cecibloom
 * @author megireci
 * @author tatibloom
 * @version 1.0
 * @since 11/11/2015.
 */

public class Statistic implements Serializable {

    /**
     * ID for the game.
     */
    private int id;
    /**
     * Time in which the game started. Possibly used for retrieving the average time of the game.
     */
    private Date startTime;
    /**
     * Time in which the game finished. Possibly used for retrieving the average time of the game.
     */
    private Date endTime;
    /**
     * Number of players in the game
     */
    private int numberOfPlayers;
    /**
     * Name of the winner.
     */
    private String winnerName;
    /**
     * Icon picked by the winner.
     */
    private int winnerIcon;
    /**
     * Name of one of the losers.
     */
    private String loser1Name;
    /**
     * Icon picked by the loser 1.
     */
    private int loser1Icon;
    /**
     * Name of one of the loser 2.
     */
    private String loser2Name;
    /**
     * Icon picked by the loser 2.
     */
    private int loser2Icon;
    /**
     * Name of one of the loser 3.
     */
    private String loser3Name;
    /**
     * Icon picked by the loser 3.
     */
    private int loser3Icon;
    /**
     * Number of rounds played in the game until someone won.
     */
    private int rounds;
    /**
     * Number of victories.
     */
    private int victories;
    /**
     * Number of defeats.
     */
    private int defeats;

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getWinnerIcon() {
        return winnerIcon;
    }

    public void setWinnerIcon(int winnerIcon) {
        this.winnerIcon = winnerIcon;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getLoser1Icon() {
        return loser1Icon;
    }

    public void setLoser1Icon(int loser1Icon) {
        this.loser1Icon = loser1Icon;
    }

    public int getLoser2Icon() {
        return loser2Icon;
    }

    public void setLoser2Icon(int loser2Icon) {
        this.loser2Icon = loser2Icon;
    }

    public int getLoser3Icon() {
        return loser3Icon;
    }

    public void setLoser3Icon(int loser3Icon) {
        this.loser3Icon = loser3Icon;
    }

    public long getDuration() {
        return TimeUnit.MILLISECONDS.toMinutes(endTime.getTime() - startTime.getTime());
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }
}