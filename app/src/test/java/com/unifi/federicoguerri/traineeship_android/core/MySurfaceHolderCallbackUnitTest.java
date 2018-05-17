package com.unifi.federicoguerri.traineeship_android.core;

import android.Manifest;
import android.os.Build;
import android.view.SurfaceHolder;

import com.google.android.gms.vision.CameraSource;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class MySurfaceHolderCallbackUnitTest {

    private OcrScanActivity activity= Robolectric.buildActivity(OcrScanActivity.class).create().visible().get();
    @Mock
    private OcrComponentsBuilder ocrComponentsBuilder;
    @Mock
    private SurfaceHolder surfaceHolder;
    @InjectMocks
    private MySurfaceHolderCallback surfaceHolderCallback=new MySurfaceHolderCallback(activity,ocrComponentsBuilder,surfaceHolder,0);
    @Mock
    private CameraSource fakeCamera;

    @Before
    public void init(){
        ShadowApplication shadowApplication=shadowOf(activity.getApplication());
        shadowApplication.grantPermissions(new String[]{Manifest.permission.CAMERA});
        MockitoAnnotations.initMocks(this);
        when(ocrComponentsBuilder.getCameraSource()).thenReturn(fakeCamera);
    }

    @Test
    public void surfaceHolder_canThrowException_thatIsCaught() throws IOException{
        surfaceHolderCallback.surfaceCreated(surfaceHolder);
        when(fakeCamera.start(any(SurfaceHolder.class))).thenThrow(IOException.class);
        verify(fakeCamera,atLeastOnce()).start(any(SurfaceHolder.class));
    }

    @Test
    public void surfaceHolder_startsCamera_whenSurfaceIsCreated() throws IOException {
        surfaceHolderCallback.surfaceCreated(surfaceHolder);
        verify(fakeCamera,atLeastOnce()).start(any(SurfaceHolder.class));
    }

    @Test
    public void surfaceHolder_stopsCamera_whenSurfaceIsDestroyed(){
        surfaceHolderCallback.surfaceDestroyed(surfaceHolder);
        verify(fakeCamera,atLeastOnce()).stop();
    }

    @Test
    public void surfaceHolder_implementsWithNoCode_surfaceChanged(){
        surfaceHolderCallback.surfaceChanged(surfaceHolder,0,0,0);
    }

    @Test
    public void surfaceHolder_requestCameraPermission_ifHasNot() throws IOException {
        ShadowApplication shadowApplication=shadowOf(activity.getApplication());
        shadowApplication.denyPermissions(new String[]{Manifest.permission.CAMERA});
        MockitoAnnotations.initMocks(this);
        when(ocrComponentsBuilder.getCameraSource()).thenReturn(fakeCamera);
        surfaceHolderCallback.surfaceCreated(surfaceHolder);
        verify(fakeCamera,atMost(0)).start(any(SurfaceHolder.class));
    }

}
