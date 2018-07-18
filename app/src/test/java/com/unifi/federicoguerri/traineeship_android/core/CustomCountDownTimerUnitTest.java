package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;
import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class CustomCountDownTimerUnitTest {

    private OcrScanActivity activity= Robolectric.buildActivity(OcrScanActivity.class).create().visible().get();
    private View view=activity.findViewById(R.id.textTargetingLayout);
    @Mock
    private OcrComponentsBuilder ocrComponentsBuilder;
    @InjectMocks
    private CustomCountdownTimer customCountDownTimer=new CustomCountdownTimer(1000,view,ocrComponentsBuilder);

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        TextView textView=Mockito.mock(TextView.class);
        when(textView.getX()).thenReturn(22f);
        when(textView.getY()).thenReturn(0f);
        when(ocrComponentsBuilder.getRecognizedTextView()).thenReturn(textView);
    }

    @Test
    public void customCountDownTimer_willCallOcrComponentBuilderRectBounds_whenOnFinish(){
        customCountDownTimer.run();
        verify(ocrComponentsBuilder,atLeastOnce()).setRectBounds(any(Rect.class));
    }

    @Test
    public void customCountDownTimer_willCallOcrComponentBuilderTextViewCoordinates_whenOnFinish(){
        customCountDownTimer.run();
        verify(ocrComponentsBuilder,atLeastOnce()).setTextViewCoordinates(anyFloat(),anyFloat(),anyFloat());
    }


    @Test
    public void customCountDownTimer_test_whenOnFinish(){
        customCountDownTimer.run();
        verify(ocrComponentsBuilder,atLeastOnce()).setTextViewCoordinates(anyFloat(),anyFloat(),anyFloat());
    }

}
