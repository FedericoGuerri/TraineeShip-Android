package com.unifi.federicoguerri.traineeship_android.core.ocr_setting_up;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.BuildConfig;
import com.unifi.federicoguerri.traineeship_android.OcrScanActivity;
import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.core.AppDatabaseInitializer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class MiniatureHandlerUnitTest {


    private OcrScanActivity activity=Robolectric.buildActivity(OcrScanActivity.class).create().visible().get();
    @Mock
    private OcrComponentsBuilder ocrComponentsBuilder;
    @Mock
    private TextView textView;
    @Mock
    private FloatingActionButton fab;

    @InjectMocks
    private MiniatureHandler miniatureHandler =new MiniatureHandler(activity,ocrComponentsBuilder,0,fab);

    @Before
    public void init(){
        AppDatabaseInitializer.initDatabase();
        MockitoAnnotations.initMocks(this);
        when(textView.getText()).thenReturn("22.2");
        when(ocrComponentsBuilder.getRecognizedTextView()).thenReturn(textView);
    }

    @After
    public void after(){
        AppDatabaseInitializer.destroyDatabase();
    }

    @Test
    public void miniatureHandler_createsConfigurationDirectory_ifDidnotExists(){
        Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.no_miniature_placeholder);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

        miniatureHandler.onPictureTaken(stream.toByteArray());

        File configDir = new File(activity.getFilesDir()
                + "/Android/data/"
                + activity.getPackageName()
                + "/ConfigurationDir");

        assertTrue(configDir.exists());
    }

    @Test
    public void miniatureHandler_wontCreateConfigurationDirectory_ifAlreadyExists(){
        File configDir_before = new File(activity.getFilesDir()
                + "/Android/data/"
                + activity.getPackageName()
                + "/ConfigurationDir");
        configDir_before.mkdirs();
        long beforeLastModified=configDir_before.lastModified();

        Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.no_miniature_placeholder);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

        miniatureHandler.onPictureTaken(stream.toByteArray());

        File configDir_after = new File(activity.getFilesDir()
                + "/Android/data/"
                + activity.getPackageName()
                + "/ConfigurationDir");
        configDir_after.setLastModified(0);

        assertNotEquals(beforeLastModified,configDir_after.lastModified());
    }


    @Test
    public void miniatureHandler_showAToastMessage_IfExceptionOccurred(){
        miniatureHandler.onPictureTaken(null);
        assertEquals(activity.getString(R.string.cant_write_to_file), ShadowToast.getTextOfLatestToast());
    }


}
