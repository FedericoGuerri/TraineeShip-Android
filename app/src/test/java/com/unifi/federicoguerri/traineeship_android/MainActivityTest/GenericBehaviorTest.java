package com.unifi.federicoguerri.traineeship_android.MainActivityTest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.MainActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowIntent;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.M)
@RunWith(RobolectricTestRunner.class)
public class GenericBehaviorTest {

    private MainActivity activity;

    @Before
    public void setUp(){
        activity=Robolectric.buildActivity(MainActivity.class).create().visible().get();
    }

    @After
    public void tearDown(){
        activity.finish();
    }

    @Test
    public void actionBar_isShowing() {
        assertEquals(true, activity.getSupportActionBar().isShowing());
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
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("com.unifi.federicoguerri.traineeship_android.OcrScanActivity",shadowIntent.getIntentClass().getName());
    }

    @Test(expected = NullPointerException.class)
    public void wontGrantPermission_ifCodeDoesntMatch(){
        activity.onRequestPermissionsResult(1,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new int[] {PackageManager.PERMISSION_GRANTED});
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("com.unifi.federicoguerri.traineeship_android.OcrScanActivity",shadowIntent.getIntentClass().getName());
    }


    @Test
    public void willDelete_allSharedPreferences_onDestroy(){
        activity.onDestroy();
        activity=Robolectric.setupActivity(MainActivity.class);
        SharedPreferences sharedPreferences = activity.getApplication().getSharedPreferences("myConfiguration", Context.MODE_PRIVATE);
        assertFalse(sharedPreferences.contains("id_price_count"));
    }


    @Test(expected = NullPointerException.class)
    public void willNotLaunchOcrScanActivity_withIntent_ifUserDoesntAllowPermission(){
        activity.onRequestPermissionsResult(10800,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new int[] {PackageManager.PERMISSION_DENIED});
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("com.unifi.federicoguerri.traineeship_android.OcrScanActivity",shadowIntent.getIntentClass().getName());
    }

    @Test
    public void willLaunchOcrScanActivity_withIntent_ifUserAllowsPermission(){
        activity.onRequestPermissionsResult(10800,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},new int[] {PackageManager.PERMISSION_GRANTED});
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(startedIntent);
        assertEquals("com.unifi.federicoguerri.traineeship_android.OcrScanActivity",shadowIntent.getIntentClass().getName());
    }

    @Test
    public void fabNewOcr_wontStartsEnterTransition_IfHasNoWritePermission()  {
        denyWritePermission_andClickOnFabNewOcr();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(-1,shadowActivity.getPendingTransitionEnterAnimationResourceId());
    }

    @Test
    public void fabNewOcr_wontStartsExitTransitionIfHasNoWritePermission()  {
        denyWritePermission_andClickOnFabNewOcr();
        ShadowActivity shadowActivity = shadowOf(activity);
        assertEquals(-1,shadowActivity.getPendingTransitionExitAnimationResourceId());
    }

    @Test(expected = NullPointerException.class)
    public void fabNewOcr_wontLaunchOcrScanActivityWithIntent_IfHasNoWritePermission()  {
        denyWritePermission_andClickOnFabNewOcr();
        ShadowActivity shadowActivity = shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        shadowOf(startedIntent);
    }

    @Test
    public void onPostResume_willNotChangeWelcomeLayoutVisibility(){
        ActivityController<MainActivity> controller =
                Robolectric.buildActivity(MainActivity.class).create().start().visible();
        View welcomeLayout=controller.get().findViewById(R.id.welcomeLayoutMainActivity);
        int previewVisibility=welcomeLayout.getVisibility();
        controller.resume();
        assertEquals(previewVisibility,welcomeLayout.getVisibility());
    }

    private void denyWritePermission_andClickOnFabNewOcr() {
        ShadowApplication shadowApplication=shadowOf(activity.getApplication());
        shadowApplication.denyPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        FloatingActionButton fabNewOcr=activity.findViewById(R.id.fabNewOcrMainActivity);
        fabNewOcr.performClick();
    }



}
