package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;

import android.os.Build;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;
import com.unifi.federicoguerri.traineeship_android.core.AppDatabaseInitializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)

public abstract class AbstractOcrScanActivityUnitTest {

    OcrScanActivity activity;
    protected abstract View getTestingComponent();
    private View testingComponent;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( OcrScanActivity.class ).create().visible().get();
       AppDatabaseInitializer.initDatabase();
        testingComponent=getTestingComponent();
    }

    @After
    public void tearDown(){
        AppDatabaseInitializer.destroyDatabase();
    }

    @Test
    public void testingComponent_isNotNull(){
        assertNotNull(testingComponent);
    }



}
