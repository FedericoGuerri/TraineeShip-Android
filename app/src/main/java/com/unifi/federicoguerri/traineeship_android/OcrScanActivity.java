package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.unifi.federicoguerri.traineeship_android.core.DataWriterToFile;
import com.unifi.federicoguerri.traineeship_android.core.OcrComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class OcrScanActivity extends AppCompatActivity {

    private SurfaceView ocrScanView;
    private OcrComponentsBuilder myOcrBuilder;
    private final int REQUEST_CAMERA_PERMISSION=10400;
    private String filePath;
    private boolean isGettingMiniature=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_scan);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        filePath=getIntent().getStringExtra("fileName");

        ocrScanView = findViewById(R.id.ocrViewOcrScanActivity);

        myOcrBuilder=new OcrComponentsBuilder(getApplicationContext());
        if (myOcrBuilder.getTextRecognizer().isOperational()) {

            myOcrBuilder.setCameraSource(1080,720);

            ocrScanView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OcrScanActivity.this,new String[]{Manifest.permission.CAMERA},
                                REQUEST_CAMERA_PERMISSION);
                        return;
                    }
                    try {
                        myOcrBuilder.getCameraSource().start(ocrScanView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    myOcrBuilder.getCameraSource().stop();
                }
            });
        }else{
            Log.d("TextRecognizer","dependencies error");
        }

        myOcrBuilder.setRecognizedTextView((TextView) findViewById(R.id.recognizedTextViewOcrScanActivity));

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
                        myOcrBuilder.getCameraSource().start(ocrScanView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    public void saveCurrentPrice(View view) {
        if(!isGettingMiniature) {
            if (!Objects.equals(myOcrBuilder.getRecognizedTextView().getText().toString(), getString(R.string.bad_recognition_get_closer_please))) {
                showMiniatureDialog();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.no_price_detected_toast), Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            findViewById(R.id.fabSaveCurrentPrice).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorAccent)));
            isGettingMiniature=false;

            myOcrBuilder.getCameraSource().takePicture(null, new CameraSource.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes) {

                    Bitmap bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    String miniaturePath=saveMiniatureFile(Bitmap.createScaledBitmap(bitmap,350,450,true));
                    saveDataToFile(miniaturePath);

                    endActivity();
                }
            });


        }
    }


    private void saveDataToFile(String miniaturePath) {
        DataWriterToFile dataWriterToFile = new DataWriterToFile();
        dataWriterToFile.setFilePath(filePath);
        try {
            dataWriterToFile.writeToPath(myOcrBuilder.getRecognizedTextView().getText().toString() +" "+ miniaturePath+" ",true);
        } catch (Exception e) {
            if(e.getMessage().equals("Failed to write to file")) {
                Toast.makeText(getApplicationContext(), getString(R.string.cant_write_to_file), Toast.LENGTH_SHORT).show();
            }else{
                e.printStackTrace();
            }
        }
        endActivity();
    }


    private void showMiniatureDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.miniature_dialog_title));
        builder.setMessage(getString(R.string.miniature_dialog_message));
        builder.setPositiveButton(getString(R.string.affermative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getMiniature();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.negative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        saveDataToFile("noMiniature");
                    }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void getMiniature() {
        findViewById(R.id.fabSaveCurrentPrice).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.fab_miniature_color)));
        isGettingMiniature=true;
        Toast.makeText(getApplicationContext(), getString(R.string.take_photo_to_product), Toast.LENGTH_SHORT).show();
    }

    private String saveMiniatureFile(Bitmap bitmap){
        String filename="noMiniature";
        try {
            String configurationDir = filePath.substring(0, filePath.lastIndexOf(File.separator));
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyy_MMdd_HH_mm_ss").format(new Date());
            filename=configurationDir + File.separator + "miniature_"+ timeStamp + ".png";
        }catch (Exception e){
            Toast.makeText(this,getString(R.string.cant_write_to_file),Toast.LENGTH_SHORT).show();
            return filename;
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
            Toast.makeText(this,getString(R.string.cant_write_to_file),Toast.LENGTH_SHORT).show();
        }
        return filename;
    }


    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void endActivity() {
        finish();
        overridePendingTransition(R.anim.end_ocr_scan_enter,R.anim.end_ocr_scan_exit);
    }


}
