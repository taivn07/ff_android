package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.ListStorePostMyProfileAdapter;
import com.paditech.fifood.model.ListStores;
import com.paditech.fifood.model.MyShops;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by PaditechPC1 on 2/22/2016.
 */
public class MyProfileFragment extends TabBaseFragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private static final String TAG = MyProfileFragment.class.getSimpleName();

    ProfileFragment mProfileFragment;
    ListStorePostMyProfileAdapter mListStorePostMyProfileAdapter;
    ListView mListStoreProfile;
    private BaseActivity mBaseActivity;
    ImageView mLogout;
    TextView mNumberPost;


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
        mNumberPost = (TextView)view.findViewById(R.id.number_post);
        mListStorePostMyProfileAdapter = new ListStorePostMyProfileAdapter(mBaseActivity);
        mListStoreProfile.setAdapter(mListStorePostMyProfileAdapter);
        mListStoreProfile.setOnItemClickListener(this);
        mLogout = (ImageView) view.findViewById(R.id.iv_logout);
        mLogout.setOnClickListener(this);
//        String body = fakeResponse();
//        final ListStores data = new Gson().fromJson(body, ListStores.class);
////        mListStorePostMyProfileAdapter.setPosts(data.data);
        getPostMyStore();

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
        switch (v.getId()){
            case R.id.iv_logout:

                break;
            default:break;
        }

    }
    private void getPostMyStore() {

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response mResponse) throws IOException {
                String body = mResponse.body().string();
                if (mResponse.isSuccessful()) {
                    Log.d(TAG, body);
                    final MyShops data = new Gson().fromJson(body, MyShops.class);

                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mListStorePostMyProfileAdapter.setPosts(data.response.shops);
                            mNumberPost.setText(String.valueOf(data.response.total));
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
        String put_offset = String.valueOf(1);
        SortedMap<String, String> params = new TreeMap<>();
        params.put("user_id", "4");
        params.put("token", "8K2MY6IVCCOZ");
        params.put("lang", "vi");
        params.put("index", "1");
        params.put("offset", put_offset);
        getAPIClient().execPostWithUrlParameters("/myshop", params, params, callback);
    }
}
