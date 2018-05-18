package com.unifi.federicoguerri.traineeship_android.core;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.unifi.federicoguerri.traineeship_android.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemsLoaderToPriceListView {

    private DataLoaderFromFile loaderFromFile;
    private String pricesPath;
    private MenuItem totalItem;
    private Activity activity;

    public ItemsLoaderToPriceListView(DataLoaderFromFile loaderFromFile, String pricesPath, MenuItem totalItem, Activity activity){
        this.loaderFromFile = loaderFromFile;
        this.pricesPath = pricesPath;
        this.totalItem = totalItem;
        this.activity = activity;
    }

    public void loadItems(){
        try {
            loaderFromFile.loadFileFromPath(pricesPath);
            ArrayList<CustomDataSet> dataSets = loaderFromFile.getRecords();
            if(dataSets.size()>0) {
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
                ((ListView)activity.findViewById(R.id.pricesListViewMainActivity)).setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                    @Override
                    public void onChildViewAdded(View view, View view1) {
                    }

                    @Override
                    public void onChildViewRemoved(View view, View view1) {
                        if(((ListView)view).getChildCount()==0){
                            view.setVisibility(View.INVISIBLE);
                            activity.findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.VISIBLE);
                        }
                    }
                });
                ((ListView) activity.findViewById(R.id.pricesListViewMainActivity)).setAdapter(adapter);
                activity.findViewById(R.id.pricesListViewMainActivity).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.INVISIBLE);
            }else{
                activity.findViewById(R.id.pricesListViewMainActivity).setVisibility(View.INVISIBLE);
                activity.findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(),activity.getText(R.string.cant_read_from_file),Toast.LENGTH_SHORT).show();
            activity.findViewById(R.id.pricesListViewMainActivity).setVisibility(View.INVISIBLE);
            activity.findViewById(R.id.welcomeLayoutMainActivity).setVisibility(View.VISIBLE);
        }
    }
}
