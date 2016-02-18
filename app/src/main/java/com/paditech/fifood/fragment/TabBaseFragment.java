package com.paditech.fifood.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.TabBarAcitivity;
import com.paditech.fifood.utils.APIClient;
import com.paditech.fifood.utils.SettingClient;


public class TabBaseFragment extends BaseFragment {

    @Override
    public  int getLayout() {
        return 0;
    }

    public void setHeaderTitle(String title){
        ((TabBarAcitivity)getActivity()).setHeaderTitle(title);
    }

    public void setHeaderButtonRight(int display){
        ((TabBarAcitivity)getActivity()).setHeaderButtonRight(display);
    }

    public void setHeaderButtonLeft(int display){
        ((TabBarAcitivity)getActivity()).setHeaderButtonLeft(display);
    }


    public void setCurrentMenu(int index){
        ((TabBarAcitivity)getActivity()).setCurrentMenu(index);
    }

    public void showFooter(){
        ((TabBarAcitivity)getActivity()).showFooter();
    }

    public void hideFooter(){
        ((TabBarAcitivity)getActivity()).hideFooter();
    }

    public void hideHeader(){
        ((TabBarAcitivity)getActivity()).hideHeader();
    }

    public void showChildFragment( Fragment fragment ) {
        showChildFragment(fragment, R.id.child_fragment_content_view, false);
    }

    public void showChildFragment( Fragment fragment, boolean isStack ) {
        showChildFragment(fragment, R.id.child_fragment_content_view, isStack);
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

    public void backToPreviousChildFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.popBackStack();
    }

    public void closeChildFragment( Fragment fragment ) {
        FragmentManager fragmentManager = getChildFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public APIClient getAPIClient() {
        return ( (TabBarAcitivity) getActivity() ).getAPIClient();
    }

    public SettingClient getSettingClient() {
        return ( (TabBarAcitivity) getActivity() ).getSettingClient();
    }

}
