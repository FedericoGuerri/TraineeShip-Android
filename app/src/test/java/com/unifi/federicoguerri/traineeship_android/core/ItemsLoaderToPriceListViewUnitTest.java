package com.unifi.federicoguerri.traineeship_android.core;

import android.os.Build;
import android.view.MenuItem;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class ItemsLoaderToPriceListViewUnitTest {

    private MainActivity activity= Robolectric.buildActivity(MainActivity.class).create().visible().get();
    @Mock
    private DataLoaderFromFile loaderFromFile;
    @Mock
    private MenuItem menuItem;
    @InjectMocks
    private ItemsLoaderToPriceListView itemsLoaderToPriceListView=new ItemsLoaderToPriceListView(loaderFromFile,"",menuItem,activity);


    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itemsLoader_setWelcomeLayoutToVisible_ifExceptionOccurred_AfterReadingRecords() throws Exception {
        when(loaderFromFile.getRecords()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.welcomeLayoutMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setPricesListToInvisible_ifExceptionOccurred_AfterReadingRecords() throws Exception {
        when(loaderFromFile.getRecords()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_showsAToastMessage_withTextFromResources_AfterReadingRecords() throws Exception {
        when(loaderFromFile.getRecords()).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(activity.getText(R.string.cant_read_from_file), ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void itemsLoader_setWelcomeLayoutToVisible_ifExceptionOccurred_AfterLoadFile() throws Exception {
        when(loaderFromFile.loadFileFromPath("")).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.VISIBLE,activity.findViewById(R.id.welcomeLayoutMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_setPricesListToInvisible_ifExceptionOccurred_AfterLoadFile() throws Exception {
        when(loaderFromFile.loadFileFromPath("")).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(View.INVISIBLE,activity.findViewById(R.id.pricesListViewMainActivity).getVisibility());
    }

    @Test
    public void itemsLoader_showsAToastMessage_withTextFromResources_AfterLoadFile() throws Exception {
        when(loaderFromFile.loadFileFromPath("")).thenThrow(Exception.class);
        itemsLoaderToPriceListView.loadItems();
        assertEquals(activity.getText(R.string.cant_read_from_file), ShadowToast.getTextOfLatestToast());
    }

}
