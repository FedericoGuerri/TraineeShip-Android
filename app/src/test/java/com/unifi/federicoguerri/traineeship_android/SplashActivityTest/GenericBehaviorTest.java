package com.unifi.federicoguerri.traineeship_android.SplashActivityTest;

import android.content.Intent;
import android.os.Build;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.SplashScreenActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertNull;
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
    public void actionBar_isNotThereByTheme(){
        assertNull(activity.getSupportActionBar());
    }

    @Test
    public void willLaunch_MainActivityWhenReady() throws Exception {
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("MainActivity",shadowIntent.getIntentClass().getSimpleName());
    }

    @Test
    public void isUsing_aThemeFromManifest(){
        ShadowActivity shadowActivity = shadowOf(activity);
        assertTrue(shadowActivity.setThemeFromManifest());
    }

    @Test
    public void startsMainActivity_withEnterTransition(){
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(R.anim.start_app_enter,shadowActivity.getPendingTransitionEnterAnimationResourceId());

    }

    @Test
    public void startsMainActivity_withExitTransition(){
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(R.anim.start_app_exit,shadowActivity.getPendingTransitionExitAnimationResourceId());

    }


}
