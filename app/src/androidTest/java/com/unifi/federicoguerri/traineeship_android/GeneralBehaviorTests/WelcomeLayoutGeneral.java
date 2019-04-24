package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
@RunWith(AndroidJUnit4.class)
@MediumTest
public class WelcomeLayoutGeneral extends  AbstractGeneral{

    @Test
    public void welcomeLayout_willBeInvisible_AfterRecognizedAPrice(){
        genericHelper.recognizeAPriceWithNoMiniature();
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
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willChangeVisibility_AfterRecognizedOnePrice_ifItWasRemoved(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).perform(click());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_wontChangeVisibility_AfterRecognizedMorePrices_ifOneWasRemoved(){
        genericHelper.recognizePricesWithNoMiniature(2);
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willChangeVisibility_AfterRecognizedMorePrices_ifAllWereRemoved(){
        genericHelper.recognizePricesWithNoMiniature(3);
        genericHelper.deleteFirstPrices(3);
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_visibilityWillChangeEventually_ifAddingAndRemovingPrices(){
        genericHelper.recognizePricesWithNoMiniature(4);
        genericHelper.deletePrice(3);
        genericHelper.deletePrice(1);
        genericHelper.deleteFirstPrices(2);
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

}
