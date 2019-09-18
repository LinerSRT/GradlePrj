package com.kct.sports.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.Preference;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.kct.sports.R;

public class PerOneKmSwitchPreference extends Preference {
    /* access modifiers changed from: private */
    public boolean mChecked = false;
    /* access modifiers changed from: private */
    public OnClickListener mClickListener = null;
    Context mContext;
    private ImageView mIcon;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (PerOneKmSwitchPreference.this.switch_ != null) {
            }
        }
    };
    private boolean mUpdateStatusOnly = false;
    Switch switch_;
    private TextView tv_summary;

    public interface OnClickListener {
        void checkChangeListen(View view, boolean z);
    }

    public PerOneKmSwitchPreference(Context context) {
        super(context);
        this.mContext = context;
    }

    public PerOneKmSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public PerOneKmSwitchPreference(Context context, AttributeSet attrs, int defStyle) {
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
        this.mIcon.setImageResource(R.drawable.perkm_alarm);
        this.tv_summary = (TextView) view.findViewById(R.id.summary);
        this.switch_ = (Switch) view.findViewById(R.id.sports_switch);
        this.switch_.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    PerOneKmSwitchPreference.this.switch_.setChecked(PerOneKmSwitchPreference.this.switch_.isChecked());
                }
                return false;
            }
        });
        this.switch_.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PerOneKmSwitchPreference.this.mChecked = isChecked;
                if (PerOneKmSwitchPreference.this.mClickListener != null) {
                    PerOneKmSwitchPreference.this.mClickListener.checkChangeListen(buttonView, isChecked);
                }
            }
        });
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
        if (this.switch_ == null) {
            return;
        }
        if (System.getInt(this.mContext.getContentResolver(), "perKmCheck", 0) == 0) {
            this.tv_summary.setText("Off");
            this.switch_.setChecked(false);
            return;
        }
        this.tv_summary.setText("On");
        this.switch_.setChecked(true);
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        super.onClick();
    }
}
