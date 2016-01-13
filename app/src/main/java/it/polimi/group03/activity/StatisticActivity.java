package it.polimi.group03.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.polimi.group03.R;
import it.polimi.group03.dao.GameDAO;
import it.polimi.group03.dao.GameDAOImpl;
import it.polimi.group03.domain.Statistic;
import it.polimi.group03.util.CommonUtil;

/**
 * This class holds the logic to support the Statistic page. All the data is retrieved
 * from database and formatted according to the layouts of the activity, the look and feel
 * will depend on the on the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @author megireci
 */
public class StatisticActivity extends FlipperActivity {

    private static final String TAG = StatisticActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Accessing to Statistic...");
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_statistic);
        setFlipper((ViewFlipper) findViewById(R.id.statViewFlipper));
        setLayouts(4);
        findViewById(R.id.btn_statistic_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

        setStatistics();
    }

    /**
     * Set the statistic for all the layouts on the flipper.<br /><br />
     * <p>First: Games played (with a countdown animation).</p>
     * <p>Second: Top 3 players including number of victories per player,
     * the icon and the last time the player was connected.</p>
     * <p>Third: Bottom 3 players including number of defeats per player,
     * the icon and the last time the player was connected.</p>
     * <p>Fourth: Average of rounds played per game (with a blink animation).</p>
     */
    private void setStatistics() {
        GameDAO gameDAO = new GameDAOImpl(this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy", Locale.US);

        //Number of Games played
        int tot_games = gameDAO.getTotalGames();
        TextView totalGames = (TextView) findViewById(R.id.txt_stat_total_games);
        TextView totalGamesX = (TextView) findViewById(R.id.txt_stat_total_games_x);
        totalGamesX.setVisibility(tot_games < 50 ? View.VISIBLE : View.INVISIBLE);
        countDown(totalGames, 0, tot_games);

        //Top 3
        List<Statistic> hallOfFame = gameDAO.findHallOfFame();
        if ( !CommonUtil.isEmpty(hallOfFame) ) {
            int icon_id_first = hallOfFame.get(0).getWinnerIcon();
            String name_first = hallOfFame.get(0).getWinnerName();
            int num_victories_first = hallOfFame.get(0).getVictories();
            Date firstLastPlay = gameDAO.getLastDateConnected(name_first);
            ImageView firstPlace = (ImageView) findViewById(R.id.img_stat_first);
            firstPlace.setImageResource(icon_id_first);
            TextView firstPlaceName = (TextView) findViewById(R.id.txt_first_place_name);
            firstPlaceName.setText(getResources().getString(R.string.stat_first_name, name_first));
            TextView firstPlaceGames = (TextView) findViewById(R.id.txt_first_place_games);
            firstPlaceGames.setText(getResources().getString(R.string.stat_first_victories, String.valueOf(num_victories_first)));
            TextView firstPlaceLastPlay = (TextView) findViewById(R.id.txt_first_place_last);
            firstPlaceLastPlay.setText(getResources().getString(R.string.stat_first_date, sdf.format(firstLastPlay)));

            if ( hallOfFame.size() > 1 ) {
                int icon_id_second = hallOfFame.get(1).getWinnerIcon();
                String name_second = hallOfFame.get(1).getWinnerName();
                int num_victories_second = hallOfFame.get(1).getVictories();
                Date secondLastPlay = gameDAO.getLastDateConnected(name_second);
                ImageView secondPlace = (ImageView) findViewById(R.id.img_stat_second);
                secondPlace.setImageResource(icon_id_second);
                TextView secondPlaceName = (TextView) findViewById(R.id.txt_second_place_name);
                secondPlaceName.setText(getResources().getString(R.string.stat_second_name, name_second));
                TextView secondPlaceGames = (TextView) findViewById(R.id.txt_second_place_games);
                secondPlaceGames.setText(getResources().getString(R.string.stat_second_victories, String.valueOf(num_victories_second)));
                TextView secondPlaceLastPlay = (TextView) findViewById(R.id.txt_second_place_last);
                secondPlaceLastPlay.setText(getResources().getString(R.string.stat_second_date, sdf.format(secondLastPlay)));
            }

            if ( hallOfFame.size() > 2 ) {
                int icon_id_third = hallOfFame.get(2).getWinnerIcon();
                String name_third = hallOfFame.get(2).getWinnerName();
                int num_victories_third = hallOfFame.get(2).getVictories();
                Date thirdLastPlay = gameDAO.getLastDateConnected(name_third);
                ImageView thirdPlace = (ImageView) findViewById(R.id.img_stat_third);
                thirdPlace.setImageResource(icon_id_third);
                TextView thirdPlaceName = (TextView) findViewById(R.id.txt_third_place_name);
                thirdPlaceName.setText(getResources().getString(R.string.stat_third_name, name_third));
                TextView thirdPlaceGames = (TextView) findViewById(R.id.txt_third_place_games);
                thirdPlaceGames.setText(getResources().getString(R.string.stat_third_victories, String.valueOf(num_victories_third)));
                TextView thirdPlaceLastPlay = (TextView) findViewById(R.id.txt_third_place_last);
                thirdPlaceLastPlay.setText(getResources().getString(R.string.stat_third_date, sdf.format(thirdLastPlay)));
            }
        }

        //Bottom 3
        List<Statistic> hallOfShame = gameDAO.findHallOfShame();

        if ( !CommonUtil.isEmpty(hallOfShame) ) {
            int icon_id_worst = hallOfShame.get(0).getLoser1Icon();
            String name_worst = hallOfShame.get(0).getLoser1Name();
            int num_victories_worst = hallOfShame.get(0).getDefeats();
            Date worstLastPlay = gameDAO.getLastDateConnected(name_worst);
            ImageView worstPlace = (ImageView) findViewById(R.id.img_stat_worst);
            worstPlace.setImageResource(icon_id_worst);
            TextView worstPlaceName = (TextView) findViewById(R.id.txt_worst_place_name);
            worstPlaceName.setText(getResources().getString(R.string.stat_worst_name, name_worst));
            TextView worstPlaceGames = (TextView) findViewById(R.id.txt_worst_place_games);
            worstPlaceGames.setText(getResources().getString(R.string.stat_worst_victories, String.valueOf(num_victories_worst)));
            TextView worstPlaceLastPlay = (TextView) findViewById(R.id.txt_worst_place_last);
            worstPlaceLastPlay.setText(getResources().getString(R.string.stat_worst_date, sdf.format(worstLastPlay)));

            if ( hallOfShame.size() > 1 ) {
                int icon_id_worse = hallOfShame.get(1).getLoser1Icon();
                String name_worse = hallOfShame.get(1).getLoser1Name();
                int num_victories_worse = hallOfShame.get(1).getDefeats();
                Date worseLastPlay = gameDAO.getLastDateConnected(name_worse);
                ImageView worsePlace = (ImageView) findViewById(R.id.img_stat_worse);
                worsePlace.setImageResource(icon_id_worse);
                TextView worsePlaceName = (TextView) findViewById(R.id.txt_worse_place_name);
                worsePlaceName.setText(getResources().getString(R.string.stat_worse_name, name_worse));
                TextView worsePlaceGames = (TextView) findViewById(R.id.txt_worse_place_games);
                worsePlaceGames.setText(getResources().getString(R.string.stat_worse_victories, String.valueOf(num_victories_worse)));
                TextView worsePlaceLastPlay = (TextView) findViewById(R.id.txt_worse_place_last);
                worsePlaceLastPlay.setText(getResources().getString(R.string.stat_worse_date, sdf.format(worseLastPlay)));
            }

            if ( hallOfShame.size() > 2 ) {
                int icon_id_bad = hallOfShame.get(2).getLoser1Icon();
                String name_bad = hallOfShame.get(2).getLoser1Name();
                int num_victories_bad = hallOfShame.get(2).getDefeats();
                Date badLastPlay = gameDAO.getLastDateConnected(name_bad);
                ImageView badPlace = (ImageView) findViewById(R.id.img_stat_bad);
                badPlace.setImageResource(icon_id_bad);
                TextView badPlaceName = (TextView) findViewById(R.id.txt_bad_place_name);
                badPlaceName.setText(getResources().getString(R.string.stat_bad_name, name_bad));
                TextView badPlaceGames = (TextView) findViewById(R.id.txt_bad_place_games);
                badPlaceGames.setText(getResources().getString(R.string.stat_bad_victories, String.valueOf(num_victories_bad)));
                TextView badPlaceLastPlay = (TextView) findViewById(R.id.txt_bad_place_last);
                badPlaceLastPlay.setText(getResources().getString(R.string.stat_bad_date, sdf.format(badLastPlay)));
            }
        }

        //Number of rounds played per game
        int avg_rounds = gameDAO.getAverageRounds();
        TextView avgGames = (TextView) findViewById(R.id.txt_stat_avg_games);
        avgGames.setText(getResources().getString(R.string.stat_avg_game_main, String.valueOf(avg_rounds)));
        getAnimationManager().blink(this, avgGames);
    }

    /**
     * Set the animation for the first view on the flipper.
     */
    private void countDown(final TextView tv, final int i, final int count) {
        if ( i > count ) {
            tv.setText(String.valueOf(count));
            return;
        }

        tv.setText(String.valueOf(i));
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(100);
        animation.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation anim) {
            }

            public void onAnimationEnd(Animation anim) {
                countDown(tv, i + 1, count);
            }

            public void onAnimationRepeat(Animation anim) {
            }

        });

        tv.startAnimation(animation);
    }

}