package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class ProfileFragment extends TabBaseFragment implements View.OnClickListener {
    BaseActivity mBaseActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.profile_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        setHeaderTitle(getString(R.string.profile));
        setHeaderButtonRight(View.INVISIBLE);
        setHeaderButtonLeft(View.INVISIBLE);
        setCurrentMenu(0);
        mBaseActivity = (BaseActivity)getActivity();
        showChildFragment(new MyProfileFragment(), true);
    }

    @Override
    public void onClick(View v) {

    }

    public void gotoDetail(){
        showChildFragment(new ShopDetailFragment(),true);
    }
}
