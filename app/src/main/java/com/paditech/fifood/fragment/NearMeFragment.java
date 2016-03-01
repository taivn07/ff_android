package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.HomeListStoreAdapter;
import com.paditech.fifood.model.ListStores;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class NearMeFragment extends TabBaseFragment implements View.OnClickListener,OnMapReadyCallback {

    HomeListStoreAdapter mHomeListStoreAdapter;
    ListView mListStoreNearMe;
    private BaseActivity mBaseActivity;
    private GoogleMap mMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.near_me_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        mBaseActivity = (BaseActivity)getActivity();
        setHeaderTitle(getString(R.string.near_me));
        setHeaderButtonRight(View.INVISIBLE);
        setHeaderButtonLeft(View.INVISIBLE);
        setCurrentMenu(0);

        mHomeListStoreAdapter = new HomeListStoreAdapter(mBaseActivity);
        mListStoreNearMe = (ListView) view.findViewById(R.id.map);
        mListStoreNearMe.setAdapter(mHomeListStoreAdapter);

        getPostStoreNearMe();
        initilizeMap();

    }

    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment)((FragmentActivity)getActivity()).getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        initilizeMap();
    }

    private void getPostStoreNearMe(){

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
                    Log.d( TAG, body );
                }
            }
        };
        String put_offset = String.valueOf(25);
        SortedMap<String, String> params = new TreeMap<>();
        params.put("user_id", "1");
        params.put("lat","20.977112");
        params.put("longth","105.842056");
        params.put("token","8K2MY6IVCCOZ");
        params.put("lang","vi");
        params.put("index","0");
        params.put("offset", put_offset);
        getAPIClient().execPostWithUrlParameters("/nearme", params, params, callback);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onDestroyView() {
        super.onDestroyView();
        SupportMapFragment mapFragment = (SupportMapFragment)((FragmentActivity)getActivity()).getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        if (mapFragment!= null)
            ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
    }

}
