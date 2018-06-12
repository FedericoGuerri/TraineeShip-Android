package com.unifi.federicoguerri.traineeship_android.core;

public class CustomException extends Exception {


    private String message;

    CustomException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
