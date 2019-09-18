package com.kct.sports.setting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import com.kct.sports.R;
import com.kct.sports.view.HeartRateSwitchPreference;
import com.kct.sports.view.LowPowerSwitchPreference;
import com.kct.sports.view.LowPowerSwitchPreference.OnClickListener;
import com.kct.sports.view.PaceCheckSwitchPreference;
import com.kct.sports.view.PerOneKmSwitchPreference;
import com.kct.sports.view.SportsCheckSwitchPreference;
import com.kct.sports.view.TargetDistanceSwitchPreference;
import com.kct.sports.view.TargetTimeSwitchPreference;
import com.kct.sports.view.TargetTimeSwitchPreference.OnTargetTimeClickListener;

public class SportsSettings extends PreferenceActivity {
    private int index = 0;
    private boolean isFirstIn = true;
    /* access modifiers changed from: private */
    public Context mContext = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SportsSettings.this.mHeartRateCheckBox.setSummary(msg.obj.toString().toString());
                    break;
                case 2:
                    SportsSettings.this.mHeartRateCheckBox.setSummary("Off");
                    break;
                case 3:
                    SportsSettings.this.mOneKmCheckBox.setSummary("Off");
                    break;
                case 4:
                    SportsSettings.this.mOneKmCheckBox.setSummary("on");
                    break;
                case 5:
                    SportsSettings.this.mTarget_timeCheckBox.setSummary(msg.obj.toString().toString());
                    break;
                case 6:
                    SportsSettings.this.mTarget_timeCheckBox.setSummary("Off");
                    break;
                case 7:
                    SportsSettings.this.mTarget_distanceCheckBox.setSummary(msg.obj.toString().toString());
                    break;
                case 8:
                    SportsSettings.this.mTarget_distanceCheckBox.setSummary("Off");
                    break;
                case 9:
                    SportsSettings.this.mPaceCheckBox.setSummary(msg.obj.toString().toString());
                    break;
                case 10:
                    SportsSettings.this.mPaceCheckBox.setSummary("Off");
                    break;
                case 11:
                    SportsSettings.this.mRunCheckBox.setSummary(msg.obj.toString().toString());
                    break;
                case 12:
                    SportsSettings.this.mRunCheckBox.setSummary("Off");
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /* access modifiers changed from: private */
    public HeartRateSwitchPreference mHeartRateCheckBox;
    private ListPreference mListPreference;
    /* access modifiers changed from: private */
    public LowPowerSwitchPreference mLowPowerBox;
    /* access modifiers changed from: private */
    public PerOneKmSwitchPreference mOneKmCheckBox;
    /* access modifiers changed from: private */
    public PaceCheckSwitchPreference mPaceCheckBox;
    /* access modifiers changed from: private */
    public SportsCheckSwitchPreference mRunCheckBox;
    private Preference mSettingTitle;
    /* access modifiers changed from: private */
    public TargetDistanceSwitchPreference mTarget_distanceCheckBox;
    /* access modifiers changed from: private */
    public TargetTimeSwitchPreference mTarget_timeCheckBox;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        Log.e("we", "onCreate");
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Log.e("we", "onResume");
        if (this.mListPreference != null) {
            int index = System.getInt(getContentResolver(), "kct_menu_sport_bg", 0);
            CharSequence[] entries = this.mListPreference.getEntries();
            this.mListPreference.setSummary(entries[index]);
            if (index == 1) {
                this.mListPreference.setValue("heibai");
            } else {
                this.mListPreference.setValue("xuanduocai");
            }
            Log.e("we", "one" + entries[index] + index + "" + entries[index].toString());
        }
    }

    private void initView() {
        addPreferencesFromResource(R.xml.settings_main);
        PreferenceManager manager = getPreferenceManager();
        this.mListPreference = (ListPreference) manager.findPreference("myListPreference");
        this.mSettingTitle = manager.findPreference("setting_title");
        this.mOneKmCheckBox = (PerOneKmSwitchPreference) manager.findPreference("perKm_alarm");
        this.mLowPowerBox = (LowPowerSwitchPreference) manager.findPreference("lowpower");
        this.mTarget_timeCheckBox = (TargetTimeSwitchPreference) manager.findPreference("target_time");
        this.mHeartRateCheckBox = (HeartRateSwitchPreference) manager.findPreference("heartrate_");
        this.mTarget_distanceCheckBox = (TargetDistanceSwitchPreference) manager.findPreference("target_distance");
        this.mPaceCheckBox = (PaceCheckSwitchPreference) manager.findPreference("pace_alarm");
        this.mRunCheckBox = (SportsCheckSwitchPreference) manager.findPreference("run_alarm");
        getPreferenceScreen().removePreference(this.mTarget_timeCheckBox);
        getPreferenceScreen().removePreference(this.mHeartRateCheckBox);
        getPreferenceScreen().removePreference(this.mTarget_distanceCheckBox);
        getPreferenceScreen().removePreference(this.mPaceCheckBox);
        getPreferenceScreen().removePreference(this.mRunCheckBox);
        getListView().setDivider(null);
    }

    private void initEvent() {
        this.mContext = this;
        this.mLowPowerBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
        this.mLowPowerBox.setOnClickListener(new OnClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "lowPower", 1);
                    SportsSettings.this.mLowPowerBox.setSummary("On");
                    return;
                }
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "lowPower", 0);
                SportsSettings.this.mLowPowerBox.setSummary("Off");
            }
        });

        this.mOneKmCheckBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                return true;
            }
        });
        this.mOneKmCheckBox.setOnClickListener(new PerOneKmSwitchPreference.OnClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "perKmCheck", 1);
                    SportsSettings.this.sendMessage(4, "On");
                    return;
                }
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "perKmCheck", 0);
                SportsSettings.this.sendMessage(3, "Off");
            }
        });
        this.mHeartRateCheckBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                int heartRateDate = System.getInt(SportsSettings.this.mContext.getContentResolver(), "heartRateDate", 0);
                Log.e("111", "heartRateDate" + heartRateDate);
                SportsSettings.this.startActivity(1, heartRateDate);
                return true;
            }
        });
        this.mHeartRateCheckBox.setOnClickListener(new HeartRateSwitchPreference.OnClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    SportsSettings.this.sendMessage(1, Integer.valueOf(System.getInt(SportsSettings.this.mContext.getContentResolver(), "heartRateDate", 0)));
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "heartRateCheck", 1);
                    return;
                }
                SportsSettings.this.sendMessage(2, "Off");
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "heartRateCheck", 0);
            }
        });
        this.mTarget_timeCheckBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SportsSettings.this.startActivity(2, System.getInt(SportsSettings.this.mContext.getContentResolver(), "TargerTime", 0));
                return true;
            }
        });
        this.mTarget_timeCheckBox.setOnClickListener(new OnTargetTimeClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    SportsSettings.this.sendMessage(5, Integer.valueOf(System.getInt(SportsSettings.this.mContext.getContentResolver(), "TargerTime", 0)));
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "targetTimeCheck", 1);
                    return;
                }
                SportsSettings.this.sendMessage(6, "Off");
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "targetTimeCheck", 0);
            }
        });
        this.mTarget_distanceCheckBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SportsSettings.this.startActivity(3, System.getInt(SportsSettings.this.mContext.getContentResolver(), "TargerDistanceDate", 0));
                return true;
            }
        });
        this.mTarget_distanceCheckBox.setOnClickListener(new TargetDistanceSwitchPreference.OnClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    SportsSettings.this.sendMessage(7, Integer.valueOf(System.getInt(SportsSettings.this.mContext.getContentResolver(), "TargerDistanceDate", 0)));
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "targetDistanceCheck", 1);
                    return;
                }
                SportsSettings.this.sendMessage(8, "Off");
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "targetDistanceCheck", 0);
            }
        });
        this.mPaceCheckBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SportsSettings.this.startActivity(4, System.getInt(SportsSettings.this.mContext.getContentResolver(), "paceAlarmDate", 0));
                return true;
            }
        });
        this.mPaceCheckBox.setOnClickListener(new PaceCheckSwitchPreference.OnClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    SportsSettings.this.sendMessage(9, Integer.valueOf(System.getInt(SportsSettings.this.mContext.getContentResolver(), "paceAlarmDate", 0)));
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "paceCheck", 1);
                    return;
                }
                SportsSettings.this.sendMessage(10, "Off");
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "paceCheck", 0);
            }
        });
        this.mRunCheckBox.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SportsSettings.this.startActivity(5, System.getInt(SportsSettings.this.mContext.getContentResolver(), "sportAlarm", 0));
                return false;
            }
        });
        this.mRunCheckBox.setOnClickListener(new SportsCheckSwitchPreference.OnClickListener() {
            public void checkChangeListen(View view, boolean isCheck) {
                if (isCheck) {
                    int runAlarm = System.getInt(SportsSettings.this.mContext.getContentResolver(), "sportAlarm", 0);
                    //System.putInt(SportsSettings.this.mContext.getContentResolver(), "runCheck", 1);
                    Log.e("alarm", runAlarm + "");
                    SportsSettings.this.sendMessage(11, Integer.valueOf(runAlarm));
                    return;
                }
                SportsSettings.this.sendMessage(12, "Off");
                //System.putInt(SportsSettings.this.mContext.getContentResolver(), "runCheck", 0);
            }
        });
    }

    /* access modifiers changed from: private */
    public void startActivity(int i, int date) {
        String initDate = "000";
        Intent intent = new Intent(this, DetailSetActivity.class);
        intent.putExtra("set_what", i);
        intent.putExtra("lastDate", date);
        Log.e("eee", "date:" + date);
        startActivityForResult(intent, 0);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case -1:
                Bundle b = data.getExtras();
                int resultDate = b.getInt("date", 0);
                saveDate(b.getInt("item", 1), resultDate);
                Log.e("eee", "resultDate:" + resultDate);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveDate(int item, int resultDate) {
        switch (item) {
            case 1:
                //System.putInt(getContentResolver(), "heartRateDate", resultDate);
                //System.putInt(this.mContext.getContentResolver(), "heartRateCheck", 1);
                sendMessage(1, Integer.valueOf(resultDate));
                return;
            case 2:
                //System.putInt(this.mContext.getContentResolver(), "TargerTime", resultDate);
                sendMessage(5, Integer.valueOf(resultDate));
                //System.putInt(this.mContext.getContentResolver(), "targetTimeCheck", 1);
                return;
            case 3:
                //System.putInt(this.mContext.getContentResolver(), "TargerDistanceDate", resultDate);
                sendMessage(7, Integer.valueOf(resultDate));
                //System.putInt(this.mContext.getContentResolver(), "targetDistanceCheck", 1);
                return;
            case 4:
                //System.putInt(this.mContext.getContentResolver(), "paceAlarmDate", resultDate);
                sendMessage(9, Integer.valueOf(resultDate));
                //System.putInt(this.mContext.getContentResolver(), "paceCheck", 1);
                return;
            case 5:
                //System.putInt(this.mContext.getContentResolver(), "sportAlarm", resultDate);
                sendMessage(11, Integer.valueOf(resultDate));
                //System.putInt(this.mContext.getContentResolver(), "runCheck", 1);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void sendMessage(int what, Object object) {
        Message message = new Message();
        message.obj = object;
        message.what = what;
        this.mHandler.sendMessage(message);
    }
}
