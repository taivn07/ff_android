package com.paditech.fifood.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.HomeListStoreAdapter;
import com.paditech.fifood.model.ListStores;
import com.paditech.fifood.utils.DialogUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by PaditechPC1 on 2/19/2016.
 */
public class SearchStoreFragment extends TabBaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = ListStoreFragment.class.getSimpleName();

    SearchFragment mSearchFragment;
    HomeListStoreAdapter mHomeListStoreAdapter;
    ListView mListStoreSearch;
    private BaseActivity mBaseActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_store, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        mSearchFragment = (SearchFragment) getParentFragment();
        mBaseActivity = mSearchFragment.mBaseActivity;
        mHomeListStoreAdapter = new HomeListStoreAdapter(mBaseActivity);
        mListStoreSearch = (ListView)view.findViewById(R.id.lv_list_store_search);
        mListStoreSearch.setAdapter(mHomeListStoreAdapter);
        mListStoreSearch.setOnItemClickListener(this);
        String body = fakeResponse();
        final ListStores data = new Gson().fromJson(body, ListStores.class);
        mHomeListStoreAdapter.setPosts(data.data);

    }

    private void getPost(){
        final Dialog dialog = DialogUtil.makeLoadingDialog(mBaseActivity);
        dialog.show();

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                dialog.dismiss();
            }
            @Override
            public void onResponse(Response response) throws IOException {
//                String body = response.body().string();
                String body = fakeResponse();
                if (response.isSuccessful()) {
                    Log.d(TAG, body);
                    final ListStores data = new Gson().fromJson(body, ListStores.class);
                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mHomeListStoreAdapter.setPosts(data.data);
                        }
                    });
                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    });
                } else {
                    body = response.body().string();
                    Log.d( TAG, body );
                }
            }
        };
        String put_offset = String.valueOf(0);
        SortedMap<String, String> params = new TreeMap<>();
        params.put("page_type", "Home");
        params.put("offset", put_offset);
        getAPIClient().execGet("/post", params, callback);
    }


    private void gotoDetail() {
        mSearchFragment.gotoDetail();
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
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"1f7a4169-4c48-4a83-94de-db4de4876f341448794181613\",\n" +
                "            \"name\": \"test_name\",\n" +
                "            \"thumbnailImageUrl\": \"\",\n" +
                "            \"accountComment\": \"\",\n" +
                "            \"isFollowed\": false\n" +
                "        },\n" +
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gotoDetail();
    }
}