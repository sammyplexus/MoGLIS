package com.example.sammybobo.moglis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Agbede on 27/04/2016.
 */
public class UserList {
    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("email_address")
    @Expose
    private String email_address;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    @SerializedName("picture_url")
    @Expose
    private String picture_url;
}
