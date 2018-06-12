package com.unifi.federicoguerri.traineeship_android.SplashActivityTest.views;

import android.os.Build;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.SplashScreenActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class SplashBackgroundViewUnitTest {

    private RelativeLayout splashBackground;

    @Before
    public void setUp() {
        SplashScreenActivity activity = Robolectric.buildActivity( SplashScreenActivity.class ).create().get();
        splashBackground=activity.findViewById(R.id.splashBackgroundView);
    }

    @Test
    public void testingComponent_isNotNull(){
        assertNotNull(splashBackground);
    }
    
    @Test
    public void splashBackground_hasMatchParentWidth(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,splashBackground.getLayoutParams().width);
    }

    @Test
    public void splashBackground_hasMatchParentHeight(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,splashBackground.getLayoutParams().height);
    }




}
