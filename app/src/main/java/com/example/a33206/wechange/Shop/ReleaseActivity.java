package com.example.a33206.wechange.Shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

import java.util.ArrayList;
import java.util.List;

public class ReleaseActivity extends AppCompatActivity {
    private Button deletebutton;
    private EditText good_name;
    private EditText good_know;
    private EditText good_price;
    private EditText good_kind;
    private TextView pre_lookbutton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
        deletebutton=findViewById(R.id.release_delete);
        good_name=findViewById(R.id.release_goodname);
        good_know =findViewById(R.id.release_know);
        good_price=findViewById(R.id.release_goodprice);
        good_kind=findViewById(R.id.release_kind);
        pre_lookbutton = findViewById(R.id.release_prelook);

        pre_lookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods goods =new Goods();
                goods=initGood();
                Intent intent = new Intent(ReleaseActivity.this,GoodShowActivity.class);
                intent.putExtra("good",goods);
                intent.putExtra("test",false);
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

    private Goods initGood() {
        Goods goods = new Goods();
        List<Integer> list=new ArrayList<>();
        goods.setGood_name(good_name.getText().toString());
        goods.setGood_price(good_price.getText().toString());
        goods.setCommit(good_know.getText().toString());
        goods.setPictures(list);

        return goods;
    }

}
