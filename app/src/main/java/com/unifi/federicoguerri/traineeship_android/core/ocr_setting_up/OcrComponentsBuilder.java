package com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.unifi.federicoguerri.traineeship_android.R;

import java.io.IOException;
import java.util.ArrayList;

public class OcrComponentsBuilder implements  Detector.Processor<TextBlock>{

    private TextRecognizer textRecognizer;
    private TextView recognizedTextView;
    private CameraSource cameraSource;
    private Rect rectBounds;
    private float originalY=0;
    private float badRecognitionSpace =0;
    private float originalX=0;
    private boolean isDetecting=true;
    private Activity activity;

    public OcrComponentsBuilder(Activity activity) {
        this.activity=activity;
        textRecognizer = new TextRecognizer.Builder(activity.getApplicationContext()).build();
    }


    public TextRecognizer getTextRecognizer() {
        return textRecognizer;
    }

    public TextView getRecognizedTextView() {
        return recognizedTextView;
    }

    public CameraSource getCameraSource() {
        return cameraSource;
    }

    public void setRecognizedTextView(TextView textView) {
        recognizedTextView=textView;
        textRecognizer.setProcessor(this);
    }

    public void setCameraSource(CameraSource cameraSource){
        this.cameraSource = cameraSource;
    }

    public Rect getRectBounds() {
        return rectBounds;
    }

    public void setRectBounds(Rect rectBounds){
        this.rectBounds=rectBounds;
    }

    public void startCamera(SurfaceHolder surfaceHolder){
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, 10400);
            return;
        }
        try {
            this.getCameraSource().start(surfaceHolder);
        } catch (IOException e) {
            Log.e("CreatingSurface",e.getMessage());
        }
    }

    public boolean isDetecting() {
        return isDetecting;
    }

    public void setDetecting(boolean detecting) {
        isDetecting = detecting;
    }

    public void setTextViewCoordinates(float x,float y,float badRecognitionSpace){
        originalX=x;
        originalY=y;
        this.badRecognitionSpace = badRecognitionSpace;
    }

    private void detect(SparseArray<TextBlock> items) {
        try {
            StringBuilder stringBuilder=new StringBuilder();
            ArrayList<Text> lines=new ArrayList<>();
            Rect itemBox=rectBounds;

            for(int j=0;j<items.size();j++) {
                for (int i = 0; i < items.get(j).getComponents().size(); i++) {
                    addTextIfInTargetRectangle(stringBuilder, lines, itemBox, items.get(j).getComponents().get(i));
                }
            }


            PriceBuilder priceBuilder = new PriceBuilder(stringBuilder.toString());
            String price=priceBuilder.getPrice();
            itemBox = priceBuilder.choosePriceThatFitsInTargetRect(lines,itemBox);

            recognizedTextView.setText(price);
            animateRecognitionTextViewToLocation(itemBox.left, itemBox.top);

        } catch (Exception e) {
            if (recognizedTextView.getX()!=originalX- badRecognitionSpace) {
                recognizedTextView.setText(recognizedTextView.getContext().getResources().getString(R.string.bad_recognition_get_closer_please));
                recognizedTextView.animate().scaleY(0f).scaleX(0f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            animateTextViewToOriginalPosition();
                            recognizedTextView.animate().scaleX(1f).scaleY(1f);
                        }
                    }).start();
            }
        }
    }



    private void addTextIfInTargetRectangle(StringBuilder stringBuilder, ArrayList<Text> lines, Rect itemBox, Text block) {
        if(block.getBoundingBox().top>itemBox.top && block.getBoundingBox().bottom<itemBox.bottom) {
            stringBuilder.append(block.getValue().replaceAll(",",".") + "\n");
            lines.add(block);
        }
    }


    @Override
    public void release() {
        //Never releasing explicitally the recognizer
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        final SparseArray<TextBlock> items=detections.getDetectedItems();
        if(items.size()>0 && isDetecting){
            recognizedTextView.post(new Runnable() {
                @Override
                public void run() {
                    detect(items);
                }
            });
        }
    }

    public void animateTextViewToOriginalPosition(){
        if(recognizedTextView.getText().toString().contains(recognizedTextView.getContext().getResources().getString(R.string.bad_recognition_get_closer_please))){
            recognizedTextView.setX(originalX- badRecognitionSpace);
            recognizedTextView.setY(originalY);
        }else {
            animateRecognitionTextViewToLocation(originalX, originalY);
        }
    }

    private void animateRecognitionTextViewToLocation(final float x,final float y) {
        recognizedTextView.animate().x(x).y(y).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                recognizedTextView.setX(x);
                recognizedTextView.setY(y);

            }
        }).start();
    }


}
