package com.unifi.federicoguerri.traineeship_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.unifi.federicoguerri.traineeship_android", appContext.getPackageName());
    }

    public static Matcher<View> withListSize (final int size) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return ((ListView) view).getCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("expected " + size + " items");
            }
        };
    }

    // hint TextView
    @Test
    public void hintTextView_isATextViewTest() throws Exception {
        onView(withId(R.id.hintTextViewMainActivity)).check(matches(instanceOf(TextView.class)));
    }

    @Test
    public void hintTextView_isColorAccentTest() throws Exception {
        onView(withId(R.id.hintTextViewMainActivity)).check(matches(hasTextColor(R.color.hint_textView_mainActivity)));
    }

    @Test
    public void hintTextView_isDisplayedTest(){
        onView(withText(InstrumentationRegistry.getTargetContext().getResources().getString(R.string.hint_textView_mainActivity))).check(matches(isDisplayed()));
    }

    // welcome TextView
    @Test
    public void welcomeTextView_isATextViewTest() throws Exception {
        onView(withId(R.id.welcomeTextViewMainActivity)).check(matches(instanceOf(TextView.class)));
    }

    @Test
    public void welcomeTextView_isColorAccentTest() throws Exception {
        onView(withId(R.id.welcomeTextViewMainActivity)).check(matches(hasTextColor(R.color.welcome_textView_mainActivity)));
    }

    @Test
    public void welcomeTextView_isDisplayedTest(){
        onView(withText(InstrumentationRegistry.getTargetContext().getResources().getString(R.string.welcome_textView_mainActivity))).check(matches(isDisplayed()));
    }

    // hint gallery TextView
    @Test
    public void hintGalleryTextView_isATextViewTest() throws Exception {
        onView(withId(R.id.hintGalleryTextViewMainActivity)).check(matches(instanceOf(TextView.class)));
    }

    @Test
    public void hintGalleryTextView_isColorAccentTest() throws Exception {
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

    @Test
    public void appLogoImageView_hasContentDescriptorTest(){
        onView(withId(R.id.appLogoImageViewMainActivity)).check(matches(withContentDescription(R.string.applogo_image_view_content_descriptor)));
    }

    // welcome layout

    @Test
    public void welcomeLayout_isVisibleTest(){
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_hasBackgroundColorTest(){
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(GetResourcesHelper.withBackgroundColorId(R.color.transparent_background_color)));
    }

    @Test
    public void welcomeLayout_hasFourChildrenTest(){
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(hasChildCount(4)));
    }

    // prices ListView

    @Test
    public void pricesListView_hasBackgroundColorTest(){
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(GetResourcesHelper.withBackgroundColorId(R.color.transparent_background_color)));
    }

    @Test
    public void pricesListView_isInvisibleIfHasNoChildTest(){
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withListSize(0)));
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

    }

    @Test
    public void pricesListView_isInvisibleThenWelcomeLayoutIsVisibleTest(){
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }






}
