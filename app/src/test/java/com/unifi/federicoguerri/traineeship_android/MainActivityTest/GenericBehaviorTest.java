package com.unifi.federicoguerri.traineeship_android.MainActivityTest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.MenuItem;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.io.File;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)
public class GenericBehaviorTest {

    private MainActivity activity;

    @Before
    public void setUp(){
        activity=Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void actionBar_isShowing() {
        assertEquals(true, activity.getSupportActionBar().isShowing());
    }

    @Test
    public void configurationDirectory_isCreated() {
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

    @Test
    public void canRequestPermissionAtRuntime(){
        activity.onRequestPermissionsResult(10800,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new int[] {PackageManager.PERMISSION_GRANTED});
    }

    @Test
    public void canrequestPermissionAtRuntime(){
        ShadowActivity shadowActivity=shadowOf(activity);
        shadowActivity.denyPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        activity.onRequestPermissionsResult(10800,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new int[] {PackageManager.PERMISSION_GRANTED});
    }

}
