package com.butterfly.pink_keyboard.keybord;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.butterfly.pink_keyboard.Ads;
import com.butterfly.pink_keyboard.R;
import com.butterfly.pink_keyboard.module.base.ActivityBase;
import com.butterfly.pink_keyboard.module.constants.ApplicationPrefs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class KeyBoardActivity extends ActivityBase implements OnClickListener {

    ConsentForm form ;

    ApplicationPrefs applicationPrefs;
    private static final int PICK_FROM_GEALLERY = 1;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    LinearLayout l_lay_keyboard_enableKeyboard, l_lay_keyboard_SetInputMethod, l_lay_keyboard_setThemes, l_lay_keyboard_setThemesBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        // GDPR nsr
        ConsentInformation consentInformation = ConsentInformation.getInstance(this);
        String[] publisherIds = {"pub-9413304849687382"};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });
        URL privacyUrl = null;
        try {
            // TODO: Replace with your app's privacy policy URL.
            privacyUrl = new URL("https://sites.google.com/view/privacy-policy-nsrdiv24");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle error.
        }
        form = new ConsentForm.Builder(this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        // Consent form loaded successfully.
                        form.show();
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }

                    @Override
                    public void onConsentFormClosed(
                            ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        // Consent form was closed.
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        // Consent form error.
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .withAdFreeOption()
                .build();
        form.load();
        //native ads 2
        FrameLayout adsFrameLayout = (FrameLayout) findViewById(R.id.adPlaceHolderFrameLayout);
        adsFrameLayout.setVisibility(View.VISIBLE);
        new Ads(KeyBoardActivity.this, adsFrameLayout).refreshAd(true, true);
        if(getPackageName().compareTo("com.butterfly.pink_keyboard") != 0)
        {
            String error = null;
            error.getBytes();
        }
        applicationPrefs = ApplicationPrefs.getInstance(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });



        initUI();
        prepareAd();
    }

    // by khalid
    private void prepareAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void initUI() {
        // TODO Auto-generated method stub

        /*Find views*/

        l_lay_keyboard_enableKeyboard = (LinearLayout) findViewById(R.id.l_lay_keyboard_enableKeyboard);
        l_lay_keyboard_SetInputMethod = (LinearLayout) findViewById(R.id.l_lay_keyboard_SetInputMethod);
        l_lay_keyboard_setThemes = (LinearLayout) findViewById(R.id.l_lay_keyboard_setThemes);
        l_lay_keyboard_setThemesBackground = (LinearLayout) findViewById(R.id.l_lay_keyboard_setThemesBackground);


        /*Add Listener*/
        l_lay_keyboard_enableKeyboard.setOnClickListener(this);
        l_lay_keyboard_SetInputMethod.setOnClickListener(this);
        l_lay_keyboard_setThemes.setOnClickListener(this);
        l_lay_keyboard_setThemesBackground.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {


            case R.id.l_lay_keyboard_enableKeyboard:
                startActivityForResult(new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS), 0);
                break;

            case R.id.l_lay_keyboard_SetInputMethod:
                ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .showInputMethodPicker();
                break;

            case R.id.l_lay_keyboard_setThemes:
                if (mInterstitialAd.isLoaded()) {
                    // by khalid
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            Intent intent2 = new Intent(KeyBoardActivity.this, ThemesListActivity.class);
                            startActivity(intent2);
                        }
                    });
                } else {
                    Intent intent2 = new Intent(this, ThemesListActivity.class);
                    startActivity(intent2);
                }

                break;

            case R.id.l_lay_keyboard_setThemesBackground:
                if (mInterstitialAd.isLoaded()) {
                    // by khalid
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                            Intent intent = new Intent(getApplicationContext(), CustomeThemesActivity.class);
                            startActivity(intent);

                        }
                    });

                } else {
                    Intent intent = new Intent(getApplicationContext(), CustomeThemesActivity.class);
                    startActivity(intent);
                }
                //takePicture();
                break;
            default:
                break;
        }

    }

    private void startCropImages(String file2) {

        Intent intent = new Intent(getApplicationContext(), Crope.class);
        intent.putExtra("image-path", file2);
        startActivityForResult(intent, 3);
        // TODO Auto-generated method stub
        //Intent intent = new Intent(getApplicationContext(),Crope.class);
        //  intent.putExtra("image-path", mFileTemp.getPath());
        // Log.i("----------------", mFileTemp.getAbsolutePath());
        //startActivity(intent);

    }

    private void takePicture() {
        try {
            if (Environment.getExternalStorageState().equals("mounted")) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(
                        Intent.createChooser(intent, "Select Picture:"),
                        PICK_FROM_GEALLERY);
            }
        } catch (ActivityNotFoundException localActivityNotFoundException) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_GEALLERY:

                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                Bitmap photo = getPreview(selectedImagePath);
                // imageView.setImageBitmap(photo);
                startCropImages(selectedImagePath);
                session.setBitmap(photo);

                break;


        }

    }

    public Bitmap getPreview(String fileName) {
        File image = new File(fileName);

        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image.getPath(), bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 64;
        // opts.inSampleSize = originalSize;
        return BitmapFactory.decodeFile(image.getPath());
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
