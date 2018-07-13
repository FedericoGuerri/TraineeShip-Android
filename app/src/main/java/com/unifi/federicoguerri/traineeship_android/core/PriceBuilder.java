package com.unifi.federicoguerri.traineeship_android.core;


import android.graphics.Rect;

import com.google.android.gms.vision.text.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PriceBuilder{

    private static final String RECOGNITION_ERROR = "recognitionError";
    private String recognized;

    PriceBuilder(String price){
        recognized=price;
    }

    public String getPrice() throws CustomException{
        chooseMoreProbablePrice();
        checkCorrectPriceFormat();
        return recognized;
    }

    private void chooseMoreProbablePrice() throws CustomException{
        Matcher matcher = getAllPrices();
        recognized=matcher.group();
        while (matcher.find()){
            String possiblePrice=matcher.group();
            if(possiblePrice.contains(",")){
                if(!recognized.contains(",")){
                    recognized=possiblePrice;
                }else{
                    recognized = checkWitchPriceHasMoreFloatDigits(possiblePrice, recognized);
                }
            }
        }
    }

    private String checkWitchPriceHasMoreFloatDigits(String price1, String price2) {
        if(price1.indexOf(',')<price2.indexOf(',')){
            return price1;
        }
        return price2;
    }

    private Matcher getAllPrices() throws CustomException {
        recognized = recognized.replaceAll("[.]", ",");
        final Pattern pattern = Pattern.compile("[0-9]+([,][0-9]{0,2})?");
        String data=" "+recognized;
        Matcher matcher = pattern.matcher(data);
        matcher.matches();
        if(!matcher.find()){
            throw new CustomException(RECOGNITION_ERROR);
        }
        return matcher;
    }


    private void checkCorrectPriceFormat() throws CustomException{
        if(recognized.length()>7){
            throw new CustomException(RECOGNITION_ERROR);
        }
        if(recognized.endsWith(",") ){
            throw new CustomException(RECOGNITION_ERROR);
        }
        if(recognized.startsWith("0") && !recognized.contains(",")){
            throw new CustomException(RECOGNITION_ERROR);
        }
    }

    public Rect choosePriceThatFitsInTargetRect(List<Text> lines, Rect defaultRect) {
        for (Text currentText : lines) {
            if (currentText.getValue().contains(recognized)) {
                return currentText.getBoundingBox();
            }
        }
        return defaultRect;
    }


}
