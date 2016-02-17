package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.fifood.R;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class SearchFragment extends TabBaseFragment implements View.OnClickListener {

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
        return R.layout.search_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeaderTitle(getString(R.string.search));
        setHeaderButtonRight(View.INVISIBLE);
        setHeaderButtonLeft(View.INVISIBLE);
        setCurrentMenu(0);

    }

    @Override
    public void onClick(View v) {

    }
}
