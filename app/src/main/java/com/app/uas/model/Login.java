package com.app.uas.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Login {
    @SerializedName("status")
    String status;
    @SerializedName("result")
    List<DetailLogin> loginUsers;
    @SerializedName("message")
    String message;

    public Login(String status, List<DetailLogin> loginUsers, String message) {
        this.status = status;
        this.loginUsers = loginUsers;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DetailLogin> getLoginUsers() {
        return loginUsers;
    }

    public void setLoginUsers(List<DetailLogin> loginUsers) {
        this.loginUsers = loginUsers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
