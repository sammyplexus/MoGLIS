package com.example.sammybobo.moglis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Agbede on 28/05/2016.
 */
public class Add_friend_model {
    @SerializedName("friend_update")
    @Expose
    private String friendUpdate;

    /**
     *
     * @return
     * The friendUpdate
     */
    public String getFriendUpdate() {
        return friendUpdate;
    }

    /**
     *
     * @param friendUpdate
     * The friend_update
     */
    public void setFriendUpdate(String friendUpdate) {
        this.friendUpdate = friendUpdate;
    }
}
