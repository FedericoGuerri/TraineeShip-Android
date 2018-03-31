package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class RecognizedTextViewUnitTest extends AbstractOcrScanActivityUnitTest {

    private TextView recognizedTextView;

    @Override
    public View getTestingComponent() {
        recognizedTextView=activity.findViewById(R.id.recognizedTextViewOcrScanActivity);
        return recognizedTextView;
    }

    @Test
    public void recognizedTextView_isVisible(){
        assertEquals(View.VISIBLE,recognizedTextView.getVisibility());
    }

    @Test
    public void recognizedTextView_hasTextSize18(){
        assertEquals(18,(int)recognizedTextView.getTextSize());
    }


    @Test
    public void recognizedTextView_hasBoldText(){
        assertEquals(Typeface.BOLD,recognizedTextView.getTypeface().getStyle());
    }

    @Test
    public void welcomeTextView_hasTextColorFromResources(){
        assertEquals(activity.getResources().getColor(R.color.colorAccent),recognizedTextView.getCurrentTextColor());
    }


    @Test
    public void recognizedTextView_widthWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,recognizedTextView.getLayoutParams().width);
    }

    @Test
    public void recognizedTextView_heightWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,recognizedTextView.getLayoutParams().height);
    }

    @Test
    public void recognizedTextView_isNotClickable(){
        assertEquals(false,recognizedTextView.isClickable());
    }

    @Test
    public void recognizedTextView_childOfOcrParentLayout(){
        assertEquals(R.id.ocrParentLayoutOcrScanActivity,((View)recognizedTextView.getParent()).getId());
    }

    @Test
    public void recognizedTextView_isShowingTextFromResources(){
        assertEquals(activity.getResources().getString(R.string.recognition_textView_defaultvalue_ocrScanActivity),recognizedTextView.getText());
    }

}
