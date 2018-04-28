package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class CustomDataSetUnitTest {

    private CustomDataSet customDataSet;
    private Float price=22.2f;
    private Bitmap miniature=null;

    @Before
    public void createObjectWithConstructor(){
        customDataSet=new CustomDataSet(price,miniature);
    }

    @Test
    public void customDataSet_hasGetterForPrice(){
        assertEquals(price,customDataSet.getPrice());
    }

    @Test
    public void customDataSet_hasGetterForMiniature(){
        assertEquals(miniature,customDataSet.getMiniature());
    }

    @Test
    public void customDataSet_isStoringTheCorrectPrice(){
        assertNotEquals(0.2f,customDataSet.getPrice());
    }

    @Test
    public void customDataSet_isStoringTheCorrectPriceCastedToFloat(){
        assertEquals(22.2f,customDataSet.getPrice());
    }




}
