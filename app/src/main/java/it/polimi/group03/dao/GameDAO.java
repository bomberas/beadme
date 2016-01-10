package it.polimi.group03.dao;

import java.util.Date;
import java.util.List;

import it.polimi.group03.domain.Statistic;

/**
 * This class holds the logic to support all the transactions performed on database,
 * is used ONLY for statistics.<br /><br />
 *
 * See {@link Statistic}
 *
 * @author tatibloom
 * @author megireci
 *
 * @version 1.0
 * @since 10/01/2016.
 */
public interface GameDAO {

    /**
     * Save a new game, with all the info related.
     *
     * @param s Statistic to be saved (refers to a game).
     */
    void save (Statistic s);

    /**
     * Retrieves all the games saved.
     *
     * @param limit if present, will limit the query
     * @return All games previously saved.
     */
    List<Statistic> findAll(int limit) ;
    /**
     * Retrieves the name, icon and number of victories of the top 3 players.
     * @return Top 3 players
     */
    List<Statistic> findHallOfFame() ;
    /**
     * Retrieves the name, icon and number of defeats of the bottom 3 players.
     * @return Bottom 3 players
     */
    List<Statistic> findHallOfShame() ;
    /**
     * Retrieves the last time in which the player was connected to the game.
     *
     * @param player player name
     * @return The last date in which the player was connected.
     */
    Date getLastDateConnected(String player) ;
    /**
     * Retrieves the average rounds played per game.
     *
     * @return average of rounds
     */
    int getAverageRounds();
    /**
     * Retrieves the total number of game played.
     *
     * @return number of games
     */
    int getTotalGames();

}