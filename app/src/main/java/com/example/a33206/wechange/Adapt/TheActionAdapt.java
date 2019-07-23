package com.example.a33206.wechange.Adapt;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TheActionAdapt extends RecyclerView.Adapter<TheActionAdapt.ViewHold> {

    @NonNull
    @Override
    public TheActionAdapt.ViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TheActionAdapt.ViewHold viewHold, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        public ViewHold(@NonNull View itemView) {
            super(itemView);
        }
    }
}
