package com.example.sammybobo.moglis;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammybobo.moglis.R;
import com.example.sammybobo.moglis.interfaces.MoGLISWebInterface;
import com.example.sammybobo.moglis.models.UserList;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sammy bobo on 03/04/2016.
 */
public class Friends_recycler extends Activity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView _recyclerView;
    public static Context context_;
    final String base_url = "http://10.0.3.2/MoGLIS/";
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<UserList> users;
    ProgressDialog progressDialog;
    Button add_friend;
    LayoutInflater inflater;
    AddNewFriendAdapter addNewFriendAdapter;
    MoGLISWebInterface service;
    final String user_id = "user_id";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid;
    AlertDialog.Builder alertDial;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_view_recyclerview);
        alertDial = new AlertDialog.Builder(this);
        context_ = getApplicationContext();
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString(user_id, "");
        add_friend = (Button)findViewById(R.id.addFriendButton);
        add_friend.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        recyclerView = (RecyclerView)findViewById(R.id.friend_recycler);
        users = callUsers();
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(new RecyclerListener(context_, new RecyclerListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView location_comment;
                TextView location_name_;
                TextView location_coord;
                alertDial.setTitle(users.get(position).getUser_name()+ "\'s location details");
                final View vi = LayoutInflater.from(Friends_recycler.getContext()).inflate(R.layout.location_details, null);
                alertDial.setView(vi);
                location_comment  = (TextView)vi.findViewById(R.id.location_click_comment);
                location_comment.setText("I'm enjoying shoprite men");
                location_name_ = (TextView)vi.findViewById(R.id.location_click_name);
                location_coord = (TextView)vi.findViewById(R.id.location_click);
                location_name_.setText(users.get(position).getUser_name());
                location_coord.setText(users.get(position).getEmail_address());
                alertDial.show();

            }
        }));
    }

    private ArrayList<UserList> callUsers()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(base_url)
                .build();
        service = retrofit.create(MoGLISWebInterface.class);
        final Observable<ArrayList<UserList>> userList = service.getUserList("getUserDetails", userid);

        userList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<UserList>>() {
                    @Override
                    public void onCompleted() {
                        progressDialog.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.hide();
                    }

                    @Override
                    public void onNext(ArrayList<UserList> userLists) {
                        Log.d("Size of this thing", userLists.size()+" ");
                        users = userLists;
                        recyclerView.setAdapter(new FriendsAdapter(getApplicationContext(), userLists));
                        progressDialog.hide();
                    }
                });

            return users;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_splash, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.addFriendButton)
        {
            Intent a = new Intent(Friends_recycler.this, Search_friends.class);
            startActivity(a);
        }
    }
    public static Context getContext(){
        return context_;
    }
}
