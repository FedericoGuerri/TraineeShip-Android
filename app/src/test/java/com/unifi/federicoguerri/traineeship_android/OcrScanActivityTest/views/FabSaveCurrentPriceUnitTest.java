package com.unifi.federicoguerri.traineeship_android.OcrScanActivityTest.views;


import android.Manifest;
import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unifi.federicoguerri.traineeship_android.R;

import org.junit.Test;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

public class FabSaveCurrentPriceUnitTest extends AbstractOcrScanActivityUnitTest{

    private FloatingActionButton fabSavePrice;


    @Override
    public View getTestingComponent() {
        fabSavePrice =activity.findViewById(R.id.fabSaveCurrentPrice);

        ShadowApplication shadowApplication=shadowOf(activity.getApplication());
        shadowApplication.grantPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        shadowApplication.grantPermissions(Manifest.permission.CAMERA);

        return fabSavePrice;
    }

    @Test
    public void fabSavePrice_childOfOcrParentLayout(){
        assertEquals(R.id.ocrParentLayoutOcrScanActivity,((View) fabSavePrice.getParent()).getId());
    }

    @Test
    public void fabSavePrice_isVisible() {
        assertEquals(View.VISIBLE, fabSavePrice.getVisibility());
    }

    @Test
    public void fabNewOcr_widthIsWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT, fabSavePrice.getLayoutParams().width);
    }

    @Test
    public void fabSavePrice_heightIsWrapContent(){
        assertEquals(ViewGroup.LayoutParams.WRAP_CONTENT, fabSavePrice.getLayoutParams().height);
    }

    @Test
    public void fabSavePrice_drawableIsnotNull() {
        assertNotNull(fabSavePrice.getDrawable());
    }

    @Test
    public void fabSavePrice_hasBackgroundTintFromResources(){
        assertEquals(getColorFromResources(R.color.colorAccent), fabSavePrice.getBackgroundTintList().getDefaultColor());
    }

    @Test
    public void fabSavePrice_hasNormalSize(){ //fabSize=Normal has value=0
        assertEquals(0, fabSavePrice.getSize());
    }

    @Test
    public void fabSavePrice_hasZeroElevation(){
        assertEquals(0,(int)fabSavePrice.getCompatElevation());
    }

    @Test
    public void fabSavePrice_isClickable() {
        assertEquals(true, fabSavePrice.isClickable());
    }

    @Test
    public void fabSavePrice_isNotEnableWhenActivityEnds() {
        tapOnFabAndPressDialogNegativeButton();
        assertEquals(false, fabSavePrice.isEnabled());
    }


    // tapping on fab will show miniature dialog

    @Test
    public void fabSavePrice_willShowADialog(){
        fabSavePrice.performClick();

        assertTrue(ShadowDialog.getLatestDialog().isShowing());
    }

    @Test
    public void fabSavePrice_willShowADialog_thatCanBeClosed(){
        tapOnFabAndPressDialogNegativeButton();
        assertFalse(ShadowDialog.getLatestDialog().isShowing());
    }

    @Test
    public void fabSavePrice_willShowDialogWith_positiveButton(){
        fabSavePrice.performClick();
        assertNotNull(ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE));
    }

    @Test
    public void fabSavePrice_willShowDialog_thatIsDismissedWhenTappingOnPositiveButton(){
        tapOnFabAndPressDialogPositiveButton();
        assertFalse(ShadowDialog.getLatestDialog().isShowing());
    }

    @Test
    public void fabSavePrice_willShowDialogWith_negativeButton(){
        fabSavePrice.performClick();
        assertNotNull(ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_NEGATIVE));
    }

    @Test
    public void fabSavePrice_willShowDialogWith_positiveButtonTextFromResources(){
        fabSavePrice.performClick();
        assertEquals(getStringFromResources(R.string.affermative),ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE).getText().toString());
    }

    @Test
    public void fabSavePrice_willShowDialogWith_negativeButtonTextFromResources(){
        fabSavePrice.performClick();
        assertEquals(getStringFromResources(R.string.negative),ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_NEGATIVE).getText().toString());
    }

    @Test
    public void fabSavePrice_willShowDialogWith_titleFromResources(){
        fabSavePrice.performClick();
        ShadowAlertDialog shadowAlertDialog=shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertEquals(getStringFromResources(R.string.miniature_dialog_title),shadowAlertDialog.getTitle().toString());
    }

    @Test
    public void fabSavePrice_willShowDialogWith_messageFromResources(){
        fabSavePrice.performClick();
        ShadowAlertDialog shadowAlertDialog=shadowOf(ShadowAlertDialog.getLatestAlertDialog());
        assertEquals(getStringFromResources(R.string.miniature_dialog_message),shadowAlertDialog.getMessage().toString());
    }

    // tapping on fab and press dialog's negative button

    @Test
    public void fabSavePrice_willEndOcrScanActivity(){
        tapOnFabAndPressDialogNegativeButton();
        assertTrue(activity.isFinishing());
    }


    @Test
    public void fabSavePrice_willEndOcrScanActivityWithEnterTransition()  {
        tapOnFabAndPressDialogNegativeButton();
        ShadowActivity shadowActivity=shadowOf(activity);
        assertEquals(R.anim.end_ocr_scan_enter,shadowActivity.getPendingTransitionEnterAnimationResourceId());
    }

    @Test
    public void fabSavePrice_willEndOcrScanActivityWithExitTransition()  {
        tapOnFabAndPressDialogNegativeButton();
        ShadowActivity shadowActivity=shadowOf(activity);
        assertEquals(R.anim.end_ocr_scan_exit,shadowActivity.getPendingTransitionExitAnimationResourceId());
    }

    @Test
    public void fabSavePrice_showsToastIfNoPricesWereRecognized()  {
        TextView recognizedTextView=activity.findViewById(R.id.recognizedTextViewOcrScanActivity);
        recognizedTextView.setText(activity.getResources().getString(R.string.bad_recognition_get_closer_please));
        fabSavePrice.performClick();
        assertEquals(activity.getResources().getString(R.string.no_price_detected_toast),ShadowToast.getTextOfLatestToast());
    }

    // tapping on fab and press dialog's positive button

    @Test
    public void fabSavePrice_wontEndOcrScanActivity(){
        tapOnFabAndPressDialogPositiveButton();
        assertFalse(activity.isFinishing());
    }

    @Test
    public void fabSavePrice_willChangeBackgroundTintList(){
        tapOnFabAndPressDialogPositiveButton();
        assertEquals(getColorFromResources(R.color.fab_miniature_color),fabSavePrice.getBackgroundTintList().getDefaultColor());
    }


    @Test
    public void fabSavePrice_willHideTextTargetingLayout_whileSavingMiniature(){
        tapOnFabAndPressDialogPositiveButton();
        assertEquals(0f,activity.findViewById(R.id.textTargetingLayout).getAlpha(),0);
    }



    @Test
    public void fabSavePrice_resetBackgroundTintList_afterTakingTheMiniature(){
        tapOnFabAndPressDialogPositiveButton();
        fabSavePrice.performClick();
        assertEquals(getColorFromResources(R.color.colorAccent),fabSavePrice.getBackgroundTintList().getDefaultColor());
    }

    @Test
    public void fabSavePrice_willReset_priceTextViewX_afterTakingTheMiniature(){
        TextView priceTextView= activity.findViewById(R.id.recognizedTextViewOcrScanActivity);
        priceTextView.setX(-200);
        tapOnFabAndPressDialogPositiveButton();
        assertTrue(priceTextView.getX()!=-200);
    }

    @Test
    public void fabSavePrice_willReset_priceTextViewY_afterTakingTheMiniature(){
        TextView priceTextView= activity.findViewById(R.id.recognizedTextViewOcrScanActivity);
        priceTextView.setY(-200);
        tapOnFabAndPressDialogPositiveButton();
        assertTrue(priceTextView.getY()!=-200);
    }




    private void tapOnFabAndPressDialogPositiveButton(){
        fabSavePrice.performClick();
        ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_POSITIVE).performClick();
    }

    private void tapOnFabAndPressDialogNegativeButton(){
        fabSavePrice.performClick();
        ShadowAlertDialog.getLatestAlertDialog().getButton(AlertDialog.BUTTON_NEGATIVE).performClick();
    }

    private String getStringFromResources(int stringId) {
        return activity.getResources().getString(stringId);
    }

    private int getColorFromResources(int colorId) {
        return activity.getResources().getColor(colorId);
    }

}
