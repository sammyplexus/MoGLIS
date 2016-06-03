package com.example.sammybobo.moglis;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.sammybobo.moglis.R;

/**
 * Created by Agbede on 16/05/2016.
 */
public class Addfriend extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.add_new_friend);
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_splash, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
