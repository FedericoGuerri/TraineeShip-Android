package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.text.TextBlock;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class TextBuilderRunnableUnitTest {

    private OcrScanActivity activity;
    private TextView textView;
    private int defaultRectValue=220;
    private TextBlock textBlock;

    @Before
    public void setUp(){
         activity = Robolectric.buildActivity( OcrScanActivity.class ).create().visible().get();
         textView=new TextView(activity.getBaseContext());
    }

    private SparseArray<TextBlock> mockSparseArray(String values){
        String[] splittedValues=values.split(" ");

        SparseArray<TextBlock> mockedArray = Mockito.mock(SparseArray.class);
        textBlock= Mockito.mock(TextBlock.class);
        when(textBlock.getBoundingBox()).thenReturn(new Rect(100,33,0,0));
        ArrayList<TextBlock> temp=new ArrayList<>();

        for (String value : splittedValues) {
            TextBlock nestedTextBlock = Mockito.mock(TextBlock.class);
            when(nestedTextBlock.getValue()).thenReturn(value);
            temp.add(nestedTextBlock);
        }
        when(textBlock.getComponents()).thenReturn((List)temp);
        when(mockedArray.get(anyInt())).thenReturn(textBlock);
        when(mockedArray.size()).thenReturn(1);

        return mockedArray;
    }

    private void initTextBuilderRunner(String valuesWithSpacesBetween){
        TextBuilderRunnable textBuilderRunnable = new TextBuilderRunnable(mockSparseArray(valuesWithSpacesBetween), textView, 120f, 22f, new Rect(defaultRectValue, defaultRectValue, defaultRectValue, defaultRectValue));
        textBuilderRunnable.run();
    }

    @Test
    public void textViewHasCorrectPrice(){
        initTextBuilderRunner("no 1,1 price");
        assertEquals("1,1",textView.getText());
    }

    @Test
    public void textView_hasTextBlockXCoordinates_ifPriceWasDetected(){
        initTextBuilderRunner("no 1,1 prices");
        assertEquals(100f,textView.getX());
    }

    @Test
    public void textView_hasTextBlockYCoordinates_ifPriceWasDetected(){
        initTextBuilderRunner("no 1,1 prices");
        assertEquals(33f,textView.getY());
    }

    @Test
    public void textView_hasDefaultXCoordinates_ifPriceWasDetected_andNoValueMatchPrice(){
        initTextBuilderRunner("no 1.1 prices");
        assertEquals(defaultRectValue,(int)textView.getX());
    }

    @Test
    public void textView_hasDefaultYCoordinates_ifPriceWasDetected_andNoValueMatchPrice(){
        initTextBuilderRunner("no 1.1 prices");
        assertEquals(defaultRectValue,(int)textView.getY());
    }

    @Test
    public void textView_hasTextFromResources(){
        initTextBuilderRunner("no prices at all");
        assertEquals(activity.getText(R.string.bad_recognition_get_closer_please),textView.getText());
    }

    @Test
    public void textView_hasOriginalXCoordinates_ifNoPricesWereDetected(){
        initTextBuilderRunner("no prices at all");
        assertEquals(120f,textView.getX());
    }

    @Test
    public void textView_hasOriginalYCoordinates_ifNoPricesWereDetected(){
        initTextBuilderRunner("no prices at all");
        assertEquals(22f,textView.getY());
    }

    @Test
    public void textView_hasBounds_fromTextBlock(){
        initTextBuilderRunner("no 1,1 price");
        verify(textBlock).getBoundingBox();
    }


}
