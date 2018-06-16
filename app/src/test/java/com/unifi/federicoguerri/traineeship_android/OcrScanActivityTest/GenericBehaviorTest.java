package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest;


import android.Manifest;
import android.content.pm.PackageManager;
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
import static org.junit.Assert.assertNull;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)
public class GenericBehaviorTest {

    private OcrScanActivity activity;

    @Before
    public void setUp() {
        activity=Robolectric.setupActivity(OcrScanActivity.class);
    }

    @Test
    public void actionBar_isNotThere() {
        assertNull(activity.getSupportActionBar());
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

    @Test
    public void test(){
        activity.onRequestPermissionsResult(10400,new String[]{Manifest.permission.CAMERA},new int[] {PackageManager.PERMISSION_GRANTED});
    }

}