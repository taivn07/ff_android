package com.paditech.fifood.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.fragment.LoginFragment;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        showFragment(new LoginFragment());

    }

    public void showHeader() {
        findViewById(R.id.frame_header).setVisibility(View.INVISIBLE);
    }

    public void setHeaderTitle(String title) {
        ((TextView) findViewById(R.id.tv_header)).setText(title);
    }

    public void hideHeader() {
        findViewById(R.id.frame_header).setVisibility(View.GONE);
    }
}
