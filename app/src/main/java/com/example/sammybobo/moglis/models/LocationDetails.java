package com.example.sammybobo.moglis.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

        public class LocationDetails {

    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("date_entered")
    @Expose
    private String dateEntered;
    @SerializedName("location_name")
    @Expose
    private String locationName;

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The dateEntered
     */
    public String getDateEntered() {
        return dateEntered;
    }

    /**
     *
     * @param dateEntered
     * The date_entered
     */
    public void setDateEntered(String dateEntered) {
        this.dateEntered = dateEntered;
    }

    /**
     *
     * @return
     * The locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     *
     * @param locationName
     * The location_name
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

}
