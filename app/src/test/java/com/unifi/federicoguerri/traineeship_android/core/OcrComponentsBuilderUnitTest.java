package com.unifi.federicoguerri.traineeship_android.core;


import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class OcrComponentsBuilderUnitTest {

    private OcrComponentsBuilder ocrBuilder;
    private OcrScanActivity activity;
    private TextView textView;
    private Rect defaultRectValue=new Rect(0,20,50,400);
    private float textViewOriginalX=120f;
    private float textViewOriginalY=22f;

    @Before
    public void init(){
        activity = Robolectric.buildActivity( OcrScanActivity.class ).create().visible().get();
        ocrBuilder=new OcrComponentsBuilder(activity.getApplicationContext());
        textView=new TextView(activity.getBaseContext());
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

    //Recognizer behavior

    private SparseArray<TextBlock> mockSparseArray(String values, int size){
        String[] splittedValues=values.split(" ");

        SparseArray<TextBlock> mockedArray = Mockito.mock(SparseArray.class);
        TextBlock textBlock= Mockito.mock(TextBlock.class);
        when(textBlock.getBoundingBox()).thenReturn(new Rect(100,33,200,330));
        ArrayList<TextBlock> temp=new ArrayList<>();

        for (String value : splittedValues) {
            TextBlock nestedTextBlock = Mockito.mock(TextBlock.class);
            when(nestedTextBlock.getValue()).thenReturn(value);
            when(nestedTextBlock.getBoundingBox()).thenReturn(new Rect(100,33,200,330));
            temp.add(nestedTextBlock);
        }
        when(textBlock.getComponents()).thenReturn((List)temp);
        when(mockedArray.get(anyInt())).thenReturn(textBlock);
        when(mockedArray.size()).thenReturn(size);

        return mockedArray;
    }

    private void initTextBuilderRunner(String valuesWithSpacesBetween,int arraySize){
        ocrBuilder.setRecognizedTextView(textView);
        ocrBuilder.setTextViewCoordinates(textViewOriginalX,textViewOriginalY);
        ocrBuilder.setRectBounds(defaultRectValue);

        Detector.Detections<TextBlock> detections= Mockito.mock(Detector.Detections.class);
        SparseArray<TextBlock> array=mockSparseArray(valuesWithSpacesBetween,arraySize);
        when(detections.getDetectedItems()).thenReturn(array);
        ocrBuilder.receiveDetections(detections);
    }

    @Test
    public void textViewHasCorrectPrice(){
        initTextBuilderRunner("no 1,1 price",1);
        assertEquals("1,1",textView.getText());
    }

    @Test
    public void textView_hasTextBlockXCoordinates_ifPriceWasDetected(){
        initTextBuilderRunner("no 1,1 prices",1);
        assertEquals(100f,textView.getX());
    }

    @Test
    public void textView_hasTextBlockYCoordinates_ifPriceWasDetected(){
        initTextBuilderRunner("no 1,1 prices",1);
        assertEquals(33f,textView.getY());
    }


    @Test
    public void textView_hasTextFromResources(){
        initTextBuilderRunner("no prices at all",1);
        assertEquals(activity.getText(R.string.bad_recognition_get_closer_please),textView.getText());
    }

    @Test
    public void textView_hasOriginalXCoordinates_ifNoPricesWereDetected(){
        initTextBuilderRunner("no prices at all",1);
        assertEquals(textViewOriginalX,textView.getX());
    }

    @Test
    public void textView_hasOriginalYCoordinates_ifNoPricesWereDetected(){
        initTextBuilderRunner("no prices at all",1);
        assertEquals(textViewOriginalY,textView.getY());
    }


    @Test
    public void ocrBuilder_wontRecognizeText_ifNotDetecting(){
        ocrBuilder.setDetecting(false);
        initTextBuilderRunner("22,2",1);
        assertEquals("",textView.getText());
    }

    @Test
    public void ocrBuilder_wontRecognizeText_ifSparseArraySizeIsZero(){
        ocrBuilder.setDetecting(true);
        initTextBuilderRunner("22,2",0);
        assertEquals("",textView.getText());
    }

    @Test
    public void ocrBuilder_canAnimateTextViewX_ToOriginalXPosition(){
        ocrBuilder.setRecognizedTextView(textView);
        textView.setX(0);
        ocrBuilder.setTextViewCoordinates(textViewOriginalX,textViewOriginalY);
        ocrBuilder.animateTextViewToOriginalPosition();
        assertEquals(textViewOriginalX,textView.getX());
    }

    @Test
    public void ocrBuilder_canAnimateTextViewY_ToOriginalXPosition(){
        ocrBuilder.setRecognizedTextView(textView);
        textView.setY(0);
        ocrBuilder.setTextViewCoordinates(textViewOriginalX,textViewOriginalY);
        ocrBuilder.animateTextViewToOriginalPosition();
        assertEquals(textViewOriginalY,textView.getY());
    }

    @Test
    public void ocrBuilder_implementsRelease_withNoActualCode(){
        ocrBuilder.release();
    }

}
