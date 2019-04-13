package com.unifi.federicoguerri.traineeship_android.core.Database;

import android.os.Build;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.core.AppDatabaseInitializer;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseHelper;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabasePrice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)

public class DatabaseHelperUnitTest {

    private DatabaseHelper helper;
    private TestDbHelper testDb;

    @Before
    public void init(){
        AppDatabaseInitializer.initDatabase();
        helper= DatabaseHelper.getHelper();
        testDb=new TestDbHelper();
    }

    @After
    public void tearDown(){
        AppDatabaseInitializer.destroyDatabase();
    }

    // Save

    @Test
    public void databaseHelper_canSavePrice_toDatabase(){
        assertTrue(helper.savePrice(new DatabasePrice("","",0))>0);
    }

    @Test
    public void databaseHelper_canSavePriceWithNotEmptyFields_toDatabase(){
        assertTrue(helper.savePrice(new DatabasePrice("price","path",0))>0);
    }

    @Test
    public void databaseHelper_canSaveMorePrices_toDatabase(){
        helper.savePrice(new DatabasePrice());
        assertEquals(1, testDb.getAllSavedPrices().size());
    }

    @Test
    public void databaseHelper_canSaveMorePricesWithNotEmptyFields_toDatabase(){
        helper.savePrice(new DatabasePrice("price","path",0));
        assertEquals(1, testDb.getAllSavedPrices().size());
    }


    // Read

    @Test
    public void databaseHelper_canReadPrice_fromDatabase(){
        testDb.savePrice("","pathToMiniature",1);
        assertEquals("pathToMiniature",helper.readPriceById(1).path);
    }

    @Test
    public void databaseHelper_canReadPrice_fromDatabase_ifThereAreMore(){
        testDb.savePrice("","pathToMiniature1",1);
        testDb.savePrice("","pathToMiniature2",2);
        testDb.savePrice("","pathToMiniature3",3);
        assertEquals("pathToMiniature2",helper.readPriceById(2).path);
    }

    @Test
    public void databaseHelper_savedPricesHasCorrectValue(){
        testDb.savePrice("11.1","",1);
        assertEquals("11.1",helper.readPriceById(1).price);
    }

    @Test
    public void databaseHelper_savedPriceHasCorrectPath(){
        testDb.savePrice("","/data/miniature.jpg",1);
        assertEquals("/data/miniature.jpg",helper.readPriceById(1).path);
    }

    // Delete

    @Test
    public void databaseHelper_canDeleteSavedPrice(){
        testDb.savePrice("","/data/miniature.jpg",1);
        helper.deletePriceById(1);
        assertNull(testDb.readPriceById(1));
    }

    @Test
    public void databaseHelper_canDeletePrices_thenReadOne(){
        testDb.savePrice("","pathToMiniature1",1);
        testDb.savePrice("","pathToMiniature2",2);
        testDb.savePrice("","pathToMiniature3",3);
        helper.deletePriceById(1);
        helper.deletePriceById(2);
        assertNotNull(helper.readPriceById(3));
    }

    @Test
    public void databaseHelper_canDeletePrice_thenSaveItAgain_withSameFields(){
        testDb.savePrice("","pathToMiniature1",1);
        helper.deletePriceById(1);
        helper.savePrice(new DatabasePrice("","pathToMiniature1",1));
        assertNotNull(testDb.readPriceById(1));
    }

    @Test
    public void databaseHelper_canDeletePrice_thenSaveItAgain_withDifferentFields(){
        testDb.savePrice("","pathToMiniature1",1);
        helper.deletePriceById(1);
        helper.savePrice(new DatabasePrice("","pathToMiniature1",2));
        assertNotNull(testDb.readPriceById(2));
    }

    // Get All Prices

    @Test
    public void databaseHelper_canReadAllPrices_withAList(){
        assertEquals(ArrayList.class,helper.getAllPrices().getClass());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_withOnePrice(){
        testDb.savePrice("","pathToMiniature2",1);
        assertEquals(1,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_withMorePrices(){
        testDb.savePrice("","pathToMiniature1",1);
        testDb.savePrice("","pathToMiniature2",2);
        assertEquals(2,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_afterDeletingSome(){
        testDb.savePrice("","pathToMiniature1",1);
        testDb.savePrice("","pathToMiniature2",2);
        testDb.savePrice("","pathToMiniature3",3);
        testDb.deletePriceById(1);
        testDb.deletePriceById(2);
        assertEquals(1,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_readsPricesOrderedById(){
        testDb.savePrice("","pathToMiniature1",2);
        testDb.savePrice("","pathToMiniature2",1);
        assertEquals(1,helper.getAllPrices().get(0).id);
    }

    // Get All Prices As DataSet

    @Test
    public void databaseHelper_canReadAllPrices_withAList_asCustomDataSet(){
        assertEquals(ArrayList.class,helper.getPricesAsDataSet().getClass());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_asCustomDataset_withOnePrice(){
        testDb.savePrice("1.1","noMiniature",1);
        assertEquals(1,helper.getPricesAsDataSet().size());
    }

    @Test
    public void databaseHelper_readsRightPricesCount_asCustomDataset_withMorePrices(){
        testDb.savePrice("1.1","noMiniature",1);
        testDb.savePrice("2.2","noMiniature",2);
        assertEquals(2,helper.getPricesAsDataSet().size());
    }


    @Test
    public void databaseHelper_doesntLoadABitmap_ifPathEqualsTo_noMiniature(){
        testDb.savePrice("1.1","noMiniature",1);
        assertNull(helper.getPricesAsDataSet().get(0).getMiniature());
    }

    @Test
    public void databaseHelper_willLoadABitmap_ifPathNotEqualsTo_noMiniature(){
        testDb.savePrice("1.1","/path/To/miniature",1);
        assertNotNull(helper.getPricesAsDataSet().get(0).getMiniature());
    }

    @Test
    public void databaseHelper_willDeleteAllPrices(){
        testDb.savePrice("1.1","/path/To/miniature",1);
        testDb.savePrice("0.0","/path/To/miniature",2);
        testDb.deleteAllPrices();
        assertEquals(0,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_willNotChangeDatabaseSize_ifThereWasNoPrices(){
        testDb.deleteAllPrices();
        assertEquals(0,helper.getAllPrices().size());
    }

    @Test
    public void databaseHelper_checkIfThereArePrices_withNoPrices(){
        testDb.deleteAllPrices();
        assertEquals(true,helper.isDatabaseEmpty());
    }

    @Test
    public void databaseHelper_checkIfThereArePrices_withMorePrices(){
        testDb.savePrice("1.1","/path/To/miniature",1);
        testDb.savePrice("0.0","/path/To/miniature",2);
        assertEquals(false,helper.isDatabaseEmpty());
    }

    @Test
    public void databaseHelper_createOneInstance(){
        assertEquals(helper.hashCode(),DatabaseHelper.getHelper().hashCode());
    }

}
