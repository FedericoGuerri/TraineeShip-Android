package com.unifi.federicoguerri.traineeship_android.helpers;


import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.AllOf.allOf;

public class GenericHelper {

    public void recognizeAPrice(String text) {
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        onView(withText(text)).perform(click());
        if(text.equals("YES")){
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        }
    }


    public void deleteFirstPrice() {
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).
                perform(click());
    }

    public void deletePrice(int index) {
        onData(anything()).atPosition(index).
                onChildView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).
                perform(click());
    }

    public void recognizeSpecificPrice_withNoMiniature(String price) {
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(withId(R.id.recognizedTextViewOcrScanActivity)).perform(setTextInTextView(price));
        onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        onView(withText("NO")).perform(click());
    }

    private static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }

}
