package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class WelcomeTextViewUnitTest extends MainActivityUnitTest {

    private TextView welcomeTextView;

    @Override
    public void getTestingComponent() {
        welcomeTextView=activity.findViewById(R.id.welcomeTextViewMainActivity);
    }

    @Test
    public void welcomeTextView_isNotNull(){
        assertNotNull(welcomeTextView);
    }

    @Test
    public void welcomeTextView_isVisible(){
        assertEquals(View.VISIBLE,welcomeTextView.getVisibility());
    }

    @Test
    public void welcomeTextView_hasTextSize18(){
        assertEquals(18,(int)welcomeTextView.getTextSize());
    }

    @Test
    public void welcomeTextView_hasTextFromResources(){
        assertEquals(activity.getResources().getString(R.string.welcome_textView_mainActivity),welcomeTextView.getText());
    }

    @Test
    public void welcomeTextView_hasBoldText(){
        assertEquals(Typeface.BOLD,welcomeTextView.getTypeface().getStyle());
    }

    @Test
    public void welcomeTextView_hasTextColorFromResources(){
        assertEquals(activity.getResources().getColor(R.color.hint_textView_mainActivity),welcomeTextView.getCurrentTextColor());
    }

    @Test
    public void welcomeTextView_widthWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,welcomeTextView.getLayoutParams().width);
    }

    @Test
    public void welcomeTextView_heightWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,welcomeTextView.getLayoutParams().height);
    }

    @Test
    public void welcomeTextView_isNotClickable(){
        assertEquals(false,welcomeTextView.isClickable());
    }

}
