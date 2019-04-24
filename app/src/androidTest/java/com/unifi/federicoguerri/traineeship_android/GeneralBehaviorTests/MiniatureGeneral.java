package com.unifi.federicoguerri.traineeship_android.GeneralBehaviorTests;


import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.unifi.federicoguerri.traineeship_android.R;
import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MiniatureGeneral extends AbstractGeneral{

    private CustomMatchers customMatchers;

    @Before
    public void settingUp() {
        customMatchers=new CustomMatchers();
    }


    @Test
    public void miniatureImageView_hasPlaceholderDrawable_ifUserClicksOnNObutton(){
        genericHelper.recognizeAPriceWithNoMiniature();
        onData(anything()).atPosition(0)
                .onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView))
                .check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWereAdded(){
        genericHelper.recognizePricesWithNoMiniature(3);
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
        onData(anything()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
        onData(anything()).atPosition(2).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));

    }



    @Test
    public void miniatureImageView_hasMiniature_ifUserClicksOnYESbutton(){
        genericHelper.recognizeAPriceWithMiniature();//miniature
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }



     @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWereDeleted(){
        genericHelper.recognizePricesWithNoMiniature(3);
        genericHelper.deleteFirstPrices(2);
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }









    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWithDifferentDrawableWereAdded(){
        genericHelper.recognizeAPriceWithMiniature();//miniature
        genericHelper.recognizeAPriceWithNoMiniature();
        genericHelper.recognizeAPriceWithMiniature();//miniature
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }




    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWithDifferentDrawableWereDeleted(){
        genericHelper.recognizeAPriceWithMiniature();//miniature
        genericHelper.recognizePricesWithNoMiniature(2);
        genericHelper.deletePrice(1);
        genericHelper.deletePrice(1);
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }@Test
    public void miniatureImageView_notChangingMiniature_ifPricesWereAdded(){
        genericHelper.recognizeAPriceWithNoMiniature();
        genericHelper.recognizeAPriceWithMiniature();
        genericHelper.recognizeAPriceWithNoMiniature();
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches((customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
        onData(anything()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));

    }



    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWereDeleted(){
        genericHelper.recognizePricesWithNoMiniature(2);
        genericHelper.recognizeAPriceWithMiniature();//miniature
        genericHelper.deleteFirstPrices(2);
        onData(anything()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }






    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWithDifferentDrawableWereDeleted(){
        genericHelper.recognizeAPriceWithNoMiniature();
        genericHelper.recognizePricesWithMiniature(2);
        genericHelper.deleteFirstPrice();
        onData(anything()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }




/*
    @Test
    public void willFail(){
        System.exit(0);
    }
*/








}
