package com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up;

import android.graphics.Rect;
import android.os.Handler;
import android.view.View;

public class CustomPostDelayedSettingUp implements Runnable{
    private View targetLayout;
    private OcrComponentsBuilder myOcrBuilder;

    public CustomPostDelayedSettingUp(long millisInFuture, View targetLayout, OcrComponentsBuilder myOcrBuilder) {
        this.targetLayout = targetLayout;
        this.myOcrBuilder = myOcrBuilder;
        Handler handler=new Handler();
        handler.postDelayed(this,millisInFuture);
    }


    @Override
    public void run() {
        int[] l = new int[2];
        targetLayout.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        myOcrBuilder.setRectBounds(new Rect(x,y,targetLayout.getWidth(),y+targetLayout.getHeight()));
        myOcrBuilder.setTextViewCoordinates(myOcrBuilder.getRecognizedTextView().getX(),myOcrBuilder.getRecognizedTextView().getY(),(float) targetLayout.getWidth()/3);
        targetLayout.animate().alpha(0.7f);
    }


}
