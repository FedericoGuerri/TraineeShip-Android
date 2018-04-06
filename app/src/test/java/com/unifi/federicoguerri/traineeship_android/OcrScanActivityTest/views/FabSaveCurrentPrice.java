package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

public class FabSaveCurrentPrice extends AbstractOcrScanActivityUnitTest{

    private FloatingActionButton fabSavePrice;

    @Override
    public View getTestingComponent() {
        fabSavePrice =activity.findViewById(R.id.fabSaveCurrentPrice);
        return fabSavePrice;
    }

    @Test
    public void fabSavePrice_childOfOcrParentLayout(){
        assertEquals(R.id.ocrParentLayoutOcrScanActivity,((View) fabSavePrice.getParent()).getId());
    }

    @Test
    public void fabSavePrice_isVisible() {
        assertEquals(View.VISIBLE, fabSavePrice.getVisibility());
    }

    @Test
    public void fabNewOcr_widthIsWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT, fabSavePrice.getLayoutParams().width);
    }

    @Test
    public void fabSavePrice_heightIsWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT, fabSavePrice.getLayoutParams().height);
    }

    @Test
    public void fabSavePrice_drawableIsnotNull() {
        assertNotNull(fabSavePrice.getDrawable());
    }

    @Test
    public void fabSavePrice_hasBackgroundTintFromResources(){
        assertEquals(getColorFromResources(R.color.colorAccent), fabSavePrice.getBackgroundTintList().getDefaultColor());
    }

    @Test
    public void fabSavePrice_hasNormalSize(){ //fabSize=Normal has value=0
        assertEquals(0, fabSavePrice.getSize());
    }

    @Test
    public void fabSavePrice_hasZeroElevation(){
        assertEquals(0,(int)fabSavePrice.getCompatElevation());
    }

    @Test
    public void fabSavePrice_isClickable() {
        assertEquals(true, fabSavePrice.isClickable());
    }

    @Test
    public void fabSavePrice_willEndOcrScanActivity()  {
        fabSavePrice.performClick();
        assertTrue(activity.isFinishing());
    }

    @Test
    public void fabSavePrice_willEndOcrScanActivityWithEnterTransition()  {
        fabSavePrice.performClick();
        ShadowActivity shadowActivity=shadowOf(activity);
        assertEquals(R.anim.end_ocr_scan_enter,shadowActivity.getPendingTransitionEnterAnimationResourceId());
    }

    @Test
    public void fabSavePrice_willEndOcrScanActivityWithExitTransition()  {
        fabSavePrice.performClick();
        ShadowActivity shadowActivity=shadowOf(activity);
        assertEquals(R.anim.end_ocr_scan_exit,shadowActivity.getPendingTransitionExitAnimationResourceId());
    }


    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

}
