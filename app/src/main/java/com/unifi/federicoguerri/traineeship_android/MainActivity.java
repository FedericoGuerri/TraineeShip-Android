package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.core.DataLoaderFromFile;
import com.unifi.federicoguerri.traineeship_android.core.ItemsLoaderToPriceListView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String pricesPath="";
    private static final int REQUEST_STORAGE_PERMISSION_CONFIGURATION=10800;
    private MenuItem totalItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!createConfigurationDirectory()){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION_CONFIGURATION);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ItemsLoaderToPriceListView itemsLoaderToPriceListView =new ItemsLoaderToPriceListView(new DataLoaderFromFile(),pricesPath,totalItem,this);
        itemsLoaderToPriceListView.loadItems();
    }


    public void startOcrScanActivity(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION_CONFIGURATION);
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
        if (requestCode == REQUEST_STORAGE_PERMISSION_CONFIGURATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            createConfigurationDirectory();
        }
    }

    private boolean createConfigurationDirectory(){
        File configDir=new File(getFilesDir()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/ConfigurationDir");
        if (!configDir.exists()) {
           if(configDir.mkdirs()){
                pricesPath=configDir.getPath() + File.separator + getFileName();
            }
        }else{
            pricesPath=configDir.getPath() + File.separator + getFileName();
        }
        return configDir.exists();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        totalItem=menu.findItem(R.id.menuitem_total_mainactivity);
        return super.onCreateOptionsMenu(menu);
    }

}
