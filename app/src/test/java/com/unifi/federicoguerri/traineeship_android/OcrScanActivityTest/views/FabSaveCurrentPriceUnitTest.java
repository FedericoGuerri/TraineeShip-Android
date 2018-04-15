package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;


import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

public class FabSaveCurrentPriceUnitTest extends AbstractOcrScanActivityUnitTest{

    private FloatingActionButton fabSavePrice;
    private ShadowApplication shadowApplication;

    @Override
    public View getTestingComponent() {
        fabSavePrice =activity.findViewById(R.id.fabSaveCurrentPrice);

        shadowApplication=shadowOf(activity.getApplication());
        shadowApplication.grantPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);

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
    public void fabSavePrice_willEndOcrScanActivity(){
        fabSavePrice.performClick();
        assertTrue(activity.isFinishing());
    }

    @Test
    public void fabSavePrice_willShowToastMessage_ifCantWriteToFile(){
        fabSavePrice.performClick();
        assertEquals(getStringFromResources(R.string.cant_write_to_file),ShadowToast.getTextOfLatestToast());
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

    @Test
    public void fabSavePrice_showsToastIfNoPricesWereRecognized()  {
        TextView recognizedTextView=activity.findViewById(R.id.recognizedTextViewOcrScanActivity);
        recognizedTextView.setText(activity.getResources().getString(R.string.bad_recognition_get_closer_please));
        fabSavePrice.performClick();
        assertEquals(activity.getResources().getString(R.string.no_price_detected_toast),ShadowToast.getTextOfLatestToast());
    }

    private String getStringFromResources(int stringId) {
        return activity.getResources().getString(stringId);
    }

    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

}
