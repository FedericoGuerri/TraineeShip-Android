package com.unifi.federicoguerri.traineeship_android.core;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.CustomAdapter;
import com.unifi.federicoguerri.traineeship_android.CustomDataSet;
import com.unifi.federicoguerri.traineeship_android.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    private CustomAdapter customAdapter;
    private MainActivity activity;
    private View convertView;
    private CustomAdapter.ViewHolder viewHolder;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity( MainActivity.class ).create().start().get();
        convertView=new View(activity.getApplicationContext());
        viewHolder=new CustomAdapter.ViewHolder();
        viewHolder.priceTextView=new TextView(activity.getApplicationContext());
        viewHolder.priceTextView.setText("22.2");
        viewHolder.miniatureImageView=new ImageView(activity.getApplicationContext());
        viewHolder.deleteImageView=new ImageView(activity.getApplicationContext());
        convertView.setTag(viewHolder);
    }


    @Test
    public void viewHolderPriceTextView_hasCorrectText(){
        assertEquals("22.2",viewHolder.priceTextView.getText().toString());
    }


    @Test
    public void viewHolderPriceTextView_hasCorrectTextCastedToFloat(){
        assertEquals(22.2f,Float.valueOf(viewHolder.priceTextView.getText().toString()));
    }

    @Test
    public void viewHolderMiniatureImageView_hasNullDrawable(){
        assertNull(viewHolder.miniatureImageView.getDrawable());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void customAdapterGetView_withNoRecordsThrowsException(){
        customAdapter=new CustomAdapter(new ArrayList<CustomDataSet>(),activity.getApplicationContext(),null);
        customAdapter.getView(0,convertView,null);
    }


    @Test
    public void customAdapterGetView_withNullViewReturnsANewView(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext(),null);
        assertFalse(convertView==customAdapter.getView(0,null,null));
    }

    @Test
    public void customAdapter_getTotalwillReturnAPriceValue(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext(),null);
        assertEquals(22.2f,customAdapter.getTotal());
    }

    @Test
    public void customAdapter_getTotalwillReturnASumOfRecognizedPrices(){
        ArrayList<CustomDataSet> records= new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        records.add(new CustomDataSet(22.2f,null));
        records.add(new CustomDataSet(0.0f,null));
        customAdapter=new CustomAdapter(records,activity.getApplicationContext(),null);
        assertEquals(44.4f,customAdapter.getTotal());
    }

}
