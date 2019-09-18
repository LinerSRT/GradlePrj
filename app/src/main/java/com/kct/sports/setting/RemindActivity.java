package com.kct.sports.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kct.sports.R;
import com.kct.sports.utils.ShowStringUitls;

public class RemindActivity extends Activity {
    private LinearLayout bgLinearLayout;
    private Button btn_ok;
    int date;
    private TextView mFinish_over;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RemindActivity.this.mVibrator.cancel();
                    RemindActivity.this.finish();
                    return;
                default:
                    return;
            }
        }
    };
    private Receiver mReceiver;
    private TextView mShowDate;
    private TextView mUnit;
    /* access modifiers changed from: private */
    public Vibrator mVibrator;
    private WakeLock mWakeLock = null;
    int which;

    public class Receiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!(action.equals("com.kct.spots.target_distance") || action.equals("com.kct.spots.target_time") || action.equals("com.kct.spots.target_pace") || !action.equals("com.kct.spots.target_distance"))) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_remind);
        initView();
        iniEvent();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.mVibrator.cancel();
            finish();
        }
        return super.onTouchEvent(event);
    }

    private void initView() {
        this.mFinish_over = (TextView) findViewById(R.id.finish_over);
        this.mShowDate = (TextView) findViewById(R.id.show_date);
        this.mUnit = (TextView) findViewById(R.id.unit);
        this.bgLinearLayout = (LinearLayout) findViewById(R.id.alarm_remind_bg);
        this.btn_ok = (Button) findViewById(R.id.btn_ok);
        this.btn_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                RemindActivity.this.finish();
            }
        });
    }

    private void iniEvent() {
        wakeUpAndUnlock(this);
        this.mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);
        this.mVibrator.vibrate(new long[]{100, 2000, 50, 2000}, -1);
        this.mHandler.sendEmptyMessageDelayed(1, 20000);
        this.mReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.kct.spots.target_distance");
        intentFilter.addAction("com.kct.spots.target_time");
        intentFilter.addAction("com.kct.spots.target_pace");
        intentFilter.addAction("com.kct.spots.per_one_km");
        registerReceiver(this.mReceiver, intentFilter);
        this.which = getIntent().getIntExtra("which", 1);
        this.date = getIntent().getIntExtra("current_date", 1);
        setText(this.which, this.date);
    }

    public void setText(int which, int date) {
        switch (which) {
            case 1:
                this.bgLinearLayout.setBackground(getResources().getDrawable(R.drawable.finish_bg));
                this.mFinish_over.setText(getResources().getString(R.string.finished));
                this.mShowDate.setText(String.valueOf(date));
                this.mShowDate.setTextColor(Color.parseColor("#22A7DF"));
                this.mUnit.setText(getResources().getString(R.string.kilometer));
                return;
            case 2:
                this.bgLinearLayout.setBackground(getResources().getDrawable(R.drawable.over_bg));
                this.mFinish_over.setText(getResources().getString(R.string.over));
                this.mShowDate.setText(String.valueOf(date));
                this.mShowDate.setTextColor(Color.parseColor("#e71f2e"));
                this.mUnit.setText(getResources().getString(R.string.heart_rate_ut));
                return;
            case 3:
                this.bgLinearLayout.setBackground(getResources().getDrawable(R.drawable.finish_bg));
                this.mFinish_over.setText(getResources().getString(R.string.finished));
                this.mShowDate.setText(String.valueOf(ShowStringUitls.formatTimer(date)));
                this.mShowDate.setTextColor(Color.parseColor("#22A7DF"));
                this.mUnit.setText(getResources().getString(R.string.target_time_take));
                return;
            case 4:
                this.bgLinearLayout.setBackground(getResources().getDrawable(R.drawable.finish_bg));
                this.mFinish_over.setText(getResources().getString(R.string.finished));
                this.mShowDate.setText(String.valueOf(date));
                this.mShowDate.setTextColor(Color.parseColor("#22A7DF"));
                this.mUnit.setText(getResources().getString(R.string.target_distance));
                return;
            case 5:
                this.bgLinearLayout.setBackground(getResources().getDrawable(R.drawable.over_bg));
                this.mFinish_over.setText(getResources().getString(R.string.have_lower));
                this.mShowDate.setText(String.valueOf(ShowStringUitls.formatPace(date)));
                this.mShowDate.setTextColor(Color.parseColor("#e71f2e"));
                this.mUnit.setText(getResources().getString(R.string.pace_ll));
                return;
            default:
                return;
        }
    }

    public void finish() {
        super.finish();
        releaseWakeLock();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.mReceiver != null) {
            unregisterReceiver(this.mReceiver);
        }
        super.onDestroy();
    }

    private void wakeUpAndUnlock(Context context) {
        this.mWakeLock = ((PowerManager) context.getSystemService(POWER_SERVICE)).newWakeLock(268435482, "bright:1");
        this.mWakeLock.acquire();
    }

    private void releaseWakeLock() {
        if (this.mWakeLock != null) {
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
    }
}
