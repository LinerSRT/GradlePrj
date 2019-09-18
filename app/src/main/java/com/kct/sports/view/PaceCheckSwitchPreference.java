package com.kct.sports.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.Preference;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.kct.sports.R;

public class PaceCheckSwitchPreference extends Preference {
    private int _bit = 0;
    private int _ten = 0;
    /* access modifiers changed from: private */
    public boolean mChecked = false;
    /* access modifiers changed from: private */
    public OnClickListener mClickListener = null;
    Context mContext;
    private ImageView mIcon;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (PaceCheckSwitchPreference.this.switch_ != null) {
            }
        }
    };
    private boolean mUpdateStatusOnly = false;
    Switch switch_;
    private TextView tv_summary;

    public interface OnClickListener {
        void checkChangeListen(View view, boolean z);
    }

    public PaceCheckSwitchPreference(Context context) {
        super(context);
        this.mContext = context;
    }

    public PaceCheckSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public PaceCheckSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
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
        this.tv_summary = (TextView) view.findViewById(R.id.summary);
        this.mIcon = (ImageView) view.findViewById(R.id.icon);
        this.mIcon.setImageResource(R.drawable.pace_alarm);
        this.switch_ = (Switch) view.findViewById(R.id.sports_switch);
        this.switch_.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PaceCheckSwitchPreference.this.mChecked = isChecked;
                if (PaceCheckSwitchPreference.this.mClickListener != null) {
                    PaceCheckSwitchPreference.this.mClickListener.checkChangeListen(buttonView, isChecked);
                }
            }
        });
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (this.switch_ != null) {
            int heartRateCheck = System.getInt(this.mContext.getContentResolver(), "paceCheck", 0);
            int paceAlarm = System.getInt(this.mContext.getContentResolver(), "paceAlarmDate", 0);
            if (heartRateCheck == 0) {
                this.switch_.setChecked(false);
                this.tv_summary.setText("Off");
            } else if (heartRateCheck == 1) {
                this.switch_.setChecked(true);
                if (paceAlarm == 0) {
                    this.tv_summary.setVisibility(View.GONE);
                    return;
                }
                this.tv_summary.setVisibility(View.VISIBLE);
                String[] strarray = formatTime(paceAlarm).split(":");
                this._ten = Integer.valueOf(strarray[1]).intValue();
                this._bit = Integer.valueOf(strarray[2]).intValue();
                this.tv_summary.setText(this.mContext.getResources().getString(R.string.setting_pace_alarm, new Object[]{Integer.valueOf(this._ten), Integer.valueOf(this._bit)}));
            }
        }
    }

    public static String formatTime(int time) {
        String hh = time / 3600 > 9 ? (time / 3600) + "" : "0" + (time / 3600);
        return hh + ":" + ((time % 3600) / 60 > 9 ? ((time % 3600) / 60) + "" : "0" + ((time % 3600) / 60)) + ":" + ((time % 3600) % 60 > 9 ? ((time % 3600) % 60) + "" : "0" + ((time % 3600) % 60));
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        super.onClick();
    }
}
