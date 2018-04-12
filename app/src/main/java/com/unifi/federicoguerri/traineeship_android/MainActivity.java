package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.unifi.federicoguerri.traineeship_android.core.DataLoaderFromFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String pricesPath;
    private final int REQUEST_STORAGE_PERMISSION=10800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String configurationDir=Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/ConfigurationDir";
        File configDir = new File(configurationDir);
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        pricesPath=configDir.getAbsolutePath() + "/" + getFileName();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Getting saved data
        DataLoaderFromFile loaderFromFile = new DataLoaderFromFile();
        try {
            loaderFromFile.loadFileFromPath(pricesPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<CustomDataSet> dataSets = loaderFromFile.getRecords();

            if(dataSets.size()>0) {
                //Popolating the ListView
                CustomAdapter adapter = new CustomAdapter(dataSets, getApplicationContext());
                ((ListView) findViewById(R.id.pricesListViewMainActivity)).setAdapter(adapter);
                findViewById(R.id.pricesListViewMainActivity).setVisibility(View.VISIBLE);
                findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.INVISIBLE);
            }else{
                findViewById(R.id.pricesListViewMainActivity).setVisibility(View.INVISIBLE);
                findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            findViewById(R.id.pricesListViewMainActivity).setVisibility(View.INVISIBLE);
            findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.VISIBLE);
        }
    }

    public void startOcrScanActivity(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            return;
        }
        lauchActivity();
    }

    private void lauchActivity(){
        Intent intent = new Intent(this, OcrScanActivity.class);
        intent.putExtra("fileName",pricesPath);
        startActivity(intent);
        overridePendingTransition(R.anim.new_ocr_scan_enter,R.anim.new_ocr_scan_exit);
    }

    private String getFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        return formatter.format(new Date()) + ".txt";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_STORAGE_PERMISSION: {
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    lauchActivity();
                }
            }

        }
    }


}
