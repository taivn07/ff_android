package com.paditech.fifood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.model.Images;
import com.paditech.fifood.model.ListStore;
import com.paditech.fifood.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/23/2016.
 */
public class ListImageStoreGridAdapter extends BaseAdapter {
    BaseActivity mBaseActivity;
    private List<Images> mImages;

    public ListImageStoreGridAdapter(BaseActivity activity) {
        super();
        this.mBaseActivity = activity;
    }

    public void setPosts(List<Images> Images) {
        this.mImages = Images;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mImages != null) {
            return mImages.size();
        }
        return 0;
    }

    @Override
    public Images getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mBaseActivity);
            convertView = inflater.inflate(R.layout.list_image_store_item, null);
            holder = new ViewHolder();
            holder.mPostImageCommentStore = (ImageView) convertView.findViewById(R.id.iv_image_store_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Images images = getItem(position);
        if (!StringUtil.isEmpty(images.thumbnail_url)) {
            Picasso.with(mBaseActivity).load(images.thumbnail_url).placeholder(R.drawable.profile_placeholder).into(holder.mPostImageCommentStore);
        }


        return convertView;
    }

    private class ViewHolder {
        ImageView mPostImageCommentStore;

    }
}
