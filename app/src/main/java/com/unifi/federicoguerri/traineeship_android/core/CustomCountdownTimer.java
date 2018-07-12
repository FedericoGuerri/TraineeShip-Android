package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.View;

public class CustomCountdownTimer extends CountDownTimer{
    private View targetLayout;
    private OcrComponentsBuilder myOcrBuilder;

    public CustomCountdownTimer(long millisInFuture, long countDownInterval, View targetLayout, OcrComponentsBuilder myOcrBuilder) {
        super(millisInFuture, countDownInterval);
        this.targetLayout = targetLayout;
        this.myOcrBuilder = myOcrBuilder;
    }

    @Override
    public void onTick(long l) {
        // Calling every second
    }

    @Override
    public void onFinish() {
        int[] l = new int[2];
        targetLayout.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        myOcrBuilder.setRectBounds(new Rect(x,y,targetLayout.getWidth(),y+targetLayout.getHeight()));
        myOcrBuilder.setTextViewCoordinates(myOcrBuilder.getRecognizedTextView().getX(),myOcrBuilder.getRecognizedTextView().getY(),(float) targetLayout.getWidth()/3);
    }
}
