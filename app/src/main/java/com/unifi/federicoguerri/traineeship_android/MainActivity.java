package com.unifi.federicoguerri.traineeship_android;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.activeandroid.query.Delete;
import com.unifi.federicoguerri.traineeship_android.core.database_active_android.DatabaseHelper;
import com.unifi.federicoguerri.traineeship_android.core.database_active_android.DatabasePrice;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.ItemsLoaderToPriceListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREF_NAME = "myConfiguration";
    public static final String ID_PRICE_COUNT = "id_price_count";
    private int id=0;
    private static final int REQUEST_STORAGE_PERMISSION_CONFIGURATION=10800;
    private ItemsLoaderToPriceListView itemsLoaderToPriceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.Builder configuration = new Configuration.Builder(this).setDatabaseName(getFileName());
        configuration.addModelClasses(DatabasePrice.class);
        ActiveAndroid.initialize(configuration.create());

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        readIdFromSharedPreferences();
        if(itemsLoaderToPriceListView!=null) {
            itemsLoaderToPriceListView.loadItems();
        }
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
        intent.putExtra("nextIndex",id);
        startActivity(intent);
        id++;
        savePriceIdToSharedPreferences();
        overridePendingTransition(R.anim.new_ocr_scan_enter,R.anim.new_ocr_scan_exit);
    }

    private void readIdFromSharedPreferences() {
        SharedPreferences prefs;
        prefs = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id = prefs.getInt(ID_PRICE_COUNT, 0);
    }

    private void savePriceIdToSharedPreferences() {
        SharedPreferences prefs= getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ID_PRICE_COUNT, id);
        editor.commit();
    }


    private String getFileName() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        return "prices_"+formatter.format(new Date())+".db";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION_CONFIGURATION && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            lauchActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        itemsLoaderToPriceListView = new ItemsLoaderToPriceListView(DatabaseHelper.getHelper(),menu.findItem(R.id.menuitem_total_mainactivity),this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs= getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(ID_PRICE_COUNT);
        prefs.edit().clear();
        prefs.edit().commit();
        new Delete().from(DatabasePrice.class).execute();
    }

}
