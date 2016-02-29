package com.paditech.fifood.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PaditechPC1 on 2/26/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private static final String TAG = ImageAdapter.class.getSimpleName();
    ArrayList<Bitmap> mList;
    LayoutInflater mInflater;
    Activity mActivity;
    SparseBooleanArray mSparseBooleanArray;

    public ImageAdapter(Activity activity, ArrayList<Bitmap> imageList) {
        // TODO Auto-generated constructor stub
        this.mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
        mSparseBooleanArray = new SparseBooleanArray();
        mList = new ArrayList<Bitmap>();
        this.mList = imageList;
    }

    public ArrayList<Bitmap> getCheckedItems() {
        ArrayList<Bitmap> mTempArry = new ArrayList<Bitmap>();

        for(int i=0;i<mList.size();i++) {
            if(mSparseBooleanArray.get(i)) {
                mTempArry.add(mList.get(i));
            }
        }

        return mTempArry;
    }

    @Override
    public int getCount() {
        Log.d(TAG,String.valueOf(mList.size()));
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
        DisplayImageOptions options;

        final ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.grid_image_item, null);
            holder = new ViewHolder();
            holder.mThumbImage = (ImageView)convertView.findViewById(R.id.iv_thumb_image);
            holder.spinner = (ProgressBar)convertView.findViewById(R.id.loading);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mThumbImage.setImageBitmap(mList.get(position));



        return convertView;
    }

    private class ViewHolder {
        ImageView mThumbImage;
            ProgressBar spinner;

    }

}
