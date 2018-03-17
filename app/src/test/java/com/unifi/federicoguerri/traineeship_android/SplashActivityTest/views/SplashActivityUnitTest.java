package com.unifi.federicoguerri.traineeship_android.SplashActivityTest.views;


import android.os.Build;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.SplashScreenActivity;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)

public abstract class SplashActivityUnitTest {

    protected SplashScreenActivity activity;
    public abstract void getTestingComponent();

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity( SplashScreenActivity.class ).create().get();
        getTestingComponent();
    }

}