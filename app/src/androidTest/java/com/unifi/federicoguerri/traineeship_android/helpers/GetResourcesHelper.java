package com.unifi.federicoguerri.traineeship_android.helpers;

import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.internal.util.Checks;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;


public class GetResourcesHelper {

    public static Matcher<View> withBackgroundTintListColorId(final int color) {
        Checks.checkNotNull(color);
        return new BoundedMatcher<View, View>(View.class) {
            @Override
            public boolean matchesSafely(View warning) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    return true;
                }
                try {
                    return InstrumentationRegistry.getTargetContext().getResources().getColor(color) == warning.getBackgroundTintList().getDefaultColor();
                }catch(NullPointerException colorWasNotSetted){
                    return false;
                }
            }
            @Override
            public void describeTo(Description description) {
                description.appendText("expected background tint: ");
                description.appendText(String.valueOf(color));
            }
        };
    }


}
