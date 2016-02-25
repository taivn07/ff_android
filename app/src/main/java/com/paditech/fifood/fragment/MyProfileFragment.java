package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.ListStorePostMyProfileAdapter;
import com.paditech.fifood.model.ListStores;

/**
 * Created by PaditechPC1 on 2/22/2016.
 */
public class MyProfileFragment extends TabBaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private static final String TAG = MyProfileFragment.class.getSimpleName();

    ProfileFragment mProfileFragment;
    ListStorePostMyProfileAdapter mListStorePostMyProfileAdapter;
    ListView mListStoreProfile;
    private BaseActivity mBaseActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        mProfileFragment = (ProfileFragment)getParentFragment();
        mBaseActivity = mProfileFragment.mBaseActivity;
        mListStoreProfile = (ListView)view.findViewById(R.id.lv_list_store_post_my_profile);
        mListStorePostMyProfileAdapter = new ListStorePostMyProfileAdapter(mBaseActivity);
        mListStoreProfile.setAdapter(mListStorePostMyProfileAdapter);
        mListStoreProfile.setOnItemClickListener(this);
        String body = fakeResponse();
        final ListStores data = new Gson().fromJson(body, ListStores.class);
//        mListStorePostMyProfileAdapter.setPosts(data.data);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gotoDetail();
    }
    private void gotoDetail() {
        mProfileFragment.gotoDetail();
    }

    @Override
    public void onClick(View v) {

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
}
