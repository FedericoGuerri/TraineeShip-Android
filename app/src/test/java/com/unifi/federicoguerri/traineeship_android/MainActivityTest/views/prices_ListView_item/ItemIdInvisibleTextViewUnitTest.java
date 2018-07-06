package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ItemIdInvisibleTextViewUnitTest extends AbstractPricesListViewWithItemLayout {

    private TextView itemIdTextView;

    @Override
    public View getTestingComponent() {
        itemIdTextView=layoutResourceView.findViewById(R.id.itemIdTextViewPricesListView);
        return itemIdTextView;
    }

    @Test
    public void itemIdInvisibleTextView_hasGoneVisibility(){
        assertEquals(View.GONE,itemIdTextView.getVisibility());
    }

    @Test
    public void itemIdInvisibleTextView_isShowingTextFromResources(){
        assertEquals(layoutResourceView.getResources().getString(R.string.itemid_textview_pricelistview_defaultvalue),itemIdTextView.getText());
    }


    @Test
    public void itemIdInvisibleTextView_widthWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,itemIdTextView.getLayoutParams().width);
    }

    @Test
    public void itemIdInvisibleTextView_heightWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,itemIdTextView.getLayoutParams().height);
    }


    @Test
    public void itemIdInvisibleTextView_childOfItemLayoutPricesListView(){
        assertEquals(R.id.itemLayoutPricesListView,((View)itemIdTextView.getParent()).getId());
    }


}
