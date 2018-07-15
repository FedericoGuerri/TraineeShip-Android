package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TextTargetingLayoutUnitTest extends AbstractOcrScanActivityUnitTest {

    private RelativeLayout textTargetingLayout;

    @Override
    public View getTestingComponent() {
        textTargetingLayout=activity.findViewById(R.id.textTargetingLayout);
        return textTargetingLayout;
    }


    @Test
    public void textTargetingLayout_isInvisible(){
        assertEquals(View.INVISIBLE,textTargetingLayout.getVisibility());
    }

    @Test
    public void textTargetingLayout_isNotClickable(){
        assertEquals(false,textTargetingLayout.isClickable());
    }

    @Test
    public void textTargetingLayout_childOfOcrParentLayout(){
        assertEquals(R.id.ocrParentLayoutOcrScanActivity,((View)textTargetingLayout.getParent()).getId());
    }

    @Test
    public void textTargetingLayout_background(){
        assertEquals(textTargetingLayout.getResources().getDrawable(R.drawable.rectangle,null),textTargetingLayout.getBackground());
    }

    @Test
    public void textTargetingLayout_hasCustomAlpha(){
        assertEquals(0.7f,textTargetingLayout.getAlpha());
    }

    @Test
    public void textTargetingLayout_hasCustomHeight(){
        assertEquals(getDisplayMetrics().heightPixels/3,textTargetingLayout.getHeight());
    }

    @Test
    public void textTargetingLayout_hasCustomWidth(){
        assertEquals(getDisplayMetrics().widthPixels,textTargetingLayout.getWidth());
    }



    @NonNull
    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics=new DisplayMetrics();
        WindowManager windowManager=(WindowManager) textTargetingLayout.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

}
