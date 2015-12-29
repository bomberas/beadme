
package it.polimi.group03.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import it.polimi.group03.R;
import it.polimi.group03.domain.Player;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 * Created by cecibloom on 28/12/2015.
 */
public class GenericOnTouchListener {

    private GameEngine engine;
    private GenericActivity parentActivity;

    public GenericOnTouchListener(){

    }

    public GenericOnTouchListener(GenericActivity activity, GameEngine engine){
        this.engine = engine;
        this.parentActivity = activity;
    }

    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(GameEngine engine){
        this.engine = engine;
    }

    public void refreshBoard(){

        for (Player player: engine.getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;

            for (int i = 0; i < player.getBeads().size(); i++) {

                if (!player.getBeads().get(i).isActive()){
                    // Make image invisible
                    ImageView bead = (ImageView)parentActivity.findViewById(CommonUtil.getBeadId(player.getId(), indexBead));
                    bead.setVisibility(View.INVISIBLE);
                }
                indexBead --;
            }

        }
    }

    public void refreshState(){
        if ( engine.isGameEndConditionReached() ){
            CommonUtil.showToastMessage(parentActivity.getApplicationContext(),
                    parentActivity.getResources().getString(R.string.winner_1) +
                    engine.getWinner().getNickname() +
                    parentActivity.getResources().getString(R.string.winner_2) +
                    parentActivity.getResources().getString(R.string.app_name),
                    Toast.LENGTH_LONG);
        } else {
            TextView text = (TextView)parentActivity.findViewById(R.id.txtCurrentPlayer);
            text.setText(parentActivity.getApplicationContext().getResources().getString(R.string.current_player) + engine.getGame().getNextPlayer().getNickname());
            CommonUtil.showToastMessage(parentActivity.getApplicationContext(), parentActivity.getResources().getString(R.string.next_player) + engine.getGame().getNextPlayer().getNickname(), Toast.LENGTH_SHORT);
        }

        for (Player player: engine.getGame().getPlayers()) {
            ImageView imgSummary = (ImageView)parentActivity.findViewById(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(CommonUtil.getSummaryImageId(player.activeBeads().size()));
        }
    }

    public GenericActivity getParentActivity() {
        return this.parentActivity;
    }
}
