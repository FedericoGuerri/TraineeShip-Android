package com.unifi.federicoguerri.traineeship_android.CustomShadows;

import com.google.android.gms.vision.text.TextRecognizer;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

@Implements(TextRecognizer.class)
public class ShadowTextRecognizer {

        @RealObject
        private TextRecognizer realRecognizer;

        @Implementation
        public boolean isOperational(){
            return true;
        }

}


