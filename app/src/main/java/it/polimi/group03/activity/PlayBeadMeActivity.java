package it.polimi.group03.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Date;
import java.util.Random;

import it.polimi.group03.R;
import it.polimi.group03.dao.GameDAO;
import it.polimi.group03.dao.GameDAOImpl;
import it.polimi.group03.domain.Bar;
import it.polimi.group03.domain.Bead;
import it.polimi.group03.domain.Player;
import it.polimi.group03.domain.Statistic;
import it.polimi.group03.domain.StatusMessage;
import it.polimi.group03.engine.GameBrain;
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
 * <p>It uses an instance of the class {@link GameEngine} to make it possible for the UI connect with the game mechanics.
 *
 * <p>It is also responsible of storing all the info related to the statics and history into the database.
 *
 * <p>This class makes it able to use the AI of the game (a.k.a Mr. Roboto) through {@link it.polimi.group03.engine.GameBrain}
 *
 * @see GameEngine
 * @see it.polimi.group03.engine.GameBrain
 * @see Statistic
 *
 * @author cecibloom
 * @version 1.0
 * @since 15/12/2015.
 *
 */
public class PlayBeadMeActivity extends GenericActivity {

    private static final String TAG = PlayBeadMeActivity.class.getSimpleName();
    private GameEngine engine;
    private GameBrain brain;
    private Statistic statistic;
    private RelativeLayout box;
    private View vie_toast;
    private TextView txt_toast;
    private int cellWidth;
    private int offsetX;
    private int offsetY;
    private int numberOfPlayers;
    private CountDownTimer counterTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_play);

        // Initializing the statistic object, this will hold all the information related to the game, and it'll be used to store data in the db
        statistic = new Statistic();
        statistic.setStartTime(new Date());

        //Setting the theme of the game

        //Getting the box layout where the bars should be shown
        box = (RelativeLayout) findViewById(R.id.box);
        //This attribute is the link to the mechanics of the game
        engine = new GameEngine();
        engine.startGame();
        brain = new GameBrain();

        //Getting the width and height of the device, these are needed to properly show all the bars and beads on the screen
        int height = getIntent().getIntExtra(Constant.HEIGHT, 0);
        int width = getIntent().getIntExtra(Constant.WIDTH, 0);

        //Getting the number of players from the settings.
        numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));

        setBoardDimensions(height, width);
        getThemeManager().setPlayBackgroundAnimation(this, (RelativeLayout) findViewById(R.id.animation), height, width);
        getMusicManager().initSoundMap(this);
    }

    /**
     * This methods controls the action of going back while playing. It prompts a customized popup to warn the player
     * that the current game will be lost if he or she decides to.
     */
    @Override
    public void onBackPressed() {
        if ( counterTimer != null ) counterTimer.cancel();
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

    /**
     *
     * This method sets the dimensions of the board according to the device height and width that was previously sent by the
     * {@link CharactersActivity}. It also calculates the proper dimensions for the bars and beads so they can all fit in the playing
     * section.
     *
     *
     * @param height the actual height of the device
     * @param width the actual width of the device
     */
    private void setBoardDimensions(int height, int width) {
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
        offsetX = width;
        offsetY = height;

        Log.i(TAG, "The maximum size allowed is " + maxAllowedSize);

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

        // Setting the board corners
        LinearLayout linBoard = (LinearLayout)findViewById(R.id.linBoard);
        RelativeLayout.LayoutParams paramsBoardLayout = (RelativeLayout.LayoutParams)linBoard.getLayoutParams();
        paramsBoardLayout.leftMargin = 3 * cellWidth - 8;
        paramsBoardLayout.topMargin = 3 * cellWidth - 8;
        paramsBoardLayout.height = (Constant.NUMBER_OF_CELLS - 6) * cellWidth + 16;
        paramsBoardLayout.width = (Constant.NUMBER_OF_CELLS - 6) * cellWidth + 16;
        linBoard.setLayoutParams(paramsBoardLayout);

        // If we're playing against Mr. Roboto it's necessary configure the bars with the values specified in the preferences of the game
        if ( numberOfPlayers == 1 ){
            String config = PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_BARS, "");

            // The bars will be configured if and only if there's a value for the corresponding preference, in any other case the initial
            // configuration of the bars will remain random.

            if ( !CommonUtil.isEmpty(config) ) {

                engine.reConfigureBars(BarOrientation.HORIZONTAL, config.substring(0, 7).toCharArray());
                engine.reConfigureBars(BarOrientation.VERTICAL, config.substring(7, 14).toCharArray());

                engine.getGame().refreshBoard();
            }
        }

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

    /**
     *
     * Sets the dimension of an horizontal bar and places it on the board, and scales it to the dimensions of the device.
     * It also sets the bar in the initial position: INNER, CENTRAL and OUTER
     *
     * @param bar the bar to be scaled
     */
    private void createAndAddHorizontalBarToUI(Bar bar){
        ImageView image = (ImageView)findViewById(CommonUtil.getBarId(bar.getOrientation(), bar.getId()));
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

    /**
     *
     * Sets the dimension of an vertical bar and places it on the board, and scales it to the dimensions of the device.
     * It also sets the bar in the initial position: INNER, CENTRAL and OUTER
     *
     * @param bar the bar to be scaled
     */
    private void createAndAddVerticalBarToUI(Bar bar){
        ImageView image = (ImageView)findViewById(CommonUtil.getBarId(bar.getOrientation(), bar.getId()));
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

    /**
     * This method creates all the beads needed in the game according to the number of players configured in the preference section.
     * It also creates the images of the Summary section, that displays how many beads a player has on the board.
     */
    private void createAndAddPlayersAndBeadsToUI(){

        RelativeLayout relPlayers = (RelativeLayout) findViewById(R.id.relPlayers);
        LinearLayout summary = (LinearLayout)findViewById(R.id.summary);

        if ( numberOfPlayers == 1) numberOfPlayers ++; //This means we're playing with Mr. Roboto

        int space = (int)((box.getLayoutParams().width - getCellWidth())/ 2 );
        int leftMargin = numberOfPlayers == 2 ? (int)(cellWidth * 2.7) : numberOfPlayers == 3 ? (int)(cellWidth * 1.2) : (int)(cellWidth * 0.4);

        statistic.setNumberOfPlayers(numberOfPlayers);

        for ( int i = 0; i < numberOfPlayers; i++ ) {

            Player player = new Player(i, getIntent().getStringExtra(Constant.PLAYER_NAME + i), "");
            player.setIsMrRoboto(!getIntent().getBooleanExtra(Constant.PLAYER_HUMAN + i, false));
            engine.addPlayer(player);

            //Adding image of player for the summary section
            int imageId = getIntent().getIntExtra(Constant.PLAYER_ICON + i, 0);
            int imageSummaryId = getThemeManager().getSummaryIcon(this, 0);

            ImageView imgSummary = new ImageView(getApplicationContext());
            imgSummary.setId(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(imageSummaryId);
            LinearLayout.LayoutParams paramsS = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsS.width = (int) (cellWidth * 1.5);
            paramsS.height = (int) (cellWidth * 1.5);
            paramsS.leftMargin = leftMargin;
            imgSummary.setLayoutParams(paramsS);
            imgSummary.setScaleType(ImageView.ScaleType.FIT_XY);
            summary.addView(imgSummary);

            ImageView imgPlayer = new ImageView(getApplicationContext());
            imgPlayer.setImageResource(imageId);
            LinearLayout.LayoutParams paramsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsP.width = (int) (cellWidth * 1.2);
            paramsP.height = (int) (cellWidth * 1.2);
            imgPlayer.setLayoutParams(paramsP);
            imgPlayer.setScaleType(ImageView.ScaleType.FIT_XY);
            summary.addView(imgPlayer);
        }

        int countIdPlayer = numberOfPlayers - 1;
        int countIdBead = 0;

        // Adding beads to the view, one after the other in the order players are suppose to play.
        for (int  j = 0; j < Constant.GAME_MAX_NUMBER_BEADS * numberOfPlayers; j++){

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

            params.leftMargin = space;
            params.width = (int) (cellWidth * 1.1);
            params.height = (int) (cellWidth * 1.1);
            params.topMargin = (int) (cellWidth * Constant.NUMBER_OF_CELLS + 1.6 * ((RelativeLayout.LayoutParams) box.getLayoutParams()).topMargin);

            int imageId = getIntent().getIntExtra(Constant.PLAYER_ICON + countIdPlayer, 0);

            ImageView imgBead = new ImageView(getApplicationContext());
            imgBead.setTag(new Bead());
            imgBead.setId(CommonUtil.getBeadId(countIdPlayer, countIdBead));
            imgBead.setImageResource(imageId);
            imgBead.setLayoutParams(params);
            imgBead.setScaleType(ImageView.ScaleType.FIT_XY);
            imgBead.setOnTouchListener(new BeadOnTouchListener(this, engine.getGame().getPlayers().get(countIdPlayer), params.leftMargin, 5));

            relPlayers.addView(imgBead, j);

            countIdPlayer--;

            if ( countIdPlayer < 0 ){
                countIdPlayer = numberOfPlayers -1;
                countIdBead++;
            }

        }

        engine.getGame().setNextPlayer(engine.getGame().getPlayers().get(0));

        if ( engine.getGame().getPlayers().get(0).isMrRoboto() ) {
            automaticBeadMove(false);
        } else {
            showBeadsOnBoard(false);
        }

        getAnimationManager().blink(this, findViewById(R.id.img_arrowRight));
    }

    /**
     * This method shows a bead on the board if the bead is active in the game, if not hides it.
     * It also plays an animation if required.
     *
     * @param animate {@code true} if an animation should be shown {@code  false} if not
     */
    public void showBeadsOnBoard(boolean animate){

        for (Player player: engine.getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;
            int placed = 0;

            for (int i = 0; i < Constant.GAME_MAX_NUMBER_BEADS; i++) {
                ImageView imgBead = (ImageView) findViewById(CommonUtil.getBeadId(player.getId(), indexBead));

                if ( player.getId() == engine.getGame().getNextPlayer().getId() ) {
                    imgBead.setVisibility(ImageView.VISIBLE);

                    getAnimationManager().fadeIn(imgBead);
                    if(((Bead)imgBead.getTag()).isActive())getAnimationManager().bounce(imgBead.getContext(), imgBead);
                } else {
                    if ( !((Bead)imgBead.getTag()).isPlaced() ) {
                        if (animate) getAnimationManager().fadeOut(imgBead);
                        imgBead.setVisibility(ImageView.GONE);
                    }
                }

                if ( ((Bead)imgBead.getTag()).isPlaced() ) placed ++;
                indexBead --;
            }

            ImageView imgSummary = (ImageView)findViewById(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(getThemeManager().getSummaryIcon(this, placed));
        }

        TextView text = (TextView) findViewById(R.id.txtCurrentPlayer);
        text.setText(getResources().getString(R.string.current_player, engine.getGame().getNextPlayer().getNickname()));

        getAnimationManager().zoomIn(this, text);
        getAnimationManager().zoomOut(this, text);
        getAnimationManager().zoomIn(this, text);

        if ( engine.isGameStartConditionReached() ) {
            (findViewById(R.id.img_arrowRight)).clearAnimation();
            (findViewById(R.id.img_arrowRight)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.waiting_spot)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.img_arrowUp)).setVisibility(View.VISIBLE);
            getAnimationManager().blink(this, findViewById(R.id.img_arrowUp));

            if( engine.getGame().getNextPlayer().isMrRoboto() ) automaticBarMove();

        }
    }

    /**
     * This method makes the calls to Mr. Roboto's brain to place a bead automatically. After receiving the position where the bead
     * should be placed it makes the necessary assignments to place the bead on the board and present it to the player.
     *
     * @see it.polimi.group03.engine.GameBrain
     *
     * @param animate {@code true} if an animation should be shown {@code  false} if not
     */
    public void automaticBeadMove(boolean animate){
        // If we're playing against Mr. Roboto
        if ( !engine.isGameStartConditionReached() ) {
            Log.i(TAG, "This is Mr. Roboto");
            Bead bead = brain.placeBead(engine);
            Log.i(TAG, "Mr Roboto thinks: " + bead.getPosition().toString());
            ImageView image = (ImageView) findViewById(CommonUtil.getBeadId(engine.getGame().getNextPlayer().getId(), engine.getGame().getNextPlayer().getRemainingBeadsToPlace() - 1));
            ((Bead) image.getTag()).setPosition(bead.getPosition().getX(), bead.getPosition().getY());
            engine.addBeadToBoard(engine.getGame().getNextPlayer(), ((Bead) image.getTag()));
            RelativeLayout.LayoutParams par = (RelativeLayout.LayoutParams) image.getLayoutParams();
            par.topMargin = CommonUtil.convertRowColumnToXY(((Bead) image.getTag()).getPosition().getX(), getOffsetY(), getCellWidth());
            par.leftMargin = CommonUtil.convertRowColumnToXY(((Bead) image.getTag()).getPosition().getY(), getOffsetX(), getCellWidth());
            image.setLayoutParams(par);
            engine.getGame().setNextPlayer(engine.getNextPlayer(engine.getGame().getNextPlayer()));

            showBeadsOnBoard(animate);
        }
    }

    /**
     * This method makes the calls to Mr. Roboto's brain to move a bar automatically. After receiving the position where the bars
     * should be moved it makes the necessary assignments to move the bar and present it to the player.
     *
     * @see it.polimi.group03.engine.GameBrain
     *
     */
    public void automaticBarMove(){
        // If we're playing against Mr. Roboto

        Log.i(TAG, "This is Mr. Roboto trying to move a bar");

        Bar bar = brain.move(engine);
        ImageView imgBar = (ImageView)findViewById(CommonUtil.getBarId(bar.getOrientation(), bar.getId()));
        Log.i(TAG, "Mr. Roboto chooses " + bar.getOrientation().toString() + " # " + bar.getId() + ". He wants to move it to " + bar.getPosition().toString());

        StatusMessage msg = engine.makeMove(bar.getId(), bar.getOrientation(), bar.getPosition(), engine.getGame().getNextPlayer());
        getMusicManager().playMoveSoundEffect(this);

        Log.i(TAG, "Mr. Roboto move gives status " + msg.getCode());

        if ( msg.getCode().equals(Constant.STATUS_OK) ) {

            ((Bar) imgBar.getTag()).setPosition(bar.getPosition());
            LinearLayout.LayoutParams par = (LinearLayout.LayoutParams) imgBar.getLayoutParams();

            if (bar.getOrientation().equals(BarOrientation.HORIZONTAL)) {
                par.leftMargin = bar.getPosition().equals(BarPosition.OUTER) ? 0 : bar.getPosition().equals(BarPosition.CENTRAL) ?
                        cellWidth : 2 * cellWidth;
            } else {
                par.topMargin = bar.getPosition().equals(BarPosition.OUTER) ? 0 : bar.getPosition().equals(BarPosition.CENTRAL) ?
                        cellWidth : 2 * cellWidth;
            }

            imgBar.setLayoutParams(par);
        }

        refreshBoard();

    }

    /**
     *
     * This method shows on the screen all the beads that are still active after a move, and hides the ones that are not,
     * and gives them the proper animation.
     *
     * <p> It also updates the views of all the current scores for every player in the game. If the game has finished starts the confetti
     * animation th rough {@link #endGame()}.
     */
    public void refreshBoard(){

        for (Player player: engine.getGame().getPlayers()) {
            int indexBead = Constant.GAME_MAX_NUMBER_BEADS - 1;

            for (int i = 0; i < player.getBeads().size(); i++) {

                if (!player.getBeads().get(i).isActive()){
                    // Make image invisible
                    ImageView bead = (ImageView)findViewById(CommonUtil.getBeadId(player.getId(), indexBead));
                    if ( bead.getVisibility() == View.VISIBLE ) {
                        bead.setVisibility(View.INVISIBLE);
                        getAnimationManager().rotate(bead.getContext(), bead);
                    }
                }
                indexBead --;
            }

            ImageView imgSummary = (ImageView)findViewById(CommonUtil.getPlayerSummaryImageId(player.getId()));
            imgSummary.setImageResource(getThemeManager().getSummaryIcon(imgSummary.getContext(), player.activeBeads().size()));
        }

        if ( engine.isGameEndConditionReached() ){
            CommonUtil.showToastMessage(getApplicationContext(), getVie_toast(), getTxt_toast(),
                    getResources().getString(R.string.winner, engine.getWinner().getNickname()),
                    Toast.LENGTH_SHORT);
            findViewById(R.id.img_arrowUp).clearAnimation();
            findViewById(R.id.img_arrowUp).setVisibility(View.GONE);

            TextView text = (TextView)findViewById(R.id.txtCurrentPlayer);
            text.setText(engine.getWinner().isMrRoboto() ? getApplicationContext().getResources().getString(R.string.roboto_winner) : getApplicationContext().getResources().getString(R.string.winner, engine.getWinner().getNickname()));
            getAnimationManager().zoomIn(getApplicationContext(), text);
            getAnimationManager().zoomOut(getApplicationContext(), text);
            getAnimationManager().zoomIn(getApplicationContext(), text);

            // Storing game info into the db
            saveGame();

            // Finishing activity with a confetti animation
            endGame();

        } else {
            TextView text = (TextView)findViewById(R.id.txtCurrentPlayer);
            text.setText(getApplicationContext().getResources().getString(R.string.current_player, engine.getGame().getNextPlayer().getNickname()));
            getAnimationManager().zoomIn(getApplicationContext(), text);
            getAnimationManager().zoomOut(getApplicationContext(), text);
            getAnimationManager().zoomIn(getApplicationContext(), text);
            CommonUtil.showToastMessage(getApplicationContext(), getVie_toast(), getTxt_toast(), getResources().getString(R.string.next_player) + engine.getGame().getNextPlayer().getNickname(),
                    Toast.LENGTH_SHORT);
        }
    }

    /**
     * This method prepares the statistics data tha will be stored in the db, it's called only when the game has finished, calls the DAO layer
     * that handle all persistence methods and uses the {@link GameDAO#save(Statistic)} method.
     *
     * @see GameDAO
     * @see Statistic
     *
     *
     */
    private void saveGame(){

        statistic.setEndTime(new Date());
        statistic.setRounds(engine.getGame().getRound());
        statistic.setWinnerName(engine.getWinner().getNickname());
        statistic.setWinnerIcon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getWinner().getId(), 0));

        switch ( numberOfPlayers ){
            case 2 :

                if ( engine.getGame().getPlayers().get(0).isActive() ){
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(1).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(1).getId(), 0));
                } else {
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(0).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(0).getId(), 0));
                }


                break;
            case 3 :

                if ( engine.getGame().getPlayers().get(0).isActive() ){
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(1).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(1).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(2).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(2).getId(), 0));

                } else  if ( engine.getGame().getPlayers().get(1).isActive() ){
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(0).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(0).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(2).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(2).getId(), 0));

                } else {
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(0).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(0).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(1).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(1).getId(), 0));
                }

                break;
            case 4 :

                if ( engine.getGame().getPlayers().get(0).isActive() ){
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(1).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(1).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(2).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(2).getId(), 0));

                    statistic.setLoser3Name(engine.getGame().getPlayers().get(3).getNickname());
                    statistic.setLoser3Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(3).getId(), 0));

                } else  if ( engine.getGame().getPlayers().get(1).isActive() ){
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(0).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(0).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(2).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(2).getId(), 0));

                    statistic.setLoser3Name(engine.getGame().getPlayers().get(3).getNickname());
                    statistic.setLoser3Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(3).getId(), 0));

                } else  if ( engine.getGame().getPlayers().get(2).isActive() ){
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(0).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(0).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(1).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(1).getId(), 0));

                    statistic.setLoser3Name(engine.getGame().getPlayers().get(3).getNickname());
                    statistic.setLoser3Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(3).getId(), 0));

                } else {
                    statistic.setLoser1Name(engine.getGame().getPlayers().get(0).getNickname());
                    statistic.setLoser1Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(0).getId(), 0));

                    statistic.setLoser2Name(engine.getGame().getPlayers().get(1).getNickname());
                    statistic.setLoser2Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(1).getId(), 0));

                    statistic.setLoser3Name(engine.getGame().getPlayers().get(2).getNickname());
                    statistic.setLoser3Icon(getIntent().getIntExtra(Constant.PLAYER_ICON + engine.getGame().getPlayers().get(2).getId(), 0));
                }
                break;

        }

        GameDAO dao = new GameDAOImpl(this);
        dao.save(statistic);
    }

    /**
     * This method starts the animation when the game has finished and someone has won.
     * 
     */
    private void endGame() {

        final Rect mDisplaySize = new Rect();
        final LayoutInflater inflate;
        final int[] CONFETTI = {
                R.drawable.confetti1,
                R.drawable.confetti2,
                R.drawable.confetti3,
                R.drawable.confetti4,
        };
        final float mScale;

        final Display display = getWindowManager().getDefaultDisplay();
        display.getRectSize(mDisplaySize);

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mScale = metrics.density;
        inflate = LayoutInflater.from(PlayBeadMeActivity.this);

        counterTimer = new CountDownTimer(15000, 300) {
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }

            public void onTick(long millisUntilFinished) {
                ImageView imageView = (ImageView) inflate.inflate(R.layout.ani_image_view, null);
                imageView.setImageResource(CONFETTI[new Random().nextInt(CONFETTI.length - 1)]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ((RelativeLayout) findViewById(R.id.container)).addView(imageView);

                RelativeLayout.LayoutParams animationLayout = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                animationLayout.setMargins(new Random().nextInt(getCellWidth()) * Constant.GAME_MAX_NUMBER_BEADS, (int) (-150 * mScale), 0, 0);
                animationLayout.width = (int) (60 * mScale);
                animationLayout.height = (int) (60 * mScale);
                imageView.setLayoutParams(animationLayout);
                getAnimationManager().confetti(mDisplaySize, mScale, imageView);
            }
        };
        counterTimer.start();
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

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

}
