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
import com.example.a33206.wechange.Action.ActionShowActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyActionAdapt extends RecyclerView.Adapter<MyActionAdapt.ViewHold> {
    private List<Action> actionList;
    private Context context;
    private boolean isSet;
    private String concealaddres="http://140.143.224.210:8080/market/activity/finish?activityid=";
    public MyActionAdapt(List<Action> actionList, Context context,boolean isSet) {
        this.actionList = actionList;
        this.context = context;
        this.isSet=isSet;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(context).inflate(R.layout.theaction_item,viewGroup,false);
        ViewHold viewHold=new ViewHold(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold viewHold, final int i) {
        final Action action=actionList.get(i);

        viewHold.number.setText(action.getActivityJoinPeoleNumber()+"/"+action.getActivityNeedPeopleNumber());
        viewHold.address.setText(action.getActivityAddress());
        viewHold.name.setText(action.getActivityName());
        viewHold.data.setText(action.getEndTime());
        Glide.with(context).load(action.getActivityIcon()).into(viewHold.pic);

        viewHold.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,ActionShowActivity.class);
                intent.putExtra("activity",action);
                context.startActivity(intent);
            }

        });
        if (isSet){
            viewHold.conceal.setText("下架活动");
            viewHold.conceal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpClient client= new OkHttpClient();
                    Request request= new Request.Builder().url(concealaddres+action.getActivityId()).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) { e.printStackTrace(); }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String data =response.body().string();
                            try {
                                JSONObject jsonObject =new JSONObject(data);
                                String status=jsonObject.get("msg").toString();
                                Log.e("<>+<>", status );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            });
        }else {

        }
    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView address;
        private TextView number;
        private TextView data;
        private ImageView pic;
        private TextView choose;
        private TextView conceal;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.the_action_name);
            address=itemView.findViewById(R.id.the_action_address);
            number =itemView.findViewById(R.id.the_action_people);
            data = itemView.findViewById(R.id.the_action_enddate);
            pic =itemView.findViewById(R.id.the_user_pic);
            choose=itemView.findViewById(R.id.item_choose);
            conceal=itemView.findViewById(R.id.item_canceal);
        }
    }
}
