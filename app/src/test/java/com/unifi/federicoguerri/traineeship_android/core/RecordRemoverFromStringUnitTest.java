package com.unifi.federicoguerri.traineeship_android.core;


import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class RecordRemoverFromStringUnitTest {

    private RecordRemoverFromString recordRemoverFromString;

    @Before
    public void init(){
        recordRemoverFromString =new RecordRemoverFromString();
    }

    @Test
    public void recordRemover_canDeleteTheOnlyOneRecord(){
        String data="0.0 noMiniature ";
        assertEquals(" ", recordRemoverFromString.remove(data,0));
    }

    @Test
    public void recordRemover_canDeleteFirstRecord(){
        String data="22.2 noMiniature 0.0 noMiniature ";
        assertEquals("0.0 noMiniature ", recordRemoverFromString.remove(data,0));
    }

    @Test
    public void recordRemover_canDeleteSecondRecord(){
        String data="22.2 noMiniature 0.0 noMiniature ";
        assertEquals("22.2 noMiniature ", recordRemoverFromString.remove(data,1));
    }

    @Test
    public void recordRemover_canDeleteARecordWithinOthers(){
        String data="22.2 noMiniature 0.0 noMiniature 22.3 noMiniature ";
        assertEquals("22.2 noMiniature 22.3 noMiniature ", recordRemoverFromString.remove(data,1));
    }

    @Test
    public void recordRemover_willReturnAStringEndingWithSpace(){
        String data="22.2 noMiniature 0.0 noMiniature 22.3 noMiniature ";
        assertTrue(recordRemoverFromString.remove(data,1).endsWith(" "));
    }

    @Test
    public void recordRemover_willNotModifyRecordsIfIndexTooHigh(){
        String data="22.2 noMiniature 0.0 noMiniature ";
        assertEquals("22.2 noMiniature 0.0 noMiniature ", recordRemoverFromString.remove(data,4));
    }

    @Test
    public void recordRemover_willNotModifyRecordsIfIndexTooLow(){
        String data="22.2 noMiniature 0.0 noMiniature ";
        assertEquals("22.2 noMiniature 0.0 noMiniature ", recordRemoverFromString.remove(data,-2));
    }

    @Test
    public void recordRemover_willNotModifyRecordsIfBadFormatted(){
        String data="22.2noMiniature0.0noMiniature ";
        assertEquals("22.2noMiniature0.0noMiniature ", recordRemoverFromString.remove(data,4));
    }

    @Test
    public void recordRemover_willAlwaysReturnAStringEndingWithSpace(){
        String data="22.2 noMiniature 0.0 noMiniature";
        assertEquals("0.0 noMiniature ", recordRemoverFromString.remove(data,0));
    }

    @Test
    public void recordRemover_canRemoveMoreRecordsFromSameString(){
        String data="22.2 noMiniature 0.0 noMiniature 22.3 noMiniature ";
        assertEquals("22.3 noMiniature ", recordRemoverFromString.remove(recordRemoverFromString.remove(data,0),0));
    }

    @Test
    public void recordRemover_willRemoveSpaceIfFirstCharacter(){
        String data=" 22.2 noMiniature 0.0 noMiniature ";
        assertEquals("22.2 noMiniature ", recordRemoverFromString.remove(data,1));
    }

}
