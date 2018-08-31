package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.PricesListAdapter;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.PricesListDataSet;

import org.junit.Test;
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
        PricesListAdapter pricesListAdapter =new PricesListAdapter(new ArrayList<PricesListDataSet>(),pricesList.getContext());
        pricesList.setAdapter(pricesListAdapter);
        assertTrue(pricesList.getAdapter()== pricesListAdapter);
    }

    @Test
    public void pricesList_hasBackgroundColorFromResources() {
        int pricesListViewColor=getColorFromResources(R.color.transparent_background_color);
        assertEquals(pricesListViewColor,((ColorDrawable)pricesList.getBackground()).getColor());
    }

    @Test
    public void pricesList_customAdapterHasNoRecords(){
        PricesListAdapter pricesListAdapter =new PricesListAdapter(new ArrayList<PricesListDataSet>(),activity.getApplicationContext());
        pricesList.setAdapter(pricesListAdapter);
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(-1,shadowListView.findIndexOfItemContainingText("22.2"));
    }


    @Test
    public void pricesList_customAdapterHasOneRecord(){
        ArrayList<PricesListDataSet> records=new ArrayList<>();
        records.add(new PricesListDataSet(22.2f,null,0));
        PricesListAdapter pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        pricesList.setAdapter(pricesListAdapter);
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(0,shadowListView.findIndexOfItemContainingText("22.2"));
    }

    @Test
    public void pricesList_customAdapterCanRemoveARecord(){
        ArrayList<PricesListDataSet> records=new ArrayList<>();
        records.add(new PricesListDataSet(22.2f,null,0));
        PricesListAdapter pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        pricesList.setAdapter(pricesListAdapter);
        records.remove(0);
        pricesListAdapter.notifyDataSetChanged();
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(-1,shadowListView.findIndexOfItemContainingText("22.2"));
    }

    @Test
    public void pricesList_customAdapterCanRemoveMoreRecords(){
        ArrayList<PricesListDataSet> records=new ArrayList<>();
        records.add(new PricesListDataSet(22.2f,null,0));
        records.add(new PricesListDataSet(0.0f,null,1));
        records.add(new PricesListDataSet(22.2f,null,2));
        PricesListAdapter pricesListAdapter =new PricesListAdapter(records,activity.getApplicationContext());
        pricesList.setAdapter(pricesListAdapter);
        records.remove(1);
        records.remove(0);
        pricesListAdapter.notifyDataSetChanged();
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(0,shadowListView.findIndexOfItemContainingText("22.2"));
    }


    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

}
