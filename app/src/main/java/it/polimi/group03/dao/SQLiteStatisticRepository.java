package it.polimi.group03.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;

import java.util.List;

import it.polimi.group03.domain.Statistic;

/**
 * Created by megireci on 15-Dec-15.
 */
/* This is the concrete repository class for our application. The database will be created on the disk for the first time. */

public class SQLiteStatisticRepository extends SQLiteOpenHelper implements GameDAO {

    //Static data members
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="Statistic";

    //context is needed to actually create the database.
    public SQLiteStatisticRepository(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String query="CREATE TABLE statistic (winnerName TEXT,rounds INTEGER PRIMARY KEY,defeats INTEGER, duration INTEGER, numberOfGames INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//methods still not finished.

    public Statistic save (Statistic s){
        ContentValues statisticTuple=new ContentValues();
        statisticTuple.put("rounds",Statistic.getRounds());


        return s;

    }

    @Override
    public List<Statistic> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    ;

}
