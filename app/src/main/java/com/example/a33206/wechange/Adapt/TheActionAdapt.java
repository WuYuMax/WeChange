package com.example.a33206.wechange.Adapt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.R;
import com.example.a33206.wechange.db.Action;

import java.util.ArrayList;
import java.util.List;

public class TheActionAdapt extends RecyclerView.Adapter<TheActionAdapt.ViewHold> {

    private List<Action> actions;
    private Context context;

    public TheActionAdapt(Context context,List<Action> actions) {
        this.context = context;
        this.actions=actions;
    }

    @NonNull
    @Override
    public TheActionAdapt.ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activityitem,viewGroup,false);
        ViewHold hold =new ViewHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull TheActionAdapt.ViewHold viewHold, int i) {
        Action action = actions.get(i);
        viewHold.action_name.setText(action.getActivityName());
        viewHold.address.setText(action.getActivityAddress());
        viewHold.date.setText(action.getEndTime());
        viewHold.number.setText("sdfsdlf");
        Glide.with(context).load(action.getActivityIcon()).into(viewHold.action_pic);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        private TextView action_name;
        private TextView address;
        private TextView number;
        private TextView date;
        private ImageView action_pic;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            action_name=itemView.findViewById(R.id.the_action_name);
            address = itemView.findViewById(R.id.the_action_address);
            number =itemView.findViewById(R.id.the_action_people);
            date = itemView.findViewById(R.id.the_action_enddate);
            action_pic=itemView.findViewById(R.id.user_pic);
        }
    }
}
