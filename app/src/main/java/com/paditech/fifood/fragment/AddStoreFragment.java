package com.paditech.fifood.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.ImageAdapter;
import com.paditech.fifood.model.ListStores;
import com.paditech.fifood.utils.DialogUtil;
import com.paditech.fifood.utils.ErrorUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class AddStoreFragment extends TabBaseFragment implements View.OnClickListener {
    ImageView mChosseImage;
    ImageView mAvatarPost;
    TextView mChooseAvatar;
    EditText mNameStore;
    EditText mAddressStore;
    EditText mContentComment;
    RelativeLayout mGood;
    ImageView mGoodImage;
    RelativeLayout mBad;
    ImageView mBadImage;
    RelativeLayout mHygieneProblem;
    ImageView mHygieneProblemImage;

    GridViewWithHeaderAndFooter mDisplayImageStore;
    BaseActivity mBaseActivity;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageAdapter imageAdapter;
    private ArrayList<Bitmap> drawablesResource;
    private  Bitmap thumbnail;
    private  Bitmap bm;
    private  boolean isHygieneProblem = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int getLayout() {
        return R.layout.add_store_fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        View view = getView();
        mBaseActivity = (BaseActivity) getActivity();
        setHeaderTitle(getString(R.string.add_store));
        setHeaderButtonRight(View.VISIBLE);
        setHeaderButtonLeft(View.INVISIBLE);
        setCurrentMenu(0);
        setupUI(view.findViewById(R.id.layout_root_create_store));

        mDisplayImageStore = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_display_image_store);
        LayoutInflater layoutInflater = LayoutInflater.from(mBaseActivity);

        View headerView = layoutInflater.inflate(R.layout.header_image_shop_grid_view, null, false);
        mDisplayImageStore.addHeaderView(headerView);
        mChosseImage = (ImageView)headerView.findViewById(R.id.iv_take_image);
        mChosseImage.setOnClickListener(this);
        mAvatarPost = (ImageView)headerView.findViewById(R.id.iv_choose_avatar_store);
        mChooseAvatar = (TextView)headerView.findViewById(R.id.tv_choose_avatar);
        mNameStore = (EditText)headerView.findViewById(R.id.et_name_store);
        mAddressStore = (EditText)headerView.findViewById(R.id.et_store_address);
        mContentComment = (EditText)headerView.findViewById(R.id.et_content_add_store);
        mGood = (RelativeLayout)headerView.findViewById(R.id.rl_good);
        mGood.setOnClickListener(this);
        mGoodImage = (ImageView)headerView.findViewById(R.id.iv_good);
        mBad = (RelativeLayout)headerView.findViewById(R.id.rl_bad);
        mBad.setOnClickListener(this);
        mBadImage = (ImageView)headerView.findViewById(R.id.iv_bad);
        mHygieneProblem = (RelativeLayout)headerView.findViewById(R.id.rl_hygiene_problem);
        mHygieneProblem.setOnClickListener(this);
        mHygieneProblemImage = (ImageView)headerView.findViewById(R.id.iv_hygiene_problem);

        drawablesResource = new ArrayList<Bitmap>();
        imageAdapter = new ImageAdapter(mBaseActivity,drawablesResource);
        mDisplayImageStore.setAdapter(imageAdapter);


    }
    @Override
    protected void onHeaderRightButtonClick() {
        Log.d(TAG, "1223");
        getPostCreateStore();
    }


    private void getPostCreateStore() {
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
        params.put("name", mNameStore.getText().toString());
        params.put("lat", "16.144326");
        params.put("longth", "105.386158");
        params.put("address", mAddressStore.getText().toString());
        params.put("content", mContentComment.getText().toString());
        params.put("lang", "vi");
        params.put("files", "1");
        params.put("is_like", "1");
        params.put("is_main", "1");
        params.put("is_report", "1");
        params.put("main_file_id", "1");
        getAPIClient().execPostWithUrlParameters("/shop_create", params, params, callback);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_take_image:
                selectImage();
                break;
            case R.id.rl_good:
                mGoodImage.setImageResource(R.drawable.ic_good_ac);
                mBadImage.setImageResource(R.drawable.ic_bad);
                break;
            case R.id.rl_bad:
                mBadImage.setImageResource(R.drawable.ic_bad_ac);
                mGoodImage.setImageResource(R.drawable.ic_good);
                break;
            case R.id.rl_hygiene_problem:

                if (!isHygieneProblem){
                    mHygieneProblemImage.setImageResource(R.drawable.checkbox);
                } else {
                    mHygieneProblemImage.setImageResource(R.drawable.checkbox_bg);
                }
                isHygieneProblem = !isHygieneProblem;
                break;
            default:break;
        }
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

        imageAdapter = new ImageAdapter(mBaseActivity, drawablesResource);
        imageAdapter.getCheckedItems();
        mDisplayImageStore.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
        mAvatarPost.setImageBitmap(drawablesResource.get(0));
        mChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float alpha = 0.5f;
                mDisplayImageStore.setAlpha(alpha);
                mDisplayImageStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mAvatarPost.setImageBitmap(drawablesResource.get(position));
                    }
                });

            }
        });

    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
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

        drawablesResource.add(thumbnail);


    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
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
        drawablesResource.add(bm);
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

}
