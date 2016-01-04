
package it.polimi.group03.activity;

import android.view.Gravity;
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
 *
 */

public class GenericOnTouchListener {

    private PlayBeadMeActivity parentActivity;

    public GenericOnTouchListener(){

    }

    public GenericOnTouchListener(PlayBeadMeActivity activity){
        this.parentActivity = activity;
    }

    public void refreshBoard(){

        for (Player player: parentActivity.getEngine().getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;

            for (int i = 0; i < player.getBeads().size(); i++) {

                if (!player.getBeads().get(i).isActive()){
                    // Make image invisible
                    ImageView bead = (ImageView)parentActivity.findViewById(CommonUtil.getBeadId(player.getId(), indexBead));
                    if ( bead.getVisibility() == View.VISIBLE ) {
                        bead.setVisibility(View.INVISIBLE);
                        parentActivity.getAnimationManager().rotate(bead.getContext(), bead);
                    }
                }
                indexBead --;
            }

        }
    }

    public void refreshState(){
        if ( parentActivity.getEngine().isGameEndConditionReached() ){
            CommonUtil.showToastMessage(parentActivity.getApplicationContext(), parentActivity.getVie_toast(), parentActivity.getTxt_toast(),
                    parentActivity.getResources().getString(R.string.winner, parentActivity.getEngine().getWinner().getNickname()),
                    Toast.LENGTH_LONG);
            parentActivity.endGame();
        } else {
            TextView text = (TextView)parentActivity.findViewById(R.id.txtCurrentPlayer);
            text.setText(parentActivity.getApplicationContext().getResources().getString(R.string.current_player,  parentActivity.getEngine().getGame().getNextPlayer().getNickname()));
            CommonUtil.showToastMessage(parentActivity.getApplicationContext(), parentActivity.getVie_toast(), parentActivity.getTxt_toast(), parentActivity.getResources().getString(R.string.next_player) + parentActivity.getEngine().getGame().getNextPlayer().getNickname(),
                    Toast.LENGTH_SHORT);
        }

        for (Player player: parentActivity.getEngine().getGame().getPlayers()) {
            ImageView imgSummary = (ImageView)parentActivity.findViewById(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(CommonUtil.getSummaryImageId(player.activeBeads().size()));
        }
    }

    public PlayBeadMeActivity getParentActivity() {
        return this.parentActivity;
    }
}
