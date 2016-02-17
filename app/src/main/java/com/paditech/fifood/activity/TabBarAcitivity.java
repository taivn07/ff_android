package com.paditech.fifood.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.fragment.FooterFragment;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class TabBarAcitivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private int mCurrentMenu = 0;
    private FooterFragment mFooter = new FooterFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_activity);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Bundle bundle = new Bundle();
        bundle.putString("message", getIntent().getStringExtra("message"));
        mFooter.setArguments(bundle);
        showFragmentFooter(mFooter);
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

    }


    public void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.frame_content_nav, fragment)
                .commit();
    }

    public void setHeaderTitle(String title) {
        ((TextView) findViewById(R.id.tv_header)).setText(title);
    }

    public void setHeaderButtonRight (int display){
        ((Button) findViewById(R.id.bt_add)).setVisibility(display);
    }

    public void setHeaderButtonLeft(int display){
        ((Button)findViewById(R.id.bt_back)).setVisibility(display);
    }


    public int getCurrentMenu() {
        return mCurrentMenu;
    }
}
