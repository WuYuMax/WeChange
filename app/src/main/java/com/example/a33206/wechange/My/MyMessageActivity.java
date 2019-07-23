package com.example.a33206.wechange.My;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.ReleaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyMessageActivity extends AppCompatActivity {
    private EditText user_name;
    private EditText user_account;
    private EditText user_password;
    private EditText user_tel;
    private EditText user_sussecc;
    private EditText user_qq;
    private CheckBox changemessage_check;
    private LinearLayout hideLayout;
    private CircleImageView user_pic;
    private Button  button;
    private String useId;
    private Uri uri;
    private byte[] bytes;
    private String address="http://www.codeskystar.cn:8080/market/user/info?userid=";
    private String addresschange="http://www.codeskystar.cn:8080/market/user/change";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message);
        useId = getIntent().getStringExtra("UserId");
        user_name=findViewById(R.id.my_message_user_name);
        user_account=findViewById(R.id.my_message_user_account);
        user_password=findViewById(R.id.my_message_password);
        user_tel=findViewById(R.id.my_message_user_tel);
        user_qq=findViewById(R.id.my_message_user_qq);
        user_pic=findViewById(R.id.my_message_image);
        user_sussecc=findViewById(R.id.my_message_user_sussgood);
        hideLayout=findViewById(R.id.my_message_hidelayout);
        changemessage_check=findViewById(R.id.my_message_changecheck);
        button=findViewById(R.id.my_message_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
        user_name.setText("sdfjsldk");
        focusLayout(false);
        hideLayout.setVisibility(View.GONE);
        changemessage_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    focusLayout(true);
                    hideLayout.setVisibility(View.VISIBLE);
                }else {
                    focusLayout(false);
                    hideLayout.setVisibility(View.GONE);
                }
            }
        });
        initLayout();
        user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setURiFormALbum();
                Glide.with(MyMessageActivity.this).load(bytes).into(user_pic);
            }
        });
    }

    private void change() {
        OkHttpClient client =new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("userAccount",user_account.getText().toString())
                .add("userName",user_name.getText().toString())
                .add("userPassword",user_password.getText().toString())
                .add("userPhone",user_tel.getText().toString())
                .add("userQq",user_qq.getText().toString())
                .add("userId",useId)
                .add("userIcon","https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1519390769,3263130287&fm=27&gp=0.jpg")
                .build();
        Request request  = new Request.Builder().url(addresschange).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    final JSONObject json = new JSONObject(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.e("-->",json.get("msg").toString());
                                Toast.makeText(MyMessageActivity.this,json.get("msg").toString(),Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initLayout() {
        OkHttpClient client = new OkHttpClient();
        final Request request =new Request.Builder().url(address+useId).build();
         client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    final JSONObject object = (JSONObject) jsonObject.get("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.e("---------------->", object.get("userIcon").toString());

                                user_account.setText(object.get("userAccount").toString());
                                user_qq.setText(object.get("userQq").toString());
                                user_name.setText(object.get("userName").toString());
                                user_tel.setText(object.get("userPhone").toString());
                                user_sussecc.setText(object.get("userSuccessAmount").toString());
                                object.get("userPassword").toString();
                                Glide.with(MyMessageActivity.this).load(object.get("userIcon")).into(user_pic);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void focusLayout(boolean b) {
        user_name.setEnabled(b);
        user_account.setEnabled(b);
        user_tel.setEnabled(b);
//        user_tel.setFreezesText(!b);
        user_qq.setEnabled(b);
        user_name.setEnabled(b);
        user_sussecc.setEnabled(false);

    }

    private void setURiFormALbum() {
        if (ContextCompat.checkSelfPermission(MyMessageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MyMessageActivity.this, new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(MyMessageActivity.this, "you denied the permission", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
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
        if (DocumentsContract.isDocumentUri(MyMessageActivity.this, uri)) {
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
            Glide.with(MyMessageActivity.this).load(Bitmap2Bytes(bitmap)).into(user_pic);
            bytes=Bitmap2Bytes(bitmap);

            uri = Uri.parse(bitmap.toString());
            Log.e("------>",uri.toString() );

            Toast.makeText(MyMessageActivity.this,uri.toString(),Toast.LENGTH_LONG).show();
         //   Toast.makeText(MyMessageActivity.this,"注意：由于文件大小问题，5Mb以上的照片不提供预览",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MyMessageActivity.this,"大哥不会把，连图库也没有",Toast.LENGTH_SHORT);
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
