package com.unifi.federicoguerri.traineeship_android.core.Database;

import android.os.Build;

import com.activeandroid.ActiveAndroid;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.core.database_active_android.DatabasePrice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)
public class DatabasePriceUnitTest {

    private DatabasePrice databasePrice;
    private String priceValue="22.2";
    private String pricePath="";
    private int index=0;

    @Before
    public void init(){
        ActiveAndroid.initialize(RuntimeEnvironment.application);
        databasePrice= new DatabasePrice(priceValue,pricePath,index);
    }

    @After
    public void tearDown(){
        ActiveAndroid.dispose();
    }


    @Test
    public void databasePrice_notEqualsToNullObject(){
        assertFalse(databasePrice.equals(null));
    }

    @Test
    public void databasePrice_equalsToItself(){
        assertTrue(databasePrice.equals(databasePrice));
    }

    @Test
    public void databasePrice_notEqualsOtherClassObject(){
        assertFalse(databasePrice.equals(new String("string")));
    }

    @Test
    public void databasePrice_equalsToOtherObject_withSameId(){
        DatabasePrice otherDatabasePrice=new DatabasePrice(priceValue,pricePath,index);
        assertTrue(databasePrice.equals(otherDatabasePrice));
    }

    @Test
    public void databasePrice_overrideHashCode(){
        assertEquals(databasePrice.hashCode(),databasePrice.hashCode());
    }
}
