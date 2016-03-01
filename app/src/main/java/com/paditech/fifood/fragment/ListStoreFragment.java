package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.HomeListStoreAdapter;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import com.paditech.fifood.model.ListStores;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by PaditechPC1 on 2/17/2016.
 */
public class ListStoreFragment extends TabBaseFragment implements AdapterView.OnItemClickListener {
    private static final String TAG = ListStoreFragment.class.getSimpleName();

    HomeFragment mhomeFragment;
    HomeListStoreAdapter mHomeListStoreAdapter;
    ListView mListStore;
    private BaseActivity mBaseActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        mhomeFragment = (HomeFragment) getParentFragment();
        mBaseActivity = mhomeFragment.mBaseActivity;
        mHomeListStoreAdapter = new HomeListStoreAdapter(mBaseActivity);

        mListStore = (ListView)view.findViewById(R.id.lv_list_store);
        mListStore.setAdapter(mHomeListStoreAdapter);
        mListStore.setOnItemClickListener(this);
        getPost();

    }


    private void getPost(){
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response mResponse) throws IOException {
                String body = mResponse.body().string();
                if (mResponse.isSuccessful()) {
                    Log.d(TAG, body);
                    final ListStores data = new Gson().fromJson(body, ListStores.class);

                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mHomeListStoreAdapter.setPosts(data.response.shops);
                        }
                    });
                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            dialog.dismiss();
                        }
                    });
                } else {
                    body = mResponse.body().string();
                    Log.d( TAG, body );
                }
            }
        };
        String put_offset = String.valueOf(25);
        SortedMap <String, String> params = new TreeMap<>();
        params.put("user_id", "10");
        params.put("lat","20.996309");
        params.put("longth","105.827309");
        params.put("token","");
        params.put("lang","vi");
        params.put("index","1");
        params.put("offset", put_offset);
        getAPIClient().execPostWithUrlParameters("/recomment", params, params, callback);
    }


    private void gotoDetail() {
        mhomeFragment.gotoDetail();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gotoDetail();
    }
}
