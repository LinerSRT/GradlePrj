package com.kct.sports.OtherFragment;

import android.Manifest;
import android.app.Activity;
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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.support.v4.app.ActivityCompat;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kct.sports.R;
import com.kct.sports.Trace.LocationSvc;
import com.kct.sports.base.NewApplication;
import com.kct.sports.utils.PreferenceUtils;
import com.kct.sports.view.PanningView;
import com.kct.sports.view.StatusIcon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchGPSActivity extends Activity {
    private static int mCurrentMode = 0;
    /* access modifiers changed from: private */
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    SearchGPSActivity.this.stopLocServer();
                    SearchGPSActivity.this.panningView.stopPanning();
                    SearchGPSActivity.this.pass_tv.setVisibility(View.VISIBLE);
                    SearchGPSActivity.this.mGPS.setVisibility(View.VISIBLE);
                    Toast.makeText(SearchGPSActivity.this, R.string.find_gps_fail, Toast.LENGTH_SHORT).show();
                    return;
                default:
                    return;
            }
        }
    };
    private IntentFilter intentFilter;
    /* access modifiers changed from: private */
    public LocationManager locationManager;
    private SearchGPSActivity mContext;
    /* access modifiers changed from: private */
    public StatusIcon mGPS;
    private TextView mGps_count;
    private TextView mGps_count_calibration;
    private ContentResolver mResolver;
    private int model;
    private MyReceiver myReceiver;
    private List<GpsSatellite> numSatelliteList = new ArrayList();
    /* access modifiers changed from: private */
    public PanningView panningView;
    /* access modifiers changed from: private */
    public TextView pass_tv;
    private int state = 4;
    /* access modifiers changed from: private|final */
    public final Listener statusListener = new Listener() {
        public void onGpsStatusChanged(int event) {
            if (ActivityCompat.checkSelfPermission(SearchGPSActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String satelliteInfo = SearchGPSActivity.this.updateGpsStatus(event, SearchGPSActivity.this.locationManager.getGpsStatus(null));
        }
    };
    WakeLock wakeLock = null;

    public class MyReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("sendLoc")) {
                SearchGPSActivity.this.handler.removeMessages(0);
                ////System.putInt(SearchGPSActivity.this.getContentResolver(), "isOpen", 1);
                SearchGPSActivity.this.startActivity(new Intent(SearchGPSActivity.this, ReadyStartActivity.class));
                SearchGPSActivity.this.finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(1);
        setContentView(R.layout.activity_sports_main);
        this.mContext = this;
        this.mResolver = this.mContext.getContentResolver();
        NewApplication.getInstance().addActivity(this);
        sendBroadcast(new Intent("com.kct.sport.start.action"));
        ////System.putInt(getContentResolver(), "sportsEnable", 1);
        ////System.putInt(getContentResolver(), "isRunning", 1);
        this.model = getIntent().getIntExtra("model", 4);
        ////System.putInt(getContentResolver(), "model", this.model);
        Log.e("qw", "model:" + this.model);
        this.myReceiver = new MyReceiver();
        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction("sendLoc");
        registerReceiver(this.myReceiver, this.intentFilter);
        isGpsOpen();
        if (readSystem() < 10) {
            Toast.makeText(this.mContext, getResources().getString(R.string.memory_hint), Toast.LENGTH_SHORT).show();
        }
        if (System.getInt(this.mContext.getContentResolver(), "lowPower", 0) == 0) {
            if (this.model == 3) {
                startActivity(new Intent(this, ReadyStartActivity.class));
            } else {
                initView();
                initEvent();
                startLocSvc();
                initAlarm();
            }
        } else if (this.model == 11) {
            initView();
            initEvent();
            startLocSvc();
            initAlarm();
        } else {
            startActivity(new Intent(this, ReadyStartActivity.class));
        }
    }

    private void initAlarm() {
        this.wakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(1, "MyWakeLock_sports2:f");
        this.wakeLock.acquire();
    }

    private void startLocSvc() {
        this.panningView.startPanning();
        this.state = 2;
//        //System.putInt(getContentResolver(), "isOpen", 0);
        startService(new Intent(this, LocationSvc.class));
        sendBroadcast(new Intent("com.kct.sports.start.run"));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        stopLocServer();
        ////System.putInt(getContentResolver(), "sportsEnable", 0);
        if (this.wakeLock != null) {
            this.wakeLock.release();
        }
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.myReceiver != null) {
            unregisterReceiver(this.myReceiver);
        }
        if (this.locationManager != null) {
            this.locationManager.removeGpsStatusListener(this.statusListener);
        }
        super.onDestroy();
    }

    private void initView() {
        this.mGPS = (StatusIcon) findViewById(R.id.search_or_pass_gps);
        this.panningView = (PanningView) findViewById(R.id.panningView);
        this.pass_tv = (TextView) findViewById(R.id.pass_tv);
        this.mGps_count = (TextView) findViewById(R.id.gps_count);
        this.mGps_count_calibration = (TextView) findViewById(R.id.gps_count_calibration);
        this.pass_tv.setText(getResources().getString(R.string.pass));
        this.locationManager = (LocationManager) this.mContext.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.locationManager.addGpsStatusListener(this.statusListener);
    }

    private long readSystem() {
        StatFs sf = new StatFs(Environment.getRootDirectory().getPath());
        long blockSize = (long) sf.getBlockSize();
        long blockCount = (long) sf.getBlockCount();
        long availCount = (long) sf.getAvailableBlocks();
        Log.e("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + ((blockSize * blockCount) / 1024) + "KB");
        Log.e("", "可用的block数目：:" + availCount + ",可用大小:" + ((availCount * blockSize) / 1024) + "KB");
        return ((availCount * blockSize) / 1024) / 1024;
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
            this.mGps_count_calibration.setText(calibration + "");
        }
        this.mGps_count.setText(this.numSatelliteList.size() + "");
        for (int i = 0; i < this.numSatelliteList.size(); i++) {
            Log.e("getSnr", "   信噪比" + i + "====" + ((GpsSatellite) this.numSatelliteList.get(i)).getSnr());
        }
        return sb2.toString();
    }

    private void initEvent() {
        this.mGPS.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (SearchGPSActivity.this.locationManager != null) {
                    SearchGPSActivity.this.locationManager.removeGpsStatusListener(SearchGPSActivity.this.statusListener);
                }
                if (SearchGPSActivity.this.wakeLock != null) {
                    SearchGPSActivity.this.wakeLock.release();
                }
                SearchGPSActivity.this.panningView.stopPanning();
                SearchGPSActivity.this.sendBroadcast(new Intent("com.kct.sport.jump"));
                SearchGPSActivity.this.startActivity(new Intent(SearchGPSActivity.this, ReadyStartActivity.class));
                SearchGPSActivity.this.finish();
            }
        });
    }

    private boolean isGpsOpen() {
        if (((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled("gps")) {
            PreferenceUtils.setPrefBoolean(this.mContext, "gpsSwitch", true);
            return true;
        }
        PreferenceUtils.setPrefBoolean(this.mContext, "gpsSwitch", false);
        gpsSwitch(1);
        return false;
    }

    public void gpsSwitch(int mode) {
        Intent intent = new Intent("com.android.settings.location.MODE_CHANGING");
        intent.putExtra("CURRENT_MODE", mCurrentMode);
        intent.putExtra("NEW_MODE", mode);
//        this.mContext.sendBroadcast(intent, "android.permission.WRITE_SECURE_SETTINGS");
        //Secure.putInt(this.mResolver, "location_mode", mode);
    }

    /* access modifiers changed from: private */
    public void stopLocServer() {
        stopService(new Intent(this, LocationSvc.class));
        boolean isSwitch = PreferenceUtils.getPrefBoolean(this.mContext, "gpsSwitch", false);
        gpsSwitch(0);
    }
}
