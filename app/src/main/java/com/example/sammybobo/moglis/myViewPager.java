package com.example.sammybobo.moglis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Sammy bobo on 19/03/2016.
 */
public class myViewPager extends FragmentPagerAdapter {
    Fragment returned_fragment;
    final int number_of_tabs = 2;
    public myViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:  returned_fragment = new SignIn();
                break;
            case 1 : returned_fragment = new SignUp();
                break;
            default:new SignIn();
        }
        return returned_fragment;
    };

    @Override
    public int getCount()
    {
        return number_of_tabs;
    }
}
