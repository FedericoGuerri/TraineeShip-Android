package com.unifi.federicoguerri.traineeship_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CustomDataSet> {
    private Context mContext;

    public CustomAdapter(ArrayList<CustomDataSet> data, Context context) {
        super(context, R.layout.item_price_listview, data);
        this.mContext=context;
    }

    public static class ViewHolder {
        public TextView priceTextView;
        public ImageView miniatureImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        CustomDataSet dataSet=getItem(position);
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.item_price_listview, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.priceTextView = rowView.findViewById(R.id.itemPriceTextViewPricesListView);
            viewHolder.miniatureImageView = rowView.findViewById(R.id.itemMiniatureImageViewItemPriceListView);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.miniatureImageView.setImageDrawable(dataSet.getMiniature());
        holder.priceTextView.setText(String.valueOf(dataSet.getPrice()));
        return rowView;
    }
}
