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
import com.paditech.fifood.model.Comments;
import com.paditech.fifood.model.ListStore;
import com.paditech.fifood.utils.CircleTransform;
import com.paditech.fifood.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/23/2016.
 */
public class ShopDetailAdapter extends BaseAdapter {
    private static final String TAG = HomeListStoreAdapter.class.getSimpleName();
    BaseActivity mBaseActivity;
    private List<Comments> mComments;

    public ShopDetailAdapter(BaseActivity activity) {
        super();
        this.mBaseActivity = activity;
    }

    public void setPostsComment(List<Comments> comments) {
        this.mComments = comments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mComments != null) {
            return mComments.size();
        }
        return 0;
    }

    @Override
    public Comments getItem(int position) {
        return mComments.get(position);
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
            holder.mContent = (TextView)convertView.findViewById(R.id.tv_account_comment);
            holder.mDaysComment = (TextView)convertView.findViewById(R.id.tv_number_days_comment_ago);
            holder.mAvatarAccountComment = (ImageView)convertView.findViewById(R.id.iv_avatar_user_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Comments comments = getItem(position);
        holder.mUserName.setText(comments.nickname);
        holder.mContent.setText(comments.content);
        holder.mDaysComment.setText(comments.created);
        if (!StringUtil.isEmpty(comments.user_profile_image)){
            Picasso.with(mBaseActivity).load(comments.user_profile_image).placeholder(R.drawable.profile_placeholder).transform(new CircleTransform()).into(holder.mAvatarAccountComment);
        }
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
        TextView mContent;
        TextView mDaysComment;
        ImageView mAvatarAccountComment;

    }
}
