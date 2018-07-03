package com.unifi.federicoguerri.traineeship_android.core;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertEquals;

public class PriceBuilderUnitTest {

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
    public void priceBuilder_returnsThefirstPrice_withComma_IfThereAreMoreCommas() throws Exception {
        readPrice("3,4,4");
        assertEquals("3,4",priceBuilder.getPrice());
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

    @Test
    public void priceBuilder_returnsTheMostProbablePriceFormattedRecognition_whenInFirstPosition() throws Exception {
        readPrice("33,99someWords1111");
        assertEquals("33,99",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_returnsTheMostProbablePriceFormattedRecognition_whenInLastPosition() throws Exception {
        readPrice("11111someWords33,99");
        assertEquals("33,99",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_returnsPriceWithLessNumbers_atCommaLeft() throws Exception {
        readPrice("1111,1someWords3,99some*__33,01");
        assertEquals("3,99",priceBuilder.getPrice());
    }

    @Test
    public void priceBuilder_returnsPriceWithLessNumbers_atCommaRight_ifSameNumbersAtCommaLeft() throws Exception {
        readPrice("0,99Words3,99some*__33,0");
        assertEquals("0,99",priceBuilder.getPrice());
    }


}
