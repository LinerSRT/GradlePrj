package com.kct.sports.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.Preference;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.kct.sports.R;
import com.kct.sports.setting.AlarmReceiver;
import com.kct.sports.utils.ShowStringUitls;
import java.util.Calendar;
import java.util.TimeZone;
import android.provider.Settings.System;

public class SportsCheckSwitchPreference extends Preference {
    private int _hundread = 0;
    private int _ten = 0;
    String hString = "";
    private Calendar mCalendar;
    /* access modifiers changed from: private */
    public boolean mChecked = false;
    /* access modifiers changed from: private */
    public OnClickListener mClickListener = null;
    Context mContext;
    private ImageView mIcon;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (SportsCheckSwitchPreference.this.switch_ != null) {
            }
        }
    };
    private boolean mUpdateStatusOnly = false;
    Switch switch_;
    String tString = "";
    private TextView tv_summary;

    public interface OnClickListener {
        void checkChangeListen(View view, boolean z);
    }

    public SportsCheckSwitchPreference(Context context) {
        super(context);
        this.mContext = context;
    }

    public SportsCheckSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SportsCheckSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        return LayoutInflater.from(this.mContext).inflate(R.layout.switch_preference_item, parent, false);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onBindView(View view) {
        super.onBindView(view);
        this.mIcon = (ImageView) view.findViewById(R.id.icon);
        this.mIcon.setImageResource(R.drawable.run_alarm);
        this.tv_summary = (TextView) view.findViewById(R.id.summary);
        this.switch_ = (Switch) view.findViewById(R.id.sports_switch);
        this.switch_.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SportsCheckSwitchPreference.this.mChecked = isChecked;
                if (SportsCheckSwitchPreference.this.mClickListener != null) {
                    SportsCheckSwitchPreference.this.mClickListener.checkChangeListen(buttonView, isChecked);
                }
            }
        });
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (this.switch_ == null) {
            return;
        }
        if (System.getInt(this.mContext.getContentResolver(), "runCheck", 0) == 0) {
            this.tv_summary.setText("Off");
            this.switch_.setChecked(false);
            stopRemind();
            return;
        }
        int runAlarm = System.getInt(this.mContext.getContentResolver(), "sportAlarm", 0);
        String[] strarray = ShowStringUitls.formatTimer(runAlarm).split(":");
        this._hundread = Integer.valueOf(strarray[0]).intValue();
        this._ten = Integer.valueOf(strarray[1]).intValue();
        Log.e("alarm", "_hundread:" + this._hundread + "_ten" + this._ten + "runAlarm:" + runAlarm);
        setAlarm(this._hundread, this._ten);
        this.switch_.setChecked(true);
        if (-1 >= this._hundread || this._hundread >= 10) {
            this.hString = String.valueOf(this._hundread);
        } else {
            this.hString = "0" + String.valueOf(this._hundread);
        }
        if (-1 >= this._ten || this._ten >= 10) {
            this.tString = String.valueOf(this._ten);
        } else {
            this.tString = "0" + String.valueOf(this._ten);
        }
        this.tv_summary.setText(this.mContext.getResources().getString(R.string.setting_run_alarm, new Object[]{this.hString, this.tString}));
    }

    private void setAlarm(int hour, int mintues) {
        this.mCalendar = Calendar.getInstance();
        this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
        long systemTime = java.lang.System.currentTimeMillis();
        this.mCalendar.setTimeInMillis(java.lang.System.currentTimeMillis());
        this.mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        this.mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        this.mCalendar.set(Calendar.MINUTE, mintues);
        this.mCalendar.set(Calendar.SECOND, 0);
        this.mCalendar.set(Calendar.MILLISECOND, 0);
        if (systemTime > this.mCalendar.getTimeInMillis()) {
            this.mCalendar.add(Calendar.DATE, 1);
        }
        ((AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE)).setRepeating(AlarmManager.RTC_WAKEUP, this.mCalendar.getTimeInMillis(), 86400000, PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.mContext, AlarmReceiver.class), 0));
    }

    private void stopRemind() {
        ((AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE)).cancel(PendingIntent.getBroadcast(this.mContext, 0, new Intent(this.mContext, AlarmReceiver.class), 0));
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        super.onClick();
    }
}
