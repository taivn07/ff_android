package com.paditech.fifood.utils;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

import okio.Buffer;

/**
 * Created by DucNM on 12/20/2015.
 */
public class APIClient {
    private static final String TAG = APIClient.class.getSimpleName();

    private static final OkHttpClient mHttpClient = Const.HTTP_CLIENT;

    static {
        mHttpClient.setConnectTimeout( 60, TimeUnit.SECONDS );
        mHttpClient.setReadTimeout( 60, TimeUnit.SECONDS );
        mHttpClient.setWriteTimeout( 60, TimeUnit.SECONDS );
        mHttpClient.setRetryOnConnectionFailure( true );
    }

    private SettingClient mSettingClient;
    private Context mContext;

    public APIClient( Context context ) {
        mContext = context;
        mSettingClient = new SettingClient( mContext );
    }

    static String getResourceURL( String resource ) {
        return String.format("%s%s", Const.API_ENDPOINT, resource);
    }

    public static JSONObject extractData( JSONObject jsonObject ) throws JSONException {
        return jsonObject.getJSONObject("");
    }

    static String makeQueryString( Map<String, String> params ) {
        StringBuilder sb = new StringBuilder();

        for ( Map.Entry<String, String> entry : params.entrySet() ) {
            if ( sb.length() > 0 ) {
                sb.append( "&" );
            }

            try {
                sb.append( URLEncoder.encode(entry.getKey(), "utf-8") ).append( "=" )
                        .append( URLEncoder.encode( entry.getValue(), "utf-8" ) );
            } catch ( UnsupportedEncodingException e ) {
                Log.w( TAG, "Encode Error", e );
            }

        }

        if ( sb.length() > 0 ) {
            sb.insert( 0, "?" );
            return sb.toString();
        } else {
            return "";
        }

    }

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    private void execMethod( String url, String method, RequestBody body, Callback callback ) {
        Log.i(TAG, String.format("method=%s, url=%s", method, url));
        final Request.Builder rb = new Request.Builder().url( url ).method( method, body );
        mHttpClient.newCall( rb.build() ).enqueue( callback );
    }

    public void execGet( String uri, SortedMap<String, String> params, Callback callback ) {
        StringBuilder sb = new StringBuilder();
        sb.append( getResourceURL( uri ) ).append(makeQueryString(params));
        final String url = sb.toString();
        execMethod(url, "GET", null, callback);
    }

    public void execDelete( String uri, SortedMap<String, String> params, Callback callback ) {
        StringBuilder sb = new StringBuilder();
        sb.append( getResourceURL( uri ) ).append( makeQueryString( params ) );
        final String url = sb.toString();
        execMethod( url, "DELETE", null, callback );
    }

    public void execPost( String uri, SortedMap<String, String> params, Callback callback ) {
        FormEncodingBuilder fb = new FormEncodingBuilder();
        for ( Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            String value = params.get( key );
            fb.add( key, value );
        }
        execMethod( getResourceURL( uri ), "POST", fb.build(), callback );
    }

    public void execPostWithUrlParameters( String uri, SortedMap<String, String> urlParams, SortedMap<String, String> formParams, Callback callback ) {
        StringBuilder sb = new StringBuilder();
        sb.append( getResourceURL( uri ) ).append( makeQueryString( urlParams ) );
        final String url = sb.toString();
        FormEncodingBuilder fb = new FormEncodingBuilder();
        for ( Iterator<String> iterator = formParams.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            String value = formParams.get( key );
            fb.add( key, value );
        }
        execMethod( url, "POST", fb.build(), callback );
    }

    public void execMultipartPost( String uri, SortedMap<String, RequestBody> params, SortedMap<String, RequestBody> files, Callback callback ) {
        final MultipartBuilder mb = new MultipartBuilder().type( MultipartBuilder.FORM );
        for ( Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            mb.addPart( Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""), params.get( key ) );
        }

        for ( Iterator<String> iterator = files.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            mb.addPart( Headers.of( "Content-Disposition", "form-data; name=\"" + key + "\"; filename=\"" + key + "\"" ), files.get( key ) );
        }
        execMethod( getResourceURL( uri ), "POST", mb.build(), callback );
    }

    public void execPut( String uri, SortedMap<String, String> params, Callback callback ) {
        FormEncodingBuilder fb = new FormEncodingBuilder();
        for ( Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            String value = params.get( key );
            fb.add( key, value );
        }
        execMethod(getResourceURL(uri), "PUT", fb.build(), callback);
    }

    public void execMultipartPut( String uri, SortedMap<String, RequestBody> params, SortedMap<String, RequestBody> files, Callback callback ) {
        final MultipartBuilder mb = new MultipartBuilder().type( MultipartBuilder.FORM );
        for ( Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            mb.addPart( Headers.of( "Content-Disposition", "form-data; name=\"" + key + "\"" ), params.get( key ) );
        }

        for ( Iterator<String> iterator = files.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            mb.addPart( Headers.of( "Content-Disposition", "form-data; name=\"" + key + "\"; filename=\"" + key + "\"" ), files.get( key ) );
        }
        execMethod( getResourceURL( uri ), "PUT", mb.build(), callback );
    }
}
