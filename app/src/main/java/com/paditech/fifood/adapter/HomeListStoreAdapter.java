package com.paditech.fifood.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.model.ListStore;

import java.util.List;

/**
 * Created by PaditechPC1 on 2/18/2016.
 */
public class HomeListStoreAdapter extends BaseAdapter  {
    private static final String TAG = HomeListStoreAdapter.class.getSimpleName();
    BaseActivity mBaseActivity;
    private List<ListStore> mListStores;

    public HomeListStoreAdapter(BaseActivity activity) {
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
            convertView = inflater.inflate(R.layout.list_store_home_item, null);
            holder = new ViewHolder();
            holder.mNameStore = (TextView)convertView.findViewById(R.id.name_store);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ListStore listStore = getItem(position);
        holder.mNameStore.setText(listStore.name);

        return convertView;
    }

    private class ViewHolder {
        TextView mNameStore;

    }
}
