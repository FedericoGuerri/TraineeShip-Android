package com.unifi.federicoguerri.traineeship_android.core;


import android.graphics.Rect;
import android.os.Build;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class OcrComponentsBuilderUnitTest {

    private OcrComponentsBuilder ocrBuilder;
    private OcrScanActivity activity;

    @Before
    public void init(){
        activity = Robolectric.buildActivity( OcrScanActivity.class ).create().get();
        ocrBuilder=new OcrComponentsBuilder(activity.getApplicationContext());
    }

    @Test
    public void ocrComponentsBuilder_hasTextRecognizerGetter(){
        assertEquals(TextRecognizer.class,ocrBuilder.getTextRecognizer().getClass());
    }

    @Test
    public void ocrComponentsBuilder_textViewIsNullByDefault(){
        assertNull(ocrBuilder.getRecognizedTextView());
    }

    @Test
    public void ocrComponentsBuilder_cameraSourceIsNullByDefault(){
        assertNull(ocrBuilder.getCameraSource());
    }

    @Test
    public void ocrComponentsBuilder_textRecognizerIsBuildInTheConstructor(){
        assertNotNull(ocrBuilder.getTextRecognizer());
    }

    @Test
    public void ocrComponentsBuilder_hasTextViewGetter(){
        TextView textView=new TextView(activity.getApplicationContext());
        ocrBuilder.setRecognizedTextView(textView);
        assertEquals(TextView.class,ocrBuilder.getRecognizedTextView().getClass());
    }

    @Test
    public void ocrComponentsBuilder_canSetTextView(){
        TextView textView=new TextView(activity.getApplicationContext());
        ocrBuilder.setRecognizedTextView(textView);
        assertEquals(textView,ocrBuilder.getRecognizedTextView());
    }

    @Test
    public void ocrComponentsBuilder_canBuildCameraSource(){
        ocrBuilder.setCameraSource(new CameraSource.Builder(activity.getApplicationContext(), ocrBuilder.getTextRecognizer()).setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1080, 720).setRequestedFps(2.0f).setAutoFocusEnabled(true).build());
        assertNotNull(ocrBuilder.getCameraSource());
    }

    @Test
    public void ocrComponentsBuilder_returnACameraSource(){
        ocrBuilder.setCameraSource(new CameraSource.Builder(activity.getApplicationContext(), ocrBuilder.getTextRecognizer()).setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1080, 720).setRequestedFps(2.0f).setAutoFocusEnabled(true).build());
        assertEquals(CameraSource.class,ocrBuilder.getCameraSource().getClass());
    }

    @Test
    public void ocrComponentsBuilder_cameraSourceUsingFrontalCamera(){
        ocrBuilder.setCameraSource(new CameraSource.Builder(activity.getApplicationContext(), ocrBuilder.getTextRecognizer()).setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1080, 720).setRequestedFps(2.0f).setAutoFocusEnabled(true).build());
        assertEquals(CameraSource.CAMERA_FACING_BACK,ocrBuilder.getCameraSource().getCameraFacing());
    }

    @Test
    public void ocrComponentsBuilder_canStoreRectangleBounds(){
        ocrBuilder.setRectBounds(new Rect(0,0,30,100));
        assertNotNull(ocrBuilder.getRectBounds());
    }

    @Test
    public void ocrComponentsBuilder_returnsRectangleBounds(){
        ocrBuilder.setRectBounds(new Rect(0,0,30,100));
        assertEquals(Rect.class,ocrBuilder.getRectBounds().getClass());
    }

    @Test
    public void ocrComponentsBuilder_canStoreCorrectRectangleBounds(){
        ocrBuilder.setRectBounds(new Rect(0,0,30,100));
        assertEquals(new Rect(0,0,30,100),ocrBuilder.getRectBounds());
    }

    @Test
    public void ocrComponentsBuilder_hasOcrRecognitionEnabledByDefault(){
        assertTrue(ocrBuilder.isDetecting());
    }

    @Test
    public void ocrComponentsBuilder_canDisableOcrRecognition(){
        ocrBuilder.setDetecting(false);
        assertFalse(ocrBuilder.isDetecting());
    }

}
