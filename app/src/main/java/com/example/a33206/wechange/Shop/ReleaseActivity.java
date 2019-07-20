package com.example.a33206.wechange.Shop;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a33206.wechange.Adapt.ReleaseGoodAdapt;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReleaseActivity extends AppCompatActivity {
    private Button deletebutton;
    private EditText good_name;
    private EditText good_know;
    private EditText good_price;
    private EditText good_kind;
    private TextView pre_lookbutton;
    private RecyclerView recyclerView;
    private ReleaseGoodAdapt releaseGoodAdapt;
    private List<Uri> uriList = new ArrayList<>();
    private Button addpicButton;
    private Uri imageuri;
    private List<Bitmap> bitmaps = new ArrayList<>();
    final int TAKE_PHOTO = 1;
    final int CHOOSE_PHOTO = 2;
    private int PhotoCurrent;
    private List<String> namelist =new ArrayList<>();
    private Button phonebutton;
    private Button bookbutton;
    private Button gamebutton;
    private Button clothbutton;
    private Button computerbutton;
    private Button peijianbutton;
    private Button jiajvbutton;
    private Button otherbutton;
    private TextView typename;
    private String goodtype;
    private List<byte[]> bytes =new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        deletebutton = findViewById(R.id.release_delete);
        good_name = findViewById(R.id.release_goodname);
        good_know = findViewById(R.id.release_know);
        good_price = findViewById(R.id.release_goodprice);
        good_kind = findViewById(R.id.release_kind);
        pre_lookbutton = findViewById(R.id.release_prelook);
        recyclerView = findViewById(R.id.release_imageRecycly);
        addpicButton = findViewById(R.id.release_addpic_button);
        phonebutton=findViewById(R.id.release_phone_button);
        bookbutton=findViewById(R.id.release_book_button);
        gamebutton=findViewById(R.id.release_game_button);
        clothbutton=findViewById(R.id.release_cloth_button);
        computerbutton=findViewById(R.id.release_computer_button);
        peijianbutton=findViewById(R.id.release_peijian_button);
        jiajvbutton=findViewById(R.id.release_jiajv_button);
        otherbutton=findViewById(R.id.release_other_button);
        typename=findViewById(R.id.release_type_text);
        initNameList();

        getname(phonebutton,0);
        getname(bookbutton,1);
        getname(gamebutton,2);
        getname(clothbutton,3);
        getname(computerbutton,4);
        getname(peijianbutton,5);
        getname(jiajvbutton,6);
        getname(otherbutton,7);

        addpicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setURiFormALbum();
                releaseGoodAdapt = new ReleaseGoodAdapt(ReleaseActivity.this, uriList, bitmaps, PhotoCurrent);
                LinearLayoutManager layoutManager = new LinearLayoutManager(ReleaseActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setAdapter(releaseGoodAdapt);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
        pre_lookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods goods = new Goods();
                goods = initGood();
                Intent intent = new Intent(ReleaseActivity.this, GoodShowActivity.class);
                intent.putExtra("good", goods);
                intent.putExtra("test", false);
                startActivity(intent);
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getname(Button phonebutton, final int i) {
        phonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(typename.getText().toString().equals(namelist.get(i))){
                    typename.setText("货物种类");
                    goodtype="null";
                }else {
                    typename.setText(namelist.get(i));
                    goodtype = namelist.get(i);
                }
            }
        });
    }

    private void initNameList() {
        namelist.add("二手手机");
        namelist.add("书籍");
        namelist.add("游戏");
        namelist.add("服饰");
        namelist.add("二手电脑");
        namelist.add("电子配件");
        namelist.add("家具");
        namelist.add("其他");
        namelist.add("null");
    }
    private void setURiFormALbum() {
        if (ContextCompat.checkSelfPermission(ReleaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ReleaseActivity.this, new
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
                    Toast.makeText(ReleaseActivity.this, "you denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                        bitmaps.add(bitmap);
                        PhotoCurrent = 2;//文件读取 Bitmap 模式
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
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
        if (DocumentsContract.isDocumentUri(ReleaseActivity.this, uri)) {
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
            bytes.add(Bitmap2Bytes(bitmap));
            PhotoCurrent = 2;
        }else {
            Toast.makeText(ReleaseActivity.this,"大哥不会把，连图库也没有",Toast.LENGTH_SHORT);
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void SetUriFromPhoto() {
        File outputImage = new File(getExternalCacheDir(), "outputImage_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageuri = FileProvider.getUriForFile(ReleaseActivity.this,
                    "com.example.cameraalbumtest.fileprovider", outputImage);
        } else {
            imageuri = Uri.fromFile(outputImage);
            PhotoCurrent = 1;//文件读取URi模式
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent
                , PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfos.size() != 0) {
            startActivityForResult(intent, TAKE_PHOTO);
            uriList.add(imageuri);
        } else {
            Toast.makeText(ReleaseActivity.this, "你都没有相机的吗", Toast.LENGTH_SHORT).show();
        }
        return;
    }

    private Goods initGood() {
        Goods goods = new Goods();
        List<Integer> list = new ArrayList<>();
        List<Uri> uriL=uriList;
        goods.setGood_name(good_name.getText().toString());
        goods.setGood_price(good_price.getText().toString());
        goods.setCommit(good_know.getText().toString());
        goods.setPictures(list);
        goods.setBytes(bytes);
        goods.setGood_type(goodtype);
        return goods;
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
             bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
             return baos.toByteArray();
          }
}