package com.kct.sports.Trace;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener;
import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.kct.sports.R;
import com.kct.sports.Run.InfoMainTestFragment;
import com.kct.sports.Trace.model.GetElevation;
import com.kct.sports.Trace.model.GetElevation.Data;
import com.kct.sports.bll.SportsBLL;
import com.kct.sports.utils.KalmanFilter;
import com.kct.sports.utils.LogUtil;
import com.kct.sports.utils.PreferenceUtils;
import com.kct.sports.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;

public class LocationSvc extends Service implements AMapLocationListener {
    private AlarmManager alarm = null;
    private Intent alarmIntent = null;
    private PendingIntent alarmPi = null;
    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.e("Kctsport", "alarmReceiver=");
            if (intent.getAction().equals("LOCATION")) {
                if (LocationSvc.this.mLocationClient != null) {
                    LocationSvc.this.mLocationClient.startLocation();
                } else if (intent.getAction().equals("com.kct.spots.timerPause")) {
                    LocationSvc.this.isStopStart = Boolean.valueOf(true);
                } else if (intent.getAction().equals("com.kct.minifundo.step_count_changed_action")) {
                    LocationSvc.this.step = intent.getIntExtra("getStepCounts", 0);
                }
            }
            if (intent.getAction().equals("com.kct.sport.jump")) {
                LocationSvc.this.mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
                LocationSvc.this.startLocation();
            }
        }
    };
    private double distance = 0.0d;
    private double elevation_date = 0.0d;
    boolean flaglaststep = false;
    private Boolean isFirst = Boolean.valueOf(true);
    private Boolean isGetElevation = Boolean.valueOf(false);
    /* access modifiers changed from: private */
    public Boolean isStopStart = Boolean.valueOf(false);
    int laststep = 0;
    private int mGpsCount = 0;
    private Intent mIntent;
    KJHttp mKJHttp;
    private double mLatSum = 0.0d;
    double mLat_avg = 0.0d;
    private OnLocationChangedListener mListener;
    public AMapLocationClient mLocationClient = null;
    private double mLongSum = 0.0d;
    double mLong_avg = 0.0d;
    private int mLtCount = 0;
    public AMapLocationClientOption mOption = null;
    private List<Double> mZbList = new ArrayList();
    List<LatLng> points = new ArrayList();
    List<LatLng> points2 = new ArrayList();
    List<LatLng> points3 = new ArrayList();
    /* access modifiers changed from: private */
    public int step = 0;
    private double tMile = 0.0d;

    public void onCreate() {
        initLocation();
        startLocation();
        initAlarm();
        this.mKJHttp = new KJHttp();
        Log.d("gjyamap", "定位服务开启");
        super.onCreate();
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    private void initLocation() {
        this.points.add(new LatLng(22.5423d, 113.9514d));
        this.mLocationClient = new AMapLocationClient(getApplicationContext());
        this.mLocationClient.setLocationListener(this);
        this.mOption = new AMapLocationClientOption();
        this.mOption.setLocationMode(AMapLocationMode.Device_Sensors);
        this.mOption.setHttpTimeOut(30000);
        this.mOption.setInterval(2000);
        this.mOption.setNeedAddress(true);
    }

    /* access modifiers changed from: private */
    public void startLocation() {
        this.mLocationClient.setLocationOption(this.mOption);
        this.mLocationClient.startLocation();
    }

    private void stopLocation() {
        this.mLocationClient.stopLocation();
        if (this.alarm != null) {
            this.alarm.cancel(this.alarmPi);
        }
    }

    public void deactivate() {
        this.mListener = null;
        if (this.mLocationClient != null) {
            this.mLocationClient.stopLocation();
            this.mLocationClient.onDestroy();
        }
        this.mLocationClient = null;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    public void onDestroy() {
        if (this.alarmReceiver != null) {
            unregisterReceiver(this.alarmReceiver);
        }
        stopLocation();
        deactivate();
        stopSelf();
        Log.d("gjyamap", "onDestroy");
        super.onDestroy();
    }

    public void onLocationChanged(AMapLocation loc) {
        LogUtil.e("wenhai", "Location=" + loc);
        if (loc != null) {
            Log.e("gjyamap", Utils.getLocationStr(loc) + loc.getSatellites());
            double mLatitude = loc.getLatitude();
            double mLongitude = loc.getLongitude();
            Log.e("gjy417", "mLatitude=" + mLatitude + "mLongitude=" + mLongitude);
            if (loc.getErrorCode() != 0) {
                this.mLtCount++;
                if (this.mLtCount % 30 == 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.gps_hint), Toast.LENGTH_LONG).show();
                }
                return;
            } else if (1 != InfoMainTestFragment.state) {
                double mAltitude;
                if (this.isGetElevation.booleanValue()) {
                    mAltitude = this.elevation_date;
                } else {
                    mAltitude = loc.getAltitude();
                }
                if (!this.flaglaststep) {
                    this.flaglaststep = true;
                    this.laststep = SportsBLL.step;
                }
                this.mGpsCount++;
                final LatLng mlatLng = new KalmanFilter().progressWithPoint(new LatLng(mLatitude, mLongitude), loc.getSpeed(), System.currentTimeMillis());
                if (mAltitude == 0.0d) {
                    String url = "https://ditu.google.cn/maps/api/elevation/json?locations=lat,lng&key=AIzaSyBkdSYZ6lZN6BtXFxc7qR4EZVJja3fsIy4".replace("lat", mLatitude + "").replace("lng", mLongitude + "");
                    Log.e("hrj", url);
                    final AMapLocation aMapLocation = loc;
                    this.mKJHttp.get(url, new HttpCallBack() {
                        public void onSuccess(String t) {
                            super.onSuccess(t);
                            GetElevation mGetElevation = (GetElevation) new Gson().fromJson(t, GetElevation.class);
                            if ("OK".equals(mGetElevation.getStatus())) {
                                LocationSvc.this.onSuccessAltitude(aMapLocation, mlatLng.latitude, mlatLng.longitude, ((Data) mGetElevation.getResults().get(0)).getElevation());
                            }
                        }
                    });
                }
                onSuccessAltitude(loc, mlatLng.latitude, mlatLng.longitude, mAltitude);
            } else {
                return;
            }
        }
        Log.d("gjyamap", "定位失败, ErrCode:" + loc.getErrorCode() + ", errInfo:" + loc.getErrorInfo());
    }

    /* access modifiers changed from: private */
    public void onSuccessAltitude(AMapLocation loc, double mLatitude, double mLongitude, double mAltitude) {
        if (this.isFirst.booleanValue()) {
            PreferenceUtils.setPrefFloat(this, "altitude", (float) mAltitude);
            PreferenceUtils.setPrefFloat(this, "latitude", (float) mLatitude);
            PreferenceUtils.setPrefFloat(this, "longitude", (float) mLongitude);
            this.isFirst = Boolean.valueOf(false);
            LatLng point = new LatLng(mLatitude, mLongitude);
            this.points.add(point);
            this.points2.add(point);
            this.mIntent = new Intent("sendLoc");
            this.mIntent.putExtra("Longitude", mLongitude);
            this.mIntent.putExtra("Latitude", mLatitude);
            this.mIntent.putExtra("Altitude", mAltitude);
            sendBroadcast(this.mIntent);
        } else {
            LatLng nextPoint = new LatLng(mLatitude, mLongitude);
            this.points2.add(nextPoint);
            if (this.isStopStart.booleanValue()) {
                this.isStopStart = Boolean.valueOf(false);
                this.points.add(nextPoint);
            } else {
                this.distance = (double) Math.abs(AMapUtils.calculateLineDistance((LatLng) this.points.get(this.points.size() - 1), nextPoint));
            }
            Log.e("Kctsport", "distance==" + this.distance + "   step== " + SportsBLL.step);
            if (this.distance > 0.0d) {
                if (this.distance >= 150.0d) {
                    this.points.add(nextPoint);
                    return;
                }
                this.points.add(nextPoint);
                this.tMile += this.distance;
                Log.e("Kctsport", "tMile=" + this.tMile + "   Longitude=" + mLongitude + "  mLatitude=" + mLatitude);
                this.mIntent = new Intent("sendLoc");
                this.mIntent.putExtra("Longitude", mLongitude);
                this.mIntent.putExtra("Latitude", mLatitude);
                this.mIntent.putExtra("Altitude", mAltitude);
                this.mIntent.putExtra("realDistance", this.tMile);
                sendBroadcast(this.mIntent);
            }
            if (loc.getProvider().equalsIgnoreCase("gps")) {
                Intent gpsIntent = new Intent("com.kct.spots.gps_sigal");
                gpsIntent.putExtra("getSatellites", loc.getSatellites());
                sendBroadcast(gpsIntent);
            }
        }
    }

    private void initAlarm() {
        this.alarmIntent = new Intent();
        IntentFilter ift = new IntentFilter();
        this.alarmPi = PendingIntent.getBroadcast(this, 0, this.alarmIntent, 0);
        this.alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction("LOCATION");
        filter.addAction("com.kct.sport.jump");
        filter.addAction("com.kct.spots.timerPause");
        registerReceiver(this.alarmReceiver, filter);
    }
}
