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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating dataFolder if not there
        File configDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/ConfigurationDir");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        //Getting saved data
        DataLoaderFromFile loaderFromFile = new DataLoaderFromFile();
        try {
            loaderFromFile.loadFileFromPath(configDir.getAbsolutePath() + "/" + "prices.txt");
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
        startActivity(intent);
    }



}
