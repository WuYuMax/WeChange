package com.example.a33206.wechange.Adapt;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.a33206.wechange.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
// type种类 ：1 Uri 加载
//          ：2 Bitmap 加载
public class ReleaseGoodAdapt extends RecyclerView.Adapter<ReleaseGoodAdapt.HoldView> {
    private  Context context;
    private  List<Uri>  urls=new ArrayList<>();
    private List<Bitmap> bitmapList = new ArrayList<>();
    private  int type;
    public ReleaseGoodAdapt(Context context , List<Uri> urlList,List<Bitmap> bitmapList,int type){
        this.context=context;
        this.urls=urlList;
        this.type = type;
        this.bitmapList=bitmapList;
    }
    @NonNull
    @Override
    public ReleaseGoodAdapt.HoldView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.good_image_list,viewGroup,false);
        HoldView holdView = new HoldView(view);
        return holdView;
    }

    @Override
    public void onBindViewHolder(@NonNull ReleaseGoodAdapt.HoldView holdView, int i) {
        if (bitmapList.size()!=0)
        Glide.with(context).load(bitmapList.get(i)).into(holdView.pic);
        else if (urls.size()!=0){
            Glide.with(context).load(urls.get(i)).into(holdView.pic);
        }
    }

    @Override
    public int getItemCount() {
        if (type==1){
            return urls.size();
        }else if (type==2){
            return bitmapList.size();
        }
        return 0;
    }

    public class HoldView extends RecyclerView.ViewHolder {
        private ImageView pic;
        public HoldView(@NonNull View itemView) {
            super(itemView);
            pic=itemView.findViewById(R.id.release_image);
        }
    }
}
