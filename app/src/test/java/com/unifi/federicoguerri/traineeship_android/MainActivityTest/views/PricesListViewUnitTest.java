package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.CustomAdapter;
import com.unifi.federicoguerri.traineeship_android.core.CustomDataSet;

import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowListView;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class PricesListViewUnitTest extends AbstractMainActivityUnitTest {

    private ListView pricesList;

    @Override
    public View getTestingComponent() {
        pricesList=activity.findViewById(R.id.pricesListViewMainActivity);
        return pricesList;
    }

    @Test
    public void pricesList_widthMatchParent(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,pricesList.getLayoutParams().width);
    }

    @Test
    public void pricesList_heightMatchParent(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,pricesList.getLayoutParams().height);
    }

    @Test
    public void pricesList_canUseCustomAdapter(){
        CustomAdapter customAdapter=new CustomAdapter(new ArrayList<CustomDataSet>(),pricesList.getContext(),null);
        pricesList.setAdapter(customAdapter);
        assertTrue(pricesList.getAdapter()==customAdapter);
    }

    @Test
    public void pricesList_hasBackgroundColorFromResources() {
        int pricesListViewColor=getColorFromResources(R.color.transparent_background_color);
        assertEquals(pricesListViewColor,((ColorDrawable)pricesList.getBackground()).getColor());
    }

    @Test
    public void pricesList_customAdapterHasNoRecords(){
        CustomAdapter customAdapter=new CustomAdapter(new ArrayList<CustomDataSet>(),activity.getApplicationContext(),null);
        pricesList.setAdapter(customAdapter);
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(-1,shadowListView.findIndexOfItemContainingText("22.2"));
    }


    @Test
    public void pricesList_customAdapterHasOneRecord(){
        ArrayList<CustomDataSet> records=new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        CustomAdapter customAdapter=new CustomAdapter(records,activity.getApplicationContext(),null);
        pricesList.setAdapter(customAdapter);
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(0,shadowListView.findIndexOfItemContainingText("22.2"));
    }

    @Test
    public void pricesList_customAdapterCanRemoveARecord(){
        ArrayList<CustomDataSet> records=new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        CustomAdapter customAdapter=new CustomAdapter(records,activity.getApplicationContext(),null);
        pricesList.setAdapter(customAdapter);
        records.remove(0);
        customAdapter.notifyDataSetChanged();
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(-1,shadowListView.findIndexOfItemContainingText("22.2"));
    }

    @Test
    public void pricesList_customAdapterCanRemoveMoreRecords(){
        ArrayList<CustomDataSet> records=new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        records.add(new CustomDataSet(0.0f,null));
        records.add(new CustomDataSet(22.2f,null));
        CustomAdapter customAdapter=new CustomAdapter(records,activity.getApplicationContext(),null);
        pricesList.setAdapter(customAdapter);
        records.remove(1);
        records.remove(0);
        customAdapter.notifyDataSetChanged();
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(0,shadowListView.findIndexOfItemContainingText("22.2"));
    }


    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

}
