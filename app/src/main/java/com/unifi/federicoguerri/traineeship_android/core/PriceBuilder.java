package com.unifi.federicoguerri.traineeship_android.core;


class PriceBuilder{

    private static final String RECOGNITION_ERROR = "recognitionError";
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
        recognized = recognized.trim().replaceAll(" +", " ");
        recognized = recognized.replaceAll("[.]", ",");
        String[] prices = recognized.split(" ");
        choosePrice(prices);
    }


    private void choosePrice(String[] prices) {
        recognized=prices[0];
        for(int i=1;i<prices.length;i++){
            if(prices[i].contains(",")) {
                if (!recognized.contains(",")) {
                    recognized = prices[i];
                } else {
                    if (prices[i].lastIndexOf(',') <= recognized.lastIndexOf(',') && hasMoreFloatNumbers(prices[i])) {
                        recognized = prices[i];
                    }
                }
            }
        }
    }

    private boolean hasMoreFloatNumbers(String price){
        return price.length() - price.lastIndexOf(',') < recognized.length() - recognized.lastIndexOf(',') || recognized.length() > price.length();
    }

    private void formatPrice() throws CustomException{
        if(recognized.length()>7){
            throw new CustomException(RECOGNITION_ERROR);
        }
        if(recognized.lastIndexOf(',')==recognized.length()-1 || recognized.startsWith(",")){
            throw new CustomException(RECOGNITION_ERROR);
        }
        if(recognized.lastIndexOf(',') != recognized.indexOf(',')){
            throw new CustomException(RECOGNITION_ERROR);
        }
    }


}
