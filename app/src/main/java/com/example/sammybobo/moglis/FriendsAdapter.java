package com.example.sammybobo.moglis;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sammybobo.moglis.models.UserList;

import java.util.ArrayList;

/**
 * Created by Sammy bobo on 03/04/2016.
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
        Context context;
        ArrayList<UserList> userlist;

    public FriendsAdapter(Context context, ArrayList<UserList> userlist){
        this.context = context;
        this.userlist = userlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_list_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.d("hello worldy", "It is here");
        holder.location.setText(userlist.get(position).getEmail_address());
        holder.name.setText(userlist.get(position).getUser_name());
    }

    @Override
    public int getItemCount()
    {
         return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout mainHolder;
        private LinearLayout textHolder;
        private TextView name;
        private TextView location;
        private ImageView imageView;
       public ViewHolder(View itemView) {
           super(itemView);
           mainHolder = (LinearLayout)itemView.findViewById(R.id.mainHolder);
           textHolder = (LinearLayout)itemView.findViewById(R.id.textPlaceHolder);
           name = (TextView)itemView.findViewById(R.id.user_fullname);
           location = (TextView)itemView.findViewById(R.id.user_location);
           Log.d("It got here", "it is here");
         //  imageView = (ImageView)itemView.findViewById(R.id.user_image);'
       }
    }
}
