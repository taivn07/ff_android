package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;

import java.util.Stack;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class HomeFragment extends TabBaseFragment implements View.OnClickListener {

    Stack<String> keywords = new Stack<>();
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

    public int getLayout() {
        return R.layout.home_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeaderTitle(getString(R.string.home));
        setHeaderButtonRight(View.INVISIBLE);
        setHeaderButtonLeft(View.INVISIBLE);
        setCurrentMenu(0);
        init();

    }

    private void init() {
        final View view = getView();
        mBaseActivity = (BaseActivity) getActivity();
        showChildFragment(new ListStoreFragment(), true);
    }

    @Override
    public void onClick(View v) {

    }

    public void gotoDetail(){
        showChildFragment(new ShopDetailFragment(),true);
    }
}
