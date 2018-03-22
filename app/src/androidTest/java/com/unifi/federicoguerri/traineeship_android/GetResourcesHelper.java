package com.unifi.federicoguerri.traineeship_android;

import android.graphics.drawable.ColorDrawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.internal.util.Checks;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;


public class GetResourcesHelper {

    public static Matcher<View> withBackgroundColorId(final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View warning) {
                try {
                    return InstrumentationRegistry.getTargetContext().getResources().getColor(color) == ((ColorDrawable) warning.getBackground()).getColor();
                }catch(NullPointerException colorWasNotSetted){
                    return false;
                }
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("expected background color: ");
                description.appendText(String.valueOf(color));
            }
        };
    }
}
