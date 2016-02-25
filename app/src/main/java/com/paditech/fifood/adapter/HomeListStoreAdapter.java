package com.paditech.fifood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.model.Shops;
import com.paditech.fifood.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/18/2016.
 */
public class HomeListStoreAdapter extends BaseAdapter  {
    private static final String TAG = HomeListStoreAdapter.class.getSimpleName();
    BaseActivity mBaseActivity;
    private List<Shops> mPost;

    public HomeListStoreAdapter(BaseActivity activity) {
        super();
        this.mBaseActivity = activity;
    }

    public void setPosts(List<Shops> posts) {
        this.mPost = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mPost != null) {
            return mPost.size();
        }
        return 0;
    }

    @Override
    public Shops getItem(int position) {
        return mPost.get(position);
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
            convertView = inflater.inflate(R.layout.list_store_home_item, null);
            holder = new ViewHolder();
            holder.mNameStore = (TextView)convertView.findViewById(R.id.name_store);
            holder.mAddress = (TextView) convertView.findViewById(R.id.tv_address_store_home);
            holder.mAvatarStore = (ImageView)convertView.findViewById(R.id.iv_avatar_store);
            holder.mStart = (RatingBar)convertView.findViewById(R.id.rb_number_star);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Shops shops = getItem(position);
        holder.mNameStore.setText(shops.name);
        holder.mAddress.setText(shops.address);
        if (!StringUtil.isEmpty(shops.file.url)){
            Picasso.with(mBaseActivity).load(shops.file.url).placeholder(R.drawable.profile_placeholder).into(holder.mAvatarStore);
        }
        holder.mStart.setRating(shops.rating);

        return convertView;
    }

    private class ViewHolder {
        TextView mNameStore;
        TextView mAddress;
        ImageView mAvatarStore;
        RatingBar mStart;

    }
}
