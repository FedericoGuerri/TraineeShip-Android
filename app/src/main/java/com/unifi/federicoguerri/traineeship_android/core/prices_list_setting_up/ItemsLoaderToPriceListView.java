package com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.database_active_android.DatabaseHelper;

import java.text.DecimalFormat;
import java.util.List;

public class ItemsLoaderToPriceListView {

    private DatabaseHelper helper;
    private MenuItem totalItem;
    private Activity activity;
    private ListView pricesList;
    private LinearLayout welcomeLayout;

    public ItemsLoaderToPriceListView(DatabaseHelper helper, MenuItem totalItem, Activity activity){
        this.helper = helper;
        this.totalItem = totalItem;
        this.activity = activity;
        pricesList = activity.findViewById(R.id.pricesListViewMainActivity);
        welcomeLayout=activity.findViewById(R.id.welcomeLayoutMainActivity);
    }

    public void loadItems(){
        try {
            List<PricesListDataSet> dataSets = helper.getPricesAsDataSet();
            if(!dataSets.isEmpty()) {
                final PricesListAdapter adapter = new PricesListAdapter(dataSets, activity.getApplicationContext());
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
                        //Not calling this, children are added "statically"
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
