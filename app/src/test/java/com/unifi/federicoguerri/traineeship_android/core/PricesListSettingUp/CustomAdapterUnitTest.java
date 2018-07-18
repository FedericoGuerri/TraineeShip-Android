package com.unifi.federicoguerri.traineeship_android.core.PricesListSettingUp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.CustomAdapter;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.CustomDataSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class CustomAdapterUnitTest {

    @InjectMocks
    private CustomAdapter customAdapter;

    private MainActivity activity;
    private View convertView;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( MainActivity.class ).create().start().get();
        convertView=new View(activity.getApplicationContext());
    }



    @Test(expected = IndexOutOfBoundsException.class)
    public void customAdapterGetView_withNoRecordsThrowsException(){
        customAdapter=new CustomAdapter(new ArrayList<CustomDataSet>(),activity.getApplicationContext());
        customAdapter.getView(0,convertView,null);
    }


    @Test
    public void customAdapterGetView_withNullViewReturnsANewView(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null,0));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext());
        assertFalse(convertView==customAdapter.getView(0,null,null));
    }

    @Test
    public void customAdapterGetView_withNullViewReturnsANewView_withNotNullMiniature(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        Bitmap miniature = BitmapFactory.decodeResource(activity.getApplicationContext().getResources(), R.drawable.no_miniature_placeholder);
        records.add(new CustomDataSet(22.2f,miniature,0));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext());
        assertFalse(convertView==customAdapter.getView(0,null,null));
    }

    @Test
    public void customAdapter_getTotalwillReturnAPriceValue(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null,0));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext());
        assertEquals(22.2f,customAdapter.getTotal());
    }

    @Test
    public void customAdapter_getTotalwillReturnASumOfRecognizedPrices(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null,0));
        records.add(new CustomDataSet(22.2f,null,0));
        records.add(new CustomDataSet(0.0f,null,0));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext());
        assertEquals(44.4f,customAdapter.getTotal());
    }




}
