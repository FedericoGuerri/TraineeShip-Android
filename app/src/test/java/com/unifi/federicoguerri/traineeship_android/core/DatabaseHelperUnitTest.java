package com.unifi.federicoguerri.traineeship_android.core;

import android.os.Build;
import android.provider.ContactsContract;

import com.activeandroid.ActiveAndroid;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)

public class DatabaseHelperUnitTest {

    private com.unifi.federicoguerri.traineeship_android.core.DatabaseHelper helper;

    @Before
    public void init(){
        ActiveAndroid.initialize(RuntimeEnvironment.application);
        helper=com.unifi.federicoguerri.traineeship_android.core.DatabaseHelper.getHelper();
    }

    @After
    public void tearDown(){
        ActiveAndroid.dispose();
    }


    @Test
    public void databaseHelper_createOneInstance(){
        assertEquals(helper.hashCode(),DatabaseHelper.getHelper().hashCode());
    }


    // Save

    @Test
    public void databaseHelper_canSavePrice_toDatabase(){
        assertTrue(helper.savePrice(new DatabasePrice("","",0)));
    }

    @Test
    public void databaseHelper_canSavePriceWithNotEmptyFields_toDatabase(){
        assertTrue(helper.savePrice(new DatabasePrice("price","path",0)));
    }

    @Test
    public void databaseHelper_canSaveMorePrices_toDatabase(){
        helper.savePrice(new DatabasePrice());
        assertTrue(helper.savePrice(new DatabasePrice()));
    }

    @Test
    public void databaseHelper_canSaveMorePricesWithNotEmptyFields_toDatabase(){
        helper.savePrice(new DatabasePrice("price","path",0));
        assertTrue(helper.savePrice(new DatabasePrice("price1","path1",1)));
    }

    // Read

    @Test
    public void databaseHelper_canReadPrice_fromDatabase(){
        helper.savePrice(new DatabasePrice("","pathToMiniature",1));
        assertEquals("pathToMiniature",helper.readPriceById(1).path);
    }

    @Test
    public void databaseHelper_canReadPrice_fromDatabase_ifThereAreMore(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        helper.savePrice(new DatabasePrice("","pathToMiniature2",2));
        helper.savePrice(new DatabasePrice("","pathToMiniature3",3));
        assertEquals("pathToMiniature2",helper.readPriceById(2).path);
    }

    @Test
    public void databaseHelper_savedPricesHasCorrectValue(){
        helper.savePrice(new DatabasePrice("11.1","",1));
        assertEquals("11.1",helper.readPriceById(1).price);
    }

    @Test
    public void databaseHelper_savedPriceHasCorrectPath(){
        helper.savePrice(new DatabasePrice("","/data/miniature.jpg",1));
        assertEquals("/data/miniature.jpg",helper.readPriceById(1).path);
    }

    // Delete

    @Test
    public void databaseHelper_canDeleteSavedPrice(){
        helper.savePrice(new DatabasePrice("","/data/miniature.jpg",1));
        helper.deletePriceById(1);
        assertNull(helper.readPriceById(1));
    }

    @Test
    public void databaseHelper_canDeletePrices_thenReadOne(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        helper.savePrice(new DatabasePrice("","pathToMiniature2",2));
        helper.savePrice(new DatabasePrice("","pathToMiniature3",3));
        helper.deletePriceById(1);
        helper.deletePriceById(2);
        assertNotNull(helper.readPriceById(3));
    }

    @Test
    public void databaseHelper_canDeletePrice_thenSaveItAgain_withSameFields(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        helper.deletePriceById(1);
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        assertNotNull(helper.readPriceById(1));
    }

    @Test
    public void databaseHelper_canDeletePrice_thenSaveItAgain_withDifferentFields(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        helper.deletePriceById(1);
        helper.savePrice(new DatabasePrice("","pathToMiniature1",2));
        assertNotNull(helper.readPriceById(2));
    }

    // Get All Prices

    @Test
    public void databaseHelper_canReadAllPrices_withAList(){
        assertEquals(ArrayList.class,helper.getAllPrices().getClass());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_withOnePrice(){
        helper.savePrice(new DatabasePrice("","pathToMiniature2",1));
        assertEquals(1,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_withMorePrices(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        helper.savePrice(new DatabasePrice("","pathToMiniature2",2));
        assertEquals(2,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_afterDeletingSome(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        helper.savePrice(new DatabasePrice("","pathToMiniature2",2));
        helper.savePrice(new DatabasePrice("","pathToMiniature3",3));
        helper.deletePriceById(1);
        helper.deletePriceById(2);
        assertEquals(1,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_readsPricesOrderedById(){
        helper.savePrice(new DatabasePrice("","pathToMiniature1",2));
        helper.savePrice(new DatabasePrice("","pathToMiniature2",1));
        assertEquals(1,helper.getAllPrices().get(0).id);
    }

    // Get All Prices As DataSet

    @Test
    public void databaseHelper_canReadAllPrices_withAList_asCustomDataSet(){
        assertEquals(ArrayList.class,helper.getPricesAsDataSet().getClass());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_asCustomDataset_withOnePrice(){
        helper.savePrice(new DatabasePrice("1.1","noMiniature",1));
        assertEquals(1,helper.getPricesAsDataSet().size());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_asCustomDataset_withMorePrices(){
        helper.savePrice(new DatabasePrice("1.1","noMiniature",1));
        helper.savePrice(new DatabasePrice("2.2","noMiniature",2));
        assertEquals(2,helper.getPricesAsDataSet().size());
    }


    @Test
    public void databaseHelper_doesntLoadABitmap_ifPathEqualsTo_noMiniature(){
        helper.savePrice(new DatabasePrice("1.1","noMiniature",1));
        assertNull(helper.getPricesAsDataSet().get(0).getMiniature());
    }

    @Test
    public void databaseHelper_willLoadABitmap_ifPathNotEqualsTo_noMiniature(){
        helper.savePrice(new DatabasePrice("1.1","/path/To/miniature",1));
        assertNotNull(helper.getPricesAsDataSet().get(0).getMiniature());
    }



}
