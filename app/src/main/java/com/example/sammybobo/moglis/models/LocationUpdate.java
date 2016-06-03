package com.example.sammybobo.moglis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationUpdate {

    @SerializedName("location_update")
    @Expose
    private String locationUpdate;

    /**
     *
     * @return
     * The locationUpdate
     */
    public String getLocationUpdate() {
        return locationUpdate;
    }

    /**
     *
     * @param locationUpdate
     * The location_update
     */
    public void setLocationUpdate(String locationUpdate) {
        this.locationUpdate = locationUpdate;
    }

}
