package com.unifi.federicoguerri.traineeship_android;

import android.graphics.drawable.ColorDrawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.internal.util.Checks;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class SplashScreenActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> rule  = new ActivityTestRule<>(SplashScreenActivity.class);

    public static Matcher<View> withBackgroundColorId(final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View warning) {
                return InstrumentationRegistry.getTargetContext().getResources().getColor(color) ==
                        ((ColorDrawable)warning.getBackground()).getColor();
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("with background color: ");
                description.appendText(String.valueOf(color));
            }
        };
    }


    @Test
    public void backgroundViewIsVisibleTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void backgroundViewHasBackgroundColorTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(withBackgroundColorId(R.color.splash_background_color)));
    }

    @Test
    public void backgroundViewHasTwoChildrenTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(hasChildCount(2)));
    }

    @Test
    public void backgroundViewHasImageViewLogoAsChildTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(withChild(withId(R.id.splashImageViewLogo))));
    }

    @Test
    public void backgroundViewIsClickableTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(isClickable()));
    }

    @Test
    public void thereIsAVisibleImageViewTest(){
        onView(withId(R.id.splashImageViewLogo)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void appLogoImageViewHasContentDescriptorTest(){
        onView(withId(R.id.splashImageViewLogo)).check(matches(withContentDescription(R.string.applogo_image_view_content_descriptor)));
    }

    @Test
    public void appLogoImageViewIsNotClickableTest(){
        onView(withId(R.id.splashImageViewLogo)).check(matches(not(isClickable())));
    }

    @Test
    public void supportActionBarIsInvisibleTest(){
        assertEquals(false,rule.getActivity().getSupportActionBar().isShowing());
    }

    @Test
    public void thereIsAVisibleHintTextViewTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void hintTextViewIsShowingHintTextTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(withText(R.string.taptostart_text_view)));
    }

    @Test
    public void hintTextViewHasWhiteTextColorTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(hasTextColor(R.color.whiteTextColor)));
    }

    @Test
    public void hintTextViewIsNotClickableTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(not(isClickable())));
    }


    @Test
    public void launchMainActivityByTappingOnBackgroundViewTest(){
        Intents.init();
        onView(withId(R.id.splashBackgroundView)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }


}
