package com.example.yallabee3.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.HomePagerAdapter;


public class HomeFragment extends Fragment  {
    Context context;


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentManager fm;
    private HomePagerAdapter pagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getActivity().getApplicationContext();
        Context mContext = getActivity();
        fm = ((FragmentActivity) mContext).getSupportFragmentManager();

        viewPager = view.findViewById(R.id.profile_viewPager);
        tabLayout = view.findViewById(R.id.profile_tabLayout);


        pagerAdapter = new HomePagerAdapter(fm);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        return view;
    }


}
