package com.example.a33206.wechange.Adapt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.BlockActivity;
import com.example.a33206.wechange.LoginActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.GoodShowActivity;
import com.example.a33206.wechange.db.Goods;

import java.util.List;

public class TheGoodAdapt extends RecyclerView.Adapter<TheGoodAdapt.ViewHolder> {
    private List<Goods> goodsList;
    private Context context;
    private SharedPreferences prefs;
    public TheGoodAdapt(Context context, List<Goods> list){
        this.goodsList =list;
        this.context =context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView goodname;
        private TextView goodknow;
        private ImageView goodpic;
        private TextView goodprice;
        private  View goodview;
        private TextView goodlike;
        private SharedPreferences preferences;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodknow=itemView.findViewById(R.id.the_good_know);
            goodname=itemView.findViewById(R.id.the_good_name);
            goodpic=itemView.findViewById(R.id.the_good_pic);
            goodprice=itemView.findViewById(R.id.the_good_price);
            goodlike=itemView.findViewById(R.id.good_likenumber);
            goodview=itemView;
        }
    }
    @NonNull
    @Override
    public TheGoodAdapt.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.thegood_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void FreshHeaderItem(List<Goods> item){
        goodsList.clear();
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull TheGoodAdapt.ViewHolder viewHolder, final int i) {
        final Goods goods = goodsList.get(i);
        viewHolder.goodprice.setText("￥ "+goods.getGood_price());
        Glide.with(context).load(goods.getPictures()).into(viewHolder.goodpic);
        viewHolder.goodname.setText(goods.getGood_name());
        viewHolder.goodknow.setText(goods.getCommit());
       // viewHolder.goodlike.setText(goods.getLikenumber()+"人想要");
        viewHolder.goodview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
                if (!prefs.getBoolean("status",false)){
                    Intent intent = new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context,BlockActivity
                            .class);
                    intent.putExtra("good",goods);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

}
