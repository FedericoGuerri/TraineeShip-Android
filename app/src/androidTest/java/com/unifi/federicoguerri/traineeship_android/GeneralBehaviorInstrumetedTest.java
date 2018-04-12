package com.unifi.federicoguerri.traineeship_android;


import android.Manifest;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class GeneralBehaviorInstrumetedTest {

    @Rule public ActivityTestRule<SplashScreenActivity> splashActivityRule = new ActivityTestRule<>(SplashScreenActivity.class);
    @Rule public GrantPermissionRule runtimePermissionCamera = GrantPermissionRule .grant(Manifest.permission.CAMERA);
    @Rule public GrantPermissionRule runtimePermissionWriteStorange = GrantPermissionRule .grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    @Rule public GrantPermissionRule runtimePermissionReadStorange = GrantPermissionRule .grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    // MainActivity

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedAPrice(){
        recognizeAPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }

    @Test
    public void pricesList_willBeVisible_AfterRecognizedAPrice(){
        recognizeAPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedASecondPrice(){
        recognizeAPrice();
        recognizeAPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(2)));
    }

    @Test
    public void pricesList_willStoreAPriceValue_AfterRecognizedAPrice(){
        recognizeAPrice();
        onView(withId(R.id.itemPriceTextViewPricesListView)).check(matches(withText("0.0")));
    }

    @Test
    public void pricesList_wontChangeListSize_AfterRecognizedAPrice_ifBackWasPressed(){
        recognizeAPrice();
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
        recognizeAPrice();
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_willBeInvisible_AfterRecognizedAPrice(){
        recognizeAPrice();
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
        recognizeAPrice();
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }


    private void recognizeAPrice() {
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
    }



}
