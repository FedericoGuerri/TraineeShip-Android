package com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up;

import android.graphics.Bitmap;

public class PricesListDataSet {

    private Float price;
    private Bitmap miniature;
    private int id;

    public PricesListDataSet(Float price, Bitmap miniature, int id) {
        this.price=price;
        this.miniature=miniature;
        this.id=id;
    }

    public Float getPrice() {
        return price;
    }

    public Bitmap getMiniature() {
        return miniature;
    }

    public int getId() {
        return id;
    }
}
