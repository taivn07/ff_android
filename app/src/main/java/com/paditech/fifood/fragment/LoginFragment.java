package com.paditech.fifood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.ListImageShopActivity;
import com.paditech.fifood.activity.MapShopActivity;
import com.paditech.fifood.activity.TabBarAcitivity;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout mLogin;
    private LinearLayout mJoin;
    private Intent mIntent;
    @Override
    public int getLayout() {
        return R.layout.login_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        mLogin = (RelativeLayout)view.findViewById(R.id.facebook_login_button);
        mLogin.setOnClickListener(this);
        mJoin = (LinearLayout)view.findViewById(R.id.skip);
        mJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.facebook_login_button:
                mIntent = new Intent(getActivity(), TabBarAcitivity.class);
                getActivity().startActivity(mIntent);
                break;
            case R.id.skip:
//                mIntent = new Intent(getActivity(), MapShopActivity.class);
//                getActivity().startActivity(mIntent);
                break;
            default:break;
        }

    }
}
