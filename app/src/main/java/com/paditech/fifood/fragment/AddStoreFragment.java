package com.paditech.fifood.fragment;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paditech.fifood.R;
import com.paditech.fifood.activity.BaseActivity;
import com.paditech.fifood.adapter.ImageAdapter;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by PaditechPC1 on 2/16/2016.
 */
public class AddStoreFragment extends TabBaseFragment implements View.OnClickListener {
    ImageView mChosseImage;
    ImageView mAvatarPost;
    TextView mChooseAvatar;
    GridViewWithHeaderAndFooter mDisplayImageStore;
    BaseActivity mBaseActivity;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageAdapter imageAdapter;
    private ArrayList<Bitmap> drawablesResource;
    private  Bitmap thumbnail;
    private  Bitmap bm;

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
        mDisplayImageStore = (GridViewWithHeaderAndFooter) view.findViewById(R.id.gv_display_image_store);
        LayoutInflater layoutInflater = LayoutInflater.from(mBaseActivity);
        View headerView = layoutInflater.inflate(R.layout.header_image_shop_grid_view, null, false);
        mDisplayImageStore.addHeaderView(headerView);
        mChosseImage = (ImageView)headerView.findViewById(R.id.iv_take_image);
        mChosseImage.setOnClickListener(this);
        mAvatarPost = (ImageView)headerView.findViewById(R.id.iv_choose_avatar_store);
        mChooseAvatar = (TextView)headerView.findViewById(R.id.tv_choose_avatar);

        drawablesResource = new ArrayList<Bitmap>();
        imageAdapter = new ImageAdapter(mBaseActivity,drawablesResource);
        mDisplayImageStore.setAdapter(imageAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_take_image:
                selectImage();
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
        mChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
