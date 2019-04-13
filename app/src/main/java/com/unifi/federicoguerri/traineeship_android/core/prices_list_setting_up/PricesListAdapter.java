package com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseHelper;

import java.util.List;

public class PricesListAdapter extends ArrayAdapter<PricesListDataSet> {

    private Context mContext;
    private PricesListAdapter adapter;
    private List<PricesListDataSet> data;

    public PricesListAdapter(List<PricesListDataSet> data, Context context) {
        super(context, R.layout.item_price_listview, data);
        this.mContext=context;
        adapter =this;
        this.data=data;
    }


    public static class ViewHolder {
        TextView priceTextView;
        ImageView miniatureImageView;
        ImageView deleteImageView;
        //TextView idTextView;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View rowView = convertView;
        final PricesListDataSet dataSet= data.get(position);
        ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.item_price_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.priceTextView = rowView.findViewById(R.id.itemPriceTextViewPricesListView);
            viewHolder.miniatureImageView = rowView.findViewById(R.id.itemMiniatureImageViewItemPriceListView);
            viewHolder.deleteImageView=rowView.findViewById(R.id.itemDeletePriceImageViewitemPriceListView);
            //viewHolder.idTextView = rowView.findViewById(R.id.itemIdTextViewPricesListView);
            rowView.setTag(viewHolder);
            adapter.notifyDataSetChanged();
        }else{
            viewHolder = (ViewHolder) rowView.getTag();
        }

        if(dataSet.getMiniature()!=null) {
            viewHolder.miniatureImageView.setImageBitmap(dataSet.getMiniature());
        }else{
            viewHolder.miniatureImageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.no_miniature_placeholder));
        }
        viewHolder.priceTextView.setText(String.valueOf(dataSet.getPrice()));
        //viewHolder.idTextView.setText(String.valueOf(dataSet.getId()));
        viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper.getHelper().deletePriceById(dataSet.getId());
                remove(dataSet);
                adapter.notifyDataSetChanged();
            }
        });
        return rowView;
    }


    public float getTotal(){
        float total=0.0f;
        for(PricesListDataSet item: data){
            total=total+item.getPrice();
        }
        return total;
    }
}
