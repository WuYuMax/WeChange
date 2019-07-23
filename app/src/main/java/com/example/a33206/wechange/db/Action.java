package com.example.a33206.wechange.db;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

public class Action implements Serializable {
    private String activityId;
    private String activityName;
    private List<URL> activityIcon;
    private	String activityAddress;
    private String userId;
    private String activityQQ;
    private String startTime;
    private String endTime;
    private String activityNeedPeopleNumber;
    private String 	activityJoinPeoleNumber;
    private String activityDetail;
    private String status;
    private byte[] TextPic;

    public byte[] getTextPic() {
        return TextPic;
    }

    public void setTextPic(byte[] textPic) {
        TextPic = textPic;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public List<URL> getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(List<URL> activityIcon) {
        this.activityIcon = activityIcon;
    }

    public String getActivityAddress() {
        return activityAddress;
    }

    public void setActivityAddress(String activityAddress) {
        this.activityAddress = activityAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityQQ() {
        return activityQQ;
    }

    public void setActivityQQ(String activityQQ) {
        this.activityQQ = activityQQ;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActivityNeedPeopleNumber() {
        return activityNeedPeopleNumber;
    }

    public void setActivityNeedPeopleNumber(String activityNeedPeopleNumber) {
        this.activityNeedPeopleNumber = activityNeedPeopleNumber;
    }

    public String getActivityJoinPeoleNumber() {
        return activityJoinPeoleNumber;
    }

    public void setActivityJoinPeoleNumber(String activityJoinPeoleNumber) {
        this.activityJoinPeoleNumber = activityJoinPeoleNumber;
    }

    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
