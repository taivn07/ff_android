package com.paditech.fifood.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.MainActivity;
import com.paditech.fifood.utils.APIClient;
import com.paditech.fifood.utils.SettingClient;


public abstract class BaseFragment extends Fragment {

	protected View mRootView;

	protected static String TAG;

	public abstract int getLayout();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(getLayout(), container, false);
		TAG = this.getClass().getName();
		return mRootView;
	}


	public void showFragment(Fragment fragment){
		((BaseActivity)getActivity()).showFragment(fragment);
	}


	public void showChildFragment( Fragment fragment, int container, boolean isStack ) {
		FragmentManager fragmentManager = getChildFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace( container, fragment );
		if ( isStack ) {
			transaction.addToBackStack(null);
		}
		transaction.commit();
	}

	public void showChildFragmentStateLoss( Fragment fragment, int container, boolean isStack ) {
		FragmentManager fragmentManager = getChildFragmentManager();
		final FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace( container, fragment );
		if ( isStack ) {
			transaction.addToBackStack(null);
		}
		transaction.commitAllowingStateLoss();
	}

	public void setHeaderTitle(String title){
		((MainActivity)getActivity()).setHeaderTitle(title);
	}

	public APIClient getAPIClient() {
		return ( (BaseActivity) getActivity() ).getAPIClient();
	}

	public SettingClient getSettingClient() {
		return ( (BaseActivity) getActivity() ).getSettingClient();
	}

	protected ImageLoader imageLoader = ImageLoader.getInstance();
}
