package com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up;

public class PriceRecognitionException extends Exception {


    private final String message;

    public PriceRecognitionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
