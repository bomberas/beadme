package it.polimi.group03.activity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import it.polimi.group03.R;
import it.polimi.group03.dao.GameDAO;
import it.polimi.group03.dao.SQLiteStatisticRepository;
import it.polimi.group03.domain.Statistic;



/**
 * Created by megireci on 29-Dec-15.
 */
public class StatisticActivity extends GenericActivity {

    private static final String TAG = "StatisticActivity";
    private GameDAO gameDAO;
    //  Context context=this;
    SQLiteDatabase db;
    //SQLiteStatisticRepository sqLiteStatisticRepository;


    Statistic s=new Statistic();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_statistic);
        Log.i(TAG, "Accessing to Statistic...");
        this.gameDAO = new SQLiteStatisticRepository(this);
        //  sqLiteStatisticRepository=new SQLiteStatisticRepository(this);
        // db=new SQLiteStatisticRepository(this).getWritableDatabase();


        findViewById(R.id.btn_statistic_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });


        gameDAO.save(s);
        //  List<Statistic> l=gameDAO.findAll();
        //  System.out.println("The size of the list it :"+ l.size());
    }
    //This method sets the data from the database to the edittext.
    private void setData (View view)
    {

        db=new SQLiteStatisticRepository(this).getReadableDatabase();

        EditText GAMES_PLAYED,DURATION,NUMBER_OF_ROUNDS;
        String games_played,duration,numberOfRounds;
        Intent intent = new Intent();

        GAMES_PLAYED=(EditText) findViewById(R.id.et_games_played);
        DURATION=(EditText) findViewById(R.id.et_duration);
        NUMBER_OF_ROUNDS=(EditText) findViewById(R.id.et_total_rounds);
        GAMES_PLAYED.setText("jkjdfskjfk");


    }



}