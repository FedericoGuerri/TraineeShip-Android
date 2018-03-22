package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;


@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)

public abstract class AbstractPricesListViewWithItemLayout  {

    protected View layoutResourceView;
    public abstract View getTestingComponent();
    private View testingComponent;

    @Before
    public void setUp() throws Exception {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class);
        layoutResourceView =  LayoutInflater.from(activityController.get()).inflate(R.layout.item_price_listview, null);
        testingComponent=getTestingComponent();
    }

    @Test
    public void testingComponent_isNotNull(){
        assertNotNull(testingComponent);
    }

}
