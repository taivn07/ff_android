package com.paditech.fifood.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.adapter.ListImageStoreGridAdapter;
import com.paditech.fifood.model.ListStores;
import com.paditech.fifood.model.ShopsDetail;
import com.paditech.fifood.model.ShopsImage;
import com.paditech.fifood.utils.StringUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by DucAnh on 2/17/2016.
 */
public class ListImageShopActivity extends HeaderBaseActivity implements View.OnClickListener {
    private static final String TAG = ListImageShopActivity.class.getSimpleName();
    GridView mListImageStore;
    ListImageStoreGridAdapter mListImageStoreGridAdapter;
    ImageView mAvatarStore;
    TextView mNameStore;
    TextView mAddressStore;
    RatingBar mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_image_shop_activity);
        init();
    }

    private void init() {
        mAvatarStore = (ImageView)findViewById(R.id.iv_avatar_store);
        mNameStore = (TextView)findViewById(R.id.name_store);
        mAddressStore = (TextView)findViewById(R.id.tv_address_store);
        mRating = (RatingBar)findViewById(R.id.rb_number_star);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mListImageStore = (GridView)findViewById(R.id.gv_list_image_store);
        mListImageStoreGridAdapter = new ListImageStoreGridAdapter(this);
        mListImageStore.setAdapter(mListImageStoreGridAdapter);

        getPostAccountStore();
        getStoreImages();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            default:break;
        }
    }

    private void getPostAccountStore(){

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response mResponse) throws IOException {
                String body = mResponse.body().string();
                if (mResponse.isSuccessful()) {
                    Log.d(TAG, body);
                    final ShopsDetail data = new Gson().fromJson(body, ShopsDetail.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mNameStore.setText(data.response.name);
                            mAddressStore.setText(data.response.address);
                            mRating.setRating(data.response.rating);
//                            if (!StringUtil.isEmpty(data.response.file.url)) {
//                                Picasso.with(getApplicationContext()).load(data.response.file.url).placeholder(R.drawable.profile_placeholder).into(mAvatarStore);
//                            }

                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } else {
                    body = mResponse.body().string();
                    Log.d( TAG, body );
                }
            }
        };
        SortedMap<String, String> params = new TreeMap<>();
        params.put("shop_id", "1");
        params.put("lat","20.996309");
        params.put("longth","105.827309");
        params.put("lang","vi");
        getAPIClient().execPostWithUrlParameters("/shop_detail", params, params, callback);
    }

    private void getStoreImages(){

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response mResponse) throws IOException {
                String body = mResponse.body().string();
                if (mResponse.isSuccessful()) {
                    Log.d(TAG, body);
                    final ShopsImage data = new Gson().fromJson(body, ShopsImage.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        mListImageStoreGridAdapter.setPosts(data.response.images);

                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } else {
                    body = mResponse.body().string();
                    Log.d( TAG, body );
                }
            }
        };
        SortedMap<String, String> params = new TreeMap<>();
        params.put("shop_id", "1");
        params.put("lang","vi");
        getAPIClient().execPostWithUrlParameters("/shop_image", params, params, callback);
    }

}
