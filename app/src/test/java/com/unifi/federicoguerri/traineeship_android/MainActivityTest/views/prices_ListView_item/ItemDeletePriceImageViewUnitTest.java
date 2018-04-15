package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ItemDeletePriceImageViewUnitTest extends AbstractPricesListViewWithItemLayout{
    private ImageView deletePriceImageView;

    @Override
    public View getTestingComponent() {
        deletePriceImageView =layoutResourceView.findViewById(R.id.itemDeletePriceImageViewitemPriceListView);
        return deletePriceImageView;
    }

    @Test
    public void deletePriceImageView_isVisible() {
        assertEquals(View.VISIBLE, deletePriceImageView.getVisibility());
    }

    @Test
    public void deletePriceImageView_isClickable()  {
        assertEquals(true, deletePriceImageView.isClickable());
    }

    @Test
    public void deletePriceImageView_widthIsWrapContent() {
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT, deletePriceImageView.getLayoutParams().width);
    }

    @Test
    public void deletePriceImageView_heightIsWrapContent()  {
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT, deletePriceImageView.getLayoutParams().height);
    }

    @Test
    public void deletePriceImageView_drawableIsNotNull()  {
        assertNotNull(deletePriceImageView.getDrawable());
    }

    @Test
    public void deletePriceImageView_childOfItemLayout(){
        assertEquals(R.id.itemLayoutPricesListView,((View) deletePriceImageView.getParent()).getId());
    }

}
