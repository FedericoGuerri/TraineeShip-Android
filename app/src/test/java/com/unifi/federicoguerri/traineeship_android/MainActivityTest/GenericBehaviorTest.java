package com.unifi.federicoguerri.traineeship_android.MainActivityTest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.MenuItem;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.DataLoaderFromFile;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class GenericBehaviorTest {

    @Mock
    private DataLoaderFromFile dataLoaderFromFile;

    @InjectMocks
    private MainActivity activity= Robolectric.buildActivity(MainActivity.class).create().visible().get();


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        //activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
    }

    @Test
    public void actionBar_isShowing() {
        assertEquals(true, activity.getSupportActionBar().isShowing());
    }

    @Test
    public void configurationDirectory_isCreated_afterPermissionIsGranted() {
        int writePermission=10800;
        activity.onRequestPermissionsResult(writePermission,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new int[]{PackageManager.PERMISSION_GRANTED});
        File directory = new File(activity.getFilesDir()
                + "/Android/data/"
                + activity.getApplicationContext().getPackageName()
                + "/ConfigurationDir");
        assertTrue(directory.exists());
        directory.delete();
    }

    @Test
    public void isUsing_customMenu() {
        shadowOf(activity).getOptionsMenu();
        assertNotNull(shadowOf(activity).getOptionsMenu());
    }

    @Test
    public void isUsing_menuItem() {
        MenuItem item= shadowOf(activity).getOptionsMenu().findItem(R.id.menuitem_total_mainactivity);
        assertEquals(activity.getString(R.string.menuitem_total_default_mainactivity),item.getTitle().toString());
    }

    @Test
    public void isUsing_visibleMenuItem() {
        MenuItem item= shadowOf(activity).getOptionsMenu().findItem(R.id.menuitem_total_mainactivity);
        assertEquals(true,item.isVisible());
    }

    @Test
    public void isUsing_notEnabledMenuItem() {
        MenuItem item= shadowOf(activity).getOptionsMenu().findItem(R.id.menuitem_total_mainactivity);
        assertEquals(false,item.isEnabled());
    }




}
