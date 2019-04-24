package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class PriceListGeneral extends AbstractGeneral{

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedASecondPrice(){
        genericHelper.recognizePricesWithNoMiniature(2);
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(2)));
    }

    @Test
    public void pricesList_willBeInvisible_ifBackWasPressedBeforePriceRecognition(){
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_willNoChangeVisibility_AfterRecognizedAPrice_ifBackWasPressed(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_willStoreAPriceValue_AfterRecognizedAPrice(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.itemPriceTextViewPricesListView)).check(matches(withText("0.0")));
    }

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedAPrice(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(ViewMatchers.withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }

    @Test
    public void pricesList_willBeVisible_AfterRecognizedAPrice(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_wontChangeListSize_AfterRecognizedAPrice_ifBackWasPressed(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }



    @Test
    public void pricesList_willChangeVisibility_AfterRecognizedOnePrice_ifItWasRemoved(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).perform(click());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_wontChangeVisibility_AfterRecognizedMorePrices_ifOneWasRemoved(){
        genericHelper.recognizePricesWithNoMiniature(2);
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_canAddAndDeletePrices(){
        genericHelper.recognizeAPriceWithNoMiniature();
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
    }

    @Test
    public void pricesList_canAddAndDeleteMultipleTimes(){
        genericHelper.recognizeAPriceWithNoMiniature();
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeAPriceWithNoMiniature();
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeAPriceWithNoMiniature();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }


    @Test
    public void pricesList_canAddAndDelete_inDifferentOrder(){
        genericHelper.recognizePricesWithNoMiniature(2);
        genericHelper.deletePrice(1);
        genericHelper.deletePrice(0);
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
    }

    @Test
    public void pricesList_visibilityWillChangeEventually_ifAddingAndRemovingPrices(){
        genericHelper.recognizePricesWithNoMiniature(4);
        genericHelper.deletePrice(3);
        genericHelper.deletePrice(1);
        genericHelper.deleteFirstPrices(2);
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_willChangeVisibility_AfterRecognizedMorePrices_ifAllWereRemoved(){
        genericHelper.recognizePricesWithNoMiniature(4);
        genericHelper.deleteFirstPrices(4);
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }



}
