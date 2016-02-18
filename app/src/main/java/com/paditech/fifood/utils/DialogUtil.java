package com.paditech.fifood.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;


import com.paditech.fifood.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by DucNM on 12/22/2015.
 */
public class DialogUtil {

    public static Dialog makeLoadingDialog( Context context ) {
        ProgressDialog dialog = new ProgressDialog( context );
        dialog.setCancelable( false );
        dialog.setMessage( context.getText( R.string.loading ) );
        return dialog;
    }


    public static Dialog makeErrorDialog( Context context, JSONObject data ) {
        return makeErrorDialog( context, data, null );
    }

    public static Dialog makeErrorDialog( Context context, JSONObject data, DialogInterface.OnClickListener onClickListener ) {
        try {
            JSONArray errors = data.getJSONArray("errors").getJSONObject(0)
                    .getJSONArray("errors");
            StringBuilder sb = new StringBuilder();
            for ( int i = 0; i < errors.length(); i++ ) {
                String msg = errors.getJSONObject( i ).toString();
                sb.append(msg).append("\n");
            }

            return makeErrorDialog( context, sb.toString(), onClickListener );
        } catch ( JSONException e ) {
            return makeErrorDialog( context, context.getString( R.string.error_retry ), onClickListener );
        }
    }

    public static Dialog makeErrorDialog( Context context, String message ) {
        return makeErrorDialog( context, message, null );
    }

    public static Dialog makeErrorDialog( Context context, String message, DialogInterface.OnClickListener onClickListener ) {
        return new AlertDialog.Builder( context ).setTitle( R.string.error_title ).setMessage( message ).setCancelable( false ).setPositiveButton( context.getText( R.string.ok ), onClickListener ).create();
    }

    public static AlertDialog makeDialog( Context context, String title, String message, final DialogInterface.OnClickListener okListener ) {
        final AlertDialog dialog = new AlertDialog.Builder( context ).setCancelable( false ).setTitle( title ).setMessage( message ).setPositiveButton( R.string.ok, okListener ).setNegativeButton( R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                dialog.dismiss();
            }
        } ).create();

        return dialog;
    }

    public static AlertDialog makeDialog( Context context, String title, String message ) {
        final AlertDialog dialog = new AlertDialog.Builder( context ).setTitle( title ).setMessage( message ).create();

        return dialog;
    }

    public static AlertDialog makeItemsDialog( Context context, List<CharSequence> items, DialogInterface.OnClickListener listener ) {
        final AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setItems( items.toArray( new CharSequence[items.size()] ), listener );
        return builder.create();
    }
}
