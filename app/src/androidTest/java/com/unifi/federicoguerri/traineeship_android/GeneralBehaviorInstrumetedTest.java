package com.unifi.federicoguerri.traineeship_android;


import android.Manifest;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.view.View;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.helpers.CustomMatchers;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.AllOf.allOf;

public class GeneralBehaviorInstrumetedTest {

    @Rule public ActivityTestRule<SplashScreenActivity> splashActivityRule = new ActivityTestRule<>(SplashScreenActivity.class);
    @Rule public GrantPermissionRule runtimePermissionCamera = GrantPermissionRule .grant(Manifest.permission.CAMERA);
    @Rule public GrantPermissionRule runtimePermissionWriteStorange = GrantPermissionRule .grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    @Rule public GrantPermissionRule runtimePermissionReadStorange = GrantPermissionRule .grant(Manifest.permission.READ_EXTERNAL_STORAGE);

    // MainActivity

    // pricesList

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedAPrice(){
        recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }

    @Test
    public void pricesList_willBeVisible_AfterRecognizedAPrice(){
        recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_willUpdateListSize_AfterRecognizedASecondPrice(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(2)));
    }

    @Test
    public void pricesList_willStoreAPriceValue_AfterRecognizedAPrice(){
        recognizeAPrice("NO");
        onView(withId(R.id.itemPriceTextViewPricesListView)).check(matches(withText("0.0")));
    }

    @Test
    public void pricesList_wontChangeListSize_AfterRecognizedAPrice_ifBackWasPressed(){
        recognizeAPrice("NO");
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }

    @Test
    public void pricesList_willBeInvisible_ifBackWasPressedBeforePriceRecognition(){
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_willNoChangeVisibility_AfterRecognizedAPrice_ifBackWasPressed(){
        recognizeAPrice("NO");
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_willChangeVisibility_AfterRecognizedOnePrice_ifItWasRemoved(){
        recognizeAPrice("NO");
        onView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).perform(click());
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_wontChangeVisibility_AfterRecognizedMorePrices_ifOneWasRemoved(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void pricesList_canAddAndDeletePrices(){
        recognizeAPrice("NO");
        deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
    }

    @Test
    public void pricesList_canAddAndDeleteMultipleTimes(){
        recognizeAPrice("NO");
        deleteFirstPrice();
        recognizeAPrice("NO");
        deleteFirstPrice();
        recognizeAPrice("NO");
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(1)));
    }


    @Test
    public void pricesList_canAddAndDelete_inDifferentOrder(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deletePrice(1);
        deletePrice(0);
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(CustomMatchers.withListSize(0)));
    }

    @Test
    public void pricesList_visibilityWillChangeEventually_ifAddingAndRemovingPrices(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deletePrice(3);
        deletePrice(1);
        deleteFirstPrice();
        deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void pricesList_willChangeVisibility_AfterRecognizedMorePrices_ifAllWereRemoved(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deleteFirstPrice();
        deleteFirstPrice();
        deleteFirstPrice();
        onView(withId(R.id.pricesListViewMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }



    // welcomeLayout

    @Test
    public void welcomeLayout_willBeInvisible_AfterRecognizedAPrice(){
        recognizeAPrice("NO");
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willBeVisible_ifBackWasPressedBeforePriceRecognition(){
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_willNoChangeVisibility_AfterRecognizedAPrice_ifBackWasPressed(){
        recognizeAPrice("NO");
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(isRoot()).perform(pressBack());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willChangeVisibility_AfterRecognizedOnePrice_ifItWasRemoved(){
        recognizeAPrice("NO");
        onView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).perform(click());
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_wontChangeVisibility_AfterRecognizedMorePrices_ifOneWasRemoved(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void welcomeLayout_willChangeVisibility_AfterRecognizedMorePrices_ifAllWereRemoved(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deleteFirstPrice();
        deleteFirstPrice();
        deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void welcomeLayout_visibilityWillChangeEventually_ifAddingAndRemovingPrices(){
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deletePrice(3);
        deletePrice(1);
        deleteFirstPrice();
        deleteFirstPrice();
        onView(withId(R.id.welcomeLayoutMainActivity)).check(matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }


    //miniature ImageView
    @Test
    public void miniatureImageView_hasPlaceholderDrawable_ifUserClicksOnNObutton(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWereAdded(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
        onData(allOf()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
        onData(allOf()).atPosition(2).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));

    }

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWereDeleted(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        deleteFirstPrice();
        deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }


    @Test
    public void miniatureImageView_hasMiniature_ifUserClicksOnYESbutton(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }

    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWereAdded(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
        onData(allOf()).atPosition(1).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
        onData(allOf()).atPosition(2).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));

    }

    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWereDeleted(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        deleteFirstPrice();
        deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }

    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWithDifferentDrawableWereAdded(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("NO");
        recognizeAPrice("YES");
        recognizeAPrice("NO");
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWithDifferentDrawableWereAdded(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }


    @Test
    public void miniatureImageView_notChangingPlaceholder_ifPricesWithDifferentDrawableWereDeleted(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        deleteFirstPrice();
        deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(customMatchers.withDrawable(R.drawable.no_miniature_placeholder)));
    }

    @Test
    public void miniatureImageView_notChangingMiniature_ifPricesWithDifferentDrawableWereDeleted(){
        CustomMatchers customMatchers=new CustomMatchers();
        recognizeAPrice("YES");
        recognizeAPrice("NO");
        recognizeAPrice("NO");
        recognizeAPrice("YES");
        recognizeAPrice("YES");
        deleteFirstPrice();
        deleteFirstPrice();
        deleteFirstPrice();
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemMiniatureImageViewItemPriceListView)).check(matches(not(customMatchers.withDrawable(R.drawable.no_miniature_placeholder))));
    }

    // prices-total menuItem

    @Test
    public void pricesTotalMenuItem_valueIsIncrementing_afterPricesRecognition(){
        recognizeSpecificPrice("NO","11.1");
        recognizeSpecificPrice("YES","11.1");
        onView(withId(R.id.menuitem_total_mainactivity)).check(matches(withText("22.2")));
    }

    @Test
    public void pricesTotalMenuItem_valueIsDecrementing_afterPricesRecognition(){
        recognizeSpecificPrice("NO","11.2");
        deleteFirstPrice();
        recognizeSpecificPrice("YES","11.1");
        onView(withId(R.id.menuitem_total_mainactivity)).check(matches(withText("11.1")));
    }

    @Test
    public void pricesTotalMenuItem_valueIsChanging_whileChangingPrices(){
        recognizeSpecificPrice("NO","22.2");
        recognizeSpecificPrice("YES","11.2");
        deleteFirstPrice();
        recognizeSpecificPrice("NO","22.2");
        deleteFirstPrice();
        recognizeSpecificPrice("YES","111.11");
        onView(withId(R.id.menuitem_total_mainactivity)).check(matches(withText("133.31")));
    }



    private void recognizeSpecificPrice(String saveMiniature,String price) {
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(withId(R.id.recognizedTextViewOcrScanActivity)).perform(setTextInTextView(price));
        onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        onView(withText(saveMiniature)).perform(click());
        if(saveMiniature.equals("YES")){
            onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        }
    }

    private void recognizeAPrice(String text) {
        onView(withId(R.id.fabNewOcrMainActivity)).perform(click());
        onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        onView(withText(text)).perform(click());
        if(text.equals("YES")){
            onView(withId(R.id.fabSaveCurrentPrice)).perform(click());
        }
    }

    private void deleteFirstPrice() {
        onData(allOf()).atPosition(0).
                onChildView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).
                perform(click());
    }

    private void deletePrice(int index) {
        onData(allOf()).atPosition(index).
                onChildView(withId(R.id.itemDeletePriceImageViewitemPriceListView)).
                perform(click());
    }


    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }


}
