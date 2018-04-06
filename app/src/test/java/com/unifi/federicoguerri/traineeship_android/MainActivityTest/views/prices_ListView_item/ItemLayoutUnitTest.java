package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.view.View;
import android.widget.LinearLayout;

import com.unifi.federicoguerri.traineeship_android.R;


public class ItemLayoutUnitTest extends AbstractPricesListViewWithItemLayout {

    private LinearLayout itemLayout;

    @Override
    public View getTestingComponent() {
        itemLayout =  layoutResourceView.findViewById(R.id.itemLayoutPricesListView);
        return itemLayout;
    }

}
