package com.example.sammybobo.moglis;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by Sammy bobo on 20/02/2016.
 */
public class MainActivity extends FragmentActivity
{
    ViewPager myViewPager;
    myViewPager _myViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = (ViewPager)findViewById(R.id.vpager);
        _myViewPager = new myViewPager(getSupportFragmentManager());
       myViewPager.setAdapter(_myViewPager);
    }
}
