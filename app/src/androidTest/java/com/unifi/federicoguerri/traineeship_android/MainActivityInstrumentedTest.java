package com.unifi.federicoguerri.traineeship_android;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasTextColor;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.unifi.federicoguerri.traineeship_android", appContext.getPackageName());
    }

    @Rule
    public ActivityTestRule<MainActivity> rule  = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void helloWorldViewInMainActivityTest() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.helloWorldTextView);
        assertThat(viewById,notNullValue());
    }

    @Test
    public void helloWorldViewInMainActivityIsATextViewTest() throws Exception {
        MainActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.helloWorldTextView);
        assertThat(viewById, instanceOf(TextView.class));
    }

    @Test
    public void helloWorldTextViewTextIsColorAccentTest() throws Exception {
        MainActivity activity = rule.getActivity();
        TextView helloworldTextView = (TextView)activity.findViewById(R.id.helloWorldTextView);

        Context appContext = InstrumentationRegistry.getTargetContext();
        int color=appContext.getResources().getColor(R.color.colorAccent);

        assertEquals(color,helloworldTextView.getCurrentTextColor());
    }

}
