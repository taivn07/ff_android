package com.paditech.fifood.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.paditech.fifood.R;
import com.paditech.fifood.model.BaseModel;

import java.util.ArrayList;


/**
 * Created by PaditechPC1 on 2/26/2016.
 */
public class ImageAdapter extends BaseAdapter {
    ArrayList<String> mList;
    LayoutInflater mInflater;
    Context mContext;
    SparseBooleanArray mSparseBooleanArray;
//    private DisplayImageOptions options;


    public ImageAdapter(Context context, ArrayList<String> imageList) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mSparseBooleanArray = new SparseBooleanArray();
        mList = new ArrayList<String>();
        this.mList = imageList;

    }

    public ArrayList<String> getCheckedItems() {
        ArrayList<String> mTempArry = new ArrayList<String>();

        for(int i=0;i<mList.size();i++) {
            if(mSparseBooleanArray.get(i)) {
                mTempArry.add(mList.get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        options = new DisplayImageOptions.Builder()
//                .showStubImage(R.drawable.stub_image)
//                .showImageForEmptyUri(R.drawable.image_for_empty_url)
//                .cacheInMemory()
//                .cacheOnDisc()
//                .build();
//
//        if(convertView == null) {
//            convertView = mInflater.inflate(R.layout.row_multiphoto_item, null);
//        }
//
//        CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
//        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
//
//        imageLoader.displayImage("file://"+mList.get(position), imageView, options, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingComplete(Bitmap loadedImage) {
//                Animation anim = AnimationUtils.loadAnimation(MultiPhotoSelectActivity.this, R.anim.fade_in);
//                imageView.setAnimation(anim);
//                anim.start();
//            }
//        });
//
//        mCheckBox.setTag(position);
//        mCheckBox.setChecked(mSparseBooleanArray.get(position));
//        mCheckBox.setOnCheckedChangeListener(mCheckedChangeListener);

        return convertView;
    }

    CompoundButton.OnCheckedChangeListener mCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // TODO Auto-generated method stub
            mSparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
        }
    };
}
