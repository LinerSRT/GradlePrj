package com.kct.sports.Run;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.Settings.System;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.Activity;
import android.util.Log;

import com.kct.sports.OtherFragment.SportsStopFragment;
import com.kct.sports.R;
import com.kct.sports.base.NewApplication;
import com.kct.sports.model.SportModel;
import com.kct.sports.provider.Sprotbroadcast;
import com.kct.sports.view.HorizontalViewPager;

import java.util.ArrayList;

public class HorizontalMainActivity extends FragmentActivity {
    private IntentFilter filter = null;
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public HorizontalViewPager mHorizontalViewPager = null;
    private InfoMainTestFragment mMainInfoFragment;
    private PagerAdapter mPagerAdapter = null;
    SportModel mSportModel;
    /* access modifiers changed from: private */
    public SportsStopFragment mSportsStopFragment;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> mTabFragments = new ArrayList();
    /* access modifiers changed from: private */
    public VerticalMainTestFragment mVerticalFragment;
    private int model;
    private MyReceiver myReceiver;
    /* access modifiers changed from: private */
    public int pauseTime = 0;
    private Sprotbroadcast receiver = null;
    WakeLock wakeLock;

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) HorizontalMainActivity.this.mTabFragments.get(position);
        }

        public int getCount() {
            return HorizontalMainActivity.this.mTabFragments.size();
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.kct.spots.current_four")) {
                HorizontalMainActivity.this.mHorizontalViewPager.setScrollble(true);
            } else if (action.equals("com.kct.spots.current_other")) {
                HorizontalMainActivity.this.mHorizontalViewPager.setScrollble(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        requestWindowFeature(1);
        setContentView(R.layout.sports_horizontal);
        this.model = System.getInt(getApplicationContext().getContentResolver(), "model", 4);
        this.mSportModel = SportModel.getSportModel(this, System.getInt(getContentResolver(), "model", 4));
        initView();
        initEvent();
        if (System.getInt(getContentResolver(), "lowPower", 0) == 0) {
            initAlarm();
        } else if (this.model == 11) {
            initAlarm();
        }
        super.onCreate(arg0);
    }

    private void initAlarm() {
        this.wakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(1, "MyWakeLock_sports1:s");
        this.wakeLock.acquire();
    }

    private void initEvent() {
        NewApplication.getInstance().addActivity(this);
        this.receiver = new Sprotbroadcast();
        this.filter = new IntentFilter();
        this.filter.addAction("com.szkct.Battery_CHANGED_ACTION");
        this.filter.addAction("android.intent.action.AIRPLANE_MODE");
        registerReceiver(this.receiver, this.filter);
        this.myReceiver = new MyReceiver();
        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction("com.kct.spots.current_four");
        this.intentFilter.addAction("com.kct.spots.current_other");
        registerReceiver(this.myReceiver, this.intentFilter);
        this.mMainInfoFragment = new InfoMainTestFragment(this.mSportModel);
        this.mSportsStopFragment = new SportsStopFragment();
        this.mVerticalFragment = new VerticalMainTestFragment(this.mSportModel);
        this.mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        this.mTabFragments.clear();
        this.mTabFragments.add(this.mSportsStopFragment);
        this.mTabFragments.add(this.mVerticalFragment);
        this.mTabFragments.add(this.mMainInfoFragment);
        this.mHorizontalViewPager.setAdapter(this.mPagerAdapter);
        this.mHorizontalViewPager.setShowIndicator(false);
        this.mHorizontalViewPager.setOverScrollMode(2);
        this.mHorizontalViewPager.setOffscreenPageLimit(2);
        this.mHorizontalViewPager.setCurrentItem(2);
        this.mHorizontalViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int currentItem) {
                if (currentItem == 0) {
                    HorizontalMainActivity.this.sendBroadcast(new Intent("com.kct.spots.current_zero"));
                    HorizontalMainActivity.this.mSportsStopFragment.onResume();
                    Log.e("gg", "CURRENT_ITEM_ZERO");
                } else if (currentItem == 1) {
                    HorizontalMainActivity.this.sendBroadcast(new Intent("com.kct.spots.current_one"));
                    HorizontalMainActivity.this.mVerticalFragment.update();
                    Log.e("gg", "CURRENT_ITEM_ONE" + HorizontalMainActivity.this.pauseTime);
                } else if (currentItem == 2) {
                    HorizontalMainActivity.this.sendBroadcast(new Intent("com.kct.spots.current_two"));
                    Log.e("gg", "CURRENT_ITEM_TWO");
                }
            }

            public void onPageScrollStateChanged(int arg0) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
        });
    }

    private void initView() {
        this.mHorizontalViewPager = (HorizontalViewPager) findViewById(R.id.H_viewpager);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.myReceiver != null) {
            unregisterReceiver(this.myReceiver);
        }
        if (this.receiver != null) {
            unregisterReceiver(this.receiver);
        }
        if (this.wakeLock != null) {
            this.wakeLock.release();
        }
        super.onDestroy();
    }

    public void onBackPressed() {
    }
}
