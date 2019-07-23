package com.example.a33206.wechange.Action;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.ReleaseActivity;
import com.example.a33206.wechange.db.Action;
import com.example.a33206.wechange.db.User;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ActionReleaseActivity extends AppCompatActivity {
    private Button backbutton;
    private EditText activity_name;
    private EditText startdata;
    private EditText enddate;
    private EditText neednumber;
    private EditText address;
    private EditText detail;
    private RecyclerView recyclerView;
    private TextView pre_look;
    private Button release;
    private ImageView actionpic;
    private Action  action=new Action();
    private User user;
    private byte[] bytes;
    final int CHOOSE_PHOTO = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_release);
        backbutton=findViewById(R.id.activity_release_backbutton);
        activity_name=findViewById(R.id.activity_release_name);
        startdata=findViewById(R.id.activity_release_start);
        enddate = findViewById(R.id.activity_release_enddata);
        neednumber = findViewById(R.id.activity_release_neednumber);
        address = findViewById(R.id.activity_release_address);
        detail = findViewById(R.id.activity_release_detail);
        pre_look = findViewById(R.id.activity_release_prelook);
        release = findViewById(R.id.activity_release_button);
        actionpic=findViewById(R.id.activity_release_pic);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actionpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setURiFormALbum();

//                action.setTextPic(bytes);
            }
        });
        pre_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initaction();
                Intent intent = new Intent(ActionReleaseActivity.this,ActionShowActivity.class);
                intent.putExtra("activity",action);
                intent.putExtra("user",user);
                intent.putExtra("test",true);
                startActivity(intent);

            }
        });
    }

    private void initaction() {
        action.setActivityName(activity_name.getText().toString());
        action.setEndTime(enddate.getText().toString());
        action.setStartTime(startdata.getText().toString());
        action.setActivityNeedPeopleNumber(neednumber.getText().toString());
        action.setActivityDetail(detail.getText().toString());
        action.setActivityJoinPeoleNumber("0");
        action.setActivityAddress(address.getText().toString());
        user = new User();
        user.setUserName("学生会");
        user.setQQ("332062922");

    }
    private void setURiFormALbum() {
        if (ContextCompat.checkSelfPermission(ActionReleaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActionReleaseActivity.this, new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(ActionReleaseActivity.this, "you denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(ActionReleaseActivity.this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android,providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equals(uri.getAuthority())) {
                imagePath = getImagePath(uri, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }
    }

    private void displayImage(String imagePath) {
        if (imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            bytes=Bitmap2Bytes(bitmap);
            action.setTextPic(bytes);
            Glide.with(ActionReleaseActivity.this).load(bytes).into(actionpic);
        }else {
            Toast.makeText(ActionReleaseActivity.this,"大哥不会把，连图库也没有",Toast.LENGTH_SHORT);
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri contentUri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(contentUri,null,selection,null,null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        return baos.toByteArray();
    }
}
