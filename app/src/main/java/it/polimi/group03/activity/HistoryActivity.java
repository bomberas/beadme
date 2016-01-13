package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import it.polimi.group03.R;
import it.polimi.group03.dao.GameDAO;
import it.polimi.group03.dao.GameDAOImpl;
import it.polimi.group03.domain.Statistic;

/**
 * This class holds the logic to support the History page of the application, the look and feel
 * will depend on the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 10/01/2016.
 */
public class HistoryActivity extends GenericActivity {

    private static final String TAG = HistoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Accessing to History ...");
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_history);

        findViewById(R.id.btn_history_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
        fillTable();
    }

    /**
     * Filling up the table layout with historic games, only 10
     * will be shown, from the most recent. Each row adds an onClick listener
     * that will show a popup with additional info like: number of rounds and
     * others players names.
     */
    private void fillTable() {
        GameDAO gameDAO = new GameDAOImpl(this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);
        List<Statistic> games = gameDAO.findAll(10);

        TableLayout table = (TableLayout) findViewById(R.id.tbl_history);

        for ( int i = 0; i < games.size(); i++) {
            Statistic game = games.get(i);
            TableRow tr = (TableRow) table.getChildAt(i+1);
            tr.setOnClickListener(getOnClickListener(game));

            TextView tvId = (TextView) tr.getChildAt(0);
            tvId.setText(String.valueOf(i + 1));
            TextView tvDate = (TextView) tr.getChildAt(1);
            tvDate.setText(sdf.format(game.getStartTime()));
            TextView tvWinner = (TextView) tr.getChildAt(2);
            tvWinner.setText(game.getWinnerName());
            TextView tvDuration = (TextView) tr.getChildAt(3);
            tvDuration.setText(game.getDuration());

            tr.startAnimation(i%2==0 ? AnimationUtils.loadAnimation(this, R.anim.slidein_left) : AnimationUtils.loadAnimation(this, R.anim.slidein_right));
        }

    }

    /**
     * Creates a new listener for all the rows present in the table, and opens
     * a dialog with additional info related to the game.
     */
    private View.OnClickListener getOnClickListener(final Statistic game) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.clearAnimation();
                Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
                intent.putExtra("game", game);
                intent.putExtra("class", HistoryActivity.class.getName());
                startActivity(intent);
            }
        };
    }

}