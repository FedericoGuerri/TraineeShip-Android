package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest;


import android.os.Build;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class GenericBehaviorTest {
    private OcrScanActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(OcrScanActivity.class).create().get();
    }

    @Test
    public void actionBar_isNotShowing() {
        assertEquals(false, activity.getSupportActionBar().isShowing());
    }

    @Test
    public void pressingBackButton_willStartEnterAnimation(){
        activity.onBackPressed();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(R.anim.end_ocr_scan_enter,shadowActivity.getPendingTransitionEnterAnimationResourceId());
    }

    @Test
    public void pressingBackButton_willStartExitAnimation(){
        activity.onBackPressed();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(R.anim.end_ocr_scan_exit,shadowActivity.getPendingTransitionExitAnimationResourceId());
    }

}