package com.unifi.federicoguerri.traineeship_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
        Intent intent = new Intent(this, OcrScanActivity.class);
        intent.putExtra("fileName",pricesPath);
        startActivity(intent);
        overridePendingTransition(R.anim.new_ocr_scan_enter,R.anim.new_ocr_scan_exit);
    }

    private String getFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        return formatter.format(new Date()) + ".txt";
    }


}
