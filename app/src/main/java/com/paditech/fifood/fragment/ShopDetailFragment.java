package com.paditech.fifood.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.activity.ListImageShopActivity;
import com.paditech.fifood.adapter.ShopDetailAdapter;
import com.paditech.fifood.model.ListStores;
import com.paditech.fifood.model.ShopsDetail;
import com.paditech.fifood.utils.DialogUtil;
import com.paditech.fifood.utils.ErrorUtil;
import com.paditech.fifood.utils.StringUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import android.os.Handler;

import java.util.logging.LogRecord;

/**
 * Created by DucAnh on 2/17/2016.
 */
public class ShopDetailFragment extends TabBaseFragment implements View.OnClickListener, AbsListView.OnScrollListener {
    private static final String TAG = ShopDetailFragment.class.getSimpleName();
    BaseActivity mBaseActivity;
    ListView mDetailStore;
    ShopDetailAdapter mShopDetailAdapter;
    ImageView mImageStore;
    TextView mNameStore;
    TextView mAddress;
    RatingBar mRating;
    TextView mNumberLike;
    TextView mNumberDislike;
    TextView mStatusChecked;
    EditText mComment;
    Button mPostComment;
    TextView mGood;
    TextView mBad;
    TextView mChooseImage;
    TextView mHygieneProblem;
    private boolean isHygieneProblem = false;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap thumbnail;
    private Bitmap bm;
    int pageCount = 1;

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        setHeaderTitle("Name");
        setHeaderButtonLeft(View.VISIBLE);
        final View view = getView();
        mBaseActivity = (BaseActivity) getActivity();
        setupUI(view.findViewById(R.id.layout_root_post_detail));

        mDetailStore = (ListView) view.findViewById(R.id.lv_detail_store);
        mComment = (EditText) view.findViewById(R.id.et_input_comment);
        mPostComment = (Button) view.findViewById(R.id.bt_post_comment);
        mPostComment.setOnClickListener(this);
        mGood = (TextView) view.findViewById(R.id.tv_status_good_shop_detail);
        mGood.setOnClickListener(this);
        mBad = (TextView) view.findViewById(R.id.tv_status_bad_shop_detail);
        mBad.setOnClickListener(this);
        mHygieneProblem = (TextView) view.findViewById(R.id.tv_hygiene_problem_shop_detail);
        mHygieneProblem.setOnClickListener(this);
        mChooseImage = (TextView) view.findViewById(R.id.tv_take_image_shop_detail);
        mChooseImage.setOnClickListener(this);


        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_shop_detail_list_view, null, false);
        mImageStore = (ImageView) headerView.findViewById(R.id.iv_image_store);
        mImageStore.setOnClickListener(this);
        mNameStore = (TextView) headerView.findViewById(R.id.name_store);
        mAddress = (TextView) headerView.findViewById(R.id.tv_address_store_detail);
        mRating = (RatingBar) headerView.findViewById(R.id.rb_number_star_store_detail);
        mNumberLike = (TextView) headerView.findViewById(R.id.number_comment_good);
        mNumberDislike = (TextView) headerView.findViewById(R.id.number_comment_bad);
        mStatusChecked = (TextView) headerView.findViewById(R.id.tv_status_checked);
        mDetailStore.addHeaderView(headerView);

        mShopDetailAdapter = new ShopDetailAdapter(mBaseActivity);
        mDetailStore.setAdapter(mShopDetailAdapter);
        mDetailStore.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, final int totalItemCount) {
                int total = firstVisibleItem + visibleItemCount;
                // Total array list i have so it
                if (pageCount < 2) {

                    if (total == totalItemCount) {
                        final Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            public void run() {

                                mShopDetailAdapter.notifyDataSetChanged();
                                mDetailStore.setSelection(totalItemCount);
                                pageCount++;
                                mDetailStore.setAdapter(mShopDetailAdapter);
                                getPostComment();
                            }
                        }, 1000);
                    }
                } else {
                    Log.e("hide footer", "footer hide");
                }
            }
        });

        getPostComment();
    }

    private void getPostComment() {

        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response mResponse) throws IOException {
                String body = mResponse.body().string();
                if (mResponse.isSuccessful()) {
                    Log.d(TAG, body);
                    final ShopsDetail data = new Gson().fromJson(body, ShopsDetail.class);

                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mShopDetailAdapter.setPostsComment(data.response.comments);
                            mNameStore.setText(data.response.name);
                            mAddress.setText(data.response.address);
                            mRating.setRating(data.response.rating);
                            mNumberLike.setText(String.valueOf(data.response.like_num));
                            mNumberDislike.setText(String.valueOf(data.response.dislike_num));
                            mStatusChecked.setText(data.response.evaluation);
                            if (!StringUtil.isEmpty(data.response.file.url)) {
                                Picasso.with(mBaseActivity).load(data.response.file.url).placeholder(R.drawable.profile_placeholder).into(mImageStore);
                            }

                        }
                    });
                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                } else {
                    body = mResponse.body().string();
                    Log.d(TAG, body);
                }
            }
        };
        SortedMap<String, String> params = new TreeMap<>();
        params.put("shop_id", "1");
        params.put("lat", "20.996309");
        params.put("longth", "105.827309");
        params.put("lang", "vi");
        getAPIClient().execPostWithUrlParameters("/shop_detail", params, params, callback);
    }

    private void postComment() {
        final Dialog dialog = DialogUtil.makeLoadingDialog(mBaseActivity);
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response mResponse) throws IOException {
                final String body = mResponse.body().string();
                final boolean success = mResponse.isSuccessful();

                mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        if (success) {
                            Toast.makeText(getActivity(), R.string.account_create_success, Toast.LENGTH_SHORT).show();
                        }
                        ErrorUtil.processError(body, new ErrorUtil.ErrorListenner() {
                            @Override
                            public boolean onError(JSONObject error) {
                                if (error.has("mailAddressAlreadyExists")) {
                                    Toast.makeText(getActivity(), R.string.error_mail_account_exists, Toast.LENGTH_SHORT).show();
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                });
            }
        };
        SortedMap<String, String> params = new TreeMap<>();
        params.put("user_id", "4");
        params.put("token", "8K2MY6IVCCOZ");
        params.put("shop_id", "1");
        params.put("content", mComment.getText().toString());
        params.put("lang", "vi");
        params.put("files", "1");
        params.put("is_like", "1");
        params.put("is_main", "1");
        params.put("is_report", "1");
        getAPIClient().execPostWithUrlParameters("/comment", params, params, callback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image_store:
                Intent intent = new Intent(mBaseActivity, ListImageShopActivity.class);
                mBaseActivity.startActivity(intent);
                break;
            case R.id.tv_take_image_shop_detail:
                selectImage();
                break;
            case R.id.tv_status_good_shop_detail:
                mGood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_good_ac, 0, 0, 0);
                mBad.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bad, 0, 0, 0);
                break;
            case R.id.tv_status_bad_shop_detail:
                mBad.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bad_ac, 0, 0, 0);
                mGood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_good, 0, 0, 0);
                break;
            case R.id.tv_hygiene_problem_shop_detail:
                if (!isHygieneProblem) {
                    mHygieneProblem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox, 0);
                } else {
                    mHygieneProblem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_bg, 0);
                }
                isHygieneProblem = !isHygieneProblem;
                break;
            case R.id.bt_post_comment:
                if (mComment.getText().toString().isEmpty()) {
                    return;
                }
                postComment();
                getPostComment();
                mComment.setText("");
                break;
            default:
                break;

        }


    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = mBaseActivity.getContentResolver().query(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String selectedImagePath = cursor.getString(column_index);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
