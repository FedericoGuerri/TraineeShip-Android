package com.unifi.federicoguerri.traineeship_android.core;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class OcrComponentsBuilder{

    private TextRecognizer textRecognizer;
    private TextView recognizedTextView;
    private CameraSource cameraSource;
    private Context context;
    private Rect rectBounds;
    private boolean isDetecting=true;
    private float originalY=0;
    private float originalX=0;

    public OcrComponentsBuilder(Context context) {
        textRecognizer = new TextRecognizer.Builder(context).build();
        this.context=context;
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
        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {
                final SparseArray<TextBlock> items=detections.getDetectedItems();
                if(items.size()>0 && isDetecting){
                    recognizedTextView.post(new TextBuilderRunnable(items,recognizedTextView,originalX,originalY,rectBounds));
                }
            }
        });
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

}
