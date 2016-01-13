package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import it.polimi.group03.R;
import it.polimi.group03.domain.Statistic;
import it.polimi.group03.util.CommonUtil;

/**
 * This class holds the logic to support the action call when a player
 * is trying to leave the play activity while playing.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 28/12/2015.
 */
public class DialogActivity extends GenericActivity {

    private static final String TAG = DialogActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
        });
        setContent();
    }

    /**
     * The content of the dialog will be set according to, which activity has fired up the intent.
     */
    private void setContent() {
        Intent intent = getIntent();

        TextView players = (TextView) findViewById(R.id.txt_dialog_text);
        TextView rounds = (TextView) findViewById(R.id.txt_dialog_text2);
        TextView losers = (TextView) findViewById(R.id.txt_dialog_text3);
        TextView loser1 = (TextView) findViewById(R.id.txt_dialog_text6);
        TextView loser2 = (TextView) findViewById(R.id.txt_dialog_text5);
        TextView loser3 = (TextView) findViewById(R.id.txt_dialog_text4);

        if ( !CommonUtil.isEmpty(intent.getStringExtra("class")) ) {
            Statistic game = (Statistic) intent.getSerializableExtra("game");

            findViewById(R.id.btn_yes).setVisibility(View.GONE);
            findViewById(R.id.btn_no).setVisibility(View.GONE);

            players.setText(getResources().getString(R.string.hist_nplayers_text, String.valueOf(game.getNumberOfPlayers())));
            rounds.setText(getResources().getString(R.string.hist_round_text, String.valueOf(game.getRounds())));
            losers.setText(getResources().getString(R.string.hist_losers_text, String.valueOf(game.getRounds())));

            if ( game.getNumberOfPlayers() == 2 ) {
                loser1.setVisibility(View.GONE);
                loser2.setVisibility(View.GONE);
                loser3.setText(getResources().getString(R.string.hist_loser1_text, game.getLoser1Name()));
            } else if ( game.getNumberOfPlayers() == 3 ) {
                loser2.setText(getResources().getString(R.string.hist_loser2_text, game.getLoser2Name()));
                loser3.setText(getResources().getString(R.string.hist_loser1_text, game.getLoser1Name()));
                loser1.setVisibility(View.GONE);
            } else {
                loser1.setText(getResources().getString(R.string.hist_loser3_text, game.getLoser3Name()));
                loser2.setText(getResources().getString(R.string.hist_loser2_text, game.getLoser2Name()));
                loser3.setText(getResources().getString(R.string.hist_loser1_text, game.getLoser1Name()));
            }

        } else {
            rounds.setVisibility(View.GONE);
            losers.setVisibility(View.GONE);
            loser1.setVisibility(View.GONE);
            loser2.setVisibility(View.GONE);
            loser3.setVisibility(View.GONE);
        }
    }

    /**
     * This methods hides the action bar set it by default for the OS; in order to obtain
     * a full screen view.
     */
    @Override
    public void hideBars() {

    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "Skipping onBackPressed...");
    }

}