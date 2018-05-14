package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Rect;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.text.TextBlock;
import com.unifi.federicoguerri.traineeship_android.R;

public class TextBuilderRunnable implements Runnable {

    private SparseArray<TextBlock> items;
    private TextView recognizedTextView;
    private float originalX;
    private float originalY;
    private Rect rectBounds;
    private Rect itemBox;

    public TextBuilderRunnable(SparseArray<TextBlock> items, TextView recognizedTextView, float originalX, float originalY,Rect rectBounds) {
        this.items = items;
        this.recognizedTextView = recognizedTextView;
        this.originalX = originalX;
        this.originalY = originalY;
        this.rectBounds = rectBounds;
    }

    @Override
    public void run() {
        try {
            StringBuilder stringBuilder=new StringBuilder();
            itemBox=rectBounds;
            for(int j=0;j<items.size();j++) {
                for (int i = 0; i < items.get(j).getComponents().size(); i++) {
                    stringBuilder.append(items.get(j).getComponents().get(i).getValue() + "\n");
                }
            }
            PriceBuilder priceBuilder = new PriceBuilder(stringBuilder.toString());
            String price=priceBuilder.getPrice();
            for(int j=0;j<items.size();j++) {
                for (int i = 0; i < items.get(j).getComponents().size(); i++) {
                    if (items.get(j).getComponents().get(i).getValue().contains(price)) {
                        itemBox = items.get(j).getBoundingBox();
                    }
                }
            }
            recognizedTextView.setText(price);
            recognizedTextView.animate().x(itemBox.left).y(itemBox.top).withEndAction(new Runnable() {
                @Override
                public void run() {
                    recognizedTextView.setX(itemBox.left);
                    recognizedTextView.setY(itemBox.top);
                }
            }).start();
        } catch (Exception e) {
            if (e.getMessage().equals("recognitionError")) {
                if(recognizedTextView.getX()!=originalX || originalY!=recognizedTextView.getY()) {
                    recognizedTextView.animate().scaleY(0f).scaleX(0f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            recognizedTextView.setText(recognizedTextView.getContext().getResources().getString(R.string.bad_recognition_get_closer_please));
                            recognizedTextView.setX(originalX);
                            recognizedTextView.setY(originalY);
                            recognizedTextView.animate().scaleX(1f).scaleY(1f);
                        }
                    }).start();
                }
            }
        }
    }


}
