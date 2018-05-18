package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unifi.federicoguerri.traineeship_android.R;

import junit.framework.Assert;

import org.junit.Test;
import org.robolectric.shadows.ShadowLinearLayout;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

public class WelcomeLayoutUnitText extends AbstractMainActivityUnitTest {

    private LinearLayout welcomeLayout;

    @Override
    public View getTestingComponent() {
        welcomeLayout=activity.findViewById(R.id.welcomeLayoutMainActivity);
        return welcomeLayout;
    }


    @Test
    public void welcomeLayout_hasCenterGravity(){
        ShadowLinearLayout shadowLinearLayout=shadowOf(welcomeLayout);
        assertEquals(Gravity.CENTER,shadowLinearLayout.getGravity());
    }

    @Test
    public void welcomeLayout_isVisible(){
        Assert.assertEquals(View.VISIBLE,welcomeLayout.getVisibility());
    }


    @Test
    public void welcomeLayout_hasMatchParentWidth(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,welcomeLayout.getLayoutParams().width);
    }

    @Test
    public void welcomeLayout_hasMatchParentHeight(){
        assertEquals(ViewGroup.LayoutParams.MATCH_PARENT,welcomeLayout.getLayoutParams().height);
    }
}
