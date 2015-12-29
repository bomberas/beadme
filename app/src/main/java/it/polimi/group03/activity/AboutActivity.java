package it.polimi.group03.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.share.widget.LikeView;

import it.polimi.group03.R;

/**
 * This class holds the logic to support the About page of the application, the look and feel
 * will depend on the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 22/12/2015.
 */
public class AboutActivity extends GenericActivity {

    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_about);
        Log.i(TAG, "Accessing to About");

        findViewById(R.id.btn_about_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

        LikeView likeView = (LikeView) findViewById(R.id.likeView);
        likeView.setLikeViewStyle(LikeView.Style.BUTTON);
        likeView.setAuxiliaryViewPosition(LikeView.AuxiliaryViewPosition.INLINE);
        likeView.setObjectIdAndType("https://www.facebook.com/polimi",LikeView.ObjectType.PAGE);
    }

}