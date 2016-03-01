package com.paditech.fifood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.model.ListStore;
import com.paditech.fifood.model.Shops;
import com.paditech.fifood.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/22/2016.
 */
public class ListStorePostMyProfileAdapter extends BaseAdapter {
    private static final String TAG = HomeListStoreAdapter.class.getSimpleName();
    BaseActivity mBaseActivity;
    private List<Shops> mShops;

    public ListStorePostMyProfileAdapter(BaseActivity activity) {
        super();
        this.mBaseActivity = activity;
    }

    public void setPosts(List<Shops> listshops) {
        this.mShops = listshops;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mShops != null) {
            return mShops.size();
        }
        return 0;
    }

    @Override
    public Shops getItem(int position) {
        return mShops.get(position);
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
            convertView = inflater.inflate(R.layout.list_store_post_my_profile_item, null);
            holder = new ViewHolder();
            holder.mNameStore = (TextView)convertView.findViewById(R.id.name_store_my_profile_post);
            holder.mAddress = (TextView)convertView.findViewById(R.id.address_post_my_profile);
            holder.mAvatarShop = (ImageView)convertView.findViewById(R.id.iv_avatar_store);
            holder.mNumberComment = (TextView)convertView.findViewById(R.id.number_comment);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Shops shops = getItem(position);
        holder.mNameStore.setText(shops.name);
        holder.mAddress.setText(shops.address);
        if (!StringUtil.isEmpty(shops.file.thumbnail_url)){
            Picasso.with(mBaseActivity).load(shops.file.thumbnail_url).placeholder(R.drawable.profile_placeholder).into(holder.mAvatarShop);
        }
        holder.mNumberComment.setText("Phản Hồi"+":"+String.valueOf(shops.total_comment));

        return convertView;
    }

    private class ViewHolder {
        TextView mNameStore;
        TextView mAddress;
        ImageView mAvatarShop;
        TextView mNumberComment;

    }
}
