package com.unifi.federicoguerri.traineeship_android.core.PricesListSettingUp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.PricesListAdapter;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.PricesListDataSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class PricesListAdapterUnitTest {

    private PricesListAdapter pricesListAdapter;

    private MainActivity activity;
    private View convertView;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( MainActivity.class ).create().start().get();
        convertView=new View(activity.getApplicationContext());
    }



    @Test(expected = IndexOutOfBoundsException.class)
    public void customAdapterGetView_withNoRecordsThrowsException(){
        pricesListAdapter =new PricesListAdapter(new ArrayList<PricesListDataSet>(),activity.getApplicationContext());
        pricesListAdapter.getView(0,convertView,null);
    }


    @Test
    public void customAdapterGetView_withNullViewReturnsANewView(){
        ArrayList<PricesListDataSet> records= new ArrayList<>();
        records.add(new PricesListDataSet(22.2f,null,0));
        pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        assertFalse(convertView== pricesListAdapter.getView(0,null,null));
    }

    @Test
    public void customAdapterGetView_withNullViewReturnsANewView_withNotNullMiniature(){
        ArrayList<PricesListDataSet> records= new ArrayList<>();
        Bitmap miniature = BitmapFactory.decodeResource(activity.getApplicationContext().getResources(), R.drawable.no_miniature_placeholder);
        records.add(new PricesListDataSet(22.2f,miniature,0));
        pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        assertFalse(convertView == pricesListAdapter.getView(0,null,null));
    }

    @Test
    public void customAdapter_getTotalwillReturnAPriceValue(){
        ArrayList<PricesListDataSet> records= new ArrayList<>();
        records.add(new PricesListDataSet(22.2f,null,0));
        pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        assertEquals(22.2f, pricesListAdapter.getTotal());
    }

    @Test
    public void customAdapter_getTotalwillReturnASumOfRecognizedPrices(){
        ArrayList<PricesListDataSet> records= new ArrayList<>();
        records.add(new PricesListDataSet(22.2f,null,0));
        records.add(new PricesListDataSet(22.2f,null,0));
        records.add(new PricesListDataSet(0.0f,null,0));
        pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        assertEquals(44.4f, pricesListAdapter.getTotal());
    }




}
