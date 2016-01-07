package it.polimi.group03.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import it.polimi.group03.R;
import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.engine.GameEngine;
import it.polimi.group03.util.BarOrientation;
import it.polimi.group03.util.BarPosition;
import it.polimi.group03.util.CommonUtil;
import it.polimi.group03.util.Constant;

/**
 *
 * This class represents the principal activity in the project. It controls all things related to
 * the mechanics of the game and the corresponding response of the UI.
 *
 * <p>Here it's necessary to initialize an instance of (@link GameEngine) this way is possible to
 * link the UI with the game brain.
 *
 * @see GameEngine
 *
 * @author cecibloom
 * @version 1.0
 * @since 15/12/2015.
 *
 */
public class PlayBeadMeActivity extends GenericActivity {

    private static final String TAG = PlayBeadMeActivity.class.getSimpleName();
    private GameEngine engine;
    private RelativeLayout box;
    private View vie_toast;
    private TextView txt_toast;
    private int cellWidth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_play);
        box = (RelativeLayout) findViewById(R.id.box);
        engine = new GameEngine();
        engine.startGame();
        int height = getIntent().getIntExtra(Constant.HEIGHT, 0);
        int width = getIntent().getIntExtra(Constant.WIDTH, 0);

        setScreenDimensions(height, width);
        getThemeManager().setPlayBackgroundAnimation(this, (RelativeLayout)findViewById(R.id.animation), height, width);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

    private void setScreenDimensions(int height, int width) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) box.getLayoutParams();

        double maxAllowedSize;

        //I will set the size to be always equal to the smallest side of the device
        if (height >= width) {
            // In this case the smallest is the width, so let's make it equal to that
            maxAllowedSize = width;
        } else {
            // In this case the smallest is the height, so we make it equal to the device's height
            maxAllowedSize = height;
        }

        // I'm saving the original values
        int offsetX = width;
        int offsetY = height;

        Log.i("TEST", "The maximum size allowed is " + maxAllowedSize);

        // I'm making the height and the width equal because I want the box to be a square equivalent to 13 size-fixed cells
        // I'm also trimming this square to be multiple of the fixed number of cells (13), this is for managing the margin, which here cannot be float
        params.height = (int)(Constant.NUMBER_OF_CELLS * (Math.floor(maxAllowedSize / Constant.NUMBER_OF_CELLS)));
        params.width = params.height;

        // Even when I make the box of size multiple of 13, I'm double guarding the "int" thing
        cellWidth = (int) (Math.floor(maxAllowedSize / Constant.NUMBER_OF_CELLS));

        // Since the gravity center didn't work, I'm managing the margins
        // The offset on X should be arranged both right and left, as an int also
        offsetX = (int)Math.floor((offsetX - params.width)/ 2);
        offsetY = (int)Math.floor((offsetY - params.height)/ 2);

        params.leftMargin = offsetX;
        params.topMargin = offsetY;

        box.setLayoutParams(params);

        // It must start in the 4 row/column
        LinearLayout linLayoutVertical = (LinearLayout)findViewById(R.id.linLayoutVertical);
        RelativeLayout.LayoutParams paramsVerticalLayout = (RelativeLayout.LayoutParams)linLayoutVertical.getLayoutParams();
        paramsVerticalLayout.topMargin = 3 * cellWidth;
        linLayoutVertical.setLayoutParams(paramsVerticalLayout);

        LinearLayout linLayoutHorizontal = (LinearLayout)findViewById(R.id.linLayoutHorizontal);
        RelativeLayout.LayoutParams paramsHorizontalLayout = (RelativeLayout.LayoutParams)linLayoutHorizontal.getLayoutParams();
        paramsHorizontalLayout.leftMargin = 3 * cellWidth;
        linLayoutHorizontal.setLayoutParams(paramsHorizontalLayout);

        for (Bar bar : engine.getGame().getBars(BarOrientation.HORIZONTAL)) {
            createAndAddHorizontalBarToUI(bar);
        }

        for (Bar bar : engine.getGame().getBars(BarOrientation.VERTICAL)) {
            createAndAddVerticalBarToUI(bar);
        }

        // Setting values for all toast messages that will be shown in this class.
        LayoutInflater inflater = LayoutInflater.from(this);
        vie_toast = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.customToast));
        txt_toast = (TextView) vie_toast.findViewById(R.id.txt_toast);

        createAndAddPlayersAndBeadsToUI();
    }

    private void createAndAddHorizontalBarToUI(Bar bar){
        ImageView image = (ImageView)findViewById(getBarId(bar.getOrientation(), bar.getId()));
        LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams)image.getLayoutParams();

        //Setting the initial position of the bars according to what the randomize method indicates
        paramsImg.leftMargin = bar.getPosition().equals(BarPosition.OUTER) ? 0 : bar.getPosition().equals(BarPosition.CENTRAL) ?
                cellWidth : 2 * cellWidth;
        paramsImg.width = cellWidth * (Constant.NUMBER_OF_CELLS - 2);
        paramsImg.height = cellWidth;
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setLayoutParams(paramsImg);
        image.setTag(bar);
        image.setOnTouchListener(new HorizontalBarOnTouchListener(this));

        LinearLayout layout = (LinearLayout)image.getParent();
        LinearLayout.LayoutParams paramsLayout = (LinearLayout.LayoutParams)layout.getLayoutParams();
        paramsLayout.height = cellWidth;
        layout.setLayoutParams(paramsLayout);

    }

    private void createAndAddVerticalBarToUI(Bar bar){
        ImageView image = (ImageView)findViewById(getBarId(bar.getOrientation(), bar.getId()));
        LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams)image.getLayoutParams();

        paramsImg.topMargin = bar.getPosition().equals(BarPosition.OUTER) ? 0 : bar.getPosition().equals(BarPosition.CENTRAL) ?
                cellWidth : 2 * cellWidth;
        paramsImg.height = cellWidth * (Constant.NUMBER_OF_CELLS - 2);
        paramsImg.width = cellWidth;
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setLayoutParams(paramsImg);
        image.setTag(bar);
        image.setOnTouchListener(new VerticalBarOnTouchListener(this));

        LinearLayout layout = (LinearLayout)image.getParent();
        LinearLayout.LayoutParams paramsLayout = (LinearLayout.LayoutParams)layout.getLayoutParams();
        paramsLayout.width = cellWidth;
        layout.setLayoutParams(paramsLayout);
    }

    private void createAndAddPlayersAndBeadsToUI(){

        int numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));

        RelativeLayout relPlayers = (RelativeLayout) findViewById(R.id.relPlayers);
        //relPlayers.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        //relPlayers.setHorizontalGravity(RelativeLayout.CENTER_HORIZONTAL);

        LinearLayout summary = (LinearLayout)findViewById(R.id.summary);
        int space = (int)((box.getLayoutParams().width - getCellWidth())/ 2 );

        for ( int i = 0; i < numberOfPlayers; i++ ) {

            Player player = new Player(i, getIntent().getStringExtra(Constant.PLAYER_NAME + i), "");
            engine.addPlayer(player);

            //Adding image of player for the summary section
            int imageId = getIntent().getIntExtra(Constant.PLAYER_ICON + i, 0);
            int imageSummaryId = getThemeManager().getSummaryIcon(this, Constant.GAME_MAX_NUMBER_BEADS);

            ImageView imgSummary = new ImageView(getApplicationContext());
            imgSummary.setId(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(imageSummaryId);
            LinearLayout.LayoutParams paramsS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsS.width = (int) (cellWidth * 1.5);
            paramsS.height = (int) (cellWidth * 1.2);
            imgSummary.setPadding(10, 0, 10, 0);
            imgSummary.setLayoutParams(paramsS);
            imgSummary.setScaleType(ImageView.ScaleType.FIT_XY);
            summary.addView(imgSummary);

            ImageView imgPlayer = new ImageView(getApplicationContext());
            imgPlayer.setImageResource(imageId);
            LinearLayout.LayoutParams paramsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsP.width = (int) (cellWidth * 1.5);
            paramsP.height = (int) (cellWidth * 1.5);
            imgPlayer.setPadding(10, 0, 10, 0);
            imgPlayer.setLayoutParams(paramsP);
            imgPlayer.setScaleType(ImageView.ScaleType.FIT_XY);
            summary.addView(imgPlayer);

        }


        int count = numberOfPlayers - 1;
        int countI = 0;
        // Adding beads to the view
        for (int  j = 0; j < Constant.GAME_MAX_NUMBER_BEADS * numberOfPlayers; j++){

            int imageId = getIntent().getIntExtra(Constant.PLAYER_ICON + count, 0);

            //int imageId = getEngine().getGame().getPlayers().get(count).getId() == 0 ? R.drawable.harry : (getEngine().getGame().getPlayers().get(count).getId() == 1 ? R.drawable.hermione : (getEngine().getGame().getPlayers().get(count).getId() == 2 ? R.drawable.voldemort : R.drawable.snape));

            ImageView imgBead= new ImageView(getApplicationContext());
            imgBead.setTag(new Bead());
            imgBead.setId(CommonUtil.getBeadId(count, countI));
            imgBead.setImageResource(imageId);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = space;
            params.width = (int) (cellWidth * 1.1);
            params.height = (int) (cellWidth * 1.1);
            params.topMargin = (int) (cellWidth * Constant.NUMBER_OF_CELLS + 1.5 * ((RelativeLayout.LayoutParams) box.getLayoutParams()).topMargin);
            imgBead.setLayoutParams(params);
            imgBead.setScaleType(ImageView.ScaleType.FIT_XY);

            imgBead.setOnTouchListener(new BeadOnTouchListener(this, getEngine().getGame().getPlayers().get(count), ((RelativeLayout.LayoutParams) box.getLayoutParams()).leftMargin,
                    ((RelativeLayout.LayoutParams) box.getLayoutParams()).topMargin,
                    params.leftMargin, 5));

            relPlayers.addView(imgBead, j);

            count--;

            if ( count < 0 ){
                count = numberOfPlayers -1;
                countI++;
            }

        }
        engine.getGame().setNextPlayer(engine.getGame().getPlayers().get(0));
        showBeadsOnBoard(false);
    }

    private int getBarId(BarOrientation pos, int id){

        switch (id) {
            case 0:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH1;
                }else {
                    return R.id.imgViewV1;
                }
            case 1:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH2;
                }else {
                    return R.id.imgViewV2;
                }
            case 2:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH3;
                }else {
                    return R.id.imgViewV3;
                }
            case 3:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH4;
                }else {
                    return R.id.imgViewV4;
                }
            case 4:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH5;
                }else {
                    return R.id.imgViewV5;
                }
            case 5:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH6;
                }else {
                    return R.id.imgViewV6;
                }
            case 6:
                if (pos.equals(BarOrientation.HORIZONTAL)){
                    return R.id.imgViewH7;
                }else {
                    return R.id.imgViewV7;
                }
            default:
                return -1;
        }
    }

    public void showBeadsOnBoard(boolean animate){

        for (Player player: getEngine().getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;

            for (int i = 0; i < Constant.GAME_MAX_NUMBER_BEADS; i++) {
                ImageView imgBead = (ImageView) findViewById(CommonUtil.getBeadId(player.getId(), indexBead));

                if ( player.getId() == getEngine().getGame().getNextPlayer().getId() ) {
                    imgBead.setVisibility(ImageView.VISIBLE);

                    getAnimationManager().fadeIn(imgBead);
                    if(((Bead)imgBead.getTag()).isActive())getAnimationManager().bounce(imgBead.getContext(), imgBead);
                } else {
                    if ( !((Bead)imgBead.getTag()).isPlaced() ) {
                        if (animate) getAnimationManager().fadeOut(imgBead);
                        imgBead.setVisibility(ImageView.GONE);
                    }
                }
                indexBead --;
            }

        }

        TextView text = (TextView) findViewById(R.id.txtCurrentPlayer);
        text.setText(getResources().getString(R.string.current_player, getEngine().getGame().getNextPlayer().getNickname()));
        CommonUtil.showToastMessage(getApplicationContext(), getVie_toast(), getTxt_toast(), getResources().getString(R.string.next_player) + getEngine().getGame().getNextPlayer().getNickname(), Toast.LENGTH_SHORT);
    }

    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public View getVie_toast() {
        return vie_toast;
    }

    public TextView getTxt_toast() {
        return txt_toast;
    }

    public void endGame() {

        final Rect mDisplaySize = new Rect();
        final LayoutInflater inflate;
        final int[] LEAVES = {

                R.drawable.greendot,
                R.drawable.lavenderdot,
                R.drawable.lavandersquare,
                R.drawable.lemongreendot,
                R.drawable.lemongreensquare,
                R.drawable.lightbluedot,
                R.drawable.lightbluesquare,
                R.drawable.orangedot,
                R.drawable.orangesquare,
                R.drawable.pinkdot,
                R.drawable.pinksquare,
                R.drawable.reddot,
                R.drawable.redsquare,
                R.drawable.serpentineblue,
                R.drawable.serpentinegreen,
                R.drawable.serpentinelightblue,
                R.drawable.serpentineorange,
                R.drawable.serpentinepurple,
                R.drawable.serpentinered,
                R.drawable.serpentinered2,
        };
        final float mScale;
        final Handler imageHandler = new Handler();

        final Display display = getWindowManager().getDefaultDisplay();
        display.getRectSize(mDisplaySize);

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScale = metrics.density;
        inflate = LayoutInflater.from(PlayBeadMeActivity.this);

        imageHandler.post(new Runnable() {
            public void run() {
                try {

                    ImageView imageView = (ImageView) inflate.inflate(R.layout.ani_image_view, null);
                    imageView.setImageResource(LEAVES[new Random().nextInt(LEAVES.length - 1)]);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    ((RelativeLayout)findViewById(R.id.container)).addView(imageView);

                    RelativeLayout.LayoutParams animationLayout = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                    animationLayout.setMargins(new Random().nextInt(getCellWidth()) * Constant.GAME_MAX_NUMBER_BEADS, (int) (-150 * mScale), 0, 0);
                    animationLayout.width = (int) (60 * mScale);
                    animationLayout.height = (int) (60 * mScale);

                    imageView.setLayoutParams(animationLayout);

                    getAnimationManager().confetti(mDisplaySize,mScale,imageView);
                    imageHandler.postDelayed(this, 500);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });

    }
}
