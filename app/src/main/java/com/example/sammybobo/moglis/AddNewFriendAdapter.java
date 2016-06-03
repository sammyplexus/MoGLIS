package com.example.sammybobo.moglis;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sammybobo.moglis.interfaces.MoGLISWebInterface;
import com.example.sammybobo.moglis.models.Add_friend_model;
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
 * Created by Agbede on 16/05/2016.
 */
public class AddNewFriendAdapter extends RecyclerView.Adapter<AddNewFriendAdapter.ViewHolder> implements Filterable{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Observable<Add_friend_model> push_friend;
    ArrayList<UserList> userlist;
    final String url = "http://10.0.3.2/MoGLIS/";
    MoGLISWebInterface moglisInterface;
    final String user_id = "user_id";
    final String user_name = "user_name";
    final String user_emailaddress = "user_emailaddress";
    final String user_fullname = "user_fullname";
    final String user_phonenumber = "user_phonenumber";
    String userid;
    Context context;
    View linear;


    public AddNewFriendAdapter(Context context, ArrayList<UserList> userlist)
    {
        this.context = context;
        this.userlist = userlist;
    }

    @Override
    public AddNewFriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_add_friends, parent, false);
        linear = view;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddNewFriendAdapter.ViewHolder holder, int position) {
        holder.name.setText(userlist.get(position).getUser_name());
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
        return null;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView name;
        private Button imageButton;
        public ViewHolder(View itemView)
        {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.add_friend_name);
            imageButton = (Button) itemView.findViewById(R.id.add_friend_image);
            imageButton.setOnClickListener(this);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_friend_name :
                Log.d("You clicked ", " "+name.getText().toString() );

                    break;

                case R.id.add_friend_image:
                    imageButton.setEnabled(false);
                    runRetro(getPosition());
                    break;

                default:

            }
        }
    }

    private void runRetro(final int position){
        context = MoGLISMaps.getContext();
        sharedPreferences = context.getSharedPreferences("user_details", 0);
        editor = sharedPreferences.edit();
        userid = sharedPreferences.getString(user_id, "");
    Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build();
    moglisInterface = retrofit.create(MoGLISWebInterface.class);

        push_friend = moglisInterface.push_friend("addfriend", userid, userlist.get(position).getUser_id());

        push_friend.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Add_friend_model>() {
                    @Override
                    public void onCompleted() {
                   Toast.makeText(MoGLISMaps.getContext(), userlist.get(position).getUser_name()+" is now a friend", Toast.LENGTH_LONG).show(); }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Add_friend_model add_friend_model) {
                        Log.d("Valu", add_friend_model.getFriendUpdate());
                    }
                });




    }
}


