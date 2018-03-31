package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class OcrScanActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<OcrScanActivity> ocrScanActivityRule = new ActivityTestRule<>(OcrScanActivity.class);
    @Rule public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule .grant(Manifest.permission.CAMERA);

    // parent layout

    @Test
    public void parentLayout_isVisibleTest(){
        onView(withId(R.id.ocrParentLayoutOcrScanActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void parentLayout_hasBackgroundColorTest(){
        onView(withId(R.id.ocrParentLayoutOcrScanActivity)).check(matches(GetResourcesHelper.withBackgroundColorId(R.color.transparent_background_color)));
    }

    @Test
    public void parentLayout_hasTwoChildrenTest(){
        onView(withId(R.id.ocrParentLayoutOcrScanActivity)).check(matches(hasChildCount(2)));
    }


    // recognized Textview

    @Test
    public void recognizedTextView_isVisible(){
        onView(withId(R.id.recognizedTextViewOcrScanActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void recognizedTextView_isShowingTextFromResources(){
        onView(withId(R.id.recognizedTextViewOcrScanActivity)).check(matches(withText(R.string.recognition_textView_defaultvalue_ocrScanActivity)));
    }

    @Test
    public void hintTextView_hasColorAccentTextColor(){
        onView(withId(R.id.recognizedTextViewOcrScanActivity)).check(matches(hasTextColor(R.color.colorAccent)));
    }


    // ocr view
    @Test
    public void ocrView_isVisible(){
        onView(withId(R.id.ocrViewOcrScanActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    // General tests

    @Test
    public void supportActionBar_isInvisibleTest(){
        assertEquals(false, ocrScanActivityRule.getActivity().getSupportActionBar().isShowing());
    }

}
