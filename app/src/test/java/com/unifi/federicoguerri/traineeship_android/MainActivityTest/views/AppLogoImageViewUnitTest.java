package com.unifi.federicoguerri.traineeship_android.MainActivityTest.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppLogoImageViewUnitTest extends AbstractMainActivityUnitTest {

    private ImageView appLogoImageView;

    @Override
    public View getTestingComponent() {
        appLogoImageView=activity.findViewById(R.id.appLogoImageViewMainActivity);
        return appLogoImageView;
    }

    @Test
    public void appLogoImageView_isVisible() throws Exception {
        assertEquals(View.VISIBLE,appLogoImageView.getVisibility());
    }

    @Test
    public void appLogoImageView_isNotClickable() throws Exception {
        assertEquals(false,appLogoImageView.isClickable());
    }

    @Test
    public void appLogoImageView_widthIsWrapContent() throws Exception {
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,appLogoImageView.getLayoutParams().width);
    }

    @Test
    public void appLogoImageView_heightIsWrapContent() throws Exception {
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT,appLogoImageView.getLayoutParams().height);
    }

    @Test
    public void appLogoImageView_drawableIsnotNull() throws Exception {
        assertNotNull(appLogoImageView.getDrawable());
    }

    @Test
    public void appLogoImageView_childOfWelcomeLayout(){
        assertEquals(R.id.welcomeLayoutMainActivity,((View)appLogoImageView.getParent()).getId());
    }

}
