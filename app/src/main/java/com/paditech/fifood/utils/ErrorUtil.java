package com.paditech.fifood.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DucNM on 12/23/2015.
 */
public class ErrorUtil {
    private static final String TAG = ErrorUtil.class.getSimpleName();

    public interface ErrorListenner {
        public boolean onError(JSONObject error);
    }

    public static void processError( String response, ErrorListenner errorListenner) {
        try {
            JSONObject object = new JSONObject( response );

            if ( !object.has( "errors" ) ) {
                return;
            }

            JSONArray errors = object.getJSONArray( "errors" ).getJSONObject(0)
                    .getJSONArray( "errors" );
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < errors.length(); i++ ) {
                sb.append( errors.getString(i) );
                sb.append( "\n" );
            }
            if ( errorListenner != null ) {
                for (int i = 0; i < errors.length(); i++) {
                    if (errorListenner.onError(errors.getJSONObject(i))) {
                        break;
                    }
                }
            }
            Log.d(TAG, sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
