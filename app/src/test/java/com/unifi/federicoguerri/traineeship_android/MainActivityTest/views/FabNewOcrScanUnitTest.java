package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;


public class FabNewOcrScanUnitTest extends AbstractMainActivityUnitTest {

    private FloatingActionButton fabNewOcr;
    private ShadowApplication shadowApplication;

    @Override
    public View getTestingComponent() {
        fabNewOcr=activity.findViewById(R.id.fabNewOcrMainActivity);

        shadowApplication=shadowOf(activity.getApplication());
        shadowApplication.grantPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);

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
        assertTrue(shadowActivity.getNextStartedActivity().hasExtra("nextIndex"));
    }


    @Test
    public void fabNewOcr_givesOcrScanActivityAnId()  {
        fabNewOcr.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        int fileName=shadowActivity.getNextStartedActivity().getIntExtra("nextIndex",0);
        assertEquals(0,fileName);
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


    @Test
    public void fabNewOcr_willSave_IdCount_inSharedPreferences_onFabClick(){
        fabNewOcr.performClick();
        SharedPreferences sharedPreferences = activity.getApplication().getSharedPreferences("myConfiguration", Context.MODE_PRIVATE);
        assertTrue(sharedPreferences.contains("id_price_count"));
    }

    @Test
    public void fabNewOcr_willIncrease_IdCount_onFabClick(){
        fabNewOcr.performClick();
        SharedPreferences sharedPreferences = activity.getApplication().getSharedPreferences("myConfiguration", Context.MODE_PRIVATE);
        int firstId=sharedPreferences.getInt("id_price_count",0);
        fabNewOcr.performClick();
        assertEquals(firstId+1,sharedPreferences.getInt("id_price_count",0));
    }


    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }


}
