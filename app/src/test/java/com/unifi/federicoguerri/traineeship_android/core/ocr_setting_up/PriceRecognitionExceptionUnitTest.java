package com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class PriceRecognitionExceptionUnitTest {

    private final String message = "message";
    private PriceRecognitionException priceRecognitionException;

    @Before
    public void createObjectWithConstructor(){
        priceRecognitionException = new PriceRecognitionException(message);
    }

    @Test
    public void customException_hasMessage_fromConstructor(){
        assertEquals(message, priceRecognitionException.getMessage());
    }


}
