package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import android.Manifest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;

import com.unifi.federicoguerri.traineeship_android.SplashScreenActivity;
import com.unifi.federicoguerri.traineeship_android.helpers.GenericHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;


public class AbstractGeneral {

    @Rule
    public ActivityTestRule<SplashScreenActivity> splashActivityRule = new ActivityTestRule<>(SplashScreenActivity.class,false,false);
    @Rule public GrantPermissionRule runtimePermissionCamera = GrantPermissionRule .grant(Manifest.permission.CAMERA);
    @Rule public GrantPermissionRule runtimePermissionWriteStorange = GrantPermissionRule .grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    @Rule public GrantPermissionRule runtimePermissionReadStorange = GrantPermissionRule .grant(Manifest.permission.READ_EXTERNAL_STORAGE);
    protected GenericHelper genericHelper;

    @Before
    public void setUp(){
        genericHelper=new GenericHelper();
        splashActivityRule.launchActivity(null);
    }

    @After
    public void clean(){
        splashActivityRule.finishActivity();
    }
}
