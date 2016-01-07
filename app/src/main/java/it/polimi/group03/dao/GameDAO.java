package it.polimi.group03.dao;

import java.util.List;

import it.polimi.group03.domain.Statistic;

public interface GameDAO {
    Statistic save (Statistic s);
    List<Statistic> findAll() ;
    List<Statistic> getVictories() ;
    List<Statistic> getTotalGames();
    void delete(int id);

}