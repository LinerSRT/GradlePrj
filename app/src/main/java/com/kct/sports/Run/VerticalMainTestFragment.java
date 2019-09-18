package com.kct.sports.Run;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kct.sports.R;
import com.kct.sports.OtherFragment.SportsStopFragment;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.model.SportModel;
import com.kct.sports.view.VerticalViewPager;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class VerticalMainTestFragment extends BaseFragment {
    List<RunDetailTestFragment> listfragment;
    private PagerAdapter mPagerAdapter = null;
    SportModel mSportModel;
    private SportsStopFragment mSportsSwitchFragment = null;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> mTabFragments = new ArrayList();
    private VerticalViewPager mVerticalViewPager = null;
    private int model = 0;

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) VerticalMainTestFragment.this.mTabFragments.get(position);
        }

        public int getCount() {
            return VerticalMainTestFragment.this.mTabFragments.size();
        }
    }

    public VerticalMainTestFragment(SportModel mSportModel) {
        this.mSportModel = mSportModel;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.run_vertical, container, false);
        this.mVerticalViewPager = (VerticalViewPager) view.findViewById(R.id.viewpager);
        this.mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        int i = System.getInt(getActivity().getContentResolver(), "model", 4);
        setModel();
        this.mVerticalViewPager.setAdapter(this.mPagerAdapter);
        this.mVerticalViewPager.setOverScrollMode(2);
        this.mVerticalViewPager.setOffscreenPageLimit(3);
        this.mVerticalViewPager.setCurrentItem(0);
        this.mVerticalViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int item) {
                Log.e("kctsports", "tem" + VerticalMainTestFragment.this.mTabFragments.size());
                if (item == VerticalMainTestFragment.this.mTabFragments.size() - 1) {
                    VerticalMainTestFragment.this.getActivity().sendBroadcast(new Intent("com.kct.spots.current_four"));
                } else {
                    VerticalMainTestFragment.this.getActivity().sendBroadcast(new Intent("com.kct.spots.current_other"));
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        return view;
    }

    private void setModel() {
        this.mTabFragments.clear();
        this.listfragment = new ArrayList();
        for (int i = 1; i < this.mSportModel.getViewIndexes().length; i++) {
            RunDetailTestFragment mRunDetailTestFragment = new RunDetailTestFragment(this.mSportModel, i);
            this.mTabFragments.add(mRunDetailTestFragment);
            this.listfragment.add(mRunDetailTestFragment);
        }
    }

    public void update() {
        for (RunDetailTestFragment mRunDetailTestFragment : this.listfragment) {
            mRunDetailTestFragment.update();
        }
    }
}
