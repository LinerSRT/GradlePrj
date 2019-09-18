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

public class HeartRateSwitchPreference extends Preference {
    /* access modifiers changed from: private */
    public boolean mChecked = false;
    /* access modifiers changed from: private */
    public OnClickListener mClickListener = null;
    Context mContext;
    private ImageView mIcon;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (HeartRateSwitchPreference.this.switch_ != null) {
            }
        }
    };
    private boolean mUpdateStatusOnly = false;
    Switch switch_;
    private TextView tv_summary;

    public interface OnClickListener {
        void checkChangeListen(View view, boolean z);
    }

    public HeartRateSwitchPreference(Context context) {
        super(context);
        this.mContext = context;
    }

    public HeartRateSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public HeartRateSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    @Override
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
        this.mIcon.setImageResource(R.drawable.heartrate);
        this.tv_summary = (TextView) view.findViewById(R.id.summary);
        this.switch_ = (Switch) view.findViewById(R.id.sports_switch);
        this.switch_.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HeartRateSwitchPreference.this.mChecked = isChecked;
                if (HeartRateSwitchPreference.this.mClickListener != null) {
                    HeartRateSwitchPreference.this.mClickListener.checkChangeListen(buttonView, isChecked);
                }
            }
        });
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (this.switch_ != null) {
            int heartRateCheck = System.getInt(this.mContext.getContentResolver(), "heartRateCheck", 0);
            int heartRateDate = System.getInt(this.mContext.getContentResolver(), "heartRateDate", 0);
            if (heartRateCheck == 0) {
                this.switch_.setChecked(false);
                this.tv_summary.setText("Off");
            } else if (heartRateCheck == 1) {
                this.switch_.setChecked(true);
                if (heartRateDate == 0) {
                    this.tv_summary.setVisibility(View.GONE);
                    return;
                }
                this.tv_summary.setVisibility(View.VISIBLE);
                this.tv_summary.setText(this.mContext.getResources().getString(R.string.setting_heartRate_alarm, new Object[]{Integer.valueOf(heartRateDate)}));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        super.onClick();
    }
}
