package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest;


import android.os.Build;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

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

}