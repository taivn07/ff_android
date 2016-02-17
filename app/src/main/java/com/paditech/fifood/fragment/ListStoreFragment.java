package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.paditech.fifood.R;

/**
 * Created by PaditechPC1 on 2/17/2016.
 */
public class ListStoreFragment extends TabBaseFragment implements View.OnClickListener {

    HomeFragment mhomeFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        mhomeFragment = (HomeFragment) getParentFragment();
        getView().findViewById(R.id.abc).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.abc:
            gotoDetail();
            break;
        default:break;
    }
    }

    private void gotoDetail() {
        mhomeFragment.gotoDetail();
    }
}
