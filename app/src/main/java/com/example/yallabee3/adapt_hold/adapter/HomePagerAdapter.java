package com.example.yallabee3.adapt_hold.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yallabee3.fragment.CategeryFragment;
import com.example.yallabee3.fragment.SponsorFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new CategeryFragment();
                return fragment;

            case 1:
                fragment = new SponsorFragment();
                return fragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Categories";

            case 1:
                return "Sponsored Ads";

            default:
                return null;
        }
    }
}
