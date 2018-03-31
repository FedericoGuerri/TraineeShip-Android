package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class OcrScanActivity extends AppCompatActivity {

    private SurfaceView ocrScanView;
    private CameraSource myCameraSource;
    private TextView recognizedTextView;
    private final int REQUEST_CAMERA_PERMISSION=10400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_scan);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        recognizedTextView=findViewById(R.id.recognizedTextViewOcrScanActivity);
        ocrScanView = findViewById(R.id.ocrViewOcrScanActivity);

        TextRecognizer myTextRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (myTextRecognizer.isOperational()) {
            myCameraSource = new CameraSource.Builder(getApplicationContext(), myTextRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024).setRequestedFps(2.0f).setAutoFocusEnabled(true).build();
            ocrScanView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OcrScanActivity.this,new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA_PERMISSION);
                        return;
                    }
                    try {
                        myCameraSource.start(ocrScanView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    myCameraSource.stop();
                }
            });
        }else{
            Log.d("TextRecognizer","dependencies error");
        }
        myTextRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
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
                            StringBuilder stringBuilder=new StringBuilder();
                            for (int i=0;i<items.size();i++){
                                TextBlock item=items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuilder.append("\n");
                            }
                            recognizedTextView.setText(stringBuilder.toString());
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA_PERMISSION: {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    try {
                        myCameraSource.start(ocrScanView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


}
