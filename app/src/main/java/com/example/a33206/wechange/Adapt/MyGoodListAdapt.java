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

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.My.MyMessageActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Goods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyGoodListAdapt extends RecyclerView.Adapter<MyGoodListAdapt.ViewHold> {
    private List<Goods> goodsList;
    private Context context;
    private List<String> namelist=new ArrayList<>();
    private boolean isBuy;
    private String address="http://140.143.224.210:8080/market/order/cancel?orderid=";
    private String addressconceal="http://140.143.224.210:8080/market/order/finnish?orderid=";
    public MyGoodListAdapt(List<Goods> goodsList, Context context,boolean isShop) {
        this.goodsList = goodsList;
        this.context = context;
        this.isBuy=isShop;
        namelist.add("交易中");
        namelist.add("交易完成");
        namelist.add("取消交易");
    }

    @NonNull
    @Override
    public MyGoodListAdapt.ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_goodlist_item,viewGroup,false);
        ViewHold viewHold =new ViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyGoodListAdapt.ViewHold viewHold, final int i) {
        final Goods goods=goodsList.get(i);
        viewHold.goodstatus.setText(namelist.get(goods.getStatus()));
        viewHold.goodStartTime.setText(goods.getCreatTime());
        viewHold.goodName.setText(goods.getGood_name());
        viewHold.Price.setText(goods.getGood_price());
        viewHold.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,MyMessageActivity.class);
                intent.putExtra("UserId",goods.getUser_Id());
                intent.putExtra("isme",false);
                context.startActivity(intent);
            }
        });
        if (!isBuy){
            viewHold.conceal.setText("完成订单");
            viewHold.conceal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        OkHttpClient client = new OkHttpClient();
                        Request request =new Request.Builder().url(addressconceal+goods.getGood_type()).build();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String data=response.body().string();
                                try {
                                    JSONObject jsonObject =new JSONObject(data);
                                    Log.e("<--->",jsonObject.get("msg").toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                });

        }else {
            viewHold.conceal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpClient client = new OkHttpClient();
                    Request request =new Request.Builder().url(address+goods.getGood_type()).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data=response.body().string();
                            try {
                                JSONObject jsonObject =new JSONObject(data);
                                Log.e("<--->",jsonObject.get("msg").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        private TextView goodName;
        private TextView goodStartTime;
        private TextView goodstatus;
        private TextView connect;
        private TextView Price;
        private TextView conceal;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            goodName=itemView.findViewById(R.id.my_goodlist_name);
            goodStartTime=itemView.findViewById(R.id.my_good_createTime);
            goodstatus = itemView.findViewById(R.id.my_good_status);
            conceal=itemView.findViewById(R.id.my_good_conceal);
            connect=itemView.findViewById(R.id.my_good_connect);
            Price=itemView.findViewById(R.id.my_good_price);
            conceal=itemView.findViewById(R.id.my_good_conceal);
        }
    }
}
