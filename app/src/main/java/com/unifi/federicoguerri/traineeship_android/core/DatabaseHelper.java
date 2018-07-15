package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static DatabaseHelper helper;

    public static DatabaseHelper getHelper(){
        if (helper == null) {
            helper = new DatabaseHelper();
        }
        return helper;
    }

    public DatabasePrice readPriceById(int id) {
        return new Select()
                .from(DatabasePrice.class)
                .where("id_" + " =?", id)
                .executeSingle();
    }

    public long savePrice(DatabasePrice price){
        return price.save();
    }


    public void deletePriceById(int id){
        new Delete().from(DatabasePrice.class).where("id_ = ?", id).execute();
    }

    public List<DatabasePrice> getAllPrices() {
        return new Select()
                .from(DatabasePrice.class)
                .orderBy("id_ ASC")
                .execute();
    }


    public List<CustomDataSet> getPricesAsDataSet() {
        ArrayList<CustomDataSet> prices=new ArrayList<>();
        for(DatabasePrice databasePrice: this.getAllPrices()){
            Bitmap miniature=null;
            if(!databasePrice.path.equals("noMiniature")){
                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inPreferredConfig = Bitmap.Config.ARGB_8888;
                miniature=BitmapFactory.decodeFile(databasePrice.path);
            }
            prices.add(new CustomDataSet(Float.valueOf(databasePrice.price),miniature,databasePrice.id));
        }
        return prices;
    }
}
