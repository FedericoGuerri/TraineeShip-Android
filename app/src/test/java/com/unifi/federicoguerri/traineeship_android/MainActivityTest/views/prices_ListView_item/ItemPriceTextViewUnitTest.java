package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class ItemPriceTextViewUnitTest extends AbstractPricesListViewWithItemLayout {

    private TextView itemPriceTextView;

    @Override
    public View getTestingComponent() {
        itemPriceTextView=layoutResourceView.findViewById(R.id.itemPriceTextViewPricesListView);
        return itemPriceTextView;
    }

    @Test
    public void itemPriceTextView_isVisible(){
        assertEquals(View.VISIBLE,itemPriceTextView.getVisibility());
    }

    @Test
    public void itemPriceTextView_isShowingTextFromResources(){
        assertEquals(layoutResourceView.getResources().getString(R.string.price_textview_listview_placeholder),itemPriceTextView.getText());
    }

    @Test
    public void itemPriceTextView_hasTextColorFromResources(){
        assertEquals(layoutResourceView.getResources().getColor(R.color.price_textView_listview),itemPriceTextView.getCurrentTextColor());
    }

    @Test
    public void itemPriceTextView_widthWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,itemPriceTextView.getLayoutParams().width);
    }

    @Test
    public void itemPriceTextView_heightWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,itemPriceTextView.getLayoutParams().height);
    }


    @Test
    public void itemPriceTextView_hasTextSize14(){
        assertEquals(14,(int)itemPriceTextView.getTextSize());
    }

    @Test
    public void itemPriceTextView_childOfItemLayoutPricesListView(){
        assertEquals(R.id.itemLayoutPricesListView,((View)itemPriceTextView.getParent()).getId());
    }


}
