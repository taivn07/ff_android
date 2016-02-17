package com.paditech.fifood.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.paditech.fifood.R;


public class BaseActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
