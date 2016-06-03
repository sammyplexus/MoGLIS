package com.example.sammybobo.moglis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.sammybobo.moglis.interfaces.MoGLISWebInterface;
import com.example.sammybobo.moglis.models.UserList;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Agbede on 23/05/2016.
 */
public class Search_friends extends AppCompatActivity {
    final String base_url = "http://10.0.3.2/MoGLIS/";
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<UserList> users;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    MoGLISWebInterface service;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String user_id = "user_id";
    final String user_name = "user_name";
    final String user_emailaddress = "user_emailaddress";
    final String user_fullname = "user_fullname";
    final String user_phonenumber = "user_phonenumber";
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_friend);
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString(user_id, "");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        recyclerView = (RecyclerView) findViewById(R.id.add_friend_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        users = callUsers();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

      /*  SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<UserList> callUsers()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
        service = retrofit.create(MoGLISWebInterface.class);
        final Observable<ArrayList<UserList>> userList = service.getUserList("getUserDet", userid);

        userList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<UserList>>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error is ", e.toString());
                        e.printStackTrace();
                        progressDialog.hide();
                    }

                    @Override
                    public void onNext(ArrayList<UserList> userLists) {
                        users = userLists;
                        recyclerView.setAdapter(new AddNewFriendAdapter(Search_friends.this, userLists));
                        progressDialog.hide();
                    }
                });

        return users;
    }
}
