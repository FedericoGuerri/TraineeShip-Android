package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.os.Build;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)

public abstract class AbstractMainActivityUnitTest {

    protected MainActivity activity;
    public abstract View getTestingComponent();
    private View testingComponent;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( MainActivity.class ).create().visible().get();
        testingComponent=getTestingComponent();
    }

    @Test
    public void testingComponent_isNotNull(){
        assertNotNull(testingComponent);
    }

}
