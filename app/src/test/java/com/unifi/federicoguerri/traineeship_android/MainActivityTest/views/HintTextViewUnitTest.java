package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class HintTextViewUnitTest extends AbstractMainActivityUnitTest {

    private TextView hintTextView;

    @Override
    public View getTestingComponent() {
        hintTextView=activity.findViewById(R.id.hintTextViewMainActivity);
        return hintTextView;
    }

    @Test
    public void hintTextView_isVisible(){
        assertEquals(View.VISIBLE,hintTextView.getVisibility());
    }

    @Test
    public void hintTextView_isShowingTextFromResources(){
        assertEquals(activity.getResources().getString(R.string.hint_textView_mainActivity),hintTextView.getText());
    }

    @Test
    public void hintTextView_hasTextColorFromResources(){
        assertEquals(activity.getResources().getColor(R.color.hint_textView_mainActivity),hintTextView.getCurrentTextColor());
    }

    @Test
    public void hintTextView_widthWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,hintTextView.getLayoutParams().width);
    }

    @Test
    public void hintTextView_heightWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,hintTextView.getLayoutParams().height);
    }

    @Test
    public void hintTextView_isNotClickable(){
        assertEquals(false,hintTextView.isClickable());
    }

    @Test
    public void hintTextView_hasTextSize14(){
        assertEquals(14,(int)hintTextView.getTextSize());
    }

    @Test
    public void hintTextView_childOfWelcomeLayout(){
        assertEquals(R.id.welcomeLayoutMainActivity,((View)hintTextView.getParent()).getId());
    }
}
