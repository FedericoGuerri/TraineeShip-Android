package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import android.support.test.espresso.matcher.ViewMatchers;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class WelcomeLayoutGeneral extends  AbstractGeneral{


    // MainActivity

    // welcomeLayout

    @Test
    public void welcomeLayout_willBeInvisible_AfterRecognizedAPrice(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willBeVisible_ifBackWasPressedBeforePriceRecognition(){
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_willNoChangeVisibility_AfterRecognizedAPrice_ifBackWasPressed(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willChangeVisibility_AfterRecognizedOnePrice_ifItWasRemoved(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).perform(click());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_wontChangeVisibility_AfterRecognizedMorePrices_ifOneWasRemoved(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willChangeVisibility_AfterRecognizedMorePrices_ifAllWereRemoved(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_visibilityWillChangeEventually_ifAddingAndRemovingPrices(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deletePrice(3);
        genericHelper.deletePrice(1);
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}
