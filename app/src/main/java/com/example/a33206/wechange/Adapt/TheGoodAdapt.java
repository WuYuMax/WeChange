package com.example.a33206.wechange.Adapt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a33206.wechange.BlockActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.GoodShowActivity;
import com.example.a33206.wechange.db.Goods;

import java.util.List;

public class TheGoodAdapt extends RecyclerView.Adapter<TheGoodAdapt.ViewHolder> {
    private List<Goods> goodsList;
    private Context context;
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            goodknow=itemView.findViewById(R.id.the_good_know);
            goodname=itemView.findViewById(R.id.the_good_name);
            goodpic=itemView.findViewById(R.id.the_good_pic);
            goodprice=itemView.findViewById(R.id.the_good_price);
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

    @Override
    public void onBindViewHolder(@NonNull TheGoodAdapt.ViewHolder viewHolder, final int i) {
        final Goods goods = goodsList.get(i);
        viewHolder.goodprice.setText("￥ "+goods.getGood_price());
        viewHolder.goodpic.setImageResource(goods.getPictures().get(0));
        viewHolder.goodname.setText(goods.getGood_name());
        viewHolder.goodknow.setText(goods.getCommit());
        viewHolder.goodview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BlockActivity
                        .class);
                intent.putExtra("good",goods);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

}
