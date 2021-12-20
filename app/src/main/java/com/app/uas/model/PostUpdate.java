package com.app.uas.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostUpdate {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<Data> dataList;
    @SerializedName("message")
    String message;

    public PostUpdate(String status, List<Data> dataList, String message) {
        this.status = status;
        this.dataList = dataList;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
