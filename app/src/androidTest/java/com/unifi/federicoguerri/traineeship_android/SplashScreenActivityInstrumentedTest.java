package com.unifi.federicoguerri.traineeship_android;

import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class SplashScreenActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<SplashScreenActivity> splashScreenRule = new ActivityTestRule<>(SplashScreenActivity.class);

    // background view

    @Test(expected = NoMatchingViewException.class)
    public void backgroundView_isOverridedByTheme(){
        onView(withId(R.id.splashBackgroundView)).check(matches(isCompletelyDisplayed()));
    }

    // General tests
    @Test
    public void user_cantInteractByTap(){
        onView(withId(android.R.id.content)).perform(click());
        onView(withId(android.R.id.content)).check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void user_cantInteractByLongClick(){
        onView(isRoot()).perform(longClick());
        onView(isRoot()).check(matches(isCompletelyDisplayed()));
    }

    @Test(expected = NoActivityResumedException.class)
    public void pressingBackButton_willTerminateTheApp(){
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void supportActionBar_isNotThere(){
        assertNull(splashScreenRule.getActivity().getSupportActionBar());
    }


}
