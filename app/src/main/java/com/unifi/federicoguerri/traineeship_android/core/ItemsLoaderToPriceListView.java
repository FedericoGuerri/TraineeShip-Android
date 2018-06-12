package com.unifi.federicoguerri.traineeship_android.core;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.unifi.federicoguerri.traineeship_android.R;

import java.text.DecimalFormat;
import java.util.List;

public class ItemsLoaderToPriceListView {

    private DataLoaderFromFile loaderFromFile;
    private String pricesPath;
    private MenuItem totalItem;
    private Activity activity;
    private ListView pricesList;
    private LinearLayout welcomeLayout;

    public ItemsLoaderToPriceListView(DataLoaderFromFile loaderFromFile, String pricesPath, MenuItem totalItem, Activity activity){
        this.loaderFromFile = loaderFromFile;
        this.pricesPath = pricesPath;
        this.totalItem = totalItem;
        this.activity = activity;

        pricesList = activity.findViewById(R.id.pricesListViewMainActivity);
        welcomeLayout=activity.findViewById(R.id.welcomeLayoutMainActivity);
    }

    public void loadItems(){
        try {
            loaderFromFile.loadFileFromPath(pricesPath);
            List<CustomDataSet> dataSets = loaderFromFile.getRecords();
            if(!dataSets.isEmpty()) {
                final CustomAdapter adapter = new CustomAdapter(dataSets, activity.getApplicationContext(),pricesPath);
                adapter.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        totalItem.setTitle(formatTotalPrice());
                    }
                    private String formatTotalPrice(){
                        String total=String.valueOf(new DecimalFormat("##.##").format(adapter.getTotal()));
                        if(total.equals("0")){
                            total="0.0";
                        }
                        return total;
                    }
                });
                pricesList.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                    @Override
                    public void onChildViewAdded(View view, View view1) {
                    }

                    @Override
                    public void onChildViewRemoved(View view, View view1) {
                        if(((ListView)view).getChildCount()==0){
                            view.setVisibility(View.INVISIBLE);
                            welcomeLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
                pricesList.setAdapter(adapter);
                pricesList.setVisibility(View.VISIBLE);
                welcomeLayout.setVisibility(View.INVISIBLE);
            }else{
                pricesList.setVisibility(View.INVISIBLE);
                welcomeLayout.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),activity.getText(R.string.cant_read_from_file),Toast.LENGTH_SHORT).show();
            pricesList.setVisibility(View.INVISIBLE);
            welcomeLayout.setVisibility(View.VISIBLE);
        }
    }
}
