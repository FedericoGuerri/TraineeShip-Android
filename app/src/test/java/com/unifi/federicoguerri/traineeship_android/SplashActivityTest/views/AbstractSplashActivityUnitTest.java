package com.unifi.federicoguerri.traineeship_android.SplashActivityTest.views;


import android.os.Build;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.SplashScreenActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)

public abstract class AbstractSplashActivityUnitTest {

    protected SplashScreenActivity activity;
    public abstract View getTestingComponent();
    private View testingComponent;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity( SplashScreenActivity.class ).create().get();
        testingComponent=getTestingComponent();
    }

    @Test
    public void testingComponent_isNotNull() throws Exception {
        assertNotNull(testingComponent);
    }

}