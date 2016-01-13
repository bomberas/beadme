package it.polimi.group03.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.polimi.group03.domain.Statistic;
import it.polimi.group03.util.CommonUtil;

/**
 * {@inheritDoc}
 */
public class GameDAOImpl extends SQLiteOpenHelper implements GameDAO  {

    private static final String TAG = GameDAOImpl.class.getSimpleName();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss", Locale.US);

    private static final String DATABASE = "BeadMe";
    private static final int DATABASE_VERSION = 11;
    private static final String TABLE = "game";
    private static final String ID = "id";
    private static final String DATE_CREATED = "dateCreated";
    private static final String DATE_ENDED = "datelastUpdated";
    private static final String PLAYERS = "numberOfPlayers";
    private static final String WINNER_NAME = "winnerName";
    private static final String WINNER_ICON = "winnerIcon";
    private static final String LOSER1_NAME = "loser1Name";
    private static final String LOSER1_ICON = "loser1Icon";
    private static final String LOSER2_NAME = "loser2Name";
    private static final String LOSER2_ICON = "loser2Icon";
    private static final String LOSER3_NAME = "loser3Name";
    private static final String LOSER3_ICON = "loser3Icon";
    private static final String ROUNDS = "rounds";

    public GameDAOImpl(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
        Log.d(TAG, "Database created/opened...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE game (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE_CREATED + " DATE, " +
                DATE_ENDED + " DATE, " + PLAYERS + " INTEGER, " + WINNER_NAME + " TEXT, " +
                WINNER_ICON + " INTEGER, " + LOSER1_NAME + " TEXT, " + LOSER1_ICON + " INTEGER, " + LOSER2_NAME + " TEXT, " +
                LOSER2_ICON + " INTEGER, " + LOSER3_NAME + " TEXT, " + LOSER3_ICON + " INTEGER, " + ROUNDS + " INTEGER)";

        db.execSQL(query);
        Log.d(TAG, "Table game created...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GameDAOImpl.class.getName(), "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will delete the old data.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Statistic s) {

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DATE_CREATED, sdf.format(s.getStartTime()));
            values.put(DATE_ENDED, sdf.format(s.getEndTime()));
            values.put(PLAYERS, s.getNumberOfPlayers());
            values.put(WINNER_NAME, s.getWinnerName());
            values.put(WINNER_ICON, s.getWinnerIcon());
            values.put(LOSER1_NAME, s.getLoser1Name());
            values.put(LOSER1_ICON, s.getLoser1Icon());
            values.put(LOSER2_NAME, s.getLoser2Name());
            values.put(LOSER2_ICON, s.getLoser2Icon());
            values.put(LOSER3_NAME, s.getLoser3Name());
            values.put(LOSER3_ICON, s.getLoser3Icon());
            values.put(ROUNDS, s.getRounds());

            db.insert(TABLE, null, values);
            db.close();
        } catch (Exception e) {
            Log.i(TAG, "Error during insertion: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Statistic> findAll(int limit)  {
        List<Statistic> stats = new ArrayList<>();

        try {
            String selectQuery = "SELECT  * FROM  " + TABLE + " ORDER BY " + DATE_CREATED + " DESC ";
            selectQuery = limit == 0 ? selectQuery : selectQuery + " LIMIT " + limit;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if ( cursor.moveToFirst() ) {
                do {
                    Statistic s = new Statistic();
                    s.setId(cursor.getInt(0));
                    s.setStartTime(sdf.parse(cursor.getString(1)));
                    s.setEndTime(sdf.parse(cursor.getString(2)));
                    s.setNumberOfPlayers(cursor.getInt(3));
                    s.setWinnerName(cursor.getString(4));
                    s.setWinnerIcon(cursor.getInt(5));
                    s.setLoser1Name(cursor.getString(6));
                    s.setLoser1Icon(cursor.getInt(7));
                    s.setLoser2Name(cursor.getString(8));
                    s.setLoser2Icon(cursor.getInt(9));
                    s.setLoser3Name(cursor.getString(10));
                    s.setLoser3Icon(cursor.getInt(11));
                    s.setRounds(cursor.getInt(12));
                    stats.add(s);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e(TAG,"Impossible to retrieve data...", e);
        }

        return stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getLastDateConnected(String player) {
        Date online = null;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String gameCountQuery = "SELECT " + DATE_ENDED + " FROM " + TABLE + " WHERE " + WINNER_NAME + " = '" + player + "' OR " +
                    LOSER1_NAME + " = '" + player + "' OR " + LOSER2_NAME + " = '" + player + "' OR " + LOSER3_NAME + " = '" + player +
                    "' ORDER BY 1 DESC LIMIT 1" ;
            Cursor cursor = db.rawQuery(gameCountQuery, null);

            if ( cursor.moveToFirst() ) {
                do {
                    online = sdf.parse(cursor.getString(0));
                } while (cursor.moveToNext());

            }

            cursor.close();

        } catch (Exception e) {
            Log.e(TAG,"Impossible to retireve data...", e);
        }

        return online;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAverageRounds() {
        int rounds = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String gameCountQuery = "SELECT AVG(" + ROUNDS + ") FROM " + TABLE;
            Cursor cursor = db.rawQuery(gameCountQuery, null);

            if ( cursor.moveToFirst() ) {
                do {
                    rounds = cursor.getInt(0);
                } while (cursor.moveToNext());

            }

            cursor.close();

        } catch (Exception e) {
            Log.e(TAG,"Impossible to retireve data...", e);
        }

        return rounds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalGames () {
        int count = 0;

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String gameCountQuery = "SELECT COUNT(" + ID + ") FROM " + TABLE;
            Cursor cursor = db.rawQuery(gameCountQuery, null);

            if ( cursor.moveToFirst() ) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());

            }

            cursor.close();

        } catch (Exception e) {
            Log.e(TAG,"Impossible to retireve data...", e);
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Statistic> findHallOfFame() {
        List<Statistic> stats = new ArrayList<>();

        try {
            String selectQuery = "SELECT " + WINNER_NAME + ", " + WINNER_ICON + ", COUNT( " + WINNER_NAME + " ) FROM  " + TABLE +
                    " GROUP BY 1,2 ORDER BY  3 DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if ( cursor.moveToFirst() ) {
                do {
                    Statistic s = new Statistic();
                    s.setWinnerName(cursor.getString(0));
                    s.setWinnerIcon(cursor.getInt(1));
                    s.setVictories(cursor.getInt(2));
                    stats.add(s);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e(TAG,"Impossible to retrieve data...", e);
        }

        return stats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Statistic> findHallOfShame() {
        List<Statistic> stats = new ArrayList<>();

        try {
            String selectQuery = "SELECT G.loserName, G.loserIcon, SUM(G.defeats) FROM ( " +
                    "SELECT G1." + LOSER1_NAME + " AS loserName, G1." + LOSER1_ICON + " AS loserIcon, COUNT(G1." + LOSER1_NAME + " ) " +
                    "AS defeats FROM " + TABLE + " G1 WHERE G1." + LOSER1_NAME + " IS NOT NULL GROUP BY 1, 2 UNION " +
                    "SELECT G2." + LOSER2_NAME + " AS loserName, G2." + LOSER2_ICON + " AS loserIcon, COUNT(G2." + LOSER2_NAME + ") " +
                    "AS defeats FROM " + TABLE + " G2 WHERE G2." + LOSER2_NAME + " IS NOT NULL GROUP BY 1, 2 UNION " +
                    "SELECT G3." + LOSER3_NAME + " AS loserName, G3." + LOSER3_ICON + " AS loserIcon, COUNT(G3." + LOSER3_NAME + ") " +
                    "AS defeats FROM " + TABLE + " G3 WHERE G3." + LOSER3_NAME + " IS NOT NULL GROUP BY 1, 2 ) " +
                    "G GROUP BY 1, 2 ORDER BY 3, 2 DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if ( cursor.moveToFirst() ) {
                do {
                    Statistic s = new Statistic();
                    s.setLoser1Name(cursor.getString(0));
                    s.setLoser1Icon(cursor.getInt(1));
                    s.setDefeats(cursor.getInt(2));
                    if ( !CommonUtil.isEmpty(s.getLoser1Name()) ) stats.add(s);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (Exception e) {
            Log.e(TAG,"Impossible to retrieve data...", e);
        }

        return stats;
    }

}