package it.polimi.group03.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.List;

import it.polimi.group03.domain.Statistic;

/**
 * Created by tatibloom on 13/12/2015.
 */

public interface GameDAO {
    public Statistic save (Statistic s);
    public List<Statistic> findAll() ;
    public List<Statistic> getVictories() ;
    public List<Statistic> getTotalGames();
    public void delete(int id);


}