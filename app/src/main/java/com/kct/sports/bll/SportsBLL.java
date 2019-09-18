package com.kct.sports.bll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings.System;
import android.util.Log;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.utils.PreferenceUtils;
import com.kct.sports.utils.Utils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SportsBLL implements ISportsBLL {
    /* access modifiers changed from: private|static */
    public static double realDistace = 0.0d;
    private static volatile ISportsBLL sportsBLL = null;
    public static int step = 0;
    /* access modifiers changed from: private */
    public double altitude;
    private double aveSpeed;
    /* access modifiers changed from: private */
    public int currentSecondTime;
    private double distance;
    /* access modifiers changed from: private */
    public boolean gpsIsWeek;
    /* access modifiers changed from: private */
    public int heartRate;
    private boolean isDebug;
    /* access modifiers changed from: private */
    public boolean isFirstAltitude;
    private boolean isFirstSpeed;
    int km_counts;
    /* access modifiers changed from: private */
    public double latitude;
    /* access modifiers changed from: private */
    public double longtitude;
    /* access modifiers changed from: private */
    public Context mContext;
    private ArrayList<Integer> mCurrentPace;
    /* access modifiers changed from: private */
    public double mFirstaltitude;
    /* access modifiers changed from: private */
    public ArrayList<Integer> mHeartRate;
    /* access modifiers changed from: private */
    public Map<Integer, Integer> mMap;
    private List<Double> mSpeed;
    private int oneKilometer;
    private int oneKilometer2;
    /* access modifiers changed from: private */
    public int oneKilometerTime;
    /* access modifiers changed from: private */
    public int readTime;
    /* access modifiers changed from: private */
    public int recordingTime;
    private ArrayList<SportsInfo> sportsInfos;
    private BroadcastReceiver sportsStepCountReceiver;
    private ArrayList<Double> totalDown;
    private double totalDown_sum;
    /* access modifiers changed from: private */
    public ArrayList<Integer> totalStep;
    /* access modifiers changed from: private */
    public ArrayList<Double> totalUp;
    private double totalUp_sum;

    static class AscComparator implements Comparator<Integer> {
        AscComparator() {
        }

        public int compare(Integer o1, Integer o2) {
            if (o1.intValue() > o2.intValue()) {
                return -1;
            }
            if (o1 == o2) {
                return 0;
            }
            return 1;
        }
    }

    static class DesComparator implements Comparator<Integer> {
        DesComparator() {
        }

        public int compare(Integer o1, Integer o2) {
            if (o1.intValue() > o2.intValue()) {
                return 1;
            }
            if (o1 == o2) {
                return 0;
            }
            return -1;
        }
    }

    public static ISportsBLL getInstance() {
        if (sportsBLL == null) {
            synchronized (ISportsBLL.class) {
                if (sportsBLL == null) {
                    sportsBLL = new SportsBLL();
                }
            }
        }
        return sportsBLL;
    }

    public void initParam(Context context) {
        if (!isInit()) {
            this.mContext = context;
            registerBroadcastReceiver();
        }
    }

    private void registerBroadcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("com.kct.minifundo.step_count_changed_action");
        myIntentFilter.addAction("com.kct.minifundo.heartRate_changed_action");
        myIntentFilter.addAction("com.kct.spots.timerPause");
        myIntentFilter.addAction("sendLoc");
        myIntentFilter.addAction("com.kct.spots.gps_sigal");
        myIntentFilter.addAction("com.kct.sports.current_second_time");
        this.mContext.registerReceiver(this.sportsStepCountReceiver, myIntentFilter);
    }

    private void unRegisterBroadcastReceiver() {
        if (this.sportsStepCountReceiver != null) {
            this.mContext.unregisterReceiver(this.sportsStepCountReceiver);
        }
    }

    public boolean isInit() {
        return this.mContext != null;
    }

    public SportsBLL() {
        this.mContext = null;
        this.sportsInfos = new ArrayList();
        this.isDebug = true;
        this.isFirstSpeed = true;
        this.gpsIsWeek = false;
        this.heartRate = 0;
        this.oneKilometer = 0;
        this.oneKilometerTime = 0;
        this.readTime = 0;
        this.recordingTime = 0;
        this.currentSecondTime = 0;
        this.distance = 0.0d;
        this.altitude = 0.0d;
        this.mFirstaltitude = 0.0d;
        this.isFirstAltitude = true;
        this.longtitude = 0.0d;
        this.latitude = 0.0d;
        this.totalUp_sum = 0.0d;
        this.totalDown_sum = 0.0d;
        this.aveSpeed = 0.0d;
        this.totalUp = new ArrayList();
        this.totalDown = new ArrayList();
        this.totalStep = new ArrayList();
        this.mHeartRate = new ArrayList();
        this.mMap = new TreeMap();
        this.mSpeed = new ArrayList();
        this.mCurrentPace = new ArrayList();
        this.km_counts = 0;
        this.oneKilometer2 = 0;
        this.sportsStepCountReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String actionName = intent.getAction().toString();
                if (actionName.equals("com.kct.minifundo.step_count_changed_action")) {
                    SportsBLL.step = intent.getIntExtra("getStepCounts", 0);
                    SportsBLL.this.totalStep.add(Integer.valueOf(SportsBLL.step));
                    Log.e("kctsports", " 收到步数：" + SportsBLL.step);
                } else if (actionName.contains("com.kct.minifundo.heartRate_changed_action")) {
                    SportsBLL.this.heartRate = intent.getIntExtra("heartRate", 0);
                    if (SportsBLL.this.heartRate != 0) {
                        SportsBLL.this.mHeartRate.add(Integer.valueOf(SportsBLL.this.heartRate));
                    }
                    Log.e("kctsports", " 收到心率：" + SportsBLL.this.heartRate);
                } else if (actionName.contains("com.kct.spots.timerPause")) {
                    SportsBLL.this.recordingTime = intent.getIntExtra("value2", 0);
                    if (SportsBLL.this.getOneKm()) {
                        SportsBLL.this.readTime = PreferenceUtils.getPrefInt(SportsBLL.this.mContext, "oneKilometerTime", 0);
                        int whichKm = PreferenceUtils.getPrefInt(SportsBLL.this.mContext, "km_counts", 0);
                        SportsBLL.this.oneKilometerTime = SportsBLL.this.recordingTime - SportsBLL.this.readTime;
                        Log.e("kctsports", " 1公里时间：recordingTime----" + SportsBLL.this.recordingTime + "readtime---" + SportsBLL.this.readTime);
                        PreferenceUtils.setPrefInt(SportsBLL.this.mContext, "oneKilometerTime", SportsBLL.this.oneKilometerTime);
                        if (SportsBLL.this.oneKilometerTime != 0) {
                            SportsBLL.this.mMap.put(Integer.valueOf(SportsBLL.this.oneKilometerTime), Integer.valueOf(whichKm));
                        }
                    }
                    Log.e("kctsports", "时间：" + SportsBLL.this.recordingTime + "海拔：" + SportsBLL.this.altitude);
                } else if (actionName.contains("com.kct.sports.current_second_time")) {
                    SportsBLL.this.currentSecondTime = intent.getIntExtra("currentSecond", 0);
                    Log.e("gjy20", "currentSecondTime====" + SportsBLL.this.currentSecondTime);
                } else if (actionName.contains("sendLoc")) {
                    SportsBLL.this.altitude = intent.getDoubleExtra("Altitude", 0.0d);
                    SportsBLL.this.longtitude = intent.getDoubleExtra("Longitude", 0.0d);
                    SportsBLL.this.latitude = intent.getDoubleExtra("Latitude", 0.0d);
                    SportsBLL.realDistace = Math.abs(intent.getDoubleExtra("realDistance", 0.0d));
                    if (SportsBLL.this.isFirstAltitude && SportsBLL.this.latitude != 0.0d) {
                        SportsBLL.this.mFirstaltitude = SportsBLL.this.altitude;
                        SportsBLL.this.isFirstAltitude = false;
                    }
                    SportsBLL.this.totalUp.add(Double.valueOf(SportsBLL.this.altitude));
                } else if (actionName.contains("com.kct.spots.gps_sigal")) {
                    int gps_satellites = intent.getIntExtra("getSatellites", 0);
                    Log.e("gjyamap", "卫星个数" + gps_satellites);
                    if (gps_satellites >= 4) {
                        SportsBLL.this.gpsIsWeek = false;
                    } else {
                        SportsBLL.this.gpsIsWeek = true;
                    }
                }
            }
        };
        this.isDebug = true;
        this.isFirstSpeed = true;
        this.isFirstAltitude = true;
        this.gpsIsWeek = false;
        step = 0;
        this.heartRate = 0;
        this.oneKilometer = 0;
        this.oneKilometerTime = 0;
        this.readTime = 0;
        this.recordingTime = 0;
        this.currentSecondTime = 0;
        this.km_counts = 0;
        this.oneKilometer2 = 0;
        this.distance = 0.0d;
        realDistace = 0.0d;
        this.altitude = 0.0d;
        this.longtitude = 0.0d;
        this.latitude = 0.0d;
        this.totalUp_sum = 0.0d;
        this.totalDown_sum = 0.0d;
        this.aveSpeed = 0.0d;
        this.mFirstaltitude = 0.0d;
        this.totalUp.clear();
        this.totalDown.clear();
        this.totalStep.clear();
        this.mHeartRate.clear();
        this.mMap.clear();
        this.mSpeed.clear();
        this.mCurrentPace.clear();
        if (this.isDebug) {
        }
    }

    public double getCurrentSecondTime() {
        this.recordingTime = this.currentSecondTime;
        return (double) this.currentSecondTime;
    }

    public int getStepCount() {
        if (System.getInt(this.mContext.getContentResolver(), "model", 4) == 11) {
            return (int) (realDistace / getStepWidth(1));
        }
        return step;
    }

    public int getHeartRate() {
        Log.e("new", "heartRate~~" + this.heartRate);
        return this.heartRate;
    }

    public double getCalorie() {
        Log.e("kctsports", "体重" + getWeight() + "卡路里：" + (((((double) getWeight()) * getDistance()) * 0.001d) * 1.036d));
        return (((double) getWeight()) * Utils.decimalTo2(getDistance() * 0.001d, 2)) * 1.036d;
    }

    public int getPace() {
        if (getDistance() == 0.0d || getDistance() == 0.0d) {
            return 0;
        }
        double getDistance = Utils.decimalTo2(getDistance() * 0.001d, 2);
        if (getDistance == 0.0d) {
            return 0;
        }
        int pace = (int) (((double) this.currentSecondTime) / getDistance);
        this.mCurrentPace.add(Integer.valueOf(pace));
        return pace;
    }

    public int getAvePace() {
        if (getDistance() == 0.0d || this.mCurrentPace.size() < 1) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < this.mCurrentPace.size(); i++) {
            total += ((Integer) this.mCurrentPace.get(i)).intValue();
        }
        int avePace = total / this.mCurrentPace.size();
        Log.e("49", "avePace=" + avePace + " total=" + total + " mSize=" + this.mCurrentPace.size());
        return avePace;
    }

    public boolean getOneKm() {
        this.oneKilometer = ((int) getDistance()) - this.oneKilometer2;
        Log.e("kctsports", "是否1公里：" + this.oneKilometer + "distance:" + this.distance + "oneKilometer2:" + this.oneKilometer2);
        if (this.oneKilometer < 1000) {
            return false;
        }
        this.km_counts++;
        this.oneKilometer2 = (int) getDistance();
        PreferenceUtils.setPrefInt(this.mContext, "km_counts", this.km_counts);
        return true;
    }

    public double getSpeed() {
        if (this.currentSecondTime <= 0 || getDistance() == 0.0d) {
            return 0.0d;
        }
        double speed = (getDistance() / 1000.0d) / (((double) this.currentSecondTime) / 3600.0d);
        Log.e("kctsports", "速度= getDistance()" + getDistance() + "recordingTime" + ((double) this.recordingTime));
        Log.e("gjyspeed", "speed:" + speed + "BigDecimal:" + new BigDecimal(speed).setScale(1, 4).doubleValue());
        return Utils.decimalTo2(speed, 2);
    }

    public double getAveSpeed() {
        if (this.isFirstSpeed) {
            this.aveSpeed = getSpeed();
            this.isFirstSpeed = false;
            Log.e("gjyspeed", "aveSpeed:" + this.aveSpeed);
            return (double) new BigDecimal(this.aveSpeed).setScale(0, 4).intValue();
        }
        double aveSpeed2 = (this.aveSpeed + getSpeed()) / 2.0d;
        Log.e("gjyspeed", "aveSpeed:" + this.aveSpeed + "getSpeed():" + getSpeed() + "aveSpeed2:" + aveSpeed2);
        this.aveSpeed = aveSpeed2;
        return new BigDecimal(this.aveSpeed).setScale(1, 4).doubleValue();
    }

    public int getStepSpeed() {
        Log.e("gjy20", "currentSecondTime=" + this.currentSecondTime);
        int stepSpeed = 0;
        if (this.currentSecondTime == 0) {
            return 0;
        }
        if (this.currentSecondTime >= 60) {
            int mStep = (int) (getDistance() / getStepWidth(1));
            stepSpeed = step / ((this.currentSecondTime / 60) + 1);
            Log.e("kctsports", "stepSpeed=" + stepSpeed + "step=" + step);
        }
        return stepSpeed;
    }

    public double getAltitude() {
        double totalAltitude = 0.0d;
        for (int i = 1; i < this.totalUp.size(); i++) {
            totalAltitude += ((Double) this.totalUp.get(i)).doubleValue();
        }
        if (totalAltitude == 0.0d) {
            return 0.0d;
        }
        return new BigDecimal(totalAltitude / ((double) this.totalUp.size())).setScale(1, 4).doubleValue();
    }

    public double getDistance() {
        if (System.getInt(this.mContext.getContentResolver(), "model", 4) == 11) {
            //System.putFloat(this.mContext.getContentResolver(), "distance", Float.parseFloat(String.valueOf(realDistace)));
            return realDistace;
        }
        this.distance = ((double) step) * getStepWidth(1);
        //System.putFloat(this.mContext.getContentResolver(), "distance", Float.parseFloat(String.valueOf(this.distance)));
        Log.e("kctsports", "步数：" + step + "步幅：" + getStepWidth(1));
        return this.distance;
    }

    public double getAltitudeUp() {
        double up = 0.0d;
        double down = 0.0d;
        for (int i = 1; i < this.totalUp.size(); i++) {
            double c = Double.valueOf(((Double) this.totalUp.get(i)).doubleValue()).doubleValue() - Double.valueOf(((Double) this.totalUp.get(i - 1)).doubleValue()).doubleValue();
            if (c > 0.0d) {
                up += c;
            } else if (c < 0.0d) {
                down += c;
            }
        }
        if (up == 0.0d) {
            return 0.0d;
        }
        try {
            return Double.valueOf(String.valueOf(up).substring(0, 4)).doubleValue();
        } catch (Exception e) {
            return up;
        }
    }

    public double getAltitudeDown() {
        double up = 0.0d;
        double down = 0.0d;
        for (int i = 1; i < this.totalUp.size(); i++) {
            double c = Double.valueOf(((Double) this.totalUp.get(i)).doubleValue()).doubleValue() - Double.valueOf(((Double) this.totalUp.get(i - 1)).doubleValue()).doubleValue();
            if (c > 0.0d) {
                up += c;
            } else if (c < 0.0d) {
                down += c;
            }
        }
        if (down == 0.0d) {
            return 0.0d;
        }
        try {
            return Double.valueOf(String.valueOf(down).substring(0, 4)).doubleValue();
        } catch (Exception e) {
            return down;
        }
    }

    public double getHorizontalSpeed() {
        if (this.currentSecondTime == 0) {
            return 0.0d;
        }
        return (getHorizontalDistance() / ((double) this.currentSecondTime)) / 3.6d;
    }

    public double getVerticalSpeed() {
        if (this.currentSecondTime == 0) {
            return 0.0d;
        }
        double vSpeed = (getVerticalDistance() / ((double) this.currentSecondTime)) / 3.6d;
        if (Math.abs(vSpeed) < 0.01d) {
            return 0.0d;
        }
        return vSpeed;
    }

    public double getVerticalDistance() {
        String verticalDistance = String.valueOf(getAltitudeDown() + getAltitudeUp());
        if (verticalDistance != null && verticalDistance.length() > 4) {
            return Double.valueOf(verticalDistance.substring(0, 4)).doubleValue();
        }
        if (verticalDistance == null || verticalDistance.length() > 4) {
            return 0.0d;
        }
        return Double.valueOf(verticalDistance).doubleValue();
    }

    public double getHorizontalDistance() {
        Float firstLongtitude = Float.valueOf(PreferenceUtils.getPrefFloat(this.mContext, "longitude", 0.0f));
        Float firstLatitude = Float.valueOf(PreferenceUtils.getPrefFloat(this.mContext, "latitude", 0.0f));
        Log.e("123", "firstLongtitude" + firstLongtitude + "firstLatitude" + firstLatitude);
        if (firstLongtitude.floatValue() == 0.0f || firstLatitude.floatValue() == 0.0f || this.latitude == 0.0d) {
            return 0.0d;
        }
        float horizontalDistance = AMapUtils.calculateLineDistance(new LatLng((double) firstLatitude.floatValue(), (double) firstLongtitude.floatValue()), new LatLng(this.latitude, this.longtitude));
        try {
            String horizontalDistance_st = String.valueOf(horizontalDistance);
            Log.e("gjy121", "horizontalDistance_st:" + horizontalDistance_st + "horizontalDistance_st.substring(0, 4)::" + horizontalDistance_st.substring(0, 4));
            return Double.valueOf(horizontalDistance_st.substring(0, 4)).doubleValue();
        } catch (Exception e) {
            return (double) horizontalDistance;
        }
    }

    public double needO2Max() {
        return Utils.decimalTo2((((double) this.currentSecondTime) * 0.056d) + 4.42d, 2);
    }

    public String getTrainingIntensity() {
        int intensity = 220 - getAge();
        int tHeartRate = 0;
        for (Integer intValue : this.mHeartRate) {
            tHeartRate += intValue.intValue();
        }
        if (this.mHeartRate.size() < 1) {
            return "--";
        }
        int currentHeartRate = tHeartRate / this.mHeartRate.size();
        if (((double) intensity) * 0.6d <= ((double) currentHeartRate) && ((double) currentHeartRate) < ((double) intensity) * 0.7d) {
            return "B";
        }
        if (((double) currentHeartRate) < ((double) intensity) * 0.6d) {
            return "C";
        }
        return "A";
    }

    public void clearDate() {
        sportsBLL = null;
        unRegisterBroadcastReceiver();
    }

    public Map<Integer, Integer> getMaxPaceAndKm() {
        return sortDesMapByKey(this.mMap);
    }

    public Map<Integer, Integer> getMinPaceAndKm() {
        return sortAscMapByKey(this.mMap);
    }

    public static Map<Integer, Integer> sortAscMapByKey(Map<Integer, Integer> map) {
        if (map == null) {
            return null;
        }
        Map<Integer, Integer> sortMap = new TreeMap(new AscComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public static Map<Integer, Integer> sortDesMapByKey(Map<Integer, Integer> map) {
        if (map == null) {
            return null;
        }
        Map<Integer, Integer> sortMap = new TreeMap(new DesComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public double getAveStepWidth() {
        int step = (((int) getDistance()) * 100) / ((int) getStepWidth(0));
        if (step == 0) {
            return 0.0d;
        }
        return getDistance() / ((double) step);
    }

    public int getWeight() {
        return System.getInt(this.mContext.getContentResolver(), "userWeight", 60);
    }

    public int getHeight() {
        return System.getInt(this.mContext.getContentResolver(), "userHeight", 172);
    }

    public double getStepWidth(int type) {
        double stride = 0.0d;
        switch (System.getInt(this.mContext.getContentResolver(), "model", 4)) {
            case 1:
                stride = (((double) getHeight()) * 0.45d) * 0.01d;
                break;
            case 2:
                stride = (((double) getHeight()) * 0.5d) * 0.01d;
                break;
            case 3:
                stride = (((double) getHeight()) * 0.6d) * 0.01d;
                break;
            case 4:
                stride = (((double) getHeight()) * 0.4d) * 0.01d;
                break;
            case 5:
                stride = (((double) getHeight()) * 0.52d) * 0.01d;
                break;
            case 6:
                stride = (((double) getHeight()) * 0.57d) * 0.01d;
                break;
            case 7:
                stride = (((double) getHeight()) * 0.57d) * 0.01d;
                break;
            case 11:
                stride = (((double) getHeight()) * 0.45d) * 0.01d;
                break;
        }
        if (type == 0) {
            return 100.0d * stride;
        }
        return (((double) getHeight()) * 0.45d) * 0.01d;
    }

    public int getAge() {
        return System.getInt(this.mContext.getContentResolver(), "userAge", 22);
    }
}
