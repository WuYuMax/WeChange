package com.example.a33206.wechange.db;

import java.io.Serializable;
import java.net.URL;

public class User implements Serializable {
    private String userId;
    private String userName;
    private String useraccount;
    private String password;
    private String phone;
    private String useIconUrl;
    private int successAmount;
    private String QQ;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(String useraccount) {
        this.useraccount = useraccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUseIconUrl() {
        return useIconUrl;
    }

    public void setUseIconUrl(String useIconUrl) {
        this.useIconUrl = useIconUrl;
    }

    public int getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(int successAmount) {
        this.successAmount = successAmount;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }
}
