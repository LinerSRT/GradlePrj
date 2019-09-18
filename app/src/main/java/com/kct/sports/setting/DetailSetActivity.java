package com.kct.sports.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kct.sports.R;
import com.kct.sports.utils.ShowStringUitls;
import com.kct.sports.view.OnWheelChangedListener;
import com.kct.sports.view.WheelView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailSetActivity extends Activity {
    private int bit;
    /* access modifiers changed from: private */
    public boolean bit_change = false;
    private Button finish_btn;
    private int hundred;
    /* access modifiers changed from: private */
    public int item;
    /* access modifiers changed from: private */
    public boolean item2_1_59 = false;
    private int lastDate;
    int lastDate_bit = 0;
    int lastDate_hundread = 0;
    int lastDate_ten = 0;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == 2) {
                        DetailSetActivity.this.numericWheelAdapter_ten = new NumericWheelAdapter(DetailSetActivity.this, 0, 0, "%01d");
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 0, 2, "%01d");
                        DetailSetActivity.this.reFreshWheelTen();
                        DetailSetActivity.this.reFreshWheelBit();
                        DetailSetActivity.this.wTen.setCurrentItem(0);
                        DetailSetActivity.this.wBit.setCurrentItem(2);
                        return;
                    } else if (msg.arg1 == 0) {
                        DetailSetActivity.this.numericWheelAdapter_ten = new NumericWheelAdapter(DetailSetActivity.this, 9, 9, "%01d");
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 7, 9, "%01d");
                        DetailSetActivity.this.reFreshWheelTen();
                        DetailSetActivity.this.reFreshWheelBit();
                        DetailSetActivity.this.wTen.setCurrentItem(9);
                        DetailSetActivity.this.wBit.setCurrentItem(7);
                        return;
                    } else {
                        DetailSetActivity.this.numericWheelAdapter_ten = new NumericWheelAdapter(DetailSetActivity.this, 0, 9, "%01d");
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 0, 9, "%01d");
                        DetailSetActivity.this.reFreshWheelTen();
                        DetailSetActivity.this.reFreshWheelBit();
                        DetailSetActivity.this.wTen.setCurrentItem(0);
                        DetailSetActivity.this.wBit.setCurrentItem(0);
                        return;
                    }
                case 2:
                    if (DetailSetActivity.this.wHundred.getCurrentItem() == 0 && DetailSetActivity.this.wTen.getCurrentItem() == 0) {
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 1, 59, "%02d");
                        DetailSetActivity.this.item2_1_59 = true;
                    } else {
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 0, 59, "%02d");
                        DetailSetActivity.this.item2_1_59 = false;
                    }
                    Log.e("gjytime", "滑动后wHundred.getCurrentItem()：" + DetailSetActivity.this.wHundred.getCurrentItem() + "wTen.getCurrentItem():" + DetailSetActivity.this.wTen.getCurrentItem());
                    DetailSetActivity.this.reFreshWheelBit();
                    return;
                case 3:
                    if (msg.arg1 == 0) {
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 1, 9, "%01d");
                        DetailSetActivity.this.reFreshWheelBit();
                        DetailSetActivity.this.wBit.setCurrentItem(1);
                        return;
                    }
                    DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 0, 9, "%01d");
                    DetailSetActivity.this.reFreshWheelBit();
                    DetailSetActivity.this.wBit.setCurrentItem(0);
                    return;
                case 4:
                    if (msg.arg1 == 10) {
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 0, 45, "%02d");
                        DetailSetActivity.this.reFreshWheelBit();
                        DetailSetActivity.this.wBit.setCurrentItem(0);
                    } else {
                        DetailSetActivity.this.numericWheelAdapter_bit = new NumericWheelAdapter(DetailSetActivity.this, 0, 59, "%02d");
                        DetailSetActivity.this.reFreshWheelBit();
                        DetailSetActivity.this.wBit.setCurrentItem(0);
                    }
                    Log.e("gjytime", "msg.arg1````" + msg.arg1);
                    return;
                default:
                    return;
            }
        }
    };
    private Typeface mNumberTypeface = null;
    /* access modifiers changed from: private */
    public NumericWheelAdapter numericWheelAdapter_bit = null;
    private NumericWheelAdapter numericWheelAdapter_hundread = null;
    /* access modifiers changed from: private */
    public NumericWheelAdapter numericWheelAdapter_ten = null;
    private TextView seperate;
    private TextView seperate2;
    private TextView seperate3;
    private TextView seperate4;
    private TextView set_what;
    private int ten;
    /* access modifiers changed from: private */
    public boolean ten_change = false;
    /* access modifiers changed from: private */
    public WheelView wBit;
    /* access modifiers changed from: private */
    public WheelView wHundred;
    /* access modifiers changed from: private */
    public WheelView wTen;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.setting_set_heartrate);
        intView();
        this.item = getIntent().getIntExtra("set_what", 1);
        this.lastDate = getIntent().getIntExtra("lastDate", 0);
        initEvent(this.item, this.lastDate);
        super.onCreate(savedInstanceState);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void initEvent(int i, int lastDate) {
        this.set_what.setText(getSetWhat(i));
        this.lastDate_hundread = 0;
        this.lastDate_ten = 0;
        this.lastDate_bit = 0;
        switch (i) {
            case 1:
                String[] heartRate_strarray = ShowStringUitls.formatTimes(lastDate).split(":");
                this.lastDate_hundread = Integer.valueOf(heartRate_strarray[0]).intValue();
                this.lastDate_ten = Integer.valueOf(heartRate_strarray[1]).intValue();
                this.lastDate_bit = Integer.valueOf(heartRate_strarray[2]).intValue();
                break;
            case 2:
                String[] strarray = ShowStringUitls.formatTimer(lastDate).split(":");
                this.lastDate_hundread = Integer.valueOf(strarray[0]).intValue();
                this.lastDate_ten = Integer.valueOf(strarray[1]).intValue();
                this.lastDate_bit = Integer.valueOf(strarray[2]).intValue();
                this.seperate.setVisibility(View.VISIBLE);
                this.seperate2.setVisibility(View.VISIBLE);
                Log.e("new", this.lastDate_hundread + "" + this.lastDate_ten + "" + this.lastDate_bit);
                break;
            case 3:
                String[] distance_strarray = ShowStringUitls.formatTimes(lastDate).split(":");
                this.lastDate_ten = Integer.valueOf(distance_strarray[1]).intValue();
                this.lastDate_bit = Integer.valueOf(distance_strarray[2]).intValue();
                this.wHundred.setVisibility(View.GONE);
                break;
            case 4:
                String[] pace_strarray = ShowStringUitls.formatTimer(lastDate).split(":");
                this.lastDate_ten = Integer.valueOf(pace_strarray[1]).intValue();
                this.lastDate_bit = Integer.valueOf(pace_strarray[2]).intValue();
                this.wHundred.setVisibility(View.GONE);
                this.seperate.setVisibility(View.GONE);
                this.seperate2.setVisibility(View.GONE);
                this.seperate3.setVisibility(View.VISIBLE);
                this.seperate4.setVisibility(View.VISIBLE);
                break;
            case 5:
                String str_run_alarm = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(getCurrentTime()));
                Log.e("gjytime", str_run_alarm);
                String[] run_strarray = str_run_alarm.split(":");
                this.lastDate_hundread = Integer.valueOf(run_strarray[0]).intValue();
                this.lastDate_ten = Integer.valueOf(run_strarray[1]).intValue();
                this.wBit.setVisibility(View.GONE);
                this.seperate.setVisibility(View.VISIBLE);
                this.seperate2.setVisibility(View.GONE);
                break;
        }
        initWheel(i);
        this.finish_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("date", DetailSetActivity.this.getDate2());
                bundle.putInt("item", DetailSetActivity.this.item);
                intent.putExtras(bundle);
                Log.e("1213", "item" + DetailSetActivity.this.item + "getDate2():" + DetailSetActivity.this.getDate2());
                DetailSetActivity.this.setResult(-1, intent);
                DetailSetActivity.this.finish();
            }
        });
        this.wHundred.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (newValue == 2 && DetailSetActivity.this.item == 1) {
                    DetailSetActivity.this.sendMessage(1, newValue);
                } else if (newValue == 0 && DetailSetActivity.this.item == 1) {
                    DetailSetActivity.this.sendMessage(1, newValue);
                } else if (newValue == 1 && DetailSetActivity.this.item == 1) {
                    DetailSetActivity.this.sendMessage(1, newValue);
                } else if (oldValue != newValue && DetailSetActivity.this.item == 2) {
                    DetailSetActivity.this.sendMessage(2, newValue);
                }
            }
        });
        this.wTen.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    DetailSetActivity.this.ten_change = true;
                }
                if (oldValue != newValue && DetailSetActivity.this.item == 2) {
                    DetailSetActivity.this.sendMessage(2, newValue);
                } else if (newValue == 0 && DetailSetActivity.this.item == 3) {
                    DetailSetActivity.this.sendMessage(3, newValue);
                } else if (DetailSetActivity.this.item == 3) {
                    DetailSetActivity.this.sendMessage(3, newValue);
                } else if (newValue == 10 && DetailSetActivity.this.item == 4) {
                    DetailSetActivity.this.sendMessage(4, newValue);
                } else if (DetailSetActivity.this.item == 4) {
                    DetailSetActivity.this.sendMessage(4, newValue);
                }
            }
        });
        this.wBit.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (oldValue != newValue) {
                    DetailSetActivity.this.bit_change = true;
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void sendMessage(int what, int value) {
        Message message = new Message();
        message.what = what;
        message.arg1 = value;
        this.mHandler.sendMessage(message);
    }

    private void reFreshWheelHundred() {
        this.numericWheelAdapter_hundread.setLabel("");
        this.numericWheelAdapter_hundread.setTextSize(40);
        this.numericWheelAdapter_hundread.setTextColor(getResources().getColor(android.R.color.white));
        this.wHundred.setViewAdapter(this.numericWheelAdapter_hundread);
        this.wHundred.setDrawShadows(true);
        this.wHundred.setCyclic(true);
        this.wHundred.invalidate();
    }

    /* access modifiers changed from: private */
    public void reFreshWheelTen() {
        this.numericWheelAdapter_ten.setLabel("");
        this.numericWheelAdapter_ten.setTextSize(40);
        this.numericWheelAdapter_ten.setTextColor(getResources().getColor(android.R.color.white));
        this.wTen.setViewAdapter(this.numericWheelAdapter_ten);
        this.wTen.setDrawShadows(true);
        this.wTen.setCyclic(true);
        this.wTen.invalidate();
    }

    /* access modifiers changed from: private */
    public void reFreshWheelBit() {
        this.numericWheelAdapter_bit.setLabel("");
        this.numericWheelAdapter_bit.setTextSize(40);
        this.numericWheelAdapter_bit.setTextColor(getResources().getColor(android.R.color.white));
        this.wBit.setViewAdapter(this.numericWheelAdapter_bit);
        this.wBit.setDrawShadows(true);
        this.wBit.setCyclic(true);
    }

    private void intView() {
        this.finish_btn = (Button) findViewById(R.id.finish);
        this.wHundred = (WheelView) findViewById(R.id.hundred);
        this.wTen = (WheelView) findViewById(R.id.ten);
        this.wBit = (WheelView) findViewById(R.id.bit);
        this.seperate = (TextView) findViewById(R.id.seperate);
        this.seperate2 = (TextView) findViewById(R.id.seperate2);
        this.seperate3 = (TextView) findViewById(R.id.seperate3);
        this.seperate4 = (TextView) findViewById(R.id.seperate4);
        this.set_what = (TextView) findViewById(R.id.set_what);
        initSeperate();
        initSeperate2();
    }

    private String getSetWhat(int i) {
        switch (i) {
            case 1:
                return getResources().getString(R.string.heartrate_date);
            case 2:
                return getResources().getString(R.string.target_time_take);
            case 3:
                return getResources().getString(R.string.distance_date);
            case 4:
                return getResources().getString(R.string.pace);
            case 5:
                return getResources().getString(R.string.remind_time);
            default:
                return "";
        }
    }

    private void initWheel(int i) {
        switch (i) {
            case 1:
                this.numericWheelAdapter_hundread = new NumericWheelAdapter(this, 0, 2, "%01d");
                if (this.lastDate_hundread != 2) {
                    if (this.lastDate_hundread != 0 || this.lastDate_ten != 9) {
                        this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 0, 9, "%01d");
                        this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 0, 9, "%01d");
                        break;
                    }
                    this.lastDate_bit--;
                    this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 9, 9, "%01d");
                    this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 7, 9, "%01d");
                    break;
                }
                this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 0, 0, "%01d");
                this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 0, 2, "%01d");
                break;
            case 2:
                this.numericWheelAdapter_hundread = new NumericWheelAdapter(this, 0, 23, "%02d");
                this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 0, 59, "%02d");
                if (this.lastDate_hundread == 0 && this.lastDate_ten == 0) {
                    this.lastDate_bit--;
                    this.item2_1_59 = true;
                    this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 1, 59, "%02d");
                } else {
                    this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 0, 59, "%02d");
                    this.item2_1_59 = false;
                }
                Log.e("gjytime", "初始化astDate_hundread:" + this.lastDate_hundread + "初始化lastDate_ten" + this.lastDate_ten + "初始化lastDate_bit" + this.lastDate_bit);
                break;
            case 3:
                this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 0, 9, "%01d");
                if (this.lastDate_ten != 0) {
                    this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 0, 9, "%01d");
                    break;
                }
                this.lastDate_bit--;
                this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 1, 9, "%01d");
                break;
            case 4:
                this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 4, 14, "%02d");
                if (this.lastDate_ten == 14) {
                    this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 0, 45, "%02d");
                } else {
                    this.numericWheelAdapter_bit = new NumericWheelAdapter(this, 0, 59, "%02d");
                }
                this.lastDate_ten -= 4;
                break;
            case 5:
                this.numericWheelAdapter_hundread = new NumericWheelAdapter(this, 0, 59, "%02d");
                this.numericWheelAdapter_ten = new NumericWheelAdapter(this, 0, 59, "%02d");
                break;
        }
        if (this.numericWheelAdapter_hundread != null) {
            reFreshWheelHundred();
            this.wHundred.setCurrentItem(this.lastDate_hundread);
        }
        if (this.numericWheelAdapter_ten != null) {
            reFreshWheelTen();
            this.wTen.setCurrentItem(this.lastDate_ten);
        }
        if (this.numericWheelAdapter_bit != null) {
            reFreshWheelBit();
            this.wBit.setCurrentItem(this.lastDate_bit);
        }
        Log.e("gjy133", "lastDate_hundread:" + this.lastDate_hundread + "~lastDate_ten:" + this.lastDate_ten + "~lastDate_bit:" + this.lastDate_bit);
    }

    /* access modifiers changed from: private */
    public int getDate2() {
        this.hundred = this.wHundred.getCurrentItem();
        if (this.item == 1 && this.hundred == 0) {
            if (this.ten_change || this.bit_change) {
                this.ten = 9;
                this.bit = this.wBit.getCurrentItem() + 7;
            } else {
                this.ten = this.wTen.getCurrentItem();
                this.bit = this.wBit.getCurrentItem();
            }
        } else if (this.item == 3 && this.wTen.getCurrentItem() == 0) {
            this.bit = this.wBit.getCurrentItem() + 1;
        } else if (this.item == 4) {
            this.ten = this.wTen.getCurrentItem() + 4;
            this.bit = this.wBit.getCurrentItem();
        } else if (this.item == 2 && this.item2_1_59) {
            this.bit = this.wBit.getCurrentItem() + 1;
        } else {
            this.ten = this.wTen.getCurrentItem();
            this.bit = this.wBit.getCurrentItem();
        }
        Log.e("gjytime", "hundred:" + this.hundred + "ten:" + this.ten + "bit:" + this.wBit.getCurrentItem() + "~lastDate_hundread:" + this.lastDate_hundread);
        if (this.item == 2 || this.item == 4 || this.item == 5) {
            return ((this.hundred * 3600) + (this.ten * 60)) + this.bit;
        }
        return ((this.hundred * 100) + (this.ten * 10)) + this.bit;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    private void initSeperate() {
        this.seperate.setTextSize(46.0f);
        this.seperate.setText(":");
    }

    private void initSeperate2() {
        this.seperate2.setTextSize(46.0f);
        this.seperate2.setText(":");
    }

    public long getCurrentTime() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
