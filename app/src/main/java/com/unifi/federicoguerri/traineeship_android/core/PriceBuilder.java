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
        recognized = recognized.replaceAll("[^0-9.,]", " ");
        recognized=recognized.trim().replaceAll(" +", " ");
        recognized = recognized.replace(".", ",");
        String[] prices = recognized.split(" ");

        recognized=prices[0];
        for(int i=1;i<prices.length;i++){
            if(prices[i].contains(",") && !recognized.contains(",")){
                recognized=prices[i];
            }else if(prices[i].contains(",") && recognized.contains(",")){
                if(prices[i].lastIndexOf(",")<=recognized.lastIndexOf(",")){
                    if(prices[i].length()-prices[i].lastIndexOf(",")<recognized.length()-recognized.lastIndexOf(",") || recognized.length()>prices[i].length()) {
                        recognized = prices[i];
                    }
                }
            }
        }
    }

    private void formatPrice() throws Exception{

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
