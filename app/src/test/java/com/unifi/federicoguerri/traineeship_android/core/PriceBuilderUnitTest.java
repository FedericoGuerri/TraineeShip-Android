package com.unifi.federicoguerri.traineeship_android.core;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertEquals;

public class PriceBuilderUnitTest{

    private PriceBuilder priceBuilder;

    @Rule
    public ExpectedException recognitionException = ExpectedException.none();


    private void readPrice(String price) {
        priceBuilder=new PriceBuilder(price);
    }


    @Test
    public void priceBuilder_returnsExceptionIfNoPrices() throws Exception {
        recognitionException.expect(Exception.class);
        recognitionException.expectMessage("recognitionError");
        readPrice("");
        assertEquals("",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_removesCapitalLetters() throws Exception {
        readPrice("ABCDEFGHIJKLMNOPQRSTUVWXYZ22.2");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_removesLetters() throws Exception {
        readPrice("22.2abcdefghijlmnopqrstuvwxyz");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_removesAccentedCharacters() throws Exception {
        readPrice("àèéìòù22.2");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_removesSpecialCharacters() throws Exception {
        readPrice("\\|!<>_§°ç£$%&()?{[()]}\b22.2");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_removesBaseArithmeticOperations() throws Exception {
        readPrice("+-*/:22,2");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_returnsPriceWithComma() throws Exception {
        readPrice("22,2");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_returnsPriceWithCommaIfThereIsADot() throws Exception {
        readPrice("22.2");
        assertEquals("22,2",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_throwsExceptionIfTooLongPrice() throws Exception {
        recognitionException.expect(Exception.class);
        recognitionException.expectMessage("recognitionError");
        readPrice("1223443334,22");
        assertEquals("",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_throwsExceptionIfThereAreMoreCommas() throws Exception {
        recognitionException.expect(Exception.class);
        recognitionException.expectMessage("recognitionError");
        readPrice("3,4,4");
        assertEquals("",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_throwsExceptionIfPriceEndsWithComma() throws Exception {
        recognitionException.expect(Exception.class);
        recognitionException.expectMessage("recognitionError");
        readPrice("222,");
        assertEquals("",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_throwsExceptionIfPriceStartsWithComma() throws Exception {
        recognitionException.expect(Exception.class);
        recognitionException.expectMessage("recognitionError");
        readPrice(",033");
        assertEquals("",priceBuilder.getPrice());
    }



}
