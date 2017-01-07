package com.example.dioncamposano17.loveadvice20.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dioncamposano17.loveadvice20.R;
import com.example.dioncamposano17.loveadvice20.adapter.ViewPagerAdapter;

public class CategoriesFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout = (TabLayout)getActivity().findViewById(R.id.tabLayout);
        viewPager = (ViewPager)getActivity().findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragments(new FamilyFragment(),"Family");
        viewPagerAdapter.addFragments(new FriendsFragment(),"Friends");
        viewPagerAdapter.addFragments(new SpecialFragment(),"Loveones");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.family_tab);
        tabLayout.getTabAt(1).setIcon(R.drawable.friends_tab);
        tabLayout.getTabAt(2).setIcon(R.drawable.special_tab);
    }

}
