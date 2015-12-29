package it.polimi.group03.activity;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import it.polimi.group03.R;
import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 *
 * This class implements the OnTouchListener event for every vertical bar on the board.
 *
 * <p>Uses the validations regarding to every kind of move performed by {@link GameEngine#makeMove(int, BarOrientation, BarPosition, Player)}.
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
public class VerticalBarOnTouchListener extends GenericOnTouchListener implements View.OnTouchListener {

    private int prevFixedY;
    private int prevMovY;
    private int leftMargin;
    private int cellWidth;

    public VerticalBarOnTouchListener(Activity activity, GameEngine engine, int cellWidth) {
        super(activity, engine);
        this.cellWidth = cellWidth;
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

        Bar bar = (Bar)v.getTag();

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {

                if ( getEngine().isGameStartConditionReached() && !getEngine().isGameEndConditionReached()) {
                    par.topMargin += (int) event.getRawY() - prevMovY;
                    prevMovY = (int) event.getRawY();
                    v.setLayoutParams(par);
                }
                return true;
            }

            case MotionEvent.ACTION_UP: {

                if ( getEngine().isGameStartConditionReached() && !getEngine().isGameEndConditionReached()) {

                    par.leftMargin = leftMargin;

                    if (bar.getPosition().equals(BarPosition.OUTER)) {
                        // OUTER position, means I can move the bar only to the middle

                        Log.i("TEST", "Starting in OUTER, I can move only to the middle, so CENTRAL, INNER not allowed");

                        if ((int) event.getRawY() >= cellWidth && (int) event.getRawY() > prevFixedY) {
                            //This means I'm moving to the right, which is the only
                            //correct movement starting from the OUTER position
                            //In this case I move just one cell, so CENTRAL

                            Log.i("TEST", "Moving from OUTER to CENTRAL");

                            // Calling validator
                            StatusMessage status = getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.CENTRAL, getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                par.topMargin = cellWidth;
                                bar.setPosition(BarPosition.CENTRAL);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.topMargin = 0;
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else {
                            Log.i("TEST", "NOT ALLOWED Moving from OUTER to other position than CENTRAL");
                            par.topMargin = 0;
                            CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(R.string.e0x2), Toast.LENGTH_LONG);
                        }

                    } else if (bar.getPosition().equals(BarPosition.CENTRAL)) {
                        // CENTRAL position, means I can move the bar to the right or to the left

                        Log.i("TEST", "Starting in CENTRAL, I can move both to the top and to the bottom, so INNER, OUTER");

                        if ((int) event.getRawY() > (prevFixedY + cellWidth)) { //This means I'm moving to the bottom
                            Log.i("TEST", "Moving from CENTRAL to INNER");

                            // Calling validator
                            StatusMessage status = getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.INNER, getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                par.topMargin = 2 * cellWidth;
                                bar.setPosition(BarPosition.INNER);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.topMargin = cellWidth;
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else if ((int) event.getRawY() < prevFixedY) {   // Not with CELL_WIDTH because is enough moving to the left, having in mind that
                            // all bar start at the beginning of each cell
                            Log.i("TEST", "Moving from CENTRAL to OUTER");

                            // Calling validator
                            StatusMessage status = getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.OUTER, getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                par.topMargin = 0;
                                bar.setPosition(BarPosition.OUTER);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.topMargin = cellWidth;
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else {
                            Log.i("TEST", "NOT ALLOWED Moving from CENTRAL to other position than INNER/OUTER.");
                            par.topMargin = cellWidth;
                        }

                    } else if (bar.getPosition().equals(BarPosition.INNER)) {

                        // INNER position, means I can move the bar only to the left

                        Log.i("TEST", "Starting in INNER, I can move only to the middle, so CENTRAL, OUTER not allowed");

                        if ((int) event.getRawY() < prevFixedY) {      // Not with CELL_WIDTH because is enough moving to the left, having in mind that
                            // all bar start at the beginning of each cell
                            Log.i("TEST", "Moving from INNER to CENTRAL");

                            // Calling validator
                            StatusMessage status = getEngine().makeMove(bar.getId(), bar.getOrientation(), BarPosition.CENTRAL, getEngine().getGame().getNextPlayer());

                            if (status.getCode().equals(Constant.STATUS_OK)) {
                                par.topMargin = cellWidth;
                                bar.setPosition(BarPosition.CENTRAL);
                                refreshBoard();
                                refreshState();
                            } else {
                                par.topMargin = 2 * cellWidth;
                                Log.i("TEST", "Invalid movement. " + v.getResources().getString(status.getRCode()));
                                CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(status.getRCode()), Toast.LENGTH_SHORT);
                            }
                        } else {
                            Log.i("TEST", "NOT ALLOWED Moving from INNER to other position than CENTRAL");
                            CommonUtil.showToastMessage(v.getContext(), v.getResources().getString(R.string.e0x2), Toast.LENGTH_LONG);
                            par.topMargin = 2 * cellWidth;
                        }

                    }

                    par.leftMargin = leftMargin;
                    v.setLayoutParams(par);
                    v.setTag(bar);
                }
                return true;
            }

            case MotionEvent.ACTION_DOWN: {
                if ( getEngine().isGameStartConditionReached() && !getEngine().isGameEndConditionReached()) {
                    prevFixedY = (int) event.getRawY();
                    prevMovY = (int) event.getRawY();
                    par.bottomMargin = -2 * v.getHeight();
                    par.rightMargin = -2 * v.getWidth();
                    leftMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).leftMargin;
                    v.setLayoutParams(par);
                }
                return true;
            }
        }
        return false;
    }

}
