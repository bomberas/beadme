package it.polimi.group03.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import it.polimi.group03.R;
import it.polimi.group03.dao.GameContract;
import it.polimi.group03.dao.GameContract.playerInfo;
import it.polimi.group03.dao.SQLiteStatisticRepository;
import it.polimi.group03.domain.Player;

/**
 * Created by megireci on 24-Dec-15.
 */
public class AddPlayerActivity extends AppCompatActivity{
    Context context=this;
    SQLiteStatisticRepository sqLiteStatisticRepository;
    SQLiteDatabase sqLiteDatabase;

    EditText nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_dialog);
        nickname=(EditText) findViewById(R.id.nickname);

    }


    public void addPlayer(View view)
    {
        String name =nickname.getText().toString();
        sqLiteStatisticRepository=new SQLiteStatisticRepository(context);
        sqLiteDatabase=sqLiteStatisticRepository.getWritableDatabase();
        // color is now for now because a spinner should be added to choose the color and putitin the database for each player
        sqLiteStatisticRepository.addPlayerInfo(playerInfo.PLAYER_ID,name,null, playerInfo.PLAYER_VICTORY, playerInfo.PLAYER_DEFEAT, sqLiteDatabase);
        Toast.makeText(getBaseContext(),"Data saved",Toast.LENGTH_LONG).show();
        sqLiteStatisticRepository.close();



    }
}
