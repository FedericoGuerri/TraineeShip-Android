package com.unifi.federicoguerri.traineeship_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;

import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;
import com.unifi.federicoguerri.traineeship_android.helpers.GetResourcesHelper;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

@MediumTest
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.unifi.federicoguerri.traineeship_android", appContext.getPackageName());
    }

    // hint TextView

    @Test
    public void hintTextView_isColorAccentTest(){
        onView(withId(R.id.hintTextViewMainActivity)).check(matches(hasTextColor(R.color.hint_textView_mainActivity)));
    }

    @Test
    public void hintTextView_isDisplayedTest(){
        onView(withText(InstrumentationRegistry.getTargetContext().getResources().getString(R.string.hint_textView_mainActivity))).check(matches(isDisplayed()));
    }

    // welcome TextView

    @Test
    public void welcomeTextView_isColorAccentTest() {
        onView(withId(R.id.welcomeTextViewMainActivity)).check(matches(hasTextColor(R.color.welcome_textView_mainActivity)));
    }

    @Test
    public void welcomeTextView_isDisplayedTest(){
        onView(withText(InstrumentationRegistry.getTargetContext().getResources().getString(R.string.welcome_textView_mainActivity))).check(matches(isDisplayed()));
    }

    // hint gallery TextView

    @Test
    public void hintGalleryTextView_isColorAccentTest() {
        onView(withId(R.id.hintGalleryTextViewMainActivity)).check(matches(hasTextColor(R.color.hint_textView_mainActivity)));
    }

    @Test
    public void hintGalleryTextView_isDisplayedTest(){
        onView(withText(InstrumentationRegistry.getTargetContext().getResources().getString(R.string.hint_gallery_textView_mainActivity))).check(matches(isDisplayed()));
    }


    // app logo Imageview

    @Test
    public void thereIsAVisibleImageViewTest(){
        onView(withId(R.id.appLogoImageViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // welcome layout

    @Test
    public void welcomeLayout_isVisibleTest(){
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    // prices ListView

    @Test
    public void pricesListView_isInvisibleIfHasNoChildTest(){
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesListView_isInvisibleThenWelcomeLayoutIsVisibleTest(){
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // fabNewOcr floatingActionButton

    @Test
    public void fabNewOcr_isVisibleTest(){
        onView(withId(R.id.fabNewOcrMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void fabNewOcr_hasBackgroundTintListColorTest(){
        onView(withId(R.id.fabNewOcrMainActivity)).check(matches(GetResourcesHelper.withBackgroundTintListColorId(R.color.colorAccent)));
    }

    @Test
    public void OcrScanActiviy_launchedByTappingOnFabNewOcrTest(){
        Intents.init();
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        intended(hasComponent(OcrScanActivity.class.getName()));
        Intents.release();
        onView(withId(R.id.ocrViewOcrScanActivity))
                .check(matches(isDisplayed()));
    }

    // General tests

    @Test
    public void supportActionBar_isVisibleTest(){
        assertEquals(true, mainActivityRule.getActivity().getSupportActionBar().isShowing());
    }

    @Test
    public void pricesTotal_isCompletelyDisplayed(){
        onView(withId(R.id.menuitem_total_mainactivity)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void pricesTotal_hasTextFromResources(){
        onView(withText(R.string.menuitem_total_default_mainactivity)).check(matches(isCompletelyDisplayed()));
    }

}
