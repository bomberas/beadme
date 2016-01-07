package it.polimi.group03.activity;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.util.Log;
import android.widget.*;

import it.polimi.group03.R;
import it.polimi.group03.dao.SQLiteStatisticRepository;
import it.polimi.group03.domain.Statistic;

public class MainActivity extends Activity {
    private static final String IMAGEVIEW_TAG = "Cte Logo";
    String msg;
    ImageView img;
    private LinearLayout.LayoutParams layoutParams;
    private static final int CELL_WIDTH = 65;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);



/*
        img = (ImageView) findViewById(R.id.imgViewH3);
        // Sets the tag
        img.setTag(IMAGEVIEW_TAG);
        // Sets a long click listener for the ImageView
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) v
                        .getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(),
                        mimeTypes, item);

                // Instantiates the drag shadow builder.
                View.DragShadowBuilder myShadow = new DragShadowBuilder(img);

                // Starts the drag
                v.startDrag(dragData, // the data to be dragged
                        myShadow, // the drag shadow builder
                        null, // no need to use local data
                        0 // flags (not currently used, set to 0)
                );
                return true;
            }
        });

        // Set the drag event listener for the View
        img.setOnDragListener(new OnDragListener() {
            @SuppressLint("NewApi")
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        layoutParams = (LinearLayout.LayoutParams) v
                                .getLayoutParams();
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_STARTED");
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENTERED");
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_EXITED");
                        x = (int) event.getX();
                        y = (int) event.getY();
                        layoutParams.leftMargin = x;
                        layoutParams.topMargin = y;
                        v.setLayoutParams(layoutParams);
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_LOCATION");
                        x = (int) event.getX();
                        y = (int) event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");

                        break;
                    case DragEvent.ACTION_DROP:
                        Log.d(msg, "ACTION_DROP event");


                        //Aquí tendría que llamar al validador de movimiento-posición

                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        */

    /*
        final ImageView img3 = (ImageView) findViewById(R.id.imgViewH3);
        img3.setOnTouchListener(new View.OnTouchListener() {
            int prevX, prevY;

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                final LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) v.getLayoutParams();

                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE: {
                        par.topMargin += (int)event.getRawY() - prevY;
                        prevY = (int)event.getRawY();
                        par.leftMargin += (int)event.getRawX() - prevX;
                        prevX = (int)event.getRawX();
                        v.setLayoutParams(par);
                        return true;
                    }

                    case MotionEvent.ACTION_UP: {
                        par.topMargin += (int) event.getRawY() - prevY;
                        par.leftMargin += (int) event.getRawX() - prevX;
                        v.setLayoutParams(par);
                        return true;
                    }

                    case MotionEvent.ACTION_DOWN: {
                        prevX = (int) event.getRawX();
                        prevY = (int) event.getRawY();
                        par.bottomMargin = -2 * v.getHeight();
                        par.rightMargin = -2 * v.getWidth();
                        v.setLayoutParams(par);
                        return true;
                    }
                }
                return false;
            }
        });
    */

        /*

        final ImageView imgH1 = (ImageView) findViewById(R.id.imgViewH1);
        imgH1.setTag(0);
        imgH1.setOnTouchListener(new View.OnTouchListener() {
            int prevX, prevY;
            int topMargin;
            int leftMargin;

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                final LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) v.getLayoutParams();

                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE: {
                        //par.topMargin += (int)event.getRawY() - prevY;
                        //prevY = (int)event.getRawY();
//                        par.leftMargin += (int) event.getRawX() - prevX;
//                        par.topMargin = topMargin;
//                        prevX = (int) event.getRawX();
//                        v.setLayoutParams(par);
                        return true;
                    }

                    case MotionEvent.ACTION_UP: {
                        //par.topMargin += (int) event.getRawY() - prevY;
                        par.topMargin = topMargin;
                        //par.leftMargin += (int) event.getRawX() - prevX;

                        int maxRight = getWidth((LinearLayout) v.getParent()); //Width of winsow more or less
                        int xAxis = (int) event.getRawX();
                        int offset = 5;

                        Log.i("TEST", "Width of parent: " + maxRight);
                        Log.i("TEST", "Size of cell: " + CELL_WIDTH);
                        Log.i("TEST", "X AxisX: " + xAxis);
                        Log.i("TEST", "Margin Left: " + leftMargin);

                        /*
                        if ((int) event.getRawX() + 110 < maxRight && (int) event.getRawX() > 110) {
                            par.leftMargin += (int) event.getRawX() - prevX;
                        }*/

   /*                     Log.i("TEST", "[offset + xAxis]: " + (offset + xAxis) + " MaxRight: " + maxRight);

                        //  if ((int) event.getRawX() + 110 < maxRight && (int) event.getRawX() > 110) {
                        //if (leftMargin <= offset + cellSize) {
                        if (v.getTag().equals(0)) {
                            // Puedo mover 2 a la derecha

                            Log.i("TEST", "leftMargin: " + leftMargin);
                            Log.i("TEST", "Puedo mover 2 a la derecha si X está en la 0 a 65 o 65 a 130");

                            if ((int) event.getRawX() > CELL_WIDTH + offset && (int) event.getRawX() < (2 * CELL_WIDTH) + offset) {
                                Log.i("TEST", "Entro Caso 1.1 Lo dejo en la pos 1");
                                //par.leftMargin += (int) event.getRawX() - prevX;
                                par.leftMargin = CELL_WIDTH + 2;
                                v.setTag(1);
                            } else if ((int) event.getRawX() > offset + (2 * CELL_WIDTH)  && (int) event.getRawX() < offset + (3 * CELL_WIDTH)) {
                                Log.i("TEST", "Entro Caso 1.2 Lo dejo en la pos 2");
                                //par.leftMargin += (int) event.getRawX() - prevX;
                                par.leftMargin = 2 * CELL_WIDTH + 2;
                                v.setTag(2);
                            } else {
                                par.leftMargin = leftMargin;
                            }

                        } else if (v.getTag() == 1) {
                            Log.i("TEST", "leftMargin: " + leftMargin);
                            Log.i("TEST", "Puedo mover uno a cada lado si X está entre 0 y 65");
                            Log.i("TEST", "X: " + (int) event.getRawX());

                            if ((int) event.getRawX() < CELL_WIDTH + offset && (int) event.getRawX() > offset) {
                                Log.i("TEST", "Entro Caso 2.1 Lo dejo en la pos 0");
                                //par.leftMargin += (int) event.getRawX() - prevX;
                                par.leftMargin = CELL_WIDTH + 2;
                                v.setTag(0);
                            } else if ((int) event.getRawX() > (2 * CELL_WIDTH) + offset && (int) event.getRawX() < (3 * CELL_WIDTH) + offset) {
                                Log.i("TEST", "Entro Caso 2.2 Lo dejo en la pos 2");
                                //par.leftMargin += (int) event.getRawX() - prevX;
                                par.leftMargin = 2 * CELL_WIDTH + 2;
                                v.setTag(2);
                            } else {
                                par.leftMargin = leftMargin;
                            }

                            //} else if (leftMargin <= offset + 3 * cellSize) {
                        } else if (v.getTag() == 2) {

                            Log.i("TEST", "leftMargin: " + leftMargin);
                            Log.i("TEST", "Puedo mover dos a la izq si X está entre 0 y 65 o 65 y 130");
                            Log.i("TEST", "X: " + (int) event.getRawX());

                            if ((int) event.getRawX() > offset && (int) event.getRawX() < CELL_WIDTH + offset) {
                                Log.i("TEST", "Entro Caso 3.1 Lo dejo en la pos 0");
                                //par.leftMargin += (int) event.getRawX() - prevX;
                                par.leftMargin = 2;
                                v.setTag(0);
                            } else if ((int) event.getRawX() > CELL_WIDTH + offset && (int) event.getRawX() < (2 * CELL_WIDTH) + offset) {
                                Log.i("TEST", "Entro Caso 3.2 Lo dejo en la pos 1");
                                //par.leftMargin += (int) event.getRawX() - prevX;
                                par.leftMargin = CELL_WIDTH + 2;
                                v.setTag(1);
                            } else {
                                par.leftMargin = leftMargin;
                            }

                        }
                        //}

                        par.topMargin = topMargin;
                        v.setLayoutParams(par);
                        leftMargin = par.leftMargin;
                        Log.i("TEST", "Antes de finalizar Event UP: [leftM]" + leftMargin);
                        return true;
                    }

                    case MotionEvent.ACTION_DOWN: {
                        prevX = (int) event.getRawX();
                        prevY = (int) event.getRawY();
                        par.bottomMargin = -2 * v.getHeight();
                        par.rightMargin = -2 * v.getWidth();
                        topMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).topMargin;
                        leftMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).leftMargin;
                        //leftMargin = 2;
                        Log.i("TEST", "Event down: [leftMargin]" + leftMargin);
                        v.setLayoutParams(par);
                        return true;
                    }
                }
                return false;
            }
        });

        System.out.println("Left Margin: " + ((LinearLayout.LayoutParams) imgH1.getLayoutParams()).leftMargin);

        ImageView imgV2 = (ImageView) findViewById(R.id.imgViewV2);
        imgV2.setTag(0);
        imgV2.setOnTouchListener(new View.OnTouchListener() {
            int prevX, prevY;
            int topMargin;
            int leftMargin;

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                final LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) v.getLayoutParams();

                switch (event.getAction()) {

                    case MotionEvent.ACTION_MOVE: {
                        int maxRight = getHeight((LinearLayout) v.getParent()); //Width of winsow more or less

                        if ((int) event.getRawY() + 110 < maxRight && (int) event.getRawY() > 110) {
                            par.topMargin += (int) event.getRawY() - prevY;
                        }

                        par.leftMargin = leftMargin;
                        prevY = (int) event.getRawY();
                        v.setLayoutParams(par);
                        return true;
                    }

                    case MotionEvent.ACTION_UP: {

                        par.leftMargin = leftMargin;

                        int maxRight = getHeight((LinearLayout) v.getParent()); //Width of winsow more or less
                        double cellSize = maxRight / 10;
                        int yAxis = (int) event.getRawY();
                        int offset = 100;

                        Log.i("TEST", "Width of parent: " + maxRight);
                        Log.i("TEST", "Size of cell: " + cellSize);
                        Log.i("TEST", "Y AxisX: " + yAxis);
                        Log.i("TEST", "topMargin: " + topMargin);
                        Log.i("TEST", "Offset: " + offset);

                        Log.i("TEST", "[offset + yAxis]: " + (offset + yAxis) + " MaxRight: " + maxRight);

                        if ((int) event.getRawY() + 110 < maxRight && (int) event.getRawY() > 110) {

                            if (v.getTag().equals(0)) {

                                if ((int) event.getRawY() > offset + cellSize - 10 && (int) event.getRawY() < offset + (2 * cellSize) - 10) {
                                    Log.i("TEST", "Entro Caso 1.1 Lo dejo en la pos 1");
                                    par.topMargin += (int) event.getRawY() - prevY;
                                    v.setTag(1);
                                } else if ((int) event.getRawY() > offset + (2 * cellSize) - 10 && (int) event.getRawY() < offset + (3 * cellSize) - 10) {
                                    Log.i("TEST", "Entro Caso 1.1 Lo dejo en la pos 2");
                                    par.topMargin += (int) event.getRawY() - prevY;
                                    v.setTag(2);
                                } else {
                                    par.topMargin = topMargin;
                                }

                                Log.i("TEST", "Caso 1 Tag: " + v.getTag().toString());

                            } else if (v.getTag() == 1) {

                                if ((int) event.getRawY() > offset && (int) event.getRawY() < offset + cellSize - 10) {
                                    Log.i("TEST", "Entro Caso 2.1 Lo dejo en la pos 0");
                                    par.topMargin += (int) event.getRawY() - prevY;
                                    v.setTag(0);
                                } else if ((int) event.getRawY() > offset + (2 * cellSize) - 10 && (int) event.getRawY() < offset + (3 * cellSize) - 10) {
                                    Log.i("TEST", "Entro Caso 2.2 Lo dejo en la pos 2");
                                    par.topMargin += (int) event.getRawY() - prevY;
                                    v.setTag(2);
                                } else {
                                    par.topMargin = topMargin;
                                }

                            } else if (v.getTag() == 2) {

                                if ((int) event.getRawY() > offset && (int) event.getRawY() < offset + cellSize - 10) {
                                    Log.i("TEST", "Entro Caso 3.1 Lo dejo en la pos 0");
                                    par.topMargin += (int) event.getRawY() - prevY;
                                    v.setTag(0);
                                } else if ((int) event.getRawY() > offset + cellSize - 10 && (int) event.getRawY() < offset + (2 * cellSize) - 10) {
                                    Log.i("TEST", "Entro Caso 3.2 Lo dejo en la pos 1");
                                    par.topMargin += (int) event.getRawY() - prevY;
                                    v.setTag(1);
                                } else {
                                    par.topMargin = topMargin;
                                }

                            }
                        }

                        par.leftMargin = leftMargin;
                        v.setLayoutParams(par);
                        topMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).topMargin;
                        Log.i("TEST", "Antes de finalizar Event UP: [leftM]" + topMargin);
                        return true;
                    }

                    case MotionEvent.ACTION_DOWN: {
                        prevX = (int) event.getRawX();
                        prevY = (int) event.getRawY();
                        par.bottomMargin = -2 * v.getHeight();
                        par.rightMargin = -2 * v.getWidth();
                        topMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).topMargin;
                        leftMargin = ((LinearLayout.LayoutParams) v.getLayoutParams()).leftMargin;
                        Log.i("TEST", "Event down: [topMargin]" + topMargin);
                        v.setLayoutParams(par);
                        return true;
                    }
                }
                return false;
            }
        });

        FloatingActionButton btn = (FloatingActionButton)findViewById(R.id.fab);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                System.out.println("Left Margin: " + ((LinearLayout.LayoutParams) imgH1.getLayoutParams()).leftMargin);
                int topMargin = ((LinearLayout.LayoutParams) imgH1.getLayoutParams()).topMargin;
                LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) imgH1.getLayoutParams();
                par.topMargin = topMargin;
                par.leftMargin += CELL_WIDTH;
                imgH1.setLayoutParams(par);
                System.out.println("Left Margin: " + ((LinearLayout.LayoutParams) imgH1.getLayoutParams()).leftMargin);
            }
        });

        */
    }


    private int getWidth(LinearLayout linearLayout){
        return linearLayout.getWidth();
    }

    private int getHeight(LinearLayout linearLayout){
        return linearLayout.getHeight();
    }
}
