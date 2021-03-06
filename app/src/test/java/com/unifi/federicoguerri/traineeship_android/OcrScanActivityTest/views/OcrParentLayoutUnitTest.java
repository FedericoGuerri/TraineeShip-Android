package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;


import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.unifi.federicoguerri.traineeship_android.R;

import junit.framework.Assert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OcrParentLayoutUnitTest extends AbstractOcrScanActivityUnitTest{

    private RelativeLayout ocrParentLayout;

    @Override
    public View getTestingComponent() {
        ocrParentLayout=activity.findViewById(R.id.ocrParentLayoutOcrScanActivity);
        return ocrParentLayout;
    }

    @Test
    public void ocrParentLayout_isVisible(){
        Assert.assertEquals(View.VISIBLE,ocrParentLayout.getVisibility());
    }


    @Test
    public void ocrParentLayout_hasMatchParentWidth(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,ocrParentLayout.getLayoutParams().width);
    }

    @Test
    public void ocrParentLayout_hasMatchParentHeight(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,ocrParentLayout.getLayoutParams().height);
    }


}
