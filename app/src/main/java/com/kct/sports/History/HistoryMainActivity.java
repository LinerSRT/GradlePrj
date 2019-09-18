package com.kct.sports.History;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.app.Activity;
import android.view.KeyEvent;

import com.kct.sports.R;
import com.kct.sports.view.HorizontalViewPager;

import java.util.ArrayList;

public class HistoryMainActivity extends FragmentActivity {
    private HistoryFragment mHistoryAcitivity;
    private HistoryLVFragment mHistoryLVFragment;
    private HorizontalViewPager mHorizontalViewPager = null;
    private PagerAdapter mPagerAdapter = null;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> mTabFragments = new ArrayList();

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) HistoryMainActivity.this.mTabFragments.get(position);
        }

        public int getCount() {
            return HistoryMainActivity.this.mTabFragments.size();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.history_horizontal);
        initVeiw();
        initEvent();
    }

    private void initEvent() {
        this.mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        this.mHistoryAcitivity = new HistoryFragment();
        this.mHistoryLVFragment = new HistoryLVFragment();
        this.mTabFragments.clear();
        this.mTabFragments.add(this.mHistoryAcitivity);
        this.mTabFragments.add(this.mHistoryLVFragment);
        this.mHorizontalViewPager.setAdapter(this.mPagerAdapter);
        this.mHorizontalViewPager.setOverScrollMode(2);
        this.mHorizontalViewPager.setOffscreenPageLimit(2);
        this.mHorizontalViewPager.setShowIndicator(true);
        this.mHorizontalViewPager.setCurrentItem(0);
    }

    private void initVeiw() {
        this.mHorizontalViewPager = (HorizontalViewPager) findViewById(R.id.H_viewpager);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }
}
