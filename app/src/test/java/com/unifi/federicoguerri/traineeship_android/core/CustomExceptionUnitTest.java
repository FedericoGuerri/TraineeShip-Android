package com.unifi.federicoguerri.traineeship_android.core;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CustomExceptionUnitTest {

    private final String message = "message";
    private CustomException customException;

    @Before
    public void createObjectWithConstructor(){
        customException = new CustomException(message);
    }

    @Test
    public void customException_hasMessage_fromConstructor(){
        assertEquals(message,customException.getMessage());
    }


}
