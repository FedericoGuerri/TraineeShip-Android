package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views.prices_ListView_item;

import android.view.View;
import android.widget.ImageView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


public class ItemMiniatureImageViewUnitTest extends AbstractPricesListViewWithItemLayout {

    private ImageView itemMiniatureImageView;

    @Override
    public View getTestingComponent() {
        itemMiniatureImageView =layoutResourceView.findViewById(R.id.itemMiniatureImageViewItemPriceListView);
        return itemMiniatureImageView;
    }

    @Test
    public void itemMiniatureImageView_isVisible()  {
        assertEquals(View.VISIBLE, itemMiniatureImageView.getVisibility());
    }


    @Test
    public void itemMiniatureImageView_widthIsInDP()  {
        assertEquals(100, itemMiniatureImageView.getLayoutParams().width);
    }

    @Test
    public void itemMiniatureImageView_heightIsInDP()  {
        assertEquals(150, itemMiniatureImageView.getLayoutParams().height);
    }

    @Test
    public void itemMiniatureImageView_drawableIsNotNull()  {
        assertNotNull(itemMiniatureImageView.getDrawable());
    }

    @Test
    public void itemMiniatureImageView_childOfItemLayout(){
        assertEquals(R.id.itemLayoutPricesListView,((View) itemMiniatureImageView.getParent()).getId());
    }

}
