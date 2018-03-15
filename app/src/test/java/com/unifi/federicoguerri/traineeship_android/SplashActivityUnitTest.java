package com.unifi.federicoguerri.traineeship_android;


import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)

public class SplashActivityUnitTest {

    private SplashScreenActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity( SplashScreenActivity.class )
                .create()
                .get();
    }

    @Test
    public void splashTextViewHasTextFromResources() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        String textFromResources=activity.getResources().getString(R.string.taptostart_text_view);
        assertEquals(textFromResources,splashTextView.getText());
    }

}