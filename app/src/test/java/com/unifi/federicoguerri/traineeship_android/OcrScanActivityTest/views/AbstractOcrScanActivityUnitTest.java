package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;

import android.os.Build;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.CustomShadows.ShadowTextRecognizer;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1, shadows = {ShadowTextRecognizer.class})
@RunWith(RobolectricTestRunner.class)

public abstract class AbstractOcrScanActivityUnitTest {

    protected OcrScanActivity activity;
    public abstract View getTestingComponent();
    private View testingComponent;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( OcrScanActivity.class ).create().visible().get();
        testingComponent=getTestingComponent();
    }

    @Test
    public void testingComponent_isNotNull(){
        assertNotNull(testingComponent);
    }



}
