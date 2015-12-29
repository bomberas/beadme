package it.polimi.group03.activity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import it.polimi.group03.R;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 *
 * This class implements the OnTouchListener event for every bead that a user has.
 *
 * <p>Contains the validation involve in placing a bead just one time, which means that after placing a bead in a correct position
 * it cannot be moved again.
 *
 * <p>It also contains the validations performed by the {@link GameEngine#addBeadToBoard(Player, Bead)}.
 *
 * @see GameEngine
 * @see Bead
 * @see Player
 * @see StatusMessage
 *
 * @author cecibloom
 * @version 1.0
 * @since 15/12/2015.
 *
 */
public class BeadOnTouchListener implements View.OnTouchListener {

    private GenericActivity activity;
    private GameEngine engine;
    private Player player;
    private int prevMovX;
    private int prevMovY;
    private int startLeftMargin;
    private int startTopMargin;
    private int cellWidth;
    private int leftMargin;
    private int topMargin;


    public BeadOnTouchListener(GenericActivity activity, GameEngine engine, Player player, int cellWidth, int leftMargin, int topMargin, int leftImg, int topImg) {
        this.activity = activity;
        this.engine = engine;
        this.player = player;
        this.cellWidth = cellWidth;
        this.leftMargin = leftMargin;
        this.topMargin = topMargin;
        this.startLeftMargin = leftImg;
        this.startTopMargin = topImg;
    }

    /**
     *
     * Called when the touch event is dispatched to the image view. This allows the listener to
     * get a chance to respond before the target view.
     *
     * @param v The image view representing the bead the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about the event.
     * @return {@code true} True if the listener has consumed the event, {@code false] otherwise.
     */
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        final RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) v.getLayoutParams();
        Bead bead = (Bead)v.getTag();

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {
                // Move only if the bead hasn't yet been placed
                if ( !bead.isPlaced() && player.getId() == engine.getGame().getNextPlayer().getId() ) {
                    par.topMargin += (int) event.getRawY() - prevMovY;
                    prevMovY = (int) event.getRawY();
                    par.leftMargin += (int) event.getRawX() - prevMovX;
                    prevMovX = (int) event.getRawX();
                    v.setLayoutParams(par);
                }
                return true;
            }

            case MotionEvent.ACTION_UP: {
                // Checking if the bead was already placed
                if ( !bead.isPlaced() && player.getId() == engine.getGame().getNextPlayer().getId() ) {

                    Log.i("TEST", "Trying to place bead");
                    Log.i("TEST", "Number of beads placed for player " + player.getId() + " = " + player.getBeads().size());

                    // Checking if the new location is inside the board
                    if ( (int)event.getRawX() > leftMargin + 3*cellWidth && (int)event.getRawX() < (leftMargin + cellWidth * (Constant.NUMBER_OF_CELLS - 2)) &&
                         (int)event.getRawY() > topMargin  + 3*cellWidth && (int)event.getRawY() < (topMargin  + cellWidth * (Constant.NUMBER_OF_CELLS - 2))) {

                        bead.setPosition(convertXYToRowColumn((int) event.getRawY(), topMargin), convertXYToRowColumn((int) event.getRawX() + 30, leftMargin));

                        if ( bead.getPosition().getX() > -1 && bead.getPosition().getY() > -1 ) {

                            // Calling validator
                            StatusMessage status = engine.addBeadToBoard(player, bead);

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                par.topMargin = convertRowColumnToXY(bead.getPosition().getX(), topMargin);
                                par.leftMargin = convertRowColumnToXY(bead.getPosition().getY(), leftMargin);
                                engine.getGame().setNextPlayer(engine.getNextPlayer(player));
                                enableImages(engine.getGame().getNextPlayer().getId());
                                Log.i("TEST", "Bead added to player " + player.getId());
                                Log.i("TEST", "New position of bead " + bead.getPosition().toString());
                            } else {
                                Log.i("TEST", "Invalid position. " + v.getResources().getString(status.getRCode()));
                                CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                                this.activity.getVibrationManager().vibrate(v.getContext());
                                par.topMargin = startTopMargin;
                                par.leftMargin = startLeftMargin;
                                bead.setPosition(-1, -1);
                                bead.setPlaced(false);
                            }
                        }else{
                            Log.i("TEST", "Invalid " + bead.getPosition().toString());
                            CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(R.string.e0x8), Toast.LENGTH_SHORT);
                            this.activity.getVibrationManager().vibrate(v.getContext());
                            par.topMargin  = startTopMargin;
                            par.leftMargin = startLeftMargin;
                        }

                    } else {
                        Log.i("TEST", "New position outside board, not possible to place bead");
                        CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(R.string.e0x8), Toast.LENGTH_SHORT);
                        this.activity.getVibrationManager().vibrate(v.getContext());
                        par.topMargin  = startTopMargin;
                        par.leftMargin = startLeftMargin;
                    }

                    v.setLayoutParams(par);
                    v.setTag(bead);
                }

                /*else {

                    Log.i("TEST", "Bead already placed, not possible to place bead");
                    CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(R.string.e0x7), Toast.LENGTH_SHORT);
                    par.topMargin = startTopMargin;
                    par.leftMargin = startLeftMargin;
                }
*/

                return true;
            }

            case MotionEvent.ACTION_DOWN: {
                if ( !bead.isPlaced() && player.getId() == engine.getGame().getNextPlayer().getId() ) {
                    Log.i("TEST", "Your moving bead number " + v.getId());
                    prevMovX = (int) event.getRawX();
                    prevMovY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    startTopMargin = ((RelativeLayout.LayoutParams) v.getLayoutParams()).topMargin;
                    startLeftMargin = ((RelativeLayout.LayoutParams) v.getLayoutParams()).leftMargin;
                    v.setLayoutParams(par);
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * This method converts the given coordinates into a valid row or column on the board.
     * Both rows and columns go from 0 to 6.
     *
     * @param x coordinate x or y that represents the position of the finger on the screen
     * @param offset the offset either on x or y coordinate.
     * @return the corresponding {@code row} or {@code column} on the board.
     */
    private int convertXYToRowColumn(int x, int offset){
        return (int)Math.floor((x - offset)/cellWidth) - 4;
    }

    /**
     *
     * This method converts the given row or column into a valid position on the screen.
     *
     * @param i the row or column in which the bead has been placed.
     * @param offset the offset either on x or y coordinate.
     * @return the corresponding {@code x coordinate} or {@code y coordinate} in which the bead will be placed.
     */
    private int convertRowColumnToXY(int i, int offset){
        return (i + 3)*cellWidth + offset;
    }

    private void enableImages(int id){

        Log.i("TEST", "The next player is: " + id);
        Log.i("TEST", "The player of this bead: " + player.getId());
        for (Player player: this.engine.getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;
            for (int i = 0; i < Constant.GAME_MAX_NUMBER_BEADS; i++) {
                ImageView imgBead = (ImageView)activity.findViewById(CommonUtil.getBeadId(player.getId(), indexBead));
                if ( player.getId() == id ) {
                    imgBead.setVisibility(ImageView.VISIBLE);
                } else {
                    if ( !((Bead)imgBead.getTag()).isPlaced() ) {
                        this.activity.getAnimationManager().rotate(imgBead.getContext(), imgBead);
                        imgBead.setVisibility(ImageView.INVISIBLE);
                    }
                }
                indexBead --;
            }
        }

        TextView text = (TextView)activity.findViewById(R.id.txtCurrentPlayer);
        text.setText(activity.getApplicationContext().getResources().getString(R.string.current_player) + engine.getGame().getNextPlayer().getNickname());
        CommonUtil.showToastMessage(activity.getApplicationContext(), activity.getResources().getString(R.string.next_player) + engine.getGame().getNextPlayer().getNickname(), Toast.LENGTH_SHORT);
    }
}
