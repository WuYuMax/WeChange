package com.example.a33206.wechange.db;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {
    private String user_Id;
    private String Good_Id;
    private List<Integer> Pictures;
    private String Good_name;
    private String Good_price;
    private String commit;
    private List<byte[]> bytes;
    private int number;
    private String Good_type;
    private int likenumber;
    private String creatTime;
    private String updateTime;
    private  int Status;

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

    public List<byte[]> getBytes() {
        return bytes;
    }

    public void setBytes(List<byte[]> bytes) {
        this.bytes = bytes;
    }

    public String getGood_Id() {
        return Good_Id;
    }

    public void setGood_Id(String good_Id) {
        Good_Id = good_Id;
    }

    public List<Integer> getPictures() {
        return Pictures;
    }

    public void setPictures(List<Integer> pictures) {
        Pictures = pictures;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_ID(String user_name) {
        this.user_Id = user_name;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
