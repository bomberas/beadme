package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private GameEngine engine;
    private RelativeLayout box;
    private int cellWidth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_play);
        box = (RelativeLayout) findViewById(R.id.box);
        engine = new GameEngine();
        engine.startGame();
        setScreenDimensions(getIntent().getIntExtra(Constant.HEIGHT, 0), getIntent().getIntExtra(Constant.WIDTH, 0));
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
        image.setOnTouchListener(new HorizontalBarOnTouchListener(this, engine, cellWidth));

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
        image.setOnTouchListener(new VerticalBarOnTouchListener(this, engine, cellWidth));

        LinearLayout layout = (LinearLayout)image.getParent();
        LinearLayout.LayoutParams paramsLayout = (LinearLayout.LayoutParams)layout.getLayoutParams();
        paramsLayout.width = cellWidth;
        layout.setLayoutParams(paramsLayout);
    }

    private void createAndAddPlayersAndBeadsToUI(){
        int numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));

        RelativeLayout relPlayers = (RelativeLayout) findViewById(R.id.relPlayers);
        relPlayers.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        relPlayers.setHorizontalGravity(RelativeLayout.CENTER_HORIZONTAL);

        LinearLayout summary = (LinearLayout)findViewById(R.id.summary);

        for ( int i = 0; i < numberOfPlayers; i++ ){

            Player player = new Player(i, getIntent().getStringExtra(Constant.PLAYER_NAME + i), "");
            engine.addPlayer(player);

            int imageId = getIntent().getIntExtra(Constant.PLAYER_ICON + i, 0);
            int imageSummaryId = getThemeManager().getSummaryIcon(this, Constant.GAME_MAX_NUMBER_BEADS);

            ImageView imgSummary= new ImageView(getApplicationContext());
            imgSummary.setId(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(imageSummaryId);
            LinearLayout.LayoutParams paramsS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsS.width = (int) (cellWidth * 1.5);
            paramsS.height = (int) (cellWidth * 1.2);
            imgSummary.setPadding(10,0,10,0);
            imgSummary.setLayoutParams(paramsS);
            imgSummary.setScaleType(ImageView.ScaleType.FIT_XY);
            summary.addView(imgSummary);

            ImageView imgPlayer= new ImageView(getApplicationContext());
            imgPlayer.setImageResource(imageId);
            LinearLayout.LayoutParams paramsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsP.width = (int) (cellWidth * 1.5);
            paramsP.height = (int) (cellWidth * 1.5);
            imgPlayer.setPadding(10,0,10,0);
            imgPlayer.setLayoutParams(paramsP);
            imgPlayer.setScaleType(ImageView.ScaleType.FIT_XY);
            summary.addView(imgPlayer);

            // Adding beads to the view
            for (int  j = 0; j < Constant.GAME_MAX_NUMBER_BEADS; j++){

                ImageView imgBead= new ImageView(getApplicationContext());
                imgBead.setTag(new Bead());
                imgBead.setId(CommonUtil.getBeadId(i, j));
                imgBead.setImageResource(imageId);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = i * cellWidth + ((RelativeLayout.LayoutParams) box.getLayoutParams()).leftMargin;
                params.width = (int) (cellWidth * 1.1);
                params.height = (int) (cellWidth * 1.1);
                params.topMargin = (int) (cellWidth * Constant.NUMBER_OF_CELLS + 1.5 * ((RelativeLayout.LayoutParams) box.getLayoutParams()).topMargin);
                imgBead.setLayoutParams(params);
                imgBead.setScaleType(ImageView.ScaleType.FIT_XY);

                imgBead.setOnTouchListener(new BeadOnTouchListener(this, engine, player, cellWidth, ((RelativeLayout.LayoutParams) box.getLayoutParams()).leftMargin,
                        ((RelativeLayout.LayoutParams) box.getLayoutParams()).topMargin,
                        params.leftMargin, 5));

                relPlayers.addView(imgBead, j);
            }

        }

        TextView text = (TextView)findViewById(R.id.txtCurrentPlayer);
        text.setText(getResources().getString(R.string.current_player) + engine.getGame().getNextPlayer().getNickname());
        engine.getGame().setNextPlayer(engine.getGame().getPlayers().get(0));
        enableImages(engine.getGame().getPlayers().get(0).getId());
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

    private void enableImages(int id){

        for (Player player: this.engine.getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;
            for (int i = 0; i < Constant.GAME_MAX_NUMBER_BEADS; i++) {
                ImageView imgBead = (ImageView) findViewById(CommonUtil.getBeadId(player.getId(), indexBead));
                if ( player.getId() == id ) {
                    imgBead.setVisibility(ImageView.VISIBLE);
                } else {
                    imgBead.setVisibility(ImageView.INVISIBLE);
                }
                indexBead --;
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

}