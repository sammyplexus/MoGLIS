package com.example.sammybobo.moglis;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Splash extends Activity
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    final String user_id = "user_id";
    final String user_name = "user_name";
    final String user_emailaddress = "user_emailaddress";
    final String user_fullname = "user_fullname";
    final String user_phonenumber = "user_phonenumber";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (sharedPreferences.contains(user_name))
        {
                        Intent a = new Intent(Splash.this, MoGLISMaps.class);
                        // overridePendingTransition();
                        startActivity(a);

        }

        else
        {
                        Intent a = new Intent(Splash.this, MainActivity.class);
                        startActivity(a);
        }

    }
}
