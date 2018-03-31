package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

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

    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }


}
