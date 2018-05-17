package com.unifi.federicoguerri.traineeship_android.core;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;

import java.io.IOException;

public class MySurfaceHolderCallback implements SurfaceHolder.Callback {
    private Activity activity;
    private OcrComponentsBuilder ocrBuilder;
    private SurfaceHolder surfaceView;
    private int requestCode;

    public MySurfaceHolderCallback(Activity activity, OcrComponentsBuilder ocrBuilder, SurfaceHolder surfaceView,int requestCode){
        this.activity = activity;
        this.ocrBuilder = ocrBuilder;
        this.surfaceView = surfaceView;
        this.requestCode = requestCode;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, requestCode);
            return;
        }

        try {
            ocrBuilder.getCameraSource().start(surfaceView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        ocrBuilder.getCameraSource().stop();
    }
}
