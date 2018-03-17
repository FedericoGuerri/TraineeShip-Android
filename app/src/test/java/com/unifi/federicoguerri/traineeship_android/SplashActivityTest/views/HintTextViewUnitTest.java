package com.unifi.federicoguerri.traineeship_android.SplashActivityTest.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HintTextViewUnitTest extends SplashActivityUnitTest {

    private TextView splashTextView;

    @Override
    public void getTestingComponent() {
        splashTextView = activity.findViewById(R.id.splashScreenTapToStarttextView);
    }


    @Test
    public void splashTextView_isNotNull() throws Exception {
        assertNotNull(splashTextView);
    }

    @Test
    public void splashTextView_isVisible() throws Exception {
        assertEquals(View.VISIBLE,splashTextView.getVisibility());
    }

    @Test
    public void splashTextView_isNotClickable() throws Exception {
        assertEquals(false,splashTextView.isClickable());
    }

    @Test
    public void splashTextView_hasTextFromResources() throws Exception {
        String textFromResources=activity.getResources().getString(R.string.taptostart_text_view);
        assertEquals(textFromResources,splashTextView.getText());
    }

    @Test
    public void splashTextView_childOfBackgroundView(){
        assertEquals(R.id.splashBackgroundView,((View)splashTextView.getParent()).getId());
    }

    @Test
    public void splashTextView_widthIsWrapContent() throws Exception {
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,splashTextView.getLayoutParams().width);
    }

    @Test
    public void splashTextView_heightIsWrapContent() throws Exception {
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,splashTextView.getLayoutParams().height);
    }

    @Test
    public void splashTextView_hasTextColorFromResources(){
        int whiteTextColor = getColorFromResources(R.color.whiteTextColor);
        assertEquals(whiteTextColor,splashTextView.getCurrentTextColor());
    }


    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }


}
