package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.fifood.R;

/**
 * Created by DucAnh on 2/17/2016.
 */
public class ShopDetailFragment extends TabBaseFragment implements View.OnClickListener {

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        setHeaderTitle("Name");
        setHeaderButtonLeft(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {

    }


}
