package com.paditech.fifood.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DucNM on 12/20/2015.
 */
public class SettingClient {
    private static final String TAG = SettingClient.class.getSimpleName();
    Context mContext;

    public SettingClient( Context context ) {
        mContext = context;
    }

    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences( Const.PREF_NAME, Context.MODE_PRIVATE );
    }

    public void saveAccountId(String accountId) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString( "account::id", accountId );
        editor.apply();
    }

    public String getAccountId() {
        SharedPreferences preferences = getPreferences();
        return preferences.getString("account::id", null);
    }

    public void saveAuthToken( String token ) {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString( "account::token", token );
        editor.apply();
    }

    public String getAuthToken() {
        SharedPreferences preferences = getPreferences();
        return preferences.getString( "account::token", null );
    }


    public void removeAccount() {
        final SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove( "account::id" );
        editor.remove( "account::token" );
        editor.remove("account::detail");
        editor.apply();
    }

    public boolean hasAccount() {
        return  ( getAccountId() != null && getAuthToken() != null ) ;
    }


}
