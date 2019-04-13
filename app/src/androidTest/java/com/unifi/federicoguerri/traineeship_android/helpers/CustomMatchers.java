package com.unifi.federicoguerri.traineeship_android.helpers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class CustomMatchers {

    public static Matcher<View> withListSize (final int size) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                return ((ListView) view).getCount () == size;
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("expected " + size + " items");
            }
        };
    }

    public Matcher<View> withDrawable (final int drawable_id) {
        return new TypeSafeMatcher<View>() {
            @Override public boolean matchesSafely (final View view) {
                ImageView imageView = (ImageView) view;
                Drawable expectedDrawable = InstrumentationRegistry.getTargetContext()
                        .getResources().getDrawable(drawable_id,null);
                if (expectedDrawable == null) {
                    return false;
                }
                Bitmap bitmap = getBitmap (imageView.getDrawable());
                Bitmap otherBitmap = getBitmap(expectedDrawable);
                return bitmap.sameAs(otherBitmap);
            }

            @Override public void describeTo (final Description description) {
                description.appendText ("expected drawable id " + drawable_id);
            }
        };
    }

    private Bitmap getBitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
