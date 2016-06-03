package com.example.sammybobo.moglis.interfaces;

import com.example.sammybobo.moglis.models.Add_friend_model;
import com.example.sammybobo.moglis.models.LocationDetails;
import com.example.sammybobo.moglis.models.LocationUpdate;
import com.example.sammybobo.moglis.models.SignUpModel;
import com.example.sammybobo.moglis.models.UserList;

import java.util.ArrayList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
/**
 * Created by Sammy bobo on 26/03/2016.
 */
public interface MoGLISWebInterface {
    @GET("user_details.php")
    Observable<SignUpModel> sign_up_pojo (
            @Query("signup_or_signin_or_getUserDetails_or_push_notifications")String value,
            @Query("user_name")String username,
            @Query("password")String password,
            @Query("email_address")String email_address
    );

    @GET("user_details.php")
    Observable<SignUpModel> sign_in_pojo (
        @Query("signup_or_signin_or_getUserDetails_or_push_notifications")String value,
        @Query("user_name")String username,
        @Query("password")String password
        );

    @GET("user_details.php")
    Observable<LocationUpdate> push_location_coordinates (
            @Query("signup_or_signin_or_getUserDetails_or_push_notifications")String value,
            @Query("user_id")int user_id,
            @Query("latitude") double latitude,
            @Query("longitude")double longitude,
            @Query("location_name")String name,
            @Query("location_comment")String comment
    );

    @GET("user_details.php")
    Observable<ArrayList<UserList>> getUserList(@Query("signup_or_signin_or_getUserDetails_or_push_notifications")String value,@Query("user_id")String user_id
    );

    @GET("user_details.php")
    Observable<ArrayList<LocationDetails>> get_locations_coordinates (
            @Query("signup_or_signin_or_getUserDetails_or_push_notifications")String reply,
            @Query("user_id")String id
    );

    @GET("user_details.php")
    Observable<Add_friend_model> push_friend (
            @Query("signup_or_signin_or_getUserDetails_or_push_notifications")String reply,
            @Query("user_id")String userid,
            @Query("friend_id")String friendid
    );
}
