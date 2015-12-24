package it.polimi.group03.dao;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

import java.util.List;

import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.Statistic;

/**
 * Created by megireci on 15-Dec-15.
 */
/* This is the concrete repository class for our application. The database will be created on the disk for the first time. */

public class SQLiteStatisticRepository extends SQLiteOpenHelper  {

    public static final String DATABASE_NAME="BEAD_ME";
    public static final int DATABASE_VERSION=1;
    public static final String CREATE_TABLE_PLAYER_QUERY="CREATE TABLE"+
            GameContract.playerInfo.TABLENAME + "(" +GameContract.playerInfo.PLAYER_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"+GameContract.playerInfo.PLAYER_NICKNAME
            +"TEXT,"+GameContract.playerInfo.PLAYER_BEAD_COLOR+"TEXT,"+GameContract.playerInfo.PLAYER_VICTORY+"INTEGER,"+GameContract.playerInfo.PLAYER_DEFEAT
            +"INTEGER);";

    public SQLiteStatisticRepository(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created/opened...");

    }

    // Method for adding player information in the table
    public void addPlayerInfo(String id,String nickname,String beadColor,String victory, String defeats, SQLiteDatabase db)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(GameContract.playerInfo.PLAYER_ID, id);
        contentValues.put(GameContract.playerInfo.PLAYER_NICKNAME, nickname);
        contentValues.put(GameContract.playerInfo.PLAYER_BEAD_COLOR, beadColor);
        contentValues.put(GameContract.playerInfo.PLAYER_VICTORY, victory);
        contentValues.put(GameContract.playerInfo.PLAYER_DEFEAT, defeats);
        // null- the system will not put content in the column if the content is empty
        db.insert(GameContract.playerInfo.TABLENAME,null,contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLAYER_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
