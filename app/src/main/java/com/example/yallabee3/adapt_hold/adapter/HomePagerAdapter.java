package com.example.yallabee3.adapt_hold.adapter;

import android.content.res.Resources;
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
//                fragment = new BlankFragment();

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
        String DeviceLang;
        DeviceLang = Resources.getSystem().getConfiguration().locale.getLanguage();
        switch (position) {
            case 0:
                if(DeviceLang.equals("ar")) {
                    return "الاقسام";
                }
                else {
                    return "Categories";
                }
            case 1:
                if(DeviceLang.equals("ar")) {
                    return "الاعلانات";
                }
                else {
                    return "Sponsored Ads";
                }

            default:
                return null;
        }
    }
}
