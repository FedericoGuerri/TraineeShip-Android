package com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseHelper;

import java.text.DecimalFormat;

public class ItemsLoaderToPriceListView {

    private DatabaseHelper helper;
    private MenuItem totalItem;
    private Context context;

    public ItemsLoaderToPriceListView(DatabaseHelper helper, MenuItem totalItem, Context context){
        this.helper = helper;
        this.totalItem = totalItem;
        this.context = context;
    }

    public void loadItems(final View welcomeLayout, final ListView pricesList){
        try {
            if(!helper.isDatabaseEmpty()) {
                final PricesListAdapter adapter = new PricesListAdapter(helper.getPricesAsDataSet(), context);
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
            Toast.makeText(context,context.getText(R.string.cant_read_from_file),Toast.LENGTH_SHORT).show();
            pricesList.setVisibility(View.INVISIBLE);
            welcomeLayout.setVisibility(View.VISIBLE);
        }
    }
}
