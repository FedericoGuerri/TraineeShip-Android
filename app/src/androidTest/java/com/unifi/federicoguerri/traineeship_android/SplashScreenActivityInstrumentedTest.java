package com.unifi.federicoguerri.traineeship_android;

import android.arch.lifecycle.Lifecycle;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SplashScreenActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> splashScreenRule = new ActivityTestRule<>(SplashScreenActivity.class);


    // background view

    @Test
    public void backgroundView_isVisibleTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void backgroundView_hasBackgroundColorTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(GetResourcesHelper.withBackgroundColorId(R.color.splash_background_color)));
    }

    @Test
    public void backgroundView_hasTwoChildrenTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(hasChildCount(2)));
    }

    @Test
    public void backgroundView_hasImageViewLogoAsChildTest(){
        onView(withId(R.id.splashBackgroundView)).check(matches(withChild(withId(R.id.splashImageViewLogo))));
    }

    @Test
    public void mainActivity_launchedByTappingOnBackgroundViewTest(){
        Intents.init();
        onView(withId(R.id.splashBackgroundView)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        Intents.release();
    }


    // app logo Imageview

    @Test
    public void thereIsAVisibleImageViewTest(){
        onView(withId(R.id.splashImageViewLogo)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void appLogoImageView_hasContentDescriptorTest(){
        onView(withId(R.id.splashImageViewLogo)).check(matches(withContentDescription(R.string.applogo_image_view_content_descriptor)));
    }


    // Hint Textview

    @Test
    public void thereIsAVisibleHintTextViewTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void hintTextView_isShowingHintTextTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(withText(R.string.taptostart_text_view)));
    }

    @Test
    public void hintTextView_hasWhiteTextColorTest(){
        onView(withId(R.id.splashScreenTapToStarttextView)).check(matches(hasTextColor(R.color.whiteTextColor)));
    }



    // General tests

    @Test
    public void launchingMainActivity_willTerminateSplashScreen(){
        onView(withId(R.id.splashBackgroundView)).perform(click());
        assertEquals(Lifecycle.State.DESTROYED,splashScreenRule.getActivity().getLifecycle().getCurrentState());
    }

    @Test(expected = NoActivityResumedException.class)
    public void pressingBackButton_willTerminateTheApp(){
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void supportActionBar_isInvisibleTest(){
        assertEquals(false, splashScreenRule.getActivity().getSupportActionBar().isShowing());
    }


}
