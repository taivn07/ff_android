package com.paditech.fifood.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.ListImageShopActivity;
import com.paditech.fifood.adapter.ShopDetailAdapter;
import com.paditech.fifood.model.ListStores;

/**
 * Created by DucAnh on 2/17/2016.
 */
public class ShopDetailFragment extends TabBaseFragment implements View.OnClickListener {
    private static final String TAG = ShopDetailFragment.class.getSimpleName();
    BaseActivity mBaseActivity;
    ListView mDetailStore;
    ShopDetailAdapter mShopDetailAdapter;
    ImageView mImageStore;

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
        mDetailStore.addHeaderView(headerView);
        mShopDetailAdapter = new ShopDetailAdapter(mBaseActivity);
        mDetailStore.setAdapter(mShopDetailAdapter);
        String body = fakeResponse();
        final ListStores data = new Gson().fromJson(body, ListStores.class);
        mShopDetailAdapter.setPosts(data.data);
    }

    private String fakeResponse() {
        return "{\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"724a3ea5-ef37-4168-bc81-548eabb1f6291455781452019\",\n" +
                "            \"name\": \"\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        }\n" +
                "    ],\n" +
                "    \"status\": \"success\"\n" +
                "}";
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
