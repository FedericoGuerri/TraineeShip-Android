package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;
import com.unifi.federicoguerri.traineeship_android.helpers.GetResourcesHelper;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class OcrScanActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<OcrScanActivity> ocrScanActivityRule = new ActivityTestRule<>(OcrScanActivity.class);
    @Rule public GrantPermissionRule runtimePermissionCamera = GrantPermissionRule .grant(Manifest.permission.CAMERA);
    @Rule public GrantPermissionRule runtimePermissionStorange = GrantPermissionRule .grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);


    @Test
    public void fabSavePrice_hasBackgroundTintListColorTest(){
        onView(withId(R.id.fabSaveCurrentPrice)).check(matches(GetResourcesHelper.withBackgroundTintListColorId(R.color.colorAccent)));
    }

    @Test
    public void fabSavePrice_hasDefaultDrawable(){
        CustomMatchers customMatchers=new CustomMatchers();
        onView(withId(R.id.fabSaveCurrentPrice)).check(matches(customMatchers.withDrawable(R.drawable.ic_format_text)));
    }


    @Test
    public void fabSavePrice_willChangeDrawableWhileTakingMiniature(){
        onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        onView(withText("YES")).perform(click());
        CustomMatchers customMatchers=new CustomMatchers();
        onView(withId(R.id.fabSaveCurrentPrice)).check(matches(customMatchers.withDrawable(R.drawable.ic_camera)));
    }


    @Test
    public void supportActionBar_isInvisibleTest(){
        assertEquals(false, ocrScanActivityRule.getActivity().getSupportActionBar().isShowing());
    }


}
