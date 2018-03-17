package com.unifi.federicoguerri.traineeship_android.SplashActivityTest.views;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

public class BackgroundViewUnitTest extends SplashActivityUnitTest {

    private View backgroundView;

    @Override
    public void getTestingComponent() {
        backgroundView = activity.findViewById(R.id.splashBackgroundView);
    }

    @Test
    public void backgroundView_isNotNull() throws Exception {
        assertNotNull(backgroundView);
    }

    @Test
    public void backgroundView_isVisible() throws Exception {
        assertEquals(View.VISIBLE,backgroundView.getVisibility());
    }

    @Test
    public void backgroundView_hasBackgroundColorFromResources() throws Exception {
        int backgroundViewColor=getColorFromResources(R.color.splash_background_color);
        assertEquals(backgroundViewColor,((ColorDrawable)backgroundView.getBackground()).getColor());
    }

    @Test
    public void backgroundView_isClickable() throws Exception {
        assertEquals(true,backgroundView.isClickable());
    }

    @Test
    public void backgroundView_widthIsMatchParent() throws Exception {
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,backgroundView.getLayoutParams().width);
    }

    @Test
    public void backgroundView_heightIsMatchParent() throws Exception {
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,backgroundView.getLayoutParams().height);
    }

    @Test
    public void backgroundView_launchMainActivityWithIntentByTapping() throws Exception {
        backgroundView.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("MainActivity",shadowIntent.getIntentClass().getSimpleName());
    }

    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }
}
