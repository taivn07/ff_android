package com.paditech.fifood.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.TabBarAcitivity;
import com.paditech.fifood.model.LoginModel;
import com.paditech.fifood.utils.DialogUtil;
import com.paditech.fifood.utils.ErrorUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout mLogin;
    private LinearLayout mJoin;
    private Intent mIntent;
    private static final List<String> FACEBOOK_PERMISSIONS = Arrays.asList("public_profile", "email", "user_friends");
    private CallbackManager mCallbackManager;
    private Dialog mLoadingDialog;
    private Dialog mLoginDialog;
    private BaseActivity mBaseActivity;
    @Override
    public int getLayout() {
        return R.layout.login_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());
        init();
    }

    private void init() {
        View view = getView();
        mBaseActivity = (BaseActivity)getActivity();
        mLoadingDialog = DialogUtil.makeLoadingDialog(mBaseActivity);
        mCallbackManager = CallbackManager.Factory.create();
        mLogin = (RelativeLayout)view.findViewById(R.id.facebook_login_button);
        mLogin.setOnClickListener(this);
        mJoin = (LinearLayout)view.findViewById(R.id.skip);
        mJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.facebook_login_button:
//                mIntent = new Intent(getActivity(), TabBarAcitivity.class);
//                getActivity().startActivity(mIntent);
                facebookLogin();
                break;
            case R.id.skip:
                mIntent = new Intent(getActivity(), TabBarAcitivity.class);
                getActivity().startActivity(mIntent);
                break;
            default:break;
        }

    }

    private void facebookLogin() {
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final AccessToken accessToken = loginResult.getAccessToken();
                Log.d(TAG, "FacebookCallback onSuccess. result=" + loginResult.toString());
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, birthday, picture.type(large)");
                GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String thumbnailUrl = "";
                            if (object.has("picture")) {
                                thumbnailUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            }
                            Log.d(TAG, "facebook: " + id + "\n" + name + "\n" + thumbnailUrl);
                            createSocialAccount(id, name, thumbnailUrl, SocialLogin.FACEBOOK);
                        } catch (JSONException ex) {
                            Log.e(TAG, "Facebook Response Error", ex);
                            return;
                        }
                    }
                });
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }


            @Override
            public void onError(FacebookException error) {

            }
        });

        LoginManager.getInstance().logInWithReadPermissions( this, FACEBOOK_PERMISSIONS);
    }

    private void createSocialAccount(final String id, String name, String thumbnailUrl, final SocialLogin socialType) {
        mLoadingDialog.show();

        Callback callback = new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                final String body = response.body().string();
                if ( response.isSuccessful() ) {
                    loginSocialAccount(id, socialType);
                }

                mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.dismiss();

                        ErrorUtil.processError(body, new ErrorUtil.ErrorListenner() {
                            @Override
                            public boolean onError(JSONObject error) {
                                if (error.has(socialType.getExistError())) {
                                    loginSocialAccount(id, socialType);
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {
                Log.d( TAG, request.body().toString() );
                mLoadingDialog.dismiss();
            }
        };
        SortedMap params = new TreeMap<String, String>();
        params.put( socialType.getIdParameter(), id );
        params.put( "name", name );
        params.put("thumbnail_image_url", thumbnailUrl);
        createAccount(params, callback);
    }

    private enum SocialLogin {
        FACEBOOK ( "/facebook", "facebook_id", "facebookIdAlreadyExists" );

        SocialLogin ( String type, String idParam, String error ) {
            mType = type;
            mIdParameter = idParam;
            mExistError = error;
        }

        private String mType;
        private String mIdParameter;
        private String mExistError;

        public String getType() {
            return mType;
        }

        public String getIdParameter() {
            return mIdParameter;
        }

        public String getExistError() {
            return mExistError;
        }
    }

    /**
     * Create account API
     * @param params
     * @param callback
     */
    private void createAccount( SortedMap params, Callback callback ) {
        mBaseActivity.getAPIClient().execPost("/account/main", params, callback);
    }

    private void loginSocialAccount(String id, SocialLogin socialType) {
        SortedMap params = new TreeMap<String, String>();
        params.put(socialType.getIdParameter(), id);
        login(params, socialType.getType());
    }

    private void login( SortedMap params , String loginType) {
        mLoadingDialog.show();

        Callback callback = new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String body = response.body().string();
                    LoginModel loginModel = new Gson().fromJson(body, LoginModel.class);
                    mBaseActivity.getSettingClient().saveAccountId(loginModel.data.accountId);
                    mBaseActivity.getSettingClient().saveAuthToken(loginModel.data.accessToken);
                    Log.d(TAG, loginModel.data.accessToken);
                    if (mBaseActivity.getIntent().getBooleanExtra("login_request", false)) {
                        mBaseActivity.setResult(mBaseActivity.RESULT_OK, new Intent());
                        mBaseActivity.finish();
                    } else {
                        goToHome();
                    }
                } else {
                    final String body = response.body().string();
                    Log.d( TAG, body );
                }

                mBaseActivity.runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        mLoadingDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {
                Log.d(TAG, request.body().toString());
                mLoadingDialog.dismiss();
            }
        };
        mBaseActivity.getAPIClient().execPost("/login" + loginType, params, callback);
    }

    private void goToHome() {
        Intent intent = new Intent( mBaseActivity, TabBarAcitivity.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
