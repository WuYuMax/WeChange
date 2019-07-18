package com.example.a33206.wechange.db;

import java.io.Serializable;
import java.util.List;

public class Goods implements Serializable {
    private String user_name;
    private String Good_Id;
    private List<Integer> Pictures;
    private String Good_name;
    private String Good_price;
    private String commit;
    private int number;


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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
