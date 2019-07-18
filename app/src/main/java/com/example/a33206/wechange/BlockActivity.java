package com.example.a33206.wechange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.a33206.wechange.Shop.GoodShowActivity;
import com.example.a33206.wechange.db.Goods;

public class BlockActivity extends AppCompatActivity {
    Goods goods;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goods = new Goods();
        goods = (Goods) getIntent().getSerializableExtra("good");
        Intent intent = new Intent(BlockActivity.this,GoodShowActivity.class);
        intent.putExtra("good",goods);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
