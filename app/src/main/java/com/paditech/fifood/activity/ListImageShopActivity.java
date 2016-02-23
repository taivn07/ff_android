package com.paditech.fifood.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.adapter.ListImageStoreGridAdapter;
import com.paditech.fifood.model.ListStores;

/**
 * Created by DucAnh on 2/17/2016.
 */
public class ListImageShopActivity extends HeaderBaseActivity implements View.OnClickListener {
    private static final String TAG = ListImageShopActivity.class.getSimpleName();
    GridView mListImageStore;
    ListImageStoreGridAdapter mListImageStoreGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_image_shop_activity);
        init();
    }

    private void init() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mListImageStore = (GridView)findViewById(R.id.gv_list_image_store);
        mListImageStoreGridAdapter = new ListImageStoreGridAdapter(this);
        mListImageStore.setAdapter(mListImageStoreGridAdapter);
        String body = fakeResponse();
        final ListStores data = new Gson().fromJson(body, ListStores.class);
        mListImageStoreGridAdapter.setPosts(data.data);

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

}
