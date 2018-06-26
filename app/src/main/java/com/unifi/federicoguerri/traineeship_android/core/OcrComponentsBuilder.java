package com.unifi.federicoguerri.traineeship_android.core;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.unifi.federicoguerri.traineeship_android.R;

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


    public OcrComponentsBuilder(Context context) {
        textRecognizer = new TextRecognizer.Builder(context).build();
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
                    addTextIfInTargetRectangle(items, stringBuilder, lines, itemBox, j, i);
                }
            }

            PriceBuilder priceBuilder = new PriceBuilder(stringBuilder.toString());
            String price=priceBuilder.getPrice();
            String notFormattedPrice=price.replace(",",".");

            itemBox = choosePriceThatFitsInTargetRect(lines, itemBox, price, notFormattedPrice);

            recognizedTextView.setText(price);
            animateRecognitionTextViewToLocation(itemBox.left, itemBox.top);

        } catch (Exception e) {
            if (e.getMessage().equals("recognitionError") && (recognizedTextView.getX()!=originalX- badRecognitionSpace || originalY!=recognizedTextView.getY())) {
                    recognizedTextView.animate().scaleY(0f).scaleX(0f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            recognizedTextView.setText(recognizedTextView.getContext().getResources().getString(R.string.bad_recognition_get_closer_please));
                            animateTextViewToOriginalPosition();
                            recognizedTextView.animate().scaleX(1f).scaleY(1f);
                        }
                    }).start();
            }
        }
    }

    private Rect choosePriceThatFitsInTargetRect(ArrayList<Text> lines, Rect itemBox, String price, String notFormattedPrice) {
        for(int i=0;i<lines.size();i++) {
            if (lines.get(i).getValue().contains(price) || lines.get(i).getValue().contains(notFormattedPrice)) {
                itemBox = lines.get(i).getBoundingBox();
                break;
            }
        }
        return itemBox;
    }

    private void addTextIfInTargetRectangle(SparseArray<TextBlock> items, StringBuilder stringBuilder, ArrayList<Text> lines, Rect itemBox, int j, int i) {
        if(items.get(j).getComponents().get(i).getBoundingBox().top>itemBox.top && items.get(j).getComponents().get(i).getBoundingBox().bottom<itemBox.bottom) {
            stringBuilder.append(items.get(j).getComponents().get(i).getValue() + "\n");
            lines.add(items.get(j).getComponents().get(i));
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
