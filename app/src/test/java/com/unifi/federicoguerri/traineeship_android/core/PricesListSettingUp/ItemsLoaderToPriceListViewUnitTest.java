package com.unifi.federicoguerri.traineeship_android.core.PricesListSettingUp;

import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.database_active_android.DatabaseHelper;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.CustomDataSet;
import com.unifi.federicoguerri.traineeship_android.core.prices_list_setting_up.ItemsLoaderToPriceListView;

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
        ActiveAndroid.initialize(RuntimeEnvironment.application);
    }

    @After
    public void tearDown(){
        ActiveAndroid.dispose();
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
        ArrayList<CustomDataSet> array=new ArrayList<>();
        array.add(new CustomDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        ListView listView=activity.findViewById(R.id.pricesListViewMainActivity);
        assertNotNull(listView.getAdapter());
    }

    @Test
    public void itemsLoader_setsWelcomeLayoutVisible_ifThereAreNoRecords(){
        ArrayList<CustomDataSet> array=new ArrayList<>();
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.welcomeLayoutMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setsPricesListInvisible_ifThereAreNoRecords() {
        ArrayList<CustomDataSet> array=new ArrayList<>();
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setsPricesListInvisible_ifAllRecordsWereRemoved() {
        ArrayList<CustomDataSet> array=new ArrayList<>();
        array.add(new CustomDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        activity.findViewById(R.id.itemDeletePriceImageViewitemPriceListView).performClick();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_notModifyPricesListVisibility_ifThereAreRecordsRemaining() {
        ArrayList<CustomDataSet> array=new ArrayList<>();
        array.add(new CustomDataSet(22.2f,null,0));
        array.add(new CustomDataSet(0.2f,null,1));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        activity.findViewById(R.id.itemDeletePriceImageViewitemPriceListView).performClick();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_callsMenuItemTotalsetTitle() {
        ArrayList<CustomDataSet> array=new ArrayList<>();
        array.add(new CustomDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        verify(menuItem,atLeastOnce()).setTitle(any(String.class));
    }

    /*
    @Test
    public void itemsLoader_setsMenuItemTotalTitle_toRecordValue() throws Exception {
        ArrayList<CustomDataSet> array=new ArrayList<>();
        array.add(new CustomDataSet(22.2f,null));
        when(loaderFromFile.getRecords()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        verify(menuItem,atLeastOnce()).setTitle("22,2");
    }
    */

    @Test
    public void itemsLoader_setsMenuItemTotalTitle_toZero_ifThereAreNoRecords() {
        ArrayList<CustomDataSet> array=new ArrayList<>();
        array.add(new CustomDataSet(22.2f,null,0));
        when(helper.getPricesAsDataSet()).thenReturn(array);
        itemsLoaderToPriceListView.loadItems();
        activity.findViewById(R.id.itemDeletePriceImageViewitemPriceListView).performClick();
        verify(menuItem,atLeastOnce()).setTitle("0.0");
    }

}