package com.example.a33206.wechange.Adapt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a33206.wechange.BlockActivity;
import com.example.a33206.wechange.LoginActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.Shop.GoodShowActivity;
import com.example.a33206.wechange.db.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GoodAdapt extends RecyclerView.Adapter<GoodAdapt.ViewHolder> {
    private Context context;
    private List<Goods> mgoodsList= new ArrayList<>();
    public GoodAdapt(Context context,List<Goods> list ){
        this.mgoodsList = list;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView good_pi;
        private TextView good_nam;
        private TextView good_pric;
        private TextView good_numbe;
        private View theview;
        public ViewHolder(View view) {
            super(view);
            good_pi =view.findViewById(R.id.good_pic);
            good_nam =view.findViewById(R.id.good_name);
            good_pric =view.findViewById(R.id.good_price);
            good_numbe =view.findViewById(R.id.good_number);
            theview = view;
        }
    }
    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

//        Log.e("-------------->", mgoodsList.get(i).getPictures().get(0)+"" );
        final Goods goods = mgoodsList.get(i);
        if (mgoodsList.get(i).getPictures().size()==0){
            viewHolder.good_pi.setImageResource(R.drawable.logo);
        }else {
            viewHolder.good_pi.setImageResource(goods.getPictures().get(0));
        }
        if (goods.getNumber()==0) {
            viewHolder.good_numbe.setText("售罄");
        }else {
            viewHolder.good_numbe.setText("库存："+goods.getNumber());
        }
        viewHolder.good_pric.setText("￥"+goods.getGood_price());
        viewHolder.good_nam.setText(goods.getGood_name());
        viewHolder.theview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,BlockActivity.class);
                intent.putExtra("good",goods);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mgoodsList.size();
    }



}
