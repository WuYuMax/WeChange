package com.example.a33206.wechange.db;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class Goods implements Serializable {
    private String user_Id; //x
    private String Good_Id; //x
    private String Pictures;   //x
    private byte[] textPic;    //x
    private String Good_name;//x
    private String Good_price;//x
    private String commit;//x
    private int number;  //x
    private String Good_type;//x
    private int likenumber;//x
    private String creatTime;//x
    private String updateTime;//x
    private  int Status;   //x

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getGood_Id() {
        return Good_Id;
    }

    public void setGood_Id(String good_Id) {
        Good_Id = good_Id;
    }

    public String getPictures() {
        return Pictures;
    }

    public void setPictures(String pictures) {
        Pictures = pictures;
    }

    public byte[] getTextPic() {
        return textPic;
    }

    public void setTextPic(byte[] textPic) {
        this.textPic = textPic;
    }

    public String getGood_name() {
        return Good_name;
    }

    public void setGood_name(String good_name) {
        Good_name = good_name;
    }

    public String getGood_price() {
        return Good_price;
    }

    public void setGood_price(String good_price) {
        Good_price = good_price;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGood_type() {
        return Good_type;
    }

    public void setGood_type(String good_type) {
        Good_type = good_type;
    }

    public int getLikenumber() {
        return likenumber;
    }

    public void setLikenumber(int likenumber) {
        this.likenumber = likenumber;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
