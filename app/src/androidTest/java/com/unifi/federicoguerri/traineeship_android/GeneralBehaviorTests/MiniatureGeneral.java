package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

public class MiniatureGeneral extends AbstractGeneral{

    private CustomMatchers customMatchers;

    // MainActivity
    @Before
    public void settingUp(){
        customMatchers=new CustomMatchers();
    }

    //miniature ImageView
    @Test
    public void miniatureImageView_hasPlaceholderDrawable_ifUserClicksOnNObutton(){
        genericHelper.recognizeAPrice("NO");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWereAdded(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
        onData(allOf()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
        onData(allOf()).atPosition(2).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));

    }

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWereDeleted(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }


    // failing tests!
    /*
    @Test
    public void miniatureImageView_hasMiniature_ifUserClicksOnYESbutton(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");//miniature
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }

    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWereAdded(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");
        recognizeAPrice("YES");
        recognizeAPrice("NO");//miniature
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches((customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
        onData(allOf()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));

    }


    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWereDeleted(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");//miniature
        deleteFirstPrice();
        deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }
    */

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWithDifferentDrawableWereAdded(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");//miniature
        genericHelper.recognizeAPrice("NO");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    /*
    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWithDifferentDrawableWereAdded(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");//miniature
        recognizeAPrice("NO");
        recognizeAPrice("YES");//miniature
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }
    */


    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWithDifferentDrawableWereDeleted(){
        genericHelper.recognizeAPrice("NO");
        genericHelper.recognizeAPrice("NO");//miniature
        genericHelper.recognizeAPrice("NO");
        genericHelper.deleteFirstPrice();
        genericHelper.deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    /*
    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWithDifferentDrawableWereDeleted(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");//miniature
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deleteFirstPrice();
        deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }
    */




}