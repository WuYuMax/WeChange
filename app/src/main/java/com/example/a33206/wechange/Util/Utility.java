package com.example.a33206.wechange.Util;

import android.text.TextUtils;
import android.util.Log;

import com.example.a33206.wechange.db.Goods;
import com.example.a33206.wechange.db.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Utility {
    public static boolean handleGoodResponse(String response, List<Goods> goodsList)
    {
        if (!TextUtils.isEmpty(response))
        {
            try {
                JSONArray jsonArray= new JSONArray(response);
                for(int i=0;i<jsonArray.length();i++)
                {
                    for (int j=0;j<jsonArray.length();j++){

                        Goods goods =new Goods();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject= (JSONObject) jsonArray.get(j);
                        Log.e("------->",  ""+jsonObject.getInt("code"));
                        goods.setStatus(jsonObject.getInt("productType"));
                        goods.setUser_Id(jsonObject.get("userId").toString());
                        goods.setGood_Id(jsonObject.getString("productId"));
                        goods.setPictures(jsonObject.getString("productIcon") .toString());
                        goods.setGood_name(jsonObject.getString("productName"));
                        goods.setNumber(jsonObject.getInt("productStock"));
                        goods.setCommit(jsonObject.getString("productDetail"));
                        goods.setGood_price(jsonObject.getString("productPrice"));
                        goods.setLikenumber(jsonObject.getInt("productWant"));
                        goodsList.add(goods);
                    }
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static User handleUserReponse(String response,String tip) throws JSONException {
        User user = new User();
        JSONObject jsonObject = new JSONObject(response);
        JSONObject jsonObject1= (JSONObject) jsonObject.get(tip);

        user.setUserName(jsonObject1.get("userName").toString());
        user.setUseIconUrl(jsonObject1.get("userIcon").toString());
        user.setUserId(jsonObject1.get("userId").toString());
        user.setPassword(jsonObject1.get("password").toString());
        user.setQQ(jsonObject1.get("useqq").toString());
        user.setPhone(jsonObject1.get("userPhone").toString());
        user.setUseraccount(jsonObject1.get("useraccount").toString());

        return user;
    }
}
