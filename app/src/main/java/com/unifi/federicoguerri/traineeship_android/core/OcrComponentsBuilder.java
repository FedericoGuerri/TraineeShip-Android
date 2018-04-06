package com.unifi.federicoguerri.traineeship_android.core;

import android.content.Context;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.unifi.federicoguerri.traineeship_android.R;

public class OcrComponentsBuilder{

    private TextRecognizer textRecognizer;
    private TextView recognizedTextView;
    private CameraSource cameraSource;
    private Context context;


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
                if(items.size()>0){
                    recognizedTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                PriceBuilder priceBuilder=new PriceBuilder(items.get(0).getValue());
                                recognizedTextView.setText(priceBuilder.getPrice());
                            } catch (Exception e) {
                                if(e.getMessage().equals("recognitionError")){
                                    recognizedTextView.setText(context.getResources().getString(R.string.bad_recognition_get_closer_please));
                                }
                            }

                        }
                    });
                }
            }
        });
    }


    public void setCameraSource(int height,int width){
        cameraSource = new CameraSource.Builder(context, textRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(height, width).setRequestedFps(2.0f).setAutoFocusEnabled(true).build();
    }


}
