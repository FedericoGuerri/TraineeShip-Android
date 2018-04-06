package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;


public class FabNewOcrScanUnitTest extends AbstractMainActivityUnitTest {

    private FloatingActionButton fabNewOcr;

    @Override
    public View getTestingComponent() {
        fabNewOcr=activity.findViewById(R.id.fabNewOcrMainActivity);
        return fabNewOcr;
    }

    @Test
    public void fabNewOcr_isVisible() {
        assertEquals(View.VISIBLE,fabNewOcr.getVisibility());
    }

    @Test
    public void fabNewOcr_isClickable() {
        assertEquals(true,fabNewOcr.isClickable());
    }

    @Test
    public void fabNewOcr_widthIsWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,fabNewOcr.getLayoutParams().width);
    }

    @Test
    public void fabNewOcr_heightIsWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,fabNewOcr.getLayoutParams().height);
    }

    @Test
    public void fabNewOcr_drawableIsnotNull() {
        assertNotNull(fabNewOcr.getDrawable());
    }

    @Test
    public void fabNewOcr_childOfParentLayout(){
        assertEquals(R.id.parentLayoutMainActivity,((View)fabNewOcr.getParent()).getId());
    }

    @Test
    public void fabNewOcr_hasBackgroundTintFromResources(){
        assertEquals(getColorFromResources(R.color.colorAccent),fabNewOcr.getBackgroundTintList().getDefaultColor());
    }

    @Test
    public void fabNewOcr_hasNormalSize(){ //fabSize=Normal has value=0
        assertEquals(0,fabNewOcr.getSize());
    }

    @Test
    public void fabNewOcr_hasZeroElevation(){
        assertEquals(0,(int)fabNewOcr.getCompatElevation());
    }

    @Test
    public void fabNewOcr_launchOcrScanActivityWithIntent()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("OcrScanActivity",shadowIntent.getIntentClass().getSimpleName());
    }

    @Test
    public void fabNewOcr_givesOcrScanActivityFileNameWithIntent()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertTrue(shadowActivity.getNextStartedActivity().hasExtra("fileName"));
    }

    @Test
    public void fabNewOcr_givesOcrScanActivityANotEmptyFileName()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertFalse(shadowActivity.getNextStartedActivity().getStringExtra("fileName").isEmpty());
    }

    @Test
    public void fabNewOcr_givesOcrScanActivityATxtFileName()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        String fileName=shadowActivity.getNextStartedActivity().getStringExtra("fileName");
        assertEquals(".txt",fileName.substring(fileName.lastIndexOf("."),fileName.length()));
    }

    @Test
    public void fabNewOcr_givesOcrScanActivityADataFormattedFileName() throws ParseException {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        String fileName=shadowActivity.getNextStartedActivity().getStringExtra("fileName");
        fileName=fileName.substring(fileName.lastIndexOf("/")+1,fileName.lastIndexOf("."));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        dateFormat.setLenient(false);
        dateFormat.parse(fileName);
    }

    @Test
    public void fabNewOcr_startsOcrScanActivityWithEnterAnimation()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(R.anim.new_ocr_scan_enter,shadowActivity.getPendingTransitionEnterAnimationResourceId());
    }

    @Test
    public void fabNewOcr_startsOcrScanActivityWithExitAnimation()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(R.anim.new_ocr_scan_exit,shadowActivity.getPendingTransitionExitAnimationResourceId());
    }

    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }


}
