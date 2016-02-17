package com.paditech.fifood.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.utils.StringUtil;

/**
 * Created by PaditechPC1 on 2/17/2016.
 */
public class HeaderBaseActivity extends BaseActivity {
    protected void setHeaderTitle ( String text ) {
        if ( findViewById( R.id.header_title ) == null ) {
            return;
        }

        if ( !StringUtil.isEmpty(text) ) {
            findViewById(R.id.header_title).setVisibility( View.VISIBLE );
            ((TextView) findViewById(R.id.header_title)).setText(text);
        } else {
            findViewById(R.id.header_title).setVisibility( View.GONE );
        }
    }

    protected void setHeaderLeftButtonImage(int resId){
        if (findViewById(R.id.header_left_button)==null){
            return;
        }
        ((ImageView)findViewById(R.id.header_left_button)).setImageResource(resId);
    }

    protected void showHeaderLeftButton(boolean show) {
        if ( findViewById( R.id.header_left_button ) == null ) {
            return;
        }

        if ( show ) {
            findViewById( R.id.header_left_button ).setVisibility( View.VISIBLE );
            findViewById( R.id.header_left_button ).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onHeaderLeftButtonClick();
                }
            });
        } else {
            findViewById( R.id.header_left_button ).setVisibility( View.GONE );
        }
    }

    protected void onHeaderLeftButtonClick() {

    }
}
