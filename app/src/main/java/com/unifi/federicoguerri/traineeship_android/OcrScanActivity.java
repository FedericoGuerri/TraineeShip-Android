package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up.MiniatureHandler;
import com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up.MySurfaceHolderCallback;
import com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up.OcrComponentsBuilder;
import com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up.PostDelayedSettingUpRunnable;

import java.util.Objects;

public class OcrScanActivity extends AppCompatActivity {

    private SurfaceView ocrScanView;
    private OcrComponentsBuilder myOcrBuilder;
    private static final int REQUEST_CAMERA_PERMISSION = 10400;
    private boolean isGettingMiniature = false;

    private FloatingActionButton fabSavePrice;
    private MiniatureHandler miniatureHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_scan);

        ocrScanView = findViewById(R.id.ocrViewOcrScanActivity);
        myOcrBuilder = new OcrComponentsBuilder(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        myOcrBuilder.setCameraSource(new CameraSource.Builder(getApplicationContext(), myOcrBuilder.getTextRecognizer()).setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(displayMetrics.heightPixels, displayMetrics.widthPixels).setRequestedFps(2.0f).setAutoFocusEnabled(true).build());

        ocrScanView.getHolder().addCallback(new MySurfaceHolderCallback(myOcrBuilder, ocrScanView.getHolder()));

        myOcrBuilder.setRecognizedTextView((TextView) findViewById(R.id.recognizedTextViewOcrScanActivity));

        resizeTargetingView(displayMetrics.widthPixels,displayMetrics.heightPixels);

        fabSavePrice =findViewById(R.id.fabSaveCurrentPrice);


        miniatureHandler =new MiniatureHandler(this,myOcrBuilder,getIntent().getIntExtra("nextIndex",0),fabSavePrice);

    }

    private void resizeTargetingView(int width, int height) {
        ViewGroup.LayoutParams params=(findViewById(R.id.textTargetingLayout)).getLayoutParams();
        params.height=height/3;
        params.width=width;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
               endActivity();
            }
            myOcrBuilder.startCamera(ocrScanView.getHolder());
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
            SystemClock.sleep(1500);
            fabSavePrice.setImageResource(R.drawable.ic_format_text);
            fabSavePrice.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this,R.color.colorAccent)));
            isGettingMiniature=false;
            myOcrBuilder.getCameraSource().takePicture(null, miniatureHandler);
        }
    }


    private void showMiniatureDialog() {
        myOcrBuilder.setDetecting(false);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.miniature_dialog_title));
        builder.setMessage(getString(R.string.miniature_dialog_message));
        builder.setPositiveButton(getString(R.string.affermative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        prepareForMiniature();
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(getString(R.string.negative), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        miniatureHandler.saveDataToFile("noMiniature");
                        endActivity();
                        dialog.dismiss();
                    }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                myOcrBuilder.setDetecting(!isGettingMiniature);
            }
        });
    }


    private void prepareForMiniature() {
        findViewById(R.id.textTargetingLayout).animate().alpha(0f);
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


    @Override
    protected void onPostResume() {
        super.onPostResume();
        new PostDelayedSettingUpRunnable(1300,findViewById(R.id.textTargetingLayout),myOcrBuilder);
    }


    private void endActivity() {
        fabSavePrice.setEnabled(false);
        finish();
        overridePendingTransition(R.anim.end_ocr_scan_enter,R.anim.end_ocr_scan_exit);
    }

}
