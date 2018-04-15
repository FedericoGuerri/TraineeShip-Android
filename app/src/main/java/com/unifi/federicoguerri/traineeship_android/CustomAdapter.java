package com.unifi.federicoguerri.traineeship_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.core.DataWriterToFile;
import com.unifi.federicoguerri.traineeship_android.core.RecordRemoverFromString;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<CustomDataSet> {

    private Context mContext;
    private String filePath;
    private CustomAdapter customAdapter;
    private ArrayList<CustomDataSet> data;
    private RecordRemoverFromString recordRemoverFromString;
    private DataWriterToFile dataWriterToFile;


    public CustomAdapter(ArrayList<CustomDataSet> data, Context context,String filePath) {
        super(context, R.layout.item_price_listview, data);
        this.mContext=context;
        this.filePath=filePath;
        customAdapter=this;
        this.data=data;
        recordRemoverFromString =new RecordRemoverFromString();
        dataWriterToFile=new DataWriterToFile();
        dataWriterToFile.setFilePath(filePath);
    }

    public static class ViewHolder {
        public TextView priceTextView;
        public ImageView miniatureImageView;
        public ImageView deleteImageView;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View rowView = convertView;
        final CustomDataSet dataSet=getItem(position);
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.item_price_listview, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.priceTextView = rowView.findViewById(R.id.itemPriceTextViewPricesListView);
            viewHolder.miniatureImageView = rowView.findViewById(R.id.itemMiniatureImageViewItemPriceListView);
            viewHolder.deleteImageView=rowView.findViewById(R.id.itemDeletePriceImageViewitemPriceListView);
            rowView.setTag(viewHolder);
        }
        final ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.miniatureImageView.setImageDrawable(dataSet.getMiniature());
        holder.priceTextView.setText(String.valueOf(dataSet.getPrice()));
        final View finalRowView = rowView;
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String modifiedData= recordRemoverFromString.remove(readFromFile(),position);
                    dataWriterToFile.writeToPath(modifiedData,false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                parent.removeViewInLayout(finalRowView);
                data.remove(position);
                customAdapter.notifyDataSetChanged();
            }
        });
        return rowView;
    }

    private String readFromFile() {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString().substring(0,contentBuilder.toString().length()-1);
    }
}
