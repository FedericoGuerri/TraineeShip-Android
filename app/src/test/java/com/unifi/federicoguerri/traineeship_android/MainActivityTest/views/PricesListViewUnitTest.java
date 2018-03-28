package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.unifi.federicoguerri.traineeship_android.CustomAdapter;
import com.unifi.federicoguerri.traineeship_android.CustomDataSet;
import com.unifi.federicoguerri.traineeship_android.R;

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
        CustomAdapter customAdapter=new CustomAdapter(new ArrayList<CustomDataSet>(),pricesList.getContext());
        pricesList.setAdapter(customAdapter);
        assertTrue(pricesList.getAdapter()==customAdapter);
    }

    @Test
    public void pricesList_hasBackgroundColorFromResources() throws Exception {
        int pricesListViewColor=getColorFromResources(R.color.transparent_background_color);
        assertEquals(pricesListViewColor,((ColorDrawable)pricesList.getBackground()).getColor());
    }

    @Test
    public void pricesList_customAdapterHasNoRecords(){
        CustomAdapter customAdapter=new CustomAdapter(new ArrayList<CustomDataSet>(),activity.getApplicationContext());
        pricesList.setAdapter(customAdapter);
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(-1,shadowListView.findIndexOfItemContainingText("22.2"));
    }


    @Test
    public void pricesList_customAdapterHasOneRecord(){
        ArrayList<CustomDataSet> records=new ArrayList<>();
        records.add(new CustomDataSet(22.2f,null));
        CustomAdapter customAdapter=new CustomAdapter(records,activity.getApplicationContext());
        pricesList.setAdapter(customAdapter);
        ShadowListView shadowListView = Shadows.shadowOf(pricesList);
        shadowListView.populateItems();
        assertEquals(0,shadowListView.findIndexOfItemContainingText("22.2"));
    }


    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

}