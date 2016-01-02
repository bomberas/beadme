package it.polimi.group03.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import it.polimi.group03.domain.Statistic;

import static it.polimi.group03.util.CommonUtil.safeLongToInt;





/* Created by megireci on 15-Dec-15.


*/
 /* This is the concrete repository class for our application. The database will be created on the disk for the first time. */

public class SQLiteStatisticRepository extends SQLiteOpenHelper implements GameDAO  {

    public static final String DATABASE_NAME="BeadMe";
    public static final int DATABASE_VERSION=3;
    public static final String PLAYER_NICKNAME = "Nickname";
    public static final String TABLE_NAME = "stats";
    public static final String GAME_ID = "GameId";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String TAG="Statistic Repository";
    public String CNT;


    public SQLiteStatisticRepository(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d(TAG, "Database created/opened...");

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_QUERY="CREATE TABLE "+
                TABLE_NAME + " ( "+ GAME_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ PLAYER_NICKNAME +" TEXT, "+ START_TIME+" DATE,"
                + END_TIME+" DATE)";

        db.execSQL(CREATE_TABLE_QUERY);
        Log.d(TAG, "Table stats created...");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteStatisticRepository.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will delete the old data.");
        db.execSQL("DROP TABLE IF EXISTS stats");
        //Method is called during creation of new database
        onCreate(db);
    }



    @Override
    public Statistic save(Statistic s) {
        Log.d(TAG, "One row inserted...");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PLAYER_NICKNAME, s.getWinnerName());
        values.put(START_TIME, String.valueOf(s.getStartTime()));
        values.put(END_TIME, String.valueOf(s.getEndTime()));


        long game_id = db.insert(TABLE_NAME, null, values);
        s.setGameID(safeLongToInt(game_id));

        return s;


    }

    @Override
    public List<Statistic> findAll()  {
        try {
            List<Statistic> stats = new ArrayList<Statistic>();
            String selectQuery = " SELECT  * FROM  " + TABLE_NAME;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Statistic s = new Statistic();
                    s.setGameID(Integer.parseInt(cursor.getString(0)));
                    //  s.setWinnerName(cursor.getString(1));
                    //  s.setStartTime(CommonUtil.convertStringToDate(cursor.getString(2)));
                    // s.setEndTime(CommonUtil.convertStringToDate(cursor.getString(3)));

                    stats.add(s);
                } while (cursor.moveToNext());
            }

            cursor.close();
            return stats;
        }
        catch(Exception e)
        {
            Log.e(TAG,"Impossible to retrieve data...",e);
            return new ArrayList<Statistic>();
        }
    }

    @Override
    public void delete(int id) {

    }


    @Override
    public List<Statistic> getVictories() {
        try {
            List<Statistic> victories = new ArrayList<Statistic>();
            String victoriesQuery = "SELECT " + PLAYER_NICKNAME + ", COUNT( " + PLAYER_NICKNAME + ") AS CNT FROM " + TABLE_NAME + " GROUP BY "
                    + PLAYER_NICKNAME + " ORDER BY CNT DESC";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(victoriesQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Statistic s = new Statistic();
                    s.setWinnerName(cursor.getString(0));
                    s.setVictories(Integer.parseInt(cursor.getString(1)));
                    victories.add(s);
                } while (cursor.moveToNext());

            }
            cursor.close();
            return victories;
        } catch (Exception e)
        {
            Log.e(TAG,"Impossible to retireve data victoi=ries");
            return new ArrayList<Statistic>();
        }
    }

    @Override
    public List<Statistic> getTotalGames ()
    {
        try {
            String gameCountQuery = "SELECT COUNT(" + GAME_ID + ") AS " + CNT + " FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            List<Statistic> listGameCount = new ArrayList<Statistic>();
            Cursor cursor = db.rawQuery(gameCountQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Statistic s = new Statistic();
                    s.setGameCount(Integer.parseInt(cursor.getString(0)));
                    listGameCount.add(s);
                } while (cursor.moveToNext());

            }
            cursor.close();
            return listGameCount;
        } catch (Exception e)
        {
            Log.e(TAG,"Impossible to retireve data Game Count...");
            return new ArrayList<Statistic>();
        }
    }
}
