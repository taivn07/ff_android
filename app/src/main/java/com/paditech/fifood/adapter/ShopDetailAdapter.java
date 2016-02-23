package com.paditech.fifood.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.ListImageShopActivity;
import com.paditech.fifood.model.ListStore;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/23/2016.
 */
public class ShopDetailAdapter extends BaseAdapter {
    private static final String TAG = HomeListStoreAdapter.class.getSimpleName();
    BaseActivity mBaseActivity;
    private List<ListStore> mListStores;

    public ShopDetailAdapter(BaseActivity activity) {
        super();
        this.mBaseActivity = activity;
    }

    public void setPosts(List<ListStore> listStores) {
        this.mListStores = listStores;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mListStores != null) {
            return mListStores.size();
        }
        return 0;
    }

    @Override
    public ListStore getItem(int position) {
        return mListStores.get(position);
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
            convertView = inflater.inflate(R.layout.list_comment_store_item, null);
            holder = new ViewHolder();
            holder.mUserName = (TextView)convertView.findViewById(R.id.tv_user_name);
            holder.mPostImageCommentStore = (ImageView)convertView.findViewById(R.id.iv_post_image_comment_account);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ListStore listStore = getItem(position);
        holder.mUserName.setText(listStore.name);
        holder.mPostImageCommentStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView mUserName;
        ImageView mPostImageCommentStore;

    }
}
