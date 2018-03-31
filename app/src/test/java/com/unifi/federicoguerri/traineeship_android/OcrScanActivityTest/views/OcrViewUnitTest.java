package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;

import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.unifi.federicoguerri.traineeship_android.R;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OcrViewUnitTest extends AbstractOcrScanActivityUnitTest {

    private SurfaceView ocrView;

    @Override
    public View getTestingComponent() {
        ocrView=activity.findViewById(R.id.ocrViewOcrScanActivity);
        return ocrView;
    }

    @Test
    public void ocrView_isVisible() {
        assertEquals(View.VISIBLE,ocrView.getVisibility());
    }

    @Test
    public void ocrView_isNotClickable()  {
        assertEquals(false,ocrView.isClickable());
    }

    @Test
    public void ocrView_widthIsMatchParent() {
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,ocrView.getLayoutParams().width);
    }

    @Test
    public void ocrView_heightIsMatchParent()  {
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,ocrView.getLayoutParams().height);
    }

    @Test
    public void ocrView_childOfOcrParentLayout(){
        Assert.assertEquals(R.id.ocrParentLayoutOcrScanActivity,((View)ocrView.getParent()).getId());
    }


}
