package com.unifi.federicoguerri.traineeship_android.core;


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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

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
        ocrBuilder.setCameraSource(720,480);
        assertNotNull(ocrBuilder.getCameraSource());
    }

    @Test
    public void ocrComponentsBuilder_returnACameraSource(){
        ocrBuilder.setCameraSource(720,480);
        assertEquals(CameraSource.class,ocrBuilder.getCameraSource().getClass());
    }

    @Test
    public void ocrComponentsBuilder_cameraSourceUsingFrontalCamera(){
        ocrBuilder.setCameraSource(720,480);
        assertEquals(CameraSource.CAMERA_FACING_BACK,ocrBuilder.getCameraSource().getCameraFacing());
    }


}
