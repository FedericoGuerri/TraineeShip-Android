package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.unifi.federicoguerri.traineeship_android.core.DataWriterToFile;
import com.unifi.federicoguerri.traineeship_android.core.MySurfaceHolderCallback;
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
    private final int REQUEST_CAMERA_PERMISSION = 10400;
    private String filePath;
    private boolean isGettingMiniature = false;

    private FloatingActionButton fabSavePrice;
    private int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_scan);

        filePath = getIntent().getStringExtra("fileName");
        ocrScanView = findViewById(R.id.ocrViewOcrScanActivity);
        myOcrBuilder = new OcrComponentsBuilder(getApplicationContext());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        //if (myOcrBuilder.getTextRecognizer().isOperational()) {
        myOcrBuilder.setCameraSource(new CameraSource.Builder(getApplicationContext(), myOcrBuilder.getTextRecognizer()).setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(displayMetrics.heightPixels, displayMetrics.widthPixels).setRequestedFps(2.0f).setAutoFocusEnabled(true).build());

        ocrScanView.getHolder().addCallback(new MySurfaceHolderCallback(this, myOcrBuilder, ocrScanView.getHolder(), REQUEST_CAMERA_PERMISSION));

        myOcrBuilder.setRecognizedTextView((TextView) findViewById(R.id.recognizedTextViewOcrScanActivity));

        resizeTargetingView(displayMetrics.widthPixels,displayMetrics.heightPixels);

        fabSavePrice =findViewById(R.id.fabSaveCurrentPrice);
        color=ContextCompat.getColor(this,R.color.colorAccent);

    }

    private void resizeTargetingView(int width, int height) {
        ViewGroup.LayoutParams params=(findViewById(R.id.textTargetingLayout)).getLayoutParams();
        params.height=height/3;
        params.width=width;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA_PERMISSION: {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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


    public void saveCurrentPrice(View view) {
        if(!isGettingMiniature) {
            if (!Objects.equals(myOcrBuilder.getRecognizedTextView().getText().toString(), getString(R.string.bad_recognition_get_closer_please))) {
                showMiniatureDialog();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.no_price_detected_toast), Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fabSavePrice.setImageResource(R.drawable.ic_format_text);
            fabSavePrice.setBackgroundTintList(ColorStateList.valueOf(color));
            isGettingMiniature=false;
            findViewById(R.id.textTargetingLayout).setVisibility(View.VISIBLE);
            myOcrBuilder.getCameraSource().takePicture(null, new CameraSource.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] bytes) {
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    String miniaturePath=saveMiniatureFile(Bitmap.createScaledBitmap(bitmap,350,450,true),filePath);
                    saveDataToFile(miniaturePath,filePath);
                    endActivity();
                }
            });
        }
    }

    private String saveMiniatureFile(Bitmap bitmap, String filePath){
        String filename="noMiniature";
        try {
            String configurationDir = filePath.substring(0, filePath.lastIndexOf(File.separator));
            String timeStamp = new SimpleDateFormat("yyyy_MMdd_HH_mm_ss").format(new Date());
            filename=configurationDir + File.separator + "miniature_"+ timeStamp + ".png";
            FileOutputStream out;
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
            Toast.makeText(this,getText(R.string.cant_write_to_file),Toast.LENGTH_SHORT).show();
            filename="noMiniature";
        }
        return filename;
    }

    private void saveDataToFile(String miniaturePath,String filePath) {
        DataWriterToFile dataWriterToFile= new DataWriterToFile();
        dataWriterToFile.setFilePath(filePath);
        try {
            dataWriterToFile.writeToPath(myOcrBuilder.getRecognizedTextView().getText().toString().replace(",",".") +" "+ miniaturePath+" ",true);
        } catch (Exception e) {
            if(e.getMessage().equals("Failed to write to file")) {
                Toast.makeText(this, getText(R.string.cant_write_to_file), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void showMiniatureDialog() {
        myOcrBuilder.setDetecting(false);
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
                        saveDataToFile("noMiniature",filePath);
                        endActivity();
                        dialog.dismiss();
                    }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(!isGettingMiniature){
                    myOcrBuilder.setDetecting(true);
                }else{
                    myOcrBuilder.setDetecting(false);
                }

            }
        });
    }


    private void getMiniature() {
        findViewById(R.id.textTargetingLayout).setVisibility(View.INVISIBLE);
        fabSavePrice.setImageResource(R.drawable.ic_camera);
        fabSavePrice.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.fab_miniature_color)));
        isGettingMiniature=true;
        Toast.makeText(getApplicationContext(), getString(R.string.take_photo_to_product), Toast.LENGTH_SHORT).show();
        myOcrBuilder.animateTextViewToOriginalPosition();
    }


    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void endActivity() {
        fabSavePrice.setEnabled(false);
        finish();
        overridePendingTransition(R.anim.end_ocr_scan_enter,R.anim.end_ocr_scan_exit);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        new CountDownTimer(2000,1000){
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                int[] l = new int[2];
                findViewById(R.id.textTargetingLayout).getLocationOnScreen(l);
                int x = l[0];
                int y = l[1];
                myOcrBuilder.setRectBounds(new Rect(x,y,findViewById(R.id.textTargetingLayout).getWidth(),y+findViewById(R.id.textTargetingLayout).getHeight()));
                myOcrBuilder.setTextViewCoordinates(myOcrBuilder.getRecognizedTextView().getX(),myOcrBuilder.getRecognizedTextView().getY());
            }
        }.start();
    }

    public OcrComponentsBuilder getMyOcrBuilder() {
        return myOcrBuilder;
    }
}
