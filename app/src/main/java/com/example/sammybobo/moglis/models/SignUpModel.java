package com.example.sammybobo.moglis.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("email_address")
    @Expose
    private String emailAddress;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("picture_url")
    @Expose
    private String pictureUrl;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("phone_serial_number")
    @Expose
    private String phoneSerialNumber;
    @SerializedName("phone_type")
    @Expose
    private String phoneType;

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return The emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress The email_address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @param pictureUrl The picture_url
     */
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    /**
     * @return The phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return The fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName The full_name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The phoneSerialNumber
     */
    public String getPhoneSerialNumber() {
        return phoneSerialNumber;
    }

    /**
     * @param phoneSerialNumber The phone_serial_number
     */
    public void setPhoneSerialNumber(String phoneSerialNumber) {
        this.phoneSerialNumber = phoneSerialNumber;
    }

    /**
     * @return The phoneType
     */
    public String getPhoneType() {
        return phoneType;
    }

    /**
     * @param phoneType The phone_type
     */
    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

}


