package com.unifi.federicoguerri.traineeship_android.core;

import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class CustomCountDownTimerUnitTest {

    @Mock
    private OcrComponentsBuilder ocrComponentsBuilder;
    @Mock
    private View view;
    @InjectMocks
    private CustomCountdownTimer customCountDownTimer=new CustomCountdownTimer(1000,1000,view,ocrComponentsBuilder);

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        int[] location = {2,1};
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return args[0];
            }
        }).when(view).getLocationOnScreen(any(location.getClass()));
        TextView textView=Mockito.mock(TextView.class);
        when(textView.getX()).thenReturn(22f);
        when(textView.getY()).thenReturn(0f);
        when(ocrComponentsBuilder.getRecognizedTextView()).thenReturn(textView);

    }

    @Test
    public void customCountDownTimer_willCallOcrComponentBuilderRectBounds_whenOnFinish(){
        customCountDownTimer.onFinish();
        verify(ocrComponentsBuilder,atLeastOnce()).setRectBounds(any(Rect.class));
    }

    @Test
    public void customCountDownTimer_willCallOcrComponentBuilderTextViewCoordinates_whenOnFinish(){
        customCountDownTimer.onFinish();
        verify(ocrComponentsBuilder,atLeastOnce()).setTextViewCoordinates(anyFloat(),anyFloat(),anyFloat());
    }


    @Test
    public void customCountDownTimer_test_whenOnFinish(){
        customCountDownTimer.onFinish();
        verify(ocrComponentsBuilder,atLeastOnce()).setTextViewCoordinates(anyFloat(),anyFloat(),anyFloat());
    }

    @Test
    public void customCountDownTimer_willNotInteractWithOcrComponentBuilder_whenOnTick(){
        customCountDownTimer.onTick((long)1);
        verifyZeroInteractions(ocrComponentsBuilder);
    }

    @Test
    public void customCountDownTimer_willNotInteractWithCallView_whenOnTick(){
        customCountDownTimer.onTick((long)1);
        verifyZeroInteractions(view);
    }
}
