package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Bitmap;

public class CustomDataSet {

    private Float price;
    private Bitmap miniature;
    private int id;

    public CustomDataSet(Float price, Bitmap miniature,int id) {
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
