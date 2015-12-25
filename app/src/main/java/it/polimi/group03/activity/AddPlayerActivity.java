package it.polimi.group03.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    //For the bead_color spinner
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    //For the database table : playerTable
    Context context=this;
    SQLiteStatisticRepository sqLiteStatisticRepository;
    SQLiteDatabase sqLiteDatabase;

    EditText nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player_dialog);
        nickname=(EditText) findViewById(R.id.nickname);
        spinner= (Spinner) findViewById(R.id.spinner_bead_color);

        adapter=ArrayAdapter.createFromResource(this,R.array.bead_colors,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //To let know what bead has been choosen
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position) + "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }



    public void addPlayer(View view)
    {
        String name =nickname.getText().toString();
        sqLiteStatisticRepository=new SQLiteStatisticRepository(context);
        sqLiteDatabase=sqLiteStatisticRepository.getWritableDatabase();
        // color is now for now because a spinner should be added to choose the color and put in the database for each player
        sqLiteStatisticRepository.addPlayerInfo(null,name,null, null, null, sqLiteDatabase);
        Toast.makeText(getBaseContext(),"Data saved",Toast.LENGTH_LONG).show();
        sqLiteStatisticRepository.close();



    }
}
