package com.unifi.federicoguerri.traineeship_android.core.PricesListSettingUp;

import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.reactiveandroid.ReActiveAndroid;
import com.reactiveandroid.ReActiveConfig;
import com.reactiveandroid.internal.database.DatabaseConfig;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseHelper;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabasePrice;
import com.unifi.federicoguerri.traineeship_android.core.database.DatabaseReactiveAndroid;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.ItemsLoaderToPriceListView;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.PricesListDataSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class ItemsLoaderToPriceListViewUnitTest {

    private MainActivity activity= Robolectric.buildActivity(MainActivity.class).create().visible().get();
    @Mock
    private DatabaseHelper helper;
    @Mock
    private MenuItem menuItem;
    @InjectMocks
    private ItemsLoaderToPriceListView itemsLoaderToPriceListView=new ItemsLoaderToPriceListView(helper,menuItem,activity);


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        ReActiveAndroid.init(new ReActiveConfig.Builder(RuntimeEnvironment.application)
                .addDatabaseConfigs(new DatabaseConfig.Builder(DatabaseReactiveAndroid.class).addModelClasses(DatabasePrice.class)
                        .build())
                .build());
    }

    @After
    public void tearDown(){
       ReActiveAndroid.destroy();
    }

    @Test
    public void itemsLoader_setWelcomeLayoutToVisible_ifExceptionOccurred_AfterReadingRecords()  {
        when(helper.getPricesAsDataSet()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.welcomeLayoutMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setPricesListToInvisible_ifExceptionOccurred_AfterReadingRecords()  {
        when(helper.getPricesAsDataSet()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_showsAToastMessage_withTextFromResources_AfterReadingRecords()  {
        when(helper.getPricesAsDataSet()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(activity.getText(R.string.cant_read_from_file), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void itemsLoader_setWelcomeLayoutToVisible_ifExceptionOccurred_AfterLoadFile(){
        when(helper.getPricesAsDataSet()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.welcomeLayoutMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setPricesListToInvisible_ifExceptionOccurred_AfterLoadFile() {
        when(helper.getPricesAsDataSet()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_showsAToastMessage_withTextFromResources_AfterLoadFile()  {
        when(helper.getPricesAsDataSet()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(activity.getText(R.string.cant_read_from_file), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void itemsLoader_setsListViewAdapter()  {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        array.add(new PricesListDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        ListView listView=activity.findViewById(R.id.pricesListViewMainActivity);
        assertNotNull(listView.getAdapter());
    }

    @Test
    public void itemsLoader_setsWelcomeLayoutVisible_ifThereAreNoRecords(){
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.welcomeLayoutMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setsPricesListInvisible_ifThereAreNoRecords() {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setsPricesListInvisible_ifAllRecordsWereRemoved() {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        array.add(new PricesListDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        activity.findViewById(R.id.itemDeletePriceImageViewitemPriceListView).performClick();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_notModifyPricesListVisibility_ifThereAreRecordsRemaining() {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        array.add(new PricesListDataSet(22.2f,null,0));
        array.add(new PricesListDataSet(0.2f,null,1));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        activity.findViewById(R.id.itemDeletePriceImageViewitemPriceListView).performClick();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_callsMenuItemTotalsetTitle() {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        array.add(new PricesListDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        verify(menuItem,atLeastOnce()).setTitle(any(String.class));
    }

    /*
    @Test
    public void itemsLoader_setsMenuItemTotalTitle_toRecordValue() throws Exception {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        array.add(new PricesListDataSet(22.2f,null));
        when(loaderFromFile.getRecords()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        verify(menuItem,atLeastOnce()).setTitle("22,2");
    }
    */

    @Test
    public void itemsLoader_setsMenuItemTotalTitle_toZero_ifThereAreNoRecords() {
        ArrayList<PricesListDataSet> array=new ArrayList<>();
        array.add(new PricesListDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        activity.findViewById(R.id.itemDeletePriceImageViewitemPriceListView).performClick();
        verify(menuItem,atLeastOnce()).setTitle("0.0");
    }

}
