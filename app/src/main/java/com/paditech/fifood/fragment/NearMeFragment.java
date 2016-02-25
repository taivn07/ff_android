package com.paditech.fifood.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.HomeListStoreAdapter;

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
//        String body = fakeResponse();
//        final ListStores data = new Gson().fromJson(body, ListStores.class);
//        mHomeListStoreAdapter.setPosts(data.data);
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
