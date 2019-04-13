package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;

import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class PriceTotalMenuItem extends AbstractGeneral{

    @Test
    public void pricesTotalMenuItem_valueIsIncrementing_afterPricesRecognition(){
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.1");
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.1");
        onView(withText("22.2")).check(matches(isDisplayed()));
    }

    @Test
    public void pricesTotalMenuItem_valueIsDecrementing_afterPricesRecognition(){
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.2");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.1");
        onView(withId(R.id.menuitem_total_mainactivity)).check(matches(isDisplayed()));
    }

    @Test
    public void pricesTotalMenuItem_valueIsChanging_whileChangingPrices(){
        genericHelper.recognizeSpecificPrice_withNoMiniature("22.2");
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.2");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeSpecificPrice_withNoMiniature("22.2");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeSpecificPrice_withNoMiniature("111.11");
        onView(withText("133.31")).check(matches(isDisplayed()));
    }
}
