package com.unifi.federicoguerri.traineeship_android;

import android.graphics.drawable.Drawable;

public class CustomDataSet {

    private Float price;
    private Drawable miniature;

    public CustomDataSet(Float price, Drawable miniature) {
        this.price=price;
        this.miniature=miniature;
    }

    public Float getPrice() {
        return price;
    }

    public Drawable getMiniature() {
        return miniature;
    }
}
