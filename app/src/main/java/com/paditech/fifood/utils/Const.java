package com.paditech.fifood.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.text.SimpleDateFormat;

/**
 * Created by DucNM on 12/20/2015.
 */
public class Const {
    private static final String TAG = Const.class.getSimpleName();

    static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    public static final String API_ENDPOINT = "http://paditech.com/fifood_server/api/v1";

    public static final String PREF_NAME = "Fifood";


    public static String getVersionName( Context context ) {
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo( context.getPackageName(), 0 );
            versionName = packageInfo.versionName;
        } catch ( PackageManager.NameNotFoundException e ) {
            Log.w( TAG, "Version Name Exception", e );
        }
        return versionName;
    }

    public static String getPackageName( Context context ) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo( context.getPackageName(), PackageManager.GET_ACTIVITIES );
            return packageInfo.packageName;
        } catch ( PackageManager.NameNotFoundException e ) {
            Log.w(TAG, "Package Name Exception", e);
            return null;
        }
    }

    public static String getPlatformName( Context context ) {
        StringBuilder sb = new StringBuilder();
        sb.append( "android" ).append( " " ).append( Build.VERSION.RELEASE );
        return sb.toString();
    }

    public enum OAuthProvider {
        Facebook( "facebook" ),
        Twitter( "twitter" ),
        Line( "line" );

        private String mCode;

        private OAuthProvider( String code ) {
            mCode = code;
        }

        public String getCode() {
            return mCode;
        }
    }
}
