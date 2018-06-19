package com.unifi.federicoguerri.traineeship_android.core;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

public class MySurfaceHolderCallback implements SurfaceHolder.Callback {
    private Activity activity;
    private OcrComponentsBuilder ocrBuilder;
    private SurfaceHolder surfaceHolder;
    private int requestCode;

    public MySurfaceHolderCallback(Activity activity, OcrComponentsBuilder ocrBuilder, SurfaceHolder surfaceHolder, int requestCode){
        this.activity = activity;
        this.ocrBuilder = ocrBuilder;
        this.surfaceHolder = surfaceHolder;
        this.requestCode = requestCode;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, requestCode);
            return;
        }
        try {
            ocrBuilder.getCameraSource().start(this.surfaceHolder);
        } catch (IOException e) {
            Log.e("CreatingSurface",e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        //Not calling this
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        ocrBuilder.getCameraSource().stop();
    }
}
