package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class HintGalleryTextViewUnitTest extends AbstractMainActivityUnitTest {

    private TextView hintGalleryTextView;

    @Override
    public View getTestingComponent() {
        hintGalleryTextView=activity.findViewById(R.id.hintGalleryTextViewMainActivity);
        return hintGalleryTextView;
    }

    @Test
    public void hintGalleryTextView_isVisible(){
        assertEquals(View.VISIBLE,hintGalleryTextView.getVisibility());
    }

    @Test
    public void hintGalleryTextView_isShowingTextFromResources(){
        assertEquals(activity.getResources().getString(R.string.hint_gallery_textView_mainActivity),hintGalleryTextView.getText());
    }

    @Test
    public void hintGalleryTextView_hasTextColorFromResources(){
        assertEquals(activity.getResources().getColor(R.color.hint_textView_mainActivity),hintGalleryTextView.getCurrentTextColor());
    }

    @Test
    public void hintGalleryTextView_widthWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,hintGalleryTextView.getLayoutParams().width);
    }

    @Test
    public void hintGalleryTextView_heightWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,hintGalleryTextView.getLayoutParams().height);
    }

    @Test
    public void hintGalleryTextView_isNotClickable(){
        assertEquals(false,hintGalleryTextView.isClickable());
    }

    @Test
    public void hintGalleryTextView_hasTextSize14(){
        assertEquals(14,(int)hintGalleryTextView.getTextSize());
    }

    @Test
    public void hintGalleryTextView_childOfWelcomeLayout(){
        assertEquals(R.id.welcomeLayoutMainActivity,((View)hintGalleryTextView.getParent()).getId());
    }
}
