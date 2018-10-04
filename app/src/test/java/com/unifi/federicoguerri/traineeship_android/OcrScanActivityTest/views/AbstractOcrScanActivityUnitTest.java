package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;

import android.os.Build;
import android.view.View;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabasePrice;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseReactiveAndroid;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
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
        ReActiveAndroid.init(new ReActiveConfig.Builder(RuntimeEnvironment.application)
                .addDatabaseConfigs(new DatabaseConfig.Builder(DatabaseReactiveAndroid.class).addModelClasses(DatabasePrice.class)
                        .build())
                .build());
        testingComponent=getTestingComponent();
    }

    @After
    public void tearDown(){
        ReActiveAndroid.destroy();
    }

    @Test
    public void testingComponent_isNotNull(){
        assertNotNull(testingComponent);
    }



}
