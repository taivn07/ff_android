package com.paditech.fifood.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.paditech.fifood.R;


public class FooterFragment extends TabBaseFragment implements View.OnClickListener{

    public static final int TAB_HOME = 1;
    public static final int TAB_NEAR_ME = 2;
    public static final int TAB_ADD_STORE = 3;
    public static final int TAB_SEARCH = 4;
    public static final int TAB_PROFILE = 5;
    private ImageView mHome,mNearMe,mAddStore,mSearch,mProfile;

    private int mCurrentTab = 0;

    @Override
    public int getLayout() {
        return R.layout.footer_layout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mHome =(ImageView)mRootView.findViewById(R.id.iv_home);
        mHome.setOnClickListener(this);
        mNearMe =(ImageView)mRootView.findViewById(R.id.iv_near_me);
        mNearMe.setOnClickListener(this);
        mAddStore =(ImageView)mRootView.findViewById(R.id.iv_add_store);
        mAddStore.setOnClickListener(this);
        mSearch =(ImageView)mRootView.findViewById(R.id.iv_search);
        mSearch.setOnClickListener(this);
        mProfile =(ImageView)mRootView.findViewById(R.id.iv_profile);
        mProfile.setOnClickListener(this);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Bundle bundle=getArguments();
        String fragmenthistory = bundle.getString("message");
        int currenTab = TAB_HOME;
        setCurrentTab(currenTab);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCurrentTab = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public void setCurrentTab(int index){


        switch (index) {
            case TAB_HOME:
                mCurrentTab = TAB_HOME;
                showFragment(new HomeFragment());
                mHome.setBackgroundColor(getResources().getColor(R.color.themebutton));
                break;
            case TAB_NEAR_ME:
                mCurrentTab = TAB_NEAR_ME;
                showFragment(new NearMeFragment());
                break;
            case TAB_ADD_STORE:
                mCurrentTab = TAB_ADD_STORE;
                showFragment(new AddStoreFragment());
                break;
            case TAB_SEARCH:
                mCurrentTab = TAB_SEARCH;
                showFragment(new SearchFragment());
                break;
            case TAB_PROFILE:
                mCurrentTab = TAB_PROFILE;
                showFragment(new ProfileFragment());
            default:
                mCurrentTab = index;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_home:
                if(mCurrentTab != TAB_HOME){
                    setCurrentTab(TAB_HOME);
                }
                mHome.setBackgroundColor(getResources().getColor(R.color.themebutton));
                mNearMe.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mAddStore.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mSearch.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mProfile.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                break;
            case R.id.iv_near_me:
                if(mCurrentTab != TAB_NEAR_ME){
                    setCurrentTab(TAB_NEAR_ME);
                }
                mNearMe.setBackgroundColor(getResources().getColor(R.color.themebutton));
                mHome.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mAddStore.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mSearch.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mProfile.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                break;
            case R.id.iv_add_store:
                if(mCurrentTab!= TAB_ADD_STORE){
                    setCurrentTab(TAB_ADD_STORE);
                }
                mAddStore.setBackgroundColor(getResources().getColor(R.color.themebutton));
                mNearMe.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mHome.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mSearch.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mProfile.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                break;
            case R.id.iv_search:
                if(mCurrentTab != TAB_SEARCH){
                    setCurrentTab(TAB_SEARCH);
                }
                mSearch.setBackgroundColor(getResources().getColor(R.color.themebutton));
                mNearMe.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mHome.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mAddStore.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mProfile.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                break;
            case R.id.iv_profile:
                if(mCurrentTab != TAB_PROFILE){
                    setCurrentTab(TAB_PROFILE);
                }
                mProfile.setBackgroundColor(getResources().getColor(R.color.themebutton));
                mNearMe.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mHome.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mSearch.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                mAddStore.setBackgroundColor(getResources().getColor(R.color.themeHeader));
                break;
        }

    }
}
