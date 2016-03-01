package com.paditech.fifood.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

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
public class SearchStoreFragment extends TabBaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private static final String TAG = SearchStoreFragment.class.getSimpleName();

    SearchFragment mSearchFragment;
    HomeListStoreAdapter mHomeListStoreAdapter;
    ListView mListStoreSearch;
    private BaseActivity mBaseActivity;
    ImageView mSearch;
    EditText mNameStore;
    EditText mAddressSearch;


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

        mSearch = (ImageView) view.findViewById(R.id.bt_search);
        mSearch.setOnClickListener(this);
        mNameStore = (EditText)view.findViewById(R.id.et_name_store);
        mAddressSearch = (EditText)view.findViewById(R.id.et_store_address);
        mHomeListStoreAdapter = new HomeListStoreAdapter(mBaseActivity);
        mListStoreSearch = (ListView) view.findViewById(R.id.lv_list_store_search);

        mListStoreSearch.setAdapter(mHomeListStoreAdapter);
        mListStoreSearch.setOnItemClickListener(this);
        getSearchStore();

    }



    private void gotoDetail() {
        mSearchFragment.gotoDetail();
    }

    private void getSearchStore() {

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
                        }
                    });
                } else {
                    body = mResponse.body().string();
                    Log.d(TAG, body);
                }
            }
        };
        String put_offset = String.valueOf(25);
        SortedMap<String, String> params = new TreeMap<>();
        params.put("name", mNameStore.getText().toString());
        params.put("lat", "16.144326");
        params.put("longth", "105.386158");
        params.put("address", mAddressSearch.getText().toString());
        params.put("lang", "vi");
        params.put("index", "0");
        params.put("offset", put_offset);
        getAPIClient().execPostWithUrlParameters("/shop_search", params, params, callback);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        gotoDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_search:
                mListStoreSearch.setAdapter(mHomeListStoreAdapter);
                getSearchStore();
                break;
            default:break;
        }
    }
}
