package com.unifi.federicoguerri.traineeship_android.core;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.unifi.federicoguerri.traineeship_android.R;

public class OcrComponentsBuilder implements  Detector.Processor<TextBlock>{

    private TextRecognizer textRecognizer;
    private TextView recognizedTextView;
    private CameraSource cameraSource;
    private Rect rectBounds;
    private Rect itemBox;
    private float originalY=0;
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

    public void setTextViewCoordinates(float x,float y){
        originalX=x;
        originalY=y;
    }

    private void detect(SparseArray<TextBlock> items) {
        try {
            StringBuilder stringBuilder=new StringBuilder();
            itemBox=rectBounds;
            for(int j=0;j<items.size();j++) {
                for (int i = 0; i < items.get(j).getComponents().size(); i++) {
                    stringBuilder.append(items.get(j).getComponents().get(i).getValue() + "\n");
                }
            }
            PriceBuilder priceBuilder = new PriceBuilder(stringBuilder.toString());
            String price=priceBuilder.getPrice();
            for(int j=0;j<items.size();j++) {
                for (int i = 0; i < items.get(j).getComponents().size(); i++) {
                    if (items.get(j).getComponents().get(i).getValue().contains(price)) {
                        itemBox = items.get(j).getBoundingBox();
                    }
                }
            }
            recognizedTextView.setText(price);
            recognizedTextView.animate().x(itemBox.left).y(itemBox.top).withEndAction(new Runnable() {
                @Override
                public void run() {
                    recognizedTextView.setX(itemBox.left);
                    recognizedTextView.setY(itemBox.top);
                }
            }).start();
        } catch (Exception e) {
            if (e.getMessage().equals("recognitionError")) {
                if(recognizedTextView.getX()!=originalX || originalY!=recognizedTextView.getY()) {
                    recognizedTextView.animate().scaleY(0f).scaleX(0f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            recognizedTextView.setText(recognizedTextView.getContext().getResources().getString(R.string.bad_recognition_get_closer_please));
                            recognizedTextView.setX(originalX);
                            recognizedTextView.setY(originalY);
                            recognizedTextView.animate().scaleX(1f).scaleY(1f);
                        }
                    }).start();
                }
            }
        }
    }

    @Override
    public void release() {

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
}
