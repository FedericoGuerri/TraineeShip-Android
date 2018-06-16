package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.view.View;

import com.unifi.federicoguerri.traineeship_android.R;


public class ItemLayoutUnitTest extends AbstractPricesListViewWithItemLayout {

    @Override
    public View getTestingComponent() {
        return  layoutResourceView.findViewById(R.id.itemLayoutPricesListView);
    }

}
