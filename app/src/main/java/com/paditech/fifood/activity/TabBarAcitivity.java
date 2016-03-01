package com.paditech.fifood.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.paditech.fifood.R;
import com.paditech.fifood.fragment.FooterFragment;
import com.paditech.fifood.utils.APIClient;
import com.paditech.fifood.utils.SettingClient;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class TabBarAcitivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    APIClient mAPIClient;
    SettingClient mSettingClient;

    private int mCurrentMenu = 0;
    private FooterFragment mFooter = new FooterFragment();
    Button mBack;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIClient = new APIClient(this);
        mSettingClient = new SettingClient(this);
        setContentView(R.layout.navi_activity);
        mBack = (Button) findViewById(R.id.bt_back);
        mBack.setOnClickListener(this);
        Bundle bundle = new Bundle();
        bundle.putString("message", getIntent().getStringExtra("message"));
        mFooter.setArguments(bundle);
        showFragmentFooter(mFooter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void showFragmentFooter(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_footer, fragment)
                .commit();
    }


    public void showFooter() {
        findViewById(R.id.frame_footer).setVisibility(View.VISIBLE);
    }

    public void hideFooter() {
        findViewById(R.id.frame_footer).setVisibility(View.GONE);
    }

    public void hideHeader() {
        findViewById(R.id.frame_header).setVisibility(View.GONE);
    }


    public void setCurrentMenu(int currentMenu) {
        this.mCurrentMenu = currentMenu;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    public void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_content_nav, fragment)
                .commit();
    }

    public void setHeaderTitle(String title) {
        ((TextView) findViewById(R.id.tv_header)).setText(title);
    }

    public void setHeaderButtonRight(int display) {
        ((Button) findViewById(R.id.bt_add)).setVisibility(display);
    }

    public void setClickButtonRight() {
        ((Button) findViewById(R.id.bt_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderRightButtonClick();
            }
        });
    }

    protected void onHeaderRightButtonClick() {
    }


    public void setHeaderButtonLeft(int display) {
        ((Button) findViewById(R.id.bt_back)).setVisibility(display);
    }


    public int getCurrentMenu() {
        return mCurrentMenu;
    }

    public APIClient getAPIClient() {
        return mAPIClient;
    }

    public SettingClient getSettingClient() {
        return mSettingClient;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "TabBarAcitivity Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.paditech.fifood.activity/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "TabBarAcitivity Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.paditech.fifood.activity/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }
}
