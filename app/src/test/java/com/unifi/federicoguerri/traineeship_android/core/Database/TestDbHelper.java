package com.unifi.federicoguerri.traineeship_android.core.Database;

import com.reactiveandroid.query.Delete;
import com.reactiveandroid.query.Select;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabasePrice;

import java.util.List;

class TestDbHelper {

    DatabasePrice readPriceById(int id) {
        return Select
                .from(DatabasePrice.class)
                .where("id_" + " =?", id)
                .fetchSingle();
    }



    void deletePriceById(int id){
        Delete.from(DatabasePrice.class).where("id_ = ?", id).execute();
    }

    void deleteAllPrices() {
        List<DatabasePrice> list= getAllSavedPrices();
        for(DatabasePrice price : list){
            price.delete();
        }
    }

    List<DatabasePrice> getAllSavedPrices() {
        return Select.from(DatabasePrice.class).orderBy("id_ ASC").fetch();
    }

    void savePrice(String price, String pathToMiniature, int id) {
        new DatabasePrice(price,pathToMiniature,id).save();
    }
}
