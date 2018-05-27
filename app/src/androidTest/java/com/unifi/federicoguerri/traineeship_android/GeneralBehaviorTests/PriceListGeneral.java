package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;

import android.support.test.espresso.matcher.ViewMatchers;
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
public class PriceListGeneral extends AbstractGeneral{

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedAPrice(){
        genericHelper.recognizeAPrice("NO");
        onView(ViewMatchers.withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }

    @Test
    public void pricesList_willBeVisible_AfterRecognizedAPrice(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedASecondPrice(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(2)));
    }

    @Test
    public void pricesList_willStoreAPriceValue_AfterRecognizedAPrice(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.itemPriceTextViewPricesListView)).check(matches(withText("0.0")));
    }

    @Test
    public void pricesList_wontChangeListSize_AfterRecognizedAPrice_ifBackWasPressed(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }

    @Test
    public void pricesList_willBeInvisible_ifBackWasPressedBeforePriceRecognition(){
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_willNoChangeVisibility_AfterRecognizedAPrice_ifBackWasPressed(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_willChangeVisibility_AfterRecognizedOnePrice_ifItWasRemoved(){
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).perform(click());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_wontChangeVisibility_AfterRecognizedMorePrices_ifOneWasRemoved(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_canAddAndDeletePrices(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
    }

    @Test
    public void pricesList_canAddAndDeleteMultipleTimes(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }


    @Test
    public void pricesList_canAddAndDelete_inDifferentOrder(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deletePrice(1);
        genericHelper.deletePrice(0);
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
    }

    @Test
    public void pricesList_visibilityWillChangeEventually_ifAddingAndRemovingPrices(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deletePrice(3);
        genericHelper.deletePrice(1);
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_willChangeVisibility_AfterRecognizedMorePrices_ifAllWereRemoved(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }



}
