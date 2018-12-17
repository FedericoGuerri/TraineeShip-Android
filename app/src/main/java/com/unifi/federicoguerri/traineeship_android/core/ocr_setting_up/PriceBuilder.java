package com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up;


import android.graphics.Rect;

import com.google.android.gms.vision.text.Text;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PriceBuilder{

    private static final String RECOGNITION_ERROR = "recognitionError";
    private String recognized;

    public PriceBuilder(String price){
        recognized=price;
    }

    public String getPrice() throws PriceRecognitionException {
        chooseMoreProbablePrice();
        checkCorrectPriceFormat();
        return recognized;
    }

    private void chooseMoreProbablePrice() throws PriceRecognitionException {
        Matcher matcher = getAllPrices();
        recognized=matcher.group();
        while (matcher.find()){
            String possiblePrice=matcher.group();
            if(possiblePrice.contains(",")){
                if(!recognized.contains(",")){
                    recognized=possiblePrice;
                }else{
                    recognized = checkWhichPriceHasMoreFloatDigits(possiblePrice, recognized);
                }
            }
        }
    }

    private String checkWhichPriceHasMoreFloatDigits(String price1, String price2) {
        if(price1.indexOf(',')<price2.indexOf(',')){
            return price1;
        }
        return price2;
    }

    private Matcher getAllPrices() throws PriceRecognitionException {
        recognized = recognized.replaceAll("[.]", ",");
        final Pattern pattern = Pattern.compile("[0-9]+([,][0-9]{0,2})?");
        String data=" "+recognized;
        Matcher matcher = pattern.matcher(data);
        matcher.matches();
        if(!matcher.find()){
            throw new PriceRecognitionException(RECOGNITION_ERROR);
        }
        return matcher;
    }


    private void checkCorrectPriceFormat() throws PriceRecognitionException {
        if(recognized.length()>7){
            throw new PriceRecognitionException(RECOGNITION_ERROR);
        }
        if(recognized.endsWith(",") ){
            throw new PriceRecognitionException(RECOGNITION_ERROR);
        }
        if(recognized.startsWith("0") && !recognized.contains(",")){
            throw new PriceRecognitionException(RECOGNITION_ERROR);
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
