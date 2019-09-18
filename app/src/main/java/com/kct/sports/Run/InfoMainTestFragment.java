package com.kct.sports.Run;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.kct.sports.R;
import com.kct.sports.base.BaseFragment;
import com.kct.sports.base.NewApplication;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.base.Trace;
import com.kct.sports.bll.SportsBLL;
import com.kct.sports.db.DBUtil;
import com.kct.sports.model.SportModel;
import com.kct.sports.provider.DateProviderService;
import com.kct.sports.setting.RemindActivity;
import com.kct.sports.utils.PreferenceUtils;
import com.kct.sports.utils.ShowStringUitls;
import com.kct.sports.utils.Utils;
import com.kct.sports.view.BeBasTextView;
import com.kct.sports.view.LantingTextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

@SuppressLint("ValidFragment")
public class InfoMainTestFragment extends BaseFragment {
    /* access modifiers changed from: private|static|final */
    public static final String TAG = InfoMainTestFragment.class.getSimpleName();
    private static int mCurrentMode = 0;
    public static int state = 0;
    static int value = -1;
    /* access modifiers changed from: private */
    public long HVSStartTime = 0;
    /* access modifiers changed from: private */
    public boolean HVSStartTimeState = false;
    /* access modifiers changed from: private */
    public boolean PaceStartTimeState = false;
    long allstarttime = 0;
    private OnKeyListener backlistener = new OnKeyListener() {
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            return true;
        }
    };
    public int bestPace = 0;
    public int bestPaceDistance = 0;
    public int currentPace = 0;
    /* access modifiers changed from: private */
    public boolean dateEnought = false;
    /* access modifiers changed from: private */
    public DBUtil dbUtil;
    /* access modifiers changed from: private */
    public boolean distanceState;
    /* access modifiers changed from: private */
    public boolean gpsIsWeek = false;
    /* access modifiers changed from: private */
    public boolean gpsIsWeekMessage = false;
    private TextView gps_count;
    /* access modifiers changed from: private */
    public boolean heartRateSate;
    private List<SportsInfo> hvsList = new ArrayList();
    long hvs_result = 0;
    int inFiveMinute = 300;
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public boolean isFiveMin = true;
    private int isLowPower;
    private ImageView iv_gps;
    long lastpausestarttime = 0;
    /* access modifiers changed from: private */
    public LocationManager locationManager = null;
    /* access modifiers changed from: private */
    public long locationStartTime = 0;
    public int lowestPace = 0;
    public int lowestPaceDistance = 0;
    private Chronometer mChronometer;
    /* access modifiers changed from: private */
    public FragmentActivity mContext;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    InfoMainTestFragment.this.tv_watch_time.setText(InfoMainTestFragment.this.showWatchTime());
                    return;
                case 2:
                    Log.e("gjy0420", "22222model=" + InfoMainTestFragment.this.model + "getSportsBLL().getStepSpeed()=" + InfoMainTestFragment.this.getSportsBLL().getStepSpeed());
                    InfoMainTestFragment.this.tv_kcal.setText(String.valueOf(Utils.decimalTo2(InfoMainTestFragment.this.getSportsBLL().getCalorie(), 2)));
                    InfoMainTestFragment.this.tv_kcal.setTypeface(NewApplication.mTypeface_date);
                    InfoMainTestFragment.this.tv_distance.setTypeface(NewApplication.mTypeface_date);
                    InfoMainTestFragment.this.tv_hearRate.setText(String.valueOf(InfoMainTestFragment.this.getSportsBLL().getHeartRate()));
                    InfoMainTestFragment.this.tv_hearRate.setTypeface(NewApplication.mTypeface_date);
                    Log.i(InfoMainTestFragment.TAG, "getSportsBLL().getStepSpeed()：~" + InfoMainTestFragment.this.getSportsBLL().getStepSpeed());
                    if (InfoMainTestFragment.this.getSportsBLL().getStepSpeed() > 0) {
                        InfoMainTestFragment.this.mStepSpeed.add(Integer.valueOf(InfoMainTestFragment.this.getSportsBLL().getStepSpeed()));
                    }
                    if (InfoMainTestFragment.this.getSportsBLL().getSpeed() > 0.0d) {
                        InfoMainTestFragment.this.mSpeed.add(Double.valueOf(InfoMainTestFragment.this.getSportsBLL().getSpeed()));
                    }
                    if (InfoMainTestFragment.this.getSportsBLL().getAveStepWidth() > 0.0d) {
                        InfoMainTestFragment.this.mStepWidth.add(Double.valueOf(InfoMainTestFragment.this.getSportsBLL().getAveStepWidth()));
                    }
                    if (InfoMainTestFragment.this.getSportsBLL().getOneKm()) {
                        InfoMainTestFragment.this.isOneKmAlarm();
                        if (!InfoMainTestFragment.this.PaceStartTimeState) {
                            InfoMainTestFragment.this.paceStartTime = InfoMainTestFragment.this.getStartTime();
                            InfoMainTestFragment.this.PaceStartTimeState = true;
                        }
                        SportsInfo pace = new SportsInfo();
                        pace.setPace(InfoMainTestFragment.this.getSportsBLL().getPace());
                        pace.setGetTime(InfoMainTestFragment.this.getCurrentTime());
                        pace.setStartTime(InfoMainTestFragment.this.paceStartTime);
                        pace.setModel(InfoMainTestFragment.this.model);
                        InfoMainTestFragment.this.dbUtil.insertPace(pace);
                    }
                    InfoMainTestFragment.this.man_right_tv.setTypeface(NewApplication.mTypeface_date);
                    return;
                case 3:
                    if (!InfoMainTestFragment.this.timeState) {
                        InfoMainTestFragment.this.isTimeAlarm(msg.arg1);
                        return;
                    }
                    return;
                case 4:
                    if (!InfoMainTestFragment.this.distanceState) {
                        InfoMainTestFragment.this.isDistanceAlarm();
                        return;
                    }
                    return;
                case 5:
                    if (!InfoMainTestFragment.this.paceState) {
                        InfoMainTestFragment.this.isPaceAlarm();
                        return;
                    }
                    return;
                case 8:
                    SportsInfo sportsInfo = new SportsInfo();
                    sportsInfo.setHeatRate(InfoMainTestFragment.this.getSportsBLL().getHeartRate());
                    sportsInfo.setSpeed(InfoMainTestFragment.this.getSportsBLL().getSpeed());
                    sportsInfo.setStepSpeed(InfoMainTestFragment.this.getSportsBLL().getStepSpeed());
                    sportsInfo.setGetTime(InfoMainTestFragment.this.getCurrentTime());
                    sportsInfo.setStartTime(InfoMainTestFragment.this.HVSStartTime);
                    sportsInfo.setModel(InfoMainTestFragment.this.model);
                    InfoMainTestFragment.this.dbUtil.insertHVS(sportsInfo);
                    if (InfoMainTestFragment.this.getSportsBLL().getHeartRate() > 0) {
                        InfoMainTestFragment.this.mHeartRate.add(Integer.valueOf(InfoMainTestFragment.this.getSportsBLL().getHeartRate()));
                        Log.e("aveherart", "size!!= " + InfoMainTestFragment.this.mHeartRate.size());
                        return;
                    }
                    return;
                case 9:
                    boolean isSwitch = PreferenceUtils.getPrefBoolean(InfoMainTestFragment.this.mContext, "gpsSwitch", false);
                    InfoMainTestFragment.this.gpsSwitch(0);
                    InfoMainTestFragment.this.getActivity().sendBroadcast(new Intent("com.kct.sport.stop.action"));
                    //System.putInt(InfoMainTestFragment.this.getActivity().getContentResolver(), "sportsEnable", 0);
                    NewApplication.getInstance().exit();
                    return;
                case 10:
                    if (!InfoMainTestFragment.this.heartRateSate) {
                        InfoMainTestFragment.this.isHeartRateAlarm();
                        return;
                    }
                    return;
                case 12:
                    InfoMainTestFragment.this.mHandler.sendEmptyMessage(9);
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public List<Integer> mHeartRate = new ArrayList();
    private ContentResolver mResolver;
    /* access modifiers changed from: private */
    public List<Double> mSpeed = new ArrayList();
    SportModel mSportModel;
    /* access modifiers changed from: private */
    public List<Integer> mStepSpeed = new ArrayList();
    /* access modifiers changed from: private */
    public List<Double> mStepWidth = new ArrayList();
    private TimerTask mTimerTask = null;
    private LantingTextView main_left;
    private LantingTextView main_mile;
    private LantingTextView main_right;
    /* access modifiers changed from: private */
    public BeBasTextView man_left_tv;
    /* access modifiers changed from: private */
    public BeBasTextView man_right_tv;
    int model = 0;
    private MyReceiver myReceiver;
    private List<GpsSatellite> numSatelliteList = new ArrayList();
    int onekm = 0;
    private List<SportsInfo> paceList = new ArrayList();
    /* access modifiers changed from: private */
    public long paceStartTime = 0;
    /* access modifiers changed from: private */
    public boolean paceState;
    long pace_result = 0;
    int pasu_time_value = 0;
    long pauseallstarttime = 0;
    private boolean perOneKmState;
    int receiveStep = 0;
    /* access modifiers changed from: private */
    public ArrayList<SportsInfo> sportsInfos = new ArrayList();
    long sports_result = 0;
    private final Listener statusListener = new Listener() {
        public void onGpsStatusChanged(int event) {
            String satelliteInfo = InfoMainTestFragment.this.updateGpsStatus(event, InfoMainTestFragment.this.locationManager.getGpsStatus(null));
        }
    };
    int stepFirst = 0;
    /* access modifiers changed from: private */
    public boolean timeState;
    private Timer timer = null;
    List<Trace> trace_points = new ArrayList();
    /* access modifiers changed from: private */
    public TextView tv_distance;
    /* access modifiers changed from: private */
    public TextView tv_hearRate;
    /* access modifiers changed from: private */
    public BeBasTextView tv_kcal;
    private TextView tv_showModel;
    /* access modifiers changed from: private */
    public TextView tv_watch_time;
    private boolean upDateState = false;
    int value2 = -1;
    int value2_times = 0;
    private View view;

    class InsertDateAsyncTask extends AsyncTask<Long, Void, Void> {
        InsertDateAsyncTask() {
        }

        /* access modifiers changed from: protected|varargs */
        public Void doInBackground(Long... params) {
            if (InfoMainTestFragment.this.dateEnought) {
                if (InfoMainTestFragment.this.sportsInfos.size() > 60) {
                    InfoMainTestFragment.this.dbUtil.deleteDate(((SportsInfo) InfoMainTestFragment.this.sportsInfos.get(0)).getId());
                    long startTime = ((SportsInfo) InfoMainTestFragment.this.sportsInfos.get(0)).getStartTime();
                    long getTime = ((SportsInfo) InfoMainTestFragment.this.sportsInfos.get(0)).getGetTime();
                    InfoMainTestFragment.this.dbUtil.deleteHVSDate(startTime, getTime);
                    InfoMainTestFragment.this.dbUtil.deletePaceDate(startTime, getTime);
                    InfoMainTestFragment.this.dbUtil.deleteTraceDate(startTime, getTime);
                }
                InfoMainTestFragment.this.saveDate();
            } else {
                long currenttime = InfoMainTestFragment.this.getCurrentTime();
                if (InfoMainTestFragment.this.HVSStartTime != 0) {
                    InfoMainTestFragment.this.dbUtil.deleteHVSDate(InfoMainTestFragment.this.HVSStartTime, currenttime);
                }
                if (InfoMainTestFragment.this.paceStartTime != 0) {
                    InfoMainTestFragment.this.dbUtil.deletePaceDate(InfoMainTestFragment.this.paceStartTime, currenttime);
                }
                if (InfoMainTestFragment.this.locationStartTime != 0) {
                    InfoMainTestFragment.this.dbUtil.deleteTraceDate(InfoMainTestFragment.this.locationStartTime, currenttime);
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            if (InfoMainTestFragment.this.sports_result <= 0 || InfoMainTestFragment.this.hvs_result <= -1 || InfoMainTestFragment.this.pace_result <= -1) {
                InfoMainTestFragment.this.sendMessage(9, 0);
            } else {
                Log.i(InfoMainTestFragment.TAG, "sports_result:" + InfoMainTestFragment.this.sports_result + "hvs_result:" + InfoMainTestFragment.this.hvs_result + "pace_result:" + InfoMainTestFragment.this.pace_result);
                InfoMainTestFragment.this.sendMessage(9, 0);
            }
            super.onPostExecute(result);
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        int i = 0;

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.contains("com.kct.spots.current_zero")) {
                if (action.contains("com.kct.spots.current_one")) {
                    InfoMainTestFragment.this.pauseTimer();
                } else if (action.contains("com.kct.spots.current_two")) {
                    InfoMainTestFragment.this.reStartTimer();
                } else if (action.equals("android.intent.action.TIME_TICK")) {
                    Message msg = new Message();
                    msg.what = 1;
                    InfoMainTestFragment.this.mHandler.sendMessage(msg);
                } else if (action.equals("com.kct.spots.timerEnd")) {
                    this.i++;
                    InfoMainTestFragment.this.dateEnought = intent.getBooleanExtra("end", false);
                    Log.i(InfoMainTestFragment.TAG, "dateEnought:" + InfoMainTestFragment.this.dateEnought + "---");
                    //System.putInt(InfoMainTestFragment.this.getActivity().getContentResolver(), "sport_status", 0);
                    InfoMainTestFragment.this.endTimer();
                } else if (!action.equals("com.kct.minifundo.step_count_changed_action")) {
                    if ("sendLoc".equals(action)) {
                        double Longitude = intent.getExtras().getDouble("Longitude", 0.0d);
                        double Latitude = intent.getExtras().getDouble("Latitude", 0.0d);
                        double Altitude = intent.getExtras().getDouble("Altitude", 0.0d);
                        Trace trace = new Trace(Latitude, Longitude);
                        trace.setAltitude(Altitude);
                        InfoMainTestFragment.this.trace_points.add(trace);
                        Trace myTrace = new Trace(Latitude, Longitude);
                        myTrace.setLatitude(new BigDecimal(trace.getLatitude()).setScale(6, 4).doubleValue());
                        myTrace.setLongitude(new BigDecimal(trace.getLongitude()).setScale(6, 4).doubleValue());
                        myTrace.setAltitude(new BigDecimal(trace.getAltitude()).setScale(1, 4).doubleValue());
                        myTrace.setItem_id(System.getInt(InfoMainTestFragment.this.getActivity().getContentResolver(), "model", 4));
                        if (InfoMainTestFragment.this.locationStartTime == 0) {
                            InfoMainTestFragment.this.locationStartTime = InfoMainTestFragment.this.getStartTime();
                        }
                        InfoMainTestFragment.this.dbUtil.insertTrace(myTrace, InfoMainTestFragment.this.locationStartTime, InfoMainTestFragment.this.getCurrentTime());
                        Log.e("kctsport", "currentTime=" + InfoMainTestFragment.this.getCurrentTime());
                    } else if ("\tcom.kct.sports.base".equals(action)) {
                        InfoMainTestFragment.this.pasu_time_value = intent.getIntExtra("pauseTime", 0);
                        Log.i(InfoMainTestFragment.TAG, "pasu_time_value:" + InfoMainTestFragment.this.pasu_time_value);
                    } else if ("com.kct.spots.gps_sigal".equals(action)) {
                        if (intent.getIntExtra("getSatellites", 0) >= 4) {
                            InfoMainTestFragment.this.gpsIsWeek = false;
                            return;
                        }
                        InfoMainTestFragment.this.gpsIsWeek = true;
                        if (!InfoMainTestFragment.this.gpsIsWeekMessage) {
                            InfoMainTestFragment.this.mHandler.sendEmptyMessage(11);
                            InfoMainTestFragment.this.gpsIsWeekMessage = true;
                        }
                    } else if ("com.kct.sports.provider.delete_hvs_and_pace".equals(action)) {
                        InfoMainTestFragment.this.mHandler.sendEmptyMessage(12);
                    }
                }
            }
        }
    }

    @SuppressLint("ValidFragment")
    public InfoMainTestFragment(SportModel mSportModel) {
        this.mSportModel = mSportModel;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        this.mResolver = this.mContext.getContentResolver();
        Utils.LOWBATT_STATUS = false;
        this.isLowPower = System.getInt(this.mContext.getContentResolver(), "lowPower", 0);
        if (this.isLowPower == 0) {
            //System.putInt(getActivity().getContentResolver(), "sport_status", 1);
        } else {
            //System.putInt(getActivity().getContentResolver(), "sport_status", 0);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.run_main, container, false);
        this.view.setFocusable(true);
        this.view.setFocusableInTouchMode(true);
        this.view.setOnKeyListener(this.backlistener);
        this.tv_distance = (TextView) this.view.findViewById(R.id.ditance);
        this.tv_hearRate = (TextView) this.view.findViewById(R.id.heartRate);
        this.tv_kcal = (BeBasTextView) this.view.findViewById(R.id.kcal);
        this.man_right_tv = (BeBasTextView) this.view.findViewById(R.id.man_right_tv);
        this.man_left_tv = (BeBasTextView) this.view.findViewById(R.id.man_left_tv);
        this.tv_watch_time = (TextView) this.view.findViewById(R.id.watch_time);
        this.tv_watch_time.setTypeface(NewApplication.mTypeface_date);
        this.tv_watch_time.setText(showWatchTime());
        this.tv_showModel = (TextView) this.view.findViewById(R.id.show_model);
        this.main_left = (LantingTextView) this.view.findViewById(R.id.main_left);
        this.main_right = (LantingTextView) this.view.findViewById(R.id.main_right);
        this.main_mile = (LantingTextView) this.view.findViewById(R.id.main_mile);
        this.iv_gps = (ImageView) this.view.findViewById(R.id.iv_gps);
        this.gps_count = (TextView) this.view.findViewById(R.id.gps_count);
        this.gps_count.setTypeface(NewApplication.mTypeface_date);
        this.dbUtil = new DBUtil(getActivity());
        this.sportsInfos = this.dbUtil.queryAllInDB();
        this.mChronometer = (Chronometer) this.view.findViewById(R.id.chronometer);
        state = 0;
        this.model = System.getInt(getActivity().getContentResolver(), "model", 4);
        if (this.isLowPower == 1 || this.model == 3) {
            this.iv_gps.setVisibility(View.GONE);
            this.gps_count.setVisibility(View.GONE);
        } else {
            this.locationManager = (LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            this.locationManager.addGpsStatusListener(this.statusListener);
        }
        initEvent();
        return this.view;
    }

    /* access modifiers changed from: private */
    public String updateGpsStatus(int event, GpsStatus status) {
        StringBuilder sb2 = new StringBuilder("");
        if (status == null) {
            sb2.append("0");
        } else if (event == 4) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            this.numSatelliteList.clear();
            int count = 0;
            int calibration = 0;
            while (it.hasNext() && count <= maxSatellites) {
                GpsSatellite s = (GpsSatellite) it.next();
                if (s.getSnr() != 0.0f) {
                    Log.e("getSnr", "   信噪比" + s.getSnr());
                    this.numSatelliteList.add(s);
                    count++;
                    if (s.getSnr() > 35.0f) {
                        calibration++;
                    }
                }
            }
            sb2.append(this.numSatelliteList.size());
        }
        if (this.numSatelliteList.size() > 3) {
            if (isAdded()) {
                this.iv_gps.setBackground(getResources().getDrawable(R.drawable.gps_on));
                this.gps_count.setTextColor(getResources().getColor(android.R.color.white));
            }
        } else if (isAdded()) {
            this.iv_gps.setBackground(getResources().getDrawable(R.drawable.gps_off));
            this.gps_count.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        this.gps_count.setText(this.numSatelliteList.size() + "");
        return sb2.toString();
    }

    public void onDestroyView() {
        if (this.myReceiver != null) {
            getActivity().unregisterReceiver(this.myReceiver);
        }
        super.onDestroyView();
    }

    public void onDestroy() {
        SportsBLL.getInstance().clearDate();
        if (this.locationManager != null) {
            this.locationManager.removeGpsStatusListener(this.statusListener);
        }
        super.onDestroy();
    }

    private void initEvent() {
        this.myReceiver = new MyReceiver();
        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction("com.kct.spots.current_zero");
        this.intentFilter.addAction("com.kct.spots.current_one");
        this.intentFilter.addAction("com.kct.spots.current_two");
        this.intentFilter.addAction("android.intent.action.TIME_TICK");
        this.intentFilter.addAction("com.kct.spots.timerEnd");
        this.intentFilter.addAction("sendLoc");
        this.intentFilter.addAction("com.kct.minifundo.step_count_changed_action");
        this.intentFilter.addAction("com.kct.spots.gps_sigal");
        this.intentFilter.addAction("\tcom.kct.sports.base");
        this.intentFilter.addAction("com.kct.sports.provider.delete_hvs_and_pace");
        getActivity().registerReceiver(this.myReceiver, this.intentFilter);
        this.mChronometer.setBase(SystemClock.elapsedRealtime());
        this.mChronometer.setTypeface(NewApplication.mTypeface_date);
        this.mChronometer.setOnChronometerTickListener(new OnChronometerTickListener() {
            public void onChronometerTick(Chronometer chronometer) {
                Log.i(InfoMainTestFragment.TAG, "state=" + InfoMainTestFragment.state);
                if (!Utils.LOWBATT_STATUS) {
                    if (InfoMainTestFragment.value == -1) {
                        InfoMainTestFragment.value = Math.abs(((int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) - InfoMainTestFragment.this.pauseallstarttime)) / 1000);
                    } else if (InfoMainTestFragment.state == 3) {
                        InfoMainTestFragment.value = 0;
                        InfoMainTestFragment.state = 0;
                    } else if (InfoMainTestFragment.state == 0) {
                        InfoMainTestFragment.value = Math.abs(((int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) - InfoMainTestFragment.this.pauseallstarttime)) / 1000);
                    } else if (InfoMainTestFragment.state != 1 && InfoMainTestFragment.state == 2) {
                        InfoMainTestFragment.value = Math.abs(((int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) - InfoMainTestFragment.this.pauseallstarttime)) / 1000);
                    }
                    Log.e("sportTime", "runTime=" + InfoMainTestFragment.value + "SystemClock.elapsedRealtime()=" + SystemClock.elapsedRealtime() + "--chronometer.getBase()=" + chronometer.getBase() + "--pauseallstarttime=" + InfoMainTestFragment.this.pauseallstarttime);
                    InfoMainTestFragment.this.sendMessage(2, 0);
                    InfoMainTestFragment.this.sendMessage(3, InfoMainTestFragment.value);
                    InfoMainTestFragment.this.sendMessage(4, 0);
                    InfoMainTestFragment.this.sendMessage(5, 0);
                    InfoMainTestFragment.this.sendMessage(6, 0);
                    InfoMainTestFragment.this.sendMessage(10, 0);
                    if (InfoMainTestFragment.value % 60 == 0 && InfoMainTestFragment.value > 0) {
                        if (!InfoMainTestFragment.this.HVSStartTimeState) {
                            InfoMainTestFragment.this.HVSStartTime = InfoMainTestFragment.this.getStartTime();
                            InfoMainTestFragment.this.HVSStartTimeState = true;
                        }
                        InfoMainTestFragment.this.inFiveMinute += 300;
                        InfoMainTestFragment.this.sendMessage(8, 0);
                        InfoMainTestFragment.this.isFiveMin = true;
                    }
                    InfoMainTestFragment.this.sendCurrentSecond();
                    chronometer.setText(ShowStringUitls.formatTimer(InfoMainTestFragment.value));
                    Log.i(InfoMainTestFragment.TAG, "value" + InfoMainTestFragment.value + "inFiveMinute" + InfoMainTestFragment.this.inFiveMinute);
                    if (InfoMainTestFragment.value % 2 == 0) {
                        try {
                            String[] ss = InfoMainTestFragment.this.mSportModel.getViewIndexes()[0];
                            InfoMainTestFragment.this.tv_distance.setText(InfoMainTestFragment.this.mSportModel.getValueFromViewIndexes(ss[0]));
                            InfoMainTestFragment.this.man_left_tv.setText(InfoMainTestFragment.this.mSportModel.getValueFromViewIndexes(ss[1]));
                            InfoMainTestFragment.this.man_right_tv.setText(InfoMainTestFragment.this.mSportModel.getValueFromViewIndexes(ss[2]));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.model = System.getInt(getActivity().getContentResolver(), "model", 4);
        startTimer();
        setDateName();
        this.tv_showModel.setText(ShowStringUitls.getModel(getActivity(), this.model));
    }

    public void sendCurrentSecond() {
        Intent intent = new Intent("com.kct.sports.current_second_time");
        intent.putExtra("currentSecond", value);
        Log.e("gjy20", "value=" + value);
        getActivity().sendBroadcast(intent);
    }

    public void setDateName() {
        String[] ss = this.mSportModel.getViewIndexes()[0];
        Log.e("gjy22", "mSportModel=" + this.mSportModel + "   ss[]=" + ss[0]);
        this.main_mile.setText(this.mSportModel.getNameFromViewIndexes(ss[0]));
        this.main_left.setText(this.mSportModel.getNameFromViewIndexes(ss[1]));
        this.main_right.setText(this.mSportModel.getNameFromViewIndexes(ss[2]));
    }

    public void startActivity(int which, int date) {
        Intent intent = new Intent(getActivity(), RemindActivity.class);
        intent.putExtra("which", which);
        intent.putExtra("current_date", date);
        getActivity().startActivity(intent);
    }

    public void isHeartRateAlarm() {
        int heartRateDate = System.getInt(getActivity().getContentResolver(), "heartRateDate", 0);
        int targetHeartRateCheck = System.getInt(getActivity().getContentResolver(), "heartRateCheck", 0);
        int heatRate = getSportsBLL().getHeartRate();
        Log.i(TAG, "heatRate:" + heatRate + "targetHeartRateCheck:" + targetHeartRateCheck + "heartRateDate:" + heartRateDate);
        if (targetHeartRateCheck == 1 && heatRate >= heartRateDate && heartRateDate != 0) {
            this.mHandler.removeMessages(10);
            this.heartRateSate = true;
            startActivity(2, heartRateDate);
        }
    }

    public void isTimeAlarm(int time) {
        int mTargerTime = System.getInt(getActivity().getContentResolver(), "TargerTime", 0);
        int targetTimeCheck = System.getInt(getActivity().getContentResolver(), "targetTimeCheck", 0);
        Log.i(TAG, "mTargerTime" + mTargerTime);
        if (targetTimeCheck == 1 && mTargerTime == time && mTargerTime != 0) {
            Log.i(TAG, "mTargerTime2：" + mTargerTime);
            this.mHandler.removeMessages(3);
            this.timeState = true;
            startActivity(3, mTargerTime);
        }
    }

    public void isDistanceAlarm() {
        double distance = getSportsBLL().getDistance() * 0.001d;
        int targetDistanceCheck = System.getInt(getActivity().getContentResolver(), "targetDistanceCheck", 0);
        int targetDistanceDate = System.getInt(getActivity().getContentResolver(), "TargerDistanceDate", 0);
        double targetDistance = Double.valueOf((double) targetDistanceDate).doubleValue();
        if (targetDistanceCheck == 1 && distance >= targetDistance) {
            this.distanceState = true;
            this.mHandler.removeMessages(4);
            startActivity(4, targetDistanceDate);
        }
    }

    public void isPaceAlarm() {
        int paceCheck = System.getInt(getActivity().getContentResolver(), "paceCheck", 0);
        int pace = getSportsBLL().getPace();
        int paceAlarm = System.getInt(getActivity().getContentResolver(), "paceAlarmDate", 0);
        if (paceCheck == 1 && pace >= paceAlarm && paceAlarm > 0) {
            this.paceState = true;
            this.mHandler.removeMessages(5);
            startActivity(5, paceAlarm);
        }
    }

    public void isOneKmAlarm() {
        double distance = getSportsBLL().getDistance();
        int perKmCheck = System.getInt(getActivity().getContentResolver(), "perKmCheck", 0);
        if (perKmCheck == 1) {
            this.perOneKmState = true;
            startActivity(1, PreferenceUtils.getPrefInt(this.mContext, "km_counts", 1));
            Log.i(TAG, "perKmCheck:" + perKmCheck + "distance" + distance);
        }
    }

    public void startTimer() {
        this.mChronometer.start();
    }

    public void pauseTimer() {
        if (this.lastpausestarttime == 0) {
            this.lastpausestarttime = SystemClock.elapsedRealtime();
        }
        this.mChronometer.stop();
        this.value2 = value;
        this.value2_times++;
        Log.i(TAG, "value2:" + this.value2 + "value2_times:" + this.value2_times);
        state = 1;
        Intent intent2 = new Intent("com.kct.spots.timerPause");
        intent2.putExtra("value2", this.value2);
        getActivity().sendBroadcast(intent2);
    }

    public void reStartTimer() {
        if (!Utils.LOWBATT_STATUS) {
            this.pauseallstarttime += SystemClock.elapsedRealtime() - this.lastpausestarttime;
            this.pasu_time_value = (int) (this.pauseallstarttime / 1000);
        }
        Log.e("sport_pasutime", "1=" + this.pasu_time_value);
        this.lastpausestarttime = 0;
        state = 2;
        this.mChronometer.start();
    }

    public void endTimer() {
        if (!(Utils.LOWBATT_STATUS || (this.lastpausestarttime == 0 && this.value2_times == 0))) {
            this.pauseallstarttime += SystemClock.elapsedRealtime() - this.lastpausestarttime;
            this.pasu_time_value = (int) (this.pauseallstarttime / 1000);
        }
        Log.e("sport_pasutime", "2=" + this.pasu_time_value);
        state = 3;
        this.mChronometer.stop();
        Log.e("sportTime", "endTime=" + value + "SystemClock.elapsedRealtime()=" + SystemClock.elapsedRealtime() + "base=" + this.mChronometer.getBase());
        new InsertDateAsyncTask().execute(new Long[0]);
        getActivity().stopService(new Intent(getActivity(), DateProviderService.class));
    }

    public void saveDate() {
        Log.e("sport", "步数\n" + getSportsBLL().getStepCount() + "最大步幅\n" + getMaxStepWidth() + "最小步幅\n" + getMinStepWidth() + "平均步幅\n" + getSportsBLL().getAveStepWidth() + "模式\n" + this.model + "距离\n" + getSportsBLL().getDistance() + "心率\n" + getSportsBLL().getHeartRate() + "max心率\n" + getMaxHeartRate() + "min心率\n" + getMinHeartRate() + "配速\n" + getSportsBLL().getPace() + "最高配速对应的公里\n" + getMaxMapFirstKey() + "最高配速对应的公里值\n" + getMaxMapFirstValue() + "最低配速对应的公里\n" + getMinMapFirstKey() + "最低配速对应的公里值\n" + getMinMapFirstValue() + "累计上升\n" + getSportsBLL().getAltitudeUp() + "累计下降\n" + getSportsBLL().getAltitudeDown() + "摄氧量\n" + getSportsBLL().needO2Max() + "训练强度\n" + getSportsBLL().getTrainingIntensity());
        if (this.locationStartTime == 0) {
            this.locationStartTime = this.paceStartTime;
        }
        if (this.locationStartTime == 0) {
            this.locationStartTime = getStartTime();
        }
        if (this.HVSStartTime == 0) {
            this.HVSStartTime = this.locationStartTime;
        }
        if (this.paceStartTime == 0) {
            this.paceStartTime = this.locationStartTime;
        }
        long currenttime = getCurrentTime();
        SportsInfo sportsInfo = new SportsInfo();
        sportsInfo.setGetTime(currenttime);
        sportsInfo.setStartTime(this.HVSStartTime);
        sportsInfo.setSpeed(getMaxSpeed());
        sportsInfo.setStepSpeed(getMaxStepSpeed());
        sportsInfo.setHeatRate(getMaxHeartRate());
        this.dbUtil.insertHVS(sportsInfo);
        sportsInfo.setSpeed(getMinSpeed());
        sportsInfo.setStepSpeed(getMinStepSpeed());
        sportsInfo.setHeatRate(getMinHeartRate());
        this.dbUtil.insertHVS(sportsInfo);
        sportsInfo.setSpeed(-1);
        sportsInfo.setStepSpeed(-1);
        sportsInfo.setHeatRate(-1);
        Trace trace = new Trace(1,1);
        trace.setLatitude(-1.0d);
        trace.setLongitude(-1.0d);
        trace.setAltitude(-1.0d);
        SportsInfo pace = new SportsInfo();
        pace.setPace(getSportsBLL().getPace());
        pace.setGetTime(currenttime);
        pace.setStartTime(this.paceStartTime);
        pace.setModel(this.model);
        this.dbUtil.insertPace(pace);
        this.dbUtil.updateTraceList(this.locationStartTime, currenttime);
        this.dbUtil.updateHVSList(this.HVSStartTime, currenttime);
        this.dbUtil.updatePaceList(this.paceStartTime, currenttime);
        this.sports_result = this.dbUtil.insertDate(this.locationStartTime, getSportsBLL().getStepCount(), getMaxStepWidth(), getMinStepWidth(), (getMaxStepWidth() + getMinStepWidth()) / 2.0d, (getMaxSpeed() + getMinSpeed()) / 2.0d, getMaxSpeed(), getMinSpeed(), getSportsBLL().getHorizontalSpeed(), getSportsBLL().getVerticalSpeed(), getSportsBLL().getStepSpeed(), getMaxStepSpeed(), getMinStepSpeed(), this.model, currenttime, (long) value, this.pasu_time_value, this.value2_times, Double.parseDouble(this.mSportModel.getMileage()) * 1000.0d, getAveHeartRate(), getMaxHeartRate(), getMinHeartRate(), getSportsBLL().getAltitude(), Double.parseDouble(this.mSportModel.getCalorie()), getSportsBLL().getPace(), getMaxMapFirstKey(), getMaxMapFirstValue(), getMinMapFirstKey(), getMinMapFirstValue(), getSportsBLL().getAltitudeUp(), getSportsBLL().getAltitudeDown(), getSportsBLL().needO2Max(), getSportsBLL().getTrainingIntensity(), getAveStepSpeed());
        Log.i(TAG, "result" + this.sports_result + "hvs_result" + this.hvs_result + "pace_result" + this.pace_result);
    }

    public int getMaxMapFirstKey() {
        Map<Integer, Integer> resultMap = new TreeMap();
        int i = getSportsBLL().getMaxPaceAndKm().size() - 1;
        Map<Integer, Integer> mapKey = new HashMap();
        for (Entry<Integer, Integer> entry : getSportsBLL().getMaxPaceAndKm().entrySet()) {
            mapKey.put(Integer.valueOf(i), (Integer) entry.getValue());
            i--;
        }
        if (mapKey == null || mapKey.isEmpty()) {
            return 0;
        }
        return ((Integer) mapKey.get(Integer.valueOf(0))).intValue();
    }

    public int getMaxMapFirstValue() {
        Map<Integer, Integer> resultMap = new TreeMap();
        int i = getSportsBLL().getMaxPaceAndKm().size() - 1;
        Map<Integer, Integer> mapValue = new HashMap();
        for (Entry<Integer, Integer> entry : getSportsBLL().getMaxPaceAndKm().entrySet()) {
            mapValue.put(Integer.valueOf(i), (Integer) entry.getKey());
            i--;
        }
        if (mapValue == null || mapValue.isEmpty()) {
            return 0;
        }
        return ((Integer) mapValue.get(Integer.valueOf(0))).intValue();
    }

    public int getMinMapFirstKey() {
        int i = getSportsBLL().getMinPaceAndKm().size() - 1;
        Map<Integer, Integer> mapKey = new HashMap();
        for (Entry<Integer, Integer> entry : getSportsBLL().getMinPaceAndKm().entrySet()) {
            mapKey.put(Integer.valueOf(i), (Integer) entry.getValue());
            i--;
        }
        if (mapKey == null || mapKey.isEmpty()) {
            return 0;
        }
        return ((Integer) mapKey.get(Integer.valueOf(0))).intValue();
    }

    public int getMinMapFirstValue() {
        int i = getSportsBLL().getMinPaceAndKm().size() - 1;
        Map<Integer, Integer> mapValue = new HashMap();
        for (Entry<Integer, Integer> entry : getSportsBLL().getMinPaceAndKm().entrySet()) {
            mapValue.put(Integer.valueOf(i), (Integer) entry.getKey());
            i--;
        }
        if (mapValue == null || mapValue.isEmpty()) {
            return 0;
        }
        return ((Integer) mapValue.get(Integer.valueOf(0))).intValue();
    }

    public double getMaxStepWidth() {
        if (this.mStepWidth.size() <= 0) {
            return 0.0d;
        }
        double maxStepWidth = ((Double) this.mStepWidth.get(0)).doubleValue();
        for (int i = 0; i < this.mStepWidth.size(); i++) {
            if (maxStepWidth < ((Double) this.mStepWidth.get(i)).doubleValue()) {
                maxStepWidth = ((Double) this.mStepWidth.get(i)).doubleValue();
            }
        }
        if (maxStepWidth - getMinStepWidth() > 100.0d) {
            maxStepWidth = getMinStepWidth();
        }
        return Utils.decimalTo2(maxStepWidth, 2);
    }

    public double getMinStepWidth() {
        if (this.mStepWidth.size() <= 0) {
            return 0.0d;
        }
        double minStepWidth = ((Double) this.mStepWidth.get(0)).doubleValue();
        int i = 0;
        while (i < this.mStepWidth.size()) {
            if (minStepWidth > ((Double) this.mStepWidth.get(i)).doubleValue() && ((Double) this.mStepWidth.get(i)).doubleValue() != 0.0d) {
                minStepWidth = ((Double) this.mStepWidth.get(i)).doubleValue();
            }
            i++;
        }
        return Utils.decimalTo2(minStepWidth, 2);
    }

    public int getMaxStepSpeed() {
        if (this.mStepSpeed.size() <= 0) {
            return 0;
        }
        int maxStepSpeed = ((Integer) this.mStepSpeed.get(0)).intValue();
        for (int i = 0; i < this.mStepSpeed.size(); i++) {
            if (maxStepSpeed < ((Integer) this.mStepSpeed.get(i)).intValue()) {
                maxStepSpeed = ((Integer) this.mStepSpeed.get(i)).intValue();
            }
        }
        return maxStepSpeed;
    }

    public int getAveStepSpeed() {
        if (this.mStepSpeed.size() <= 0) {
            return 0;
        }
        int aveStepSpeed = 0;
        for (int i = 0; i < this.mStepSpeed.size(); i++) {
            aveStepSpeed += ((Integer) this.mStepSpeed.get(i)).intValue();
        }
        return aveStepSpeed / this.mStepSpeed.size();
    }

    public int getMinStepSpeed() {
        if (this.mStepSpeed.size() <= 0) {
            return 0;
        }
        int minStepSpeed = ((Integer) this.mStepSpeed.get(0)).intValue();
        for (int i = 0; i < this.mStepSpeed.size(); i++) {
            if (minStepSpeed > ((Integer) this.mStepSpeed.get(i)).intValue()) {
                minStepSpeed = ((Integer) this.mStepSpeed.get(i)).intValue();
            }
        }
        return minStepSpeed;
    }

    public double getMaxSpeed() {
        if (this.mSpeed.size() <= 0) {
            return 0.0d;
        }
        double maxSpeed = ((Double) this.mSpeed.get(0)).doubleValue();
        for (int i = 0; i < this.mSpeed.size(); i++) {
            if (maxSpeed < ((Double) this.mSpeed.get(i)).doubleValue()) {
                maxSpeed = ((Double) this.mSpeed.get(i)).doubleValue();
            }
        }
        return Utils.decimalTo2(maxSpeed, 2);
    }

    public double getMinSpeed() {
        if (this.mSpeed.size() <= 0) {
            return 0.0d;
        }
        double minSpeed = ((Double) this.mSpeed.get(0)).doubleValue();
        for (int i = 0; i < this.mSpeed.size(); i++) {
            if (minSpeed > ((Double) this.mSpeed.get(i)).doubleValue()) {
                minSpeed = ((Double) this.mSpeed.get(i)).doubleValue();
            }
        }
        return Utils.decimalTo2(minSpeed, 2);
    }

    public int getMaxHeartRate() {
        if (this.mHeartRate.size() <= 0) {
            return 0;
        }
        int minHeartRate = ((Integer) this.mHeartRate.get(0)).intValue();
        for (int i = 0; i < this.mHeartRate.size(); i++) {
            if (minHeartRate < ((Integer) this.mHeartRate.get(i)).intValue()) {
                minHeartRate = ((Integer) this.mHeartRate.get(i)).intValue();
            }
        }
        return minHeartRate;
    }

    public int getMinHeartRate() {
        if (this.mHeartRate.size() <= 0) {
            return 0;
        }
        int minHeartRate = ((Integer) this.mHeartRate.get(0)).intValue();
        for (int i = 0; i < this.mHeartRate.size(); i++) {
            if (minHeartRate > ((Integer) this.mHeartRate.get(i)).intValue()) {
                minHeartRate = ((Integer) this.mHeartRate.get(i)).intValue();
            }
        }
        return minHeartRate;
    }

    public int getAveHeartRate() {
        int i;
        List<SportsInfo> sportsInfos = this.dbUtil.getHVS(this.HVSStartTime, getCurrentTime());
        List<Integer> mHeartRate = new ArrayList();
        for (i = 0; i < sportsInfos.size(); i++) {
            int heatRate = ((SportsInfo) sportsInfos.get(i)).getHeatRate();
            if (heatRate > 0) {
                mHeartRate.add(Integer.valueOf(heatRate));
            }
        }
        if (mHeartRate.size() <= 0) {
            return 0;
        }
        int toalHeart = 0;
        for (i = 0; i < mHeartRate.size(); i++) {
            toalHeart += ((Integer) mHeartRate.get(i)).intValue();
        }
        Log.e("aveherart", "toalHeart=" + toalHeart + "  ..size= " + mHeartRate.size());
        return toalHeart / mHeartRate.size();
    }

    public long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public long getStartTime() {
        Calendar calendar = Calendar.getInstance();
        if (this.allstarttime == 0) {
            this.allstarttime = calendar.getTimeInMillis();
        }
        return this.allstarttime;
    }

    public String showWatchTime() {
        return new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date(getCurrentTime()));
    }

    public void sendMessage(int what, int arg) {
        Message message = new Message();
        message.what = what;
        message.arg1 = arg;
        this.mHandler.sendMessage(message);
    }

    public void gpsSwitch(int mode) {
        Intent intent = new Intent("com.android.settings.location.MODE_CHANGING");
        intent.putExtra("CURRENT_MODE", mCurrentMode);
        intent.putExtra("NEW_MODE", mode);
        this.mContext.sendBroadcast(intent, "android.permission.WRITE_SECURE_SETTINGS");
        Secure.putInt(this.mResolver, "location_mode", mode);
    }
}
