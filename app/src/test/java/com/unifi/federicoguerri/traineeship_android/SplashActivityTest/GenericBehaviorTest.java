package com.unifi.federicoguerri.traineeship_android.SplashActivityTest;

import android.os.Build;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.SplashScreenActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class GenericBehaviorTest {

    private SplashScreenActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity( SplashScreenActivity.class ).create().get();
    }

    @Test
    public void pressingBackButton_willFinishTheApp(){
        activity.onBackPressed();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertTrue(shadowActivity.isFinishing());
    }

    @Test
    public void actionBar_isHidden(){
        assertEquals(false,activity.getSupportActionBar().isShowing());
    }
}
