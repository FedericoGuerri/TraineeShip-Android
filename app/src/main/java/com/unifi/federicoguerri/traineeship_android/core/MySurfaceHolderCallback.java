package com.unifi.federicoguerri.traineeship_android.core;

import android.view.SurfaceHolder;

public class MySurfaceHolderCallback implements SurfaceHolder.Callback {
    private OcrComponentsBuilder ocrBuilder;
    private SurfaceHolder surfaceHolder;

    public MySurfaceHolderCallback(OcrComponentsBuilder ocrBuilder, SurfaceHolder surfaceHolder){
        this.ocrBuilder = ocrBuilder;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        ocrBuilder.startCamera(this.surfaceHolder);
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
