package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Bitmap;

public class CustomDataSet {

    private Float price;
    private Bitmap miniature;

    public CustomDataSet(Float price, Bitmap miniature) {
        this.price=price;
        this.miniature=miniature;
    }

    public Float getPrice() {
        return price;
    }

    public Bitmap getMiniature() {
        return miniature;
    }
}
