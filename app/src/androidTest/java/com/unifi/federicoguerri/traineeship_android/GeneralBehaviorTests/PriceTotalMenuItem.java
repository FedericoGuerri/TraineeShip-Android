package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PriceTotalMenuItem extends AbstractGeneral{

    // prices-total menuItem


    @Test
    public void pricesTotalMenuItem_valueIsIncrementing_afterPricesRecognition(){
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.1");
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.1");//miniature
        onView(withText("22.2")).check(matches(isDisplayed()));
    }


    @Test
    public void pricesTotalMenuItem_valueIsDecrementing_afterPricesRecognition(){
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.2");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.1");//miniature
        onView(withId(R.id.menuitem_total_mainactivity)).check(matches(isDisplayed()));
    }



    @Test
    public void pricesTotalMenuItem_valueIsChanging_whileChangingPrices(){
        genericHelper.recognizeSpecificPrice_withNoMiniature("22.2");
        genericHelper.recognizeSpecificPrice_withNoMiniature("11.2"); //miniature
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeSpecificPrice_withNoMiniature("22.2");
        genericHelper.deleteFirstPrice();
        genericHelper.recognizeSpecificPrice_withNoMiniature("111.11");//miniature
        onView(withText("133.31")).check(matches(isDisplayed()));
    }



}