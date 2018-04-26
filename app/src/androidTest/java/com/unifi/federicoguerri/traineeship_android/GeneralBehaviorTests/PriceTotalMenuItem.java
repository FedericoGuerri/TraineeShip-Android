package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import android.Manifest;
import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

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
