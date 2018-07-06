package com.unifi.federicoguerri.traineeship_android.core;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.unifi.federicoguerri.traineeship_android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MiniatureSaver implements CameraSource.PictureCallback {

    private Activity activity;
    private OcrComponentsBuilder myOcrBuilder;
    private int nextIndex;
    private FloatingActionButton fabSavePrice;

    public MiniatureSaver(Activity activity, OcrComponentsBuilder myOcrBuilder, int nextIndex, FloatingActionButton fabSavePrice) {
        this.activity = activity;
        this.myOcrBuilder = myOcrBuilder;
        this.nextIndex = nextIndex;
        this.fabSavePrice = fabSavePrice;
    }

    private String appDirectory() {
        File configDir = new File(activity.getFilesDir()
                + "/Android/data/"
                + activity.getPackageName()
                + "/ConfigurationDir");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        return configDir.getAbsolutePath();
    }

    private String saveMiniatureFile(Bitmap bitmap){
        String filename;
        try {
            String timeStamp = new SimpleDateFormat("yyyy_MMdd_HH_mm_ss").format(new Date());
            filename=appDirectory() + File.separator + "miniature_"+ timeStamp + ".png";
            FileOutputStream out;
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
            Toast.makeText(activity, activity.getString(R.string.cant_write_to_file),Toast.LENGTH_SHORT).show();
            filename="noMiniature";
        }
        return filename;
    }

    public void saveDataToFile(String miniaturePath) {
        DatabaseHelper.getHelper().savePrice(
                new DatabasePrice(myOcrBuilder.getRecognizedTextView().getText().toString().replace(",",".")
                        ,miniaturePath
                        ,nextIndex));
    }

    @Override
    public void onPictureTaken(byte[] bytes) {
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        String miniaturePath=saveMiniatureFile(Bitmap.createScaledBitmap(bitmap,350,450,true));
        saveDataToFile(miniaturePath);
        endActivity();
    }

    public void endActivity() {
        fabSavePrice.setEnabled(false);
        activity.finish();
        activity.overridePendingTransition(R.anim.end_ocr_scan_enter,R.anim.end_ocr_scan_exit);
    }
}
