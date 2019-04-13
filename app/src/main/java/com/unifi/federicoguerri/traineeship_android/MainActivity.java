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
import android.widget.ListView;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseHelper;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabasePrice;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseReactiveAndroid;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.ItemsLoaderToPriceListView;


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
        DatabaseConfig appDatabase = new DatabaseConfig.Builder(DatabaseReactiveAndroid.class)
                .addModelClasses(DatabasePrice.class)
                .build();
        ReActiveAndroid.init(new ReActiveConfig.Builder(this)
                .addDatabaseConfigs(appDatabase)
                .build());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        readIdFromSharedPreferences();
        if(itemsLoaderToPriceListView!=null) {
            itemsLoaderToPriceListView.loadItems(this.findViewById(R.id.welcomeLayoutMainActivity),(ListView)this.findViewById(R.id.pricesListViewMainActivity));
        }
    }


    public void startOcrScanActivity(View view) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION_CONFIGURATION);
            return;
        }
        launchActivity();
    }


    private void launchActivity(){
        Intent intent = new Intent(this, OcrScanActivity.class);
        intent.putExtra("nextIndex",id);
        startActivity(intent);
        id++;
        savePriceIdToSharedPreferences();
        overridePendingTransition(R.anim.new_ocr_scan_enter,R.anim.new_ocr_scan_exit);
    }

    private void readIdFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        id = prefs.getInt(ID_PRICE_COUNT, 0);
    }

    private void savePriceIdToSharedPreferences() {
        SharedPreferences.Editor editor = getSharedPreferencesEditor();
        editor.putInt(ID_PRICE_COUNT, id);
        editor.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_PERMISSION_CONFIGURATION && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            launchActivity();
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
        removePriceIdCountFromDatabase();
        DatabaseHelper.getHelper().deleteAllPrices();
        ReActiveAndroid.destroy();
    }

    private void removePriceIdCountFromDatabase() {
        SharedPreferences.Editor edit = getSharedPreferencesEditor();
        edit.remove(ID_PRICE_COUNT);
        edit.commit();
    }

    private SharedPreferences.Editor getSharedPreferencesEditor() {
        SharedPreferences prefs= getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.edit();
    }

}
