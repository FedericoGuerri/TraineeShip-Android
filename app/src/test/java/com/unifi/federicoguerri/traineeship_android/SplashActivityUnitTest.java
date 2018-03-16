package com.unifi.federicoguerri.traineeship_android;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)

public class SplashActivityUnitTest {

    private SplashScreenActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity( SplashScreenActivity.class )
                .create()
                .get();
    }


    // Hint TextView

    @Test
    public void splashTextView_isNotNull() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        assertNotNull(splashTextView);
    }

    @Test
    public void splashTextView_isVisible() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        assertEquals(View.VISIBLE,splashTextView.getVisibility());
    }

    @Test
    public void splashTextView_isNotClickable() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        assertEquals(false,splashTextView.isClickable());
    }

    @Test
    public void splashTextView_hasTextFromResources() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        String textFromResources=activity.getResources().getString(R.string.taptostart_text_view);
        assertEquals(textFromResources,splashTextView.getText());
    }

    @Test
    public void splashTextView_childOfBackgroundView(){
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        assertEquals(R.id.splashBackgroundView,((View)splashTextView.getParent()).getId());
    }

    @Test
    public void splashTextView_widthIsWrapContent() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,splashTextView.getLayoutParams().width);
    }

    @Test
    public void splashTextView_heightIsWrapContent() throws Exception {
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,splashTextView.getLayoutParams().height);
    }

    @Test
    public void splashTextView_hasTextColorFromResources(){
        TextView splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
        int whiteTextColor = getColorFromResources(R.color.whiteTextColor);
        assertEquals(whiteTextColor,splashTextView.getCurrentTextColor());
    }

    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

    // Background View

    @Test
    public void backgroundView_isNotNull() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        assertNotNull(backgroundView);
    }

    @Test
    public void backgroundView_isVisible() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        assertEquals(View.VISIBLE,backgroundView.getVisibility());
    }

    @Test
    public void backgroundView_hasBackgroundColorFromResources() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        int backgroundViewColor=getColorFromResources(R.color.splash_background_color);
        assertEquals(backgroundViewColor,((ColorDrawable)backgroundView.getBackground()).getColor());
    }

    @Test
    public void backgroundView_isClickable() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        assertEquals(true,backgroundView.isClickable());
    }

    @Test
    public void backgroundView_widthIsMatchParent() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,backgroundView.getLayoutParams().width);
    }

    @Test
    public void backgroundView_heightIsMatchParent() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,backgroundView.getLayoutParams().height);
    }

    @Test
    public void backgroundView_launchMainActivityWithIntentByTapping() throws Exception {
        View backgroundView = activity.findViewById(R.id.splashBackgroundView);
        backgroundView.performClick();
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("MainActivity",shadowIntent.getIntentClass().getSimpleName());
    }


    // App Logo ImageView

    @Test
    public void appLogoImageView_isNotNull() throws Exception {
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertNotNull(appLogoImageView);
    }

    @Test
    public void appLogoImageView_isVisible() throws Exception {
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertEquals(View.VISIBLE,appLogoImageView.getVisibility());
    }

    @Test
    public void appLogoImageView_isNotClickable() throws Exception {
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertEquals(false,appLogoImageView.isClickable());
    }

    @Test
    public void appLogoImageView_childOfBackgroundView(){
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertEquals(R.id.splashBackgroundView,((View)appLogoImageView.getParent()).getId());
    }

    @Test
    public void appLogoImageView_widthIsWrapContent() throws Exception {
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,appLogoImageView.getLayoutParams().width);
    }

    @Test
    public void appLogoImageView_heightIsWrapContent() throws Exception {
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,appLogoImageView.getLayoutParams().height);
    }

    @Test
    public void appLogoImageView_drawableIsnotNull() throws Exception {
        ImageView appLogoImageView = activity.findViewById(R.id.splashImageViewLogo);
        assertNotNull(appLogoImageView.getDrawable());
    }


    // General tests

    @Test
    public void pressingBackButton_willFinishTheApp(){
        activity.onBackPressed();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertTrue(shadowActivity.isFinishing());
    }


}