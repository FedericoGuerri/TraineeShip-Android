package com.unifi.federicoguerri.traineeship_android.core;


public class PriceBuilder{

    private String recognized;

    public PriceBuilder(String price){
        recognized=price;
    }

    public String getPrice() throws Exception{
        removeLetters();
        formatPrice();
        return recognized;
    }

    private void removeLetters() {
        recognized = recognized.replaceAll("[^0-9.,]", "");
    }

    private void formatPrice() throws Exception{

        recognized = recognized.replace(".", ",");

        if(recognized.length()>7){
            throw new Exception("recognitionError");
        }

        if(recognized.lastIndexOf(",")==recognized.length()-1 || recognized.startsWith(",")){
            throw new Exception("recognitionError");
        }

        if(recognized.lastIndexOf(",") != recognized.indexOf(",")){
            throw new Exception("recognitionError");
        }


    }


}
