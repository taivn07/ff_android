package com.paditech.fifood.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.paditech.fifood.R;
import com.paditech.fifood.utils.APIClient;
import com.paditech.fifood.utils.SettingClient;


public class BaseActivity extends FragmentActivity {
	APIClient mAPIClient;
	SettingClient mSettingClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAPIClient = new APIClient( this );
		mSettingClient = new SettingClient( this );
	}

	public APIClient getAPIClient() {
		return mAPIClient;
	}

	public SettingClient getSettingClient() {
		return mSettingClient;
	}

	public void showFragment(Fragment fragment){
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, fragment)
				.commit();
	}

	public void showFragmentAddToBackStack(Fragment fragment){
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, fragment)
				.addToBackStack(null)
				.commit();
	}

	public void showFragmentWithArg(Fragment fragment, Bundle arg){
		fragment.setArguments(arg);
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, fragment)
				.commit();
	}

	public void showFragmentWithArgAddToBackStack(Fragment fragment, Bundle arg){
		fragment.setArguments(arg);
		FragmentManager fm = getFragmentManager();
		fm.beginTransaction().replace(R.id.frame_content, fragment)
				.addToBackStack(null)
				.commit();
	}



}
