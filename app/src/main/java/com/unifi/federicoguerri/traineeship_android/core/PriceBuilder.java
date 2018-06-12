package com.unifi.federicoguerri.traineeship_android.core;


class PriceBuilder{

    private final String recognitionMessage = "recognitionError";
    private String recognized;

    PriceBuilder(String price){
        recognized=price;
    }

    public String getPrice() throws CustomException{
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
            if(prices[i].contains(",")) {
                if (!recognized.contains(",")) {
                    recognized = prices[i];
                } else if (recognized.contains(",") && prices[i].lastIndexOf(',') <= recognized.lastIndexOf(',')) {
                    if (prices[i].length() - prices[i].lastIndexOf(',') < recognized.length() - recognized.lastIndexOf(',') || recognized.length() > prices[i].length()) {
                        recognized = prices[i];
                    }
                }
            }
        }
    }

    private void formatPrice() throws CustomException{
        if(recognized.length()>7){
            throw new CustomException(recognitionMessage);
        }
        if(recognized.lastIndexOf(',')==recognized.length()-1 || recognized.startsWith(",")){
            throw new CustomException(recognitionMessage);
        }
        if(recognized.lastIndexOf(',') != recognized.indexOf(',')){
            throw new CustomException(recognitionMessage);
        }
    }


}
