package com.kct.sports.History;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.app.Activity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.kct.sports.R;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.view.HorizontalViewPager;

import java.util.ArrayList;

public class DetailHorizontalActivity extends FragmentActivity {
    boolean dispthflag = true;
    float height;
    private HistoryDeleteActivity mHistoryDeleteActivity;
    /* access modifiers changed from: private */
    public HistoryDetialFargment mHistoryDetialActivity;
    /* access modifiers changed from: private */
    public HorizontalViewPager mHorizontalViewPager = null;
    private PagerAdapter mPagerAdapter = null;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> mTabFragments = new ArrayList();
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("we", action);
            if (action.equals("com.kct.spots.cancel_delete")) {
                DetailHorizontalActivity.this.mHorizontalViewPager.setCurrentItem(0);
            }
        }
    };
    private int position;
    private ArrayList<SportsInfo> sportsInfos = null;
    float width;

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) DetailHorizontalActivity.this.mTabFragments.get(position);
        }

        public int getCount() {
            return DetailHorizontalActivity.this.mTabFragments.size();
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.history_horizontal);
        Display display = getWindowManager().getDefaultDisplay();
        this.height = (float) display.getHeight();
        this.width = (float) display.getWidth();
        initVeiw();
        initEvent();
    }

    private void initEvent() {
        try {
            Bundle bundle = getIntent().getExtras();
            this.sportsInfos = (ArrayList) bundle.getSerializable("sportsInfos");
            this.position = bundle.getInt("position");
        } catch (Exception e) {
        }
        this.mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        this.mHistoryDeleteActivity = new HistoryDeleteActivity(this.position, this.sportsInfos);
        this.mHistoryDetialActivity = new HistoryDetialFargment(this.position, this.sportsInfos);
        this.mTabFragments.clear();
        this.mTabFragments.add(this.mHistoryDetialActivity);
        this.mTabFragments.add(this.mHistoryDeleteActivity);
        this.mHorizontalViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int arg0) {
                boolean z = false;
                if (arg0 == 0) {
                    DetailHorizontalActivity detailHorizontalActivity = DetailHorizontalActivity.this;
                    if (DetailHorizontalActivity.this.mHistoryDetialActivity.getCurrentItem() == 0) {
                        z = true;
                    }
                    detailHorizontalActivity.dispthflag = z;
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        this.mHistoryDetialActivity.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    DetailHorizontalActivity.this.dispthflag = true;
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        this.mHorizontalViewPager.setAdapter(this.mPagerAdapter);
        this.mHorizontalViewPager.setOverScrollMode(2);
        this.mHorizontalViewPager.setOffscreenPageLimit(2);
        this.mHorizontalViewPager.setCurrentItem(0);
        this.mHorizontalViewPager.setShowIndicator(false);
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.kct.spots.cancel_delete");
        registerReceiver(this.myReceiver, myIntentFilter);
    }

    private void initVeiw() {
        this.mHorizontalViewPager = (HorizontalViewPager) findViewById(R.id.H_viewpager);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.myReceiver != null) {
            unregisterReceiver(this.myReceiver);
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getX() > (this.width / 7.0f) * 6.0f) {
            this.dispthflag = false;
        } else if (ev.getY() > (this.height / 7.0f) * 6.0f) {
            this.dispthflag = false;
        } else if (this.dispthflag) {
            this.mHistoryDetialActivity.getMapView().dispatchTouchEvent(ev);
            this.mHistoryDetialActivity.getMapView().onTouchEvent(ev);
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
