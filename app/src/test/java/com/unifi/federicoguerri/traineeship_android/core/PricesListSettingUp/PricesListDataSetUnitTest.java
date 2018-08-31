package com.unifi.federicoguerri.traineeship_android.core.PricesListSettingUp;

import android.graphics.Bitmap;

import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.PricesListDataSet;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class PricesListDataSetUnitTest {

    private PricesListDataSet pricesListDataSet;
    private Float price=22.2f;
    private Bitmap miniature=null;
    private int id;

    @Before
    public void createObjectWithConstructor(){
        pricesListDataSet =new PricesListDataSet(price,miniature,id);
    }

    @Test
    public void customDataSet_hasGetterForPrice(){
        assertEquals(price, pricesListDataSet.getPrice());
    }

    @Test
    public void customDataSet_hasGetterForMiniature(){
        assertEquals(miniature, pricesListDataSet.getMiniature());
    }

    @Test
    public void customDataSet_isStoringTheCorrectPrice(){
        assertNotEquals(0.2f, pricesListDataSet.getPrice());
    }

    @Test
    public void customDataSet_isStoringTheCorrectPriceCastedToFloat(){
        assertEquals(22.2f, pricesListDataSet.getPrice());
    }




}
