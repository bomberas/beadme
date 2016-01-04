package it.polimi.group03.activity;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import it.polimi.group03.R;
import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Player;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;

import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.util.Constant;

/**
 *
 * This class implements the OnTouchListener event for every horizontal bar on the board.
 *
 * <p>Uses the validations regarding to every kind of move performed by {@link GameEngine#makeMove(int, BarOrientation, BarPosition, Player)}.
 *
 *
 * @see GameEngine
 * @see Bar
 * @see StatusMessage
 *
 * @author cecibloom
 * @version 1.0
 * @since 15/12/2015.
 *
 */
public class HorizontalBarOnTouchListener extends GenericOnTouchListener implements View.OnTouchListener{

    private int prevFixedX;
    private int prevMovX;
    private int topMargin;

    public HorizontalBarOnTouchListener(PlayBeadMeActivity activity) {
        super(activity);
    }

    /**
     *
     * Called when the touch event is dispatched to the image view. This allows the listener to
     * get a chance to respond before the target view.
     *
     * @param v The image view representing the horizontal bar the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about the event.
     * @return {@code true} True if the listener has consumed the event, {@code false] otherwise.
     */
    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        final LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) v.getLayoutParams();

        Bar bar = (Bar) v.getTag();

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {

                if ( getParentActivity().getEngine().isGameStartConditionReached() && !getParentActivity().getEngine().isGameEndConditionReached()) {
                    par.leftMargin += (int) event.getRawX() - prevMovX;
                    prevMovX = (int) event.getRawX();
                    v.setLayoutParams(par);
                }
                return true;
            }

            case MotionEvent.ACTION_UP: {

                if ( getParentActivity().getEngine().isGameStartConditionReached() && !getParentActivity().getEngine().isGameEndConditionReached()) {

                    par.topMargin = topMargin;

                    if (bar.getPosition().equals(BarPosition.OUTER)) {
                        // OUTER position, means I can move the bar only to the right

                        Log.i("TEST", "Starting in OUTER, I can move only to the right, so CENTRAL, INNER not allowed");

                        if ((int) event.getRawX() >= getParentActivity().getCellWidth() && (int) event.getRawX() > prevFixedX) {
                            //This means I'm moving to the right, which is the only
                            //correct movement starting from the OUTER position
                            //In this case I move just one cell, so CENTRAL
                            Log.i("TEST", "Moving from OUTER to CENTRAL");

                            // Calling validator
                            StatusMessage status = getParentActivity().getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.CENTRAL, getParentActivity().getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                getParentActivity().getMusicManager().playMoveSoundEffect(getParentActivity().getApplicationContext());
                                par.leftMargin = getParentActivity().getCellWidth();
                                bar.setPosition(BarPosition.CENTRAL);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.leftMargin = 0;
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                getParentActivity().getVibrationManager().vibrate(v.getContext());
                                CommonUtil.showToastMessage(v.getContext(), getParentActivity().getVie_toast(), getParentActivity().getTxt_toast(),
                                        v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else {
                            Log.i("TEST", "NOT ALLOWED Moving from OUTER to other position than CENTRAL");
                            par.leftMargin = 0;
                            getParentActivity().getVibrationManager().vibrate(v.getContext());
                            CommonUtil.showToastMessage(v.getContext(), getParentActivity().getVie_toast(), getParentActivity().getTxt_toast(), v.getResources().getString(R.string.e0x2), Toast.LENGTH_SHORT);
                        }

                    } else if (bar.getPosition().equals(BarPosition.CENTRAL)) {
                        // CENTRAL position, means I can move the bar to the right or to the left

                        Log.i("TEST", "Starting in CENTRAL, I can move both to the right and to the left, so INNER, OUTER");

                        if ((int) event.getRawX() > (prevFixedX + getParentActivity().getCellWidth())) { //This means I'm moving to the right
                            Log.i("TEST", "Moving from CENTRAL to INNER");

                            // Calling validator
                            StatusMessage status = getParentActivity().getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.INNER, getParentActivity().getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                getParentActivity().getMusicManager().playMoveSoundEffect(getParentActivity().getApplicationContext());
                                par.leftMargin = 2 * getParentActivity().getCellWidth();
                                bar.setPosition(BarPosition.INNER);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.leftMargin = getParentActivity().getCellWidth();
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                getParentActivity().getVibrationManager().vibrate(v.getContext());
                                CommonUtil.showToastMessage(v.getContext(), getParentActivity().getVie_toast(), getParentActivity().getTxt_toast(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else if ((int) event.getRawX() < prevFixedX) {   // Not with CELL_WIDTH because is enough moving to the left, having in mind that
                            // all bar start at the beginning of each cell
                            Log.i("TEST", "Moving from CENTRAL to OUTER");

                            // Calling validator
                            StatusMessage status = getParentActivity().getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.OUTER, getParentActivity().getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                getParentActivity().getMusicManager().playMoveSoundEffect(getParentActivity().getApplicationContext());
                                par.leftMargin = 0;
                                bar.setPosition(BarPosition.OUTER);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.leftMargin = getParentActivity().getCellWidth();
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                getParentActivity().getVibrationManager().vibrate(v.getContext());
                                CommonUtil.showToastMessage(v.getContext(), getParentActivity().getVie_toast(), getParentActivity().getTxt_toast(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else {
                            Log.i("TEST", "NOT ALLOWED Moving from CENTRAL to other position than INNER/OUTER.");
                            par.leftMargin = getParentActivity().getCellWidth();
                        }

                    } else if (bar.getPosition().equals(BarPosition.INNER)) {

                        // INNER position, means I can move the bar only to the left

                        Log.i("TEST", "Starting in INNER, I can move only to the left, so CENTRAL, OUTER not allowed");

                        if ((int) event.getRawX() < prevFixedX) {      // Not with CELL_WIDTH because is enough moving to the left, having in mind that
                            // all bar start at the beginning of each cell
                            Log.i("TEST", "Moving from INNER to CENTRAL");

                            // Calling validator
                            StatusMessage status = getParentActivity().getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.CENTRAL, getParentActivity().getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                getParentActivity().getMusicManager().playMoveSoundEffect(getParentActivity().getApplicationContext());
                                par.leftMargin = getParentActivity().getCellWidth();
                                bar.setPosition(BarPosition.CENTRAL);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.leftMargin = 2 * getParentActivity().getCellWidth();
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                getParentActivity().getVibrationManager().vibrate(v.getContext());
                                CommonUtil.showToastMessage(v.getContext(), getParentActivity().getVie_toast(), getParentActivity().getTxt_toast(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else {
                            Log.i("TEST", "NOT ALLOWED Moving from INNER to other position than CENTRAL");
                            par.leftMargin = 2 * getParentActivity().getCellWidth();
                            getParentActivity().getVibrationManager().vibrate(v.getContext());
                            CommonUtil.showToastMessage(v.getContext(), getParentActivity().getVie_toast(), getParentActivity().getTxt_toast(), v.getResources().getString(R.string.e0x2), Toast.LENGTH_SHORT);
                        }

                    }

                    par.topMargin = topMargin;
                    v.setLayoutParams(par);
                    v.setTag(bar);
                }
                return true;
            }

            case MotionEvent.ACTION_DOWN: {
                if ( getParentActivity().getEngine().isGameStartConditionReached() && !getParentActivity().getEngine().isGameEndConditionReached()) {
                    prevFixedX = (int) event.getRawX();
                    prevMovX = (int) event.getRawX();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    topMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).topMargin;
                    v.setLayoutParams(par);
                }
                return true;
            }
        }
        return false;
    }
}