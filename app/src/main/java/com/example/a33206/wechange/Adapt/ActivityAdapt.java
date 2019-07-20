package com.example.a33206.wechange.Adapt;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.Action.ActionShowActivity;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;
import com.example.a33206.wechange.db.User;


import java.util.List;

public class ActivityAdapt extends RecyclerView.Adapter<ActivityAdapt.ViewHold> {
    private List<User> userList;
    private List<Action> actionList;
    private Context context;
    private int picaddress;

    public ActivityAdapt(List<User> userList, List<Action> actionList, Context context, int picaddress) {
        this.userList = userList;
        this.actionList = actionList;
        this.context = context;
        this.picaddress = picaddress;
    }


    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activityitem,viewGroup,false);
        ViewHold holdView = new ViewHold(view);
        return holdView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holdView, final int i) {
        final User user = userList.get(i);
        final Action action = actionList.get(i);
        Glide.with(context).load(R.drawable.logo).into(holdView.user_pic);
//        Glide.with(context).load(user.getUseIconUrl()).into(holdView.user_pic);
        holdView.user_pic.setImageResource(picaddress);
        holdView.user_name.setText(user.getUserName());
        holdView.action_data.setText("报名截止日期："+action.getEndTime());
        holdView.action_detail.setText(action.getActivityDetail());
        holdView.action_number.setText("人数："+action.getActivityJoinPeoleNumber()+"/"
                +action.getActivityNeedPeopleNumber());
        holdView.action_name.setText(action.getActivityName());
        holdView.action_push_data.setText(action.getStartTime());
        holdView.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ActionShowActivity.class);
                intent.putExtra("ActionId",action.getActivityId());
                intent.putExtra("activity",action);
                intent.putExtra("user",user);
                intent.putExtra("pic",R.drawable.logo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        private ImageView user_pic;
        private TextView user_name;
        private TextView action_push_data;
        private TextView action_detail;
        private TextView action_number;
        private TextView action_data;
        private Button collect;
        private TextView action_name;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            user_pic=itemView.findViewById(R.id.activity_user_pic);
            user_name=itemView.findViewById(R.id.activity_user_name);
            action_push_data=itemView.findViewById(R.id.activity_push_data);
            action_number=itemView.findViewById(R.id.activity_people);
            action_detail=itemView.findViewById(R.id.activity_detail);
            action_data=itemView.findViewById(R.id.activity_data);
            collect=itemView.findViewById(R.id.jion);
            action_name=itemView.findViewById(R.id.action_name);

        }
    }
}
