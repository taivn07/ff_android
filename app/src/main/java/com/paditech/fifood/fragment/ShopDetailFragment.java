package com.paditech.fifood.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.ListImageShopActivity;
import com.paditech.fifood.adapter.ShopDetailAdapter;
import com.paditech.fifood.model.ListStores;
import com.paditech.fifood.model.ShopsDetail;
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
public class ShopDetailFragment extends TabBaseFragment implements View.OnClickListener {
    private static final String TAG = ShopDetailFragment.class.getSimpleName();
    BaseActivity mBaseActivity;
    ListView mDetailStore;
    ShopDetailAdapter mShopDetailAdapter;
    ImageView mImageStore;
    TextView mNameStore;
    TextView mAddress;
    RatingBar mRating;
    TextView mNumberLike;
    TextView mNumberDislike;
    TextView mStatusChecked;


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
        final View view =getView();

        mBaseActivity=(BaseActivity)getActivity();
        mDetailStore = (ListView) view.findViewById(R.id.lv_detail_store);
        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_shop_detail_list_view, null, false);
        mImageStore = (ImageView)headerView.findViewById(R.id.iv_image_store);
        mImageStore.setOnClickListener(this);
        mNameStore = (TextView)headerView.findViewById(R.id.name_store);
        mAddress = (TextView)headerView.findViewById(R.id.tv_address_store_detail);
        mRating = (RatingBar)headerView.findViewById(R.id.rb_number_star_store_detail);
        mNumberLike = (TextView) headerView.findViewById(R.id.number_comment_good);
        mNumberDislike = (TextView) headerView.findViewById(R.id.number_comment_bad);
        mStatusChecked = (TextView) headerView.findViewById(R.id.tv_status_checked);
        mDetailStore.addHeaderView(headerView);
        mShopDetailAdapter = new ShopDetailAdapter(mBaseActivity);
        mDetailStore.setAdapter(mShopDetailAdapter);
        getPostComment();
    }

    private void getPostComment(){

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

                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mShopDetailAdapter.setPostsComment(data.response.comments);
                            mNameStore.setText(data.response.name);
                            mAddress.setText(data.response.address);
                            mRating.setRating(data.response.rating);
                            mNumberLike.setText(String.valueOf(data.response.like_num));
                            mNumberDislike.setText(String.valueOf(data.response.dislike_num));
                            mStatusChecked.setText(data.response.evaluation);
                            if (!StringUtil.isEmpty(data.response.file.url)){
                                Picasso.with(mBaseActivity).load(data.response.file.url).placeholder(R.drawable.profile_placeholder).into(mImageStore);
                            }

                        }
                    });
                    mBaseActivity.runOnUiThread(new Runnable() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_image_store:
                Intent intent = new Intent(mBaseActivity, ListImageShopActivity.class);
                mBaseActivity.startActivity(intent);
                break;
            default:break;

        }


    }


}
