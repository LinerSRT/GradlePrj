package com.kct.sports.History;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.view.VerticalViewPager;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class HistoryDetialFargment extends BaseFragment {
    private HistoryDetailFiveFragment mHistoryDetailFiveFragment;
    private HistoryDetailFourFragment mHistoryDetailFourFragment;
    private HistoryDetailOneFragment mHistoryDetailOneFragment;
    private HistoryDetailSixFragment mHistoryDetailSixFragment;
    private HistoryDetailThreeFragment mHistoryDetailThreeFragment;
    private HistoryDetailTwoFragment mHistoryDetailTwoFragment;
    OnPageChangeListener mOnPageChangeListener;
    private PagerAdapter mPagerAdapter = null;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> mTabFragments = new ArrayList();
    private VerticalViewPager mVerticalViewPager = null;
    private int position;
    private ArrayList<SportsInfo> sportsInfos = null;
    private View view;

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) HistoryDetialFargment.this.mTabFragments.get(position);
        }

        public int getCount() {
            return HistoryDetialFargment.this.mTabFragments.size();
        }
    }

    public HistoryDetialFargment(int position, ArrayList<SportsInfo> sportsInfos) {
        this.position = position;
        this.sportsInfos = sportsInfos;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.run_vertical, container, false);
        initView();
        initEvent();
        return this.view;
    }

    private void initEvent() {
        this.mHistoryDetailOneFragment = new HistoryDetailOneFragment(this.position, this.sportsInfos);
        this.mHistoryDetailTwoFragment = new HistoryDetailTwoFragment(this.position, this.sportsInfos);
        this.mHistoryDetailThreeFragment = new HistoryDetailThreeFragment(this.position, this.sportsInfos);
        this.mHistoryDetailFourFragment = new HistoryDetailFourFragment(this.position, this.sportsInfos);
        this.mHistoryDetailFiveFragment = new HistoryDetailFiveFragment(this.position, this.sportsInfos);
        this.mHistoryDetailSixFragment = new HistoryDetailSixFragment(this.position, this.sportsInfos);
        this.mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        this.mTabFragments.clear();
        this.mTabFragments.add(this.mHistoryDetailOneFragment);
        this.mTabFragments.add(this.mHistoryDetailTwoFragment);
        this.mTabFragments.add(this.mHistoryDetailFourFragment);
        this.mTabFragments.add(this.mHistoryDetailFiveFragment);
        this.mTabFragments.add(this.mHistoryDetailSixFragment);
        this.mVerticalViewPager.setAdapter(this.mPagerAdapter);
        this.mVerticalViewPager.setOverScrollMode(2);
        this.mVerticalViewPager.setOffscreenPageLimit(2);
        this.mVerticalViewPager.setCurrentItem(0);
    }

    private void initView() {
        this.mVerticalViewPager = (VerticalViewPager) this.view.findViewById(R.id.viewpager);
        this.mVerticalViewPager.setOnPageChangeListener(this.mOnPageChangeListener);
    }

    public View getMapView() {
        return this.mHistoryDetailOneFragment.getMapView();
    }

    public int getCurrentItem() {
        return this.mVerticalViewPager.getCurrentItem();
    }

    public void setOnPageChangeListener(OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }
}
