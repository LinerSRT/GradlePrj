package com.kct.sports.provider;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class DateProviderService extends Service {
    public boolean IsHeartFirst = true;
    public boolean first = true;
    public int mCount = 0;
    /* access modifiers changed from: private */
    public Sensor mHeartRateSensor;
    /* access modifiers changed from: private */
    public SensorManager mSensorManager;
    private StepSensorListen mStepSensorListen;
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("broadcast.kct.step")) {
                int step = intent.getIntExtra("step", -1);
                DateProviderService.this.myStep += step;
                Intent step_intent = new Intent("com.kct.minifundo.step_count_changed_action");
                step_intent.putExtra("getStepCounts", DateProviderService.this.myStep);
                DateProviderService.this.sendBroadcast(step_intent);
                Log.e("119", "step:" + step + "myStep:" + DateProviderService.this.myStep);
            }
        }
    };
    public int myStep = 0;

    private class StepSensorListen implements SensorEventListener {
        /* synthetic */ StepSensorListen(DateProviderService this$0, StepSensorListen stepSensorListen) {
            this();
        }

        private StepSensorListen() {
        }

        /* access modifiers changed from: private */
        public void startListen(Context context) {
            DateProviderService.this.mHeartRateSensor = DateProviderService.this.mSensorManager.getDefaultSensor(21);
            DateProviderService.this.mSensorManager.registerListener(this, DateProviderService.this.mHeartRateSensor, 0);
        }

        public void stopListen() {
            DateProviderService.this.mSensorManager.unregisterListener(this);
        }

        public void onSensorChanged(SensorEvent event) {
            Log.e("new", " onSensorChanged：event.sensor.getType()=" + event.sensor.getType());
            switch (event.sensor.getType()) {
                case 21:
                    int heartRate = (int) event.values[0];
                    Log.e("new", " 心率1：heartRate:" + heartRate);
                    if (DateProviderService.this.IsHeartFirst) {
                        if (heartRate != 0) {
                            DateProviderService.this.IsHeartFirst = false;
                            DateProviderService.this.mCount = 0;
                        } else {
                            DateProviderService dateProviderService = DateProviderService.this;
                            dateProviderService.mCount++;
                            if (DateProviderService.this.mCount >= 2) {
                                DateProviderService.this.IsHeartFirst = false;
                            }
                        }
                    }
                    if (!DateProviderService.this.IsHeartFirst && heartRate >= 0 && heartRate < 200) {
                        Intent intent = new Intent("com.kct.minifundo.heartRate_changed_action");
                        intent.putExtra("heartRate", heartRate);
                        DateProviderService.this.sendBroadcast(intent);
                        Log.e("new", " 心率2：heartRate:" + heartRate);
                        break;
                    }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.mStepSensorListen = new StepSensorListen(this, null);
        this.mStepSensorListen.startListen(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("broadcast.kct.step");
        registerReceiver(this.myReceiver, filter);
        Log.e("new", "DateProviderService启动服务");
    }

    public void onDestroy() {
        this.mStepSensorListen.stopListen();
        if (this.myReceiver != null) {
            unregisterReceiver(this.myReceiver);
        }
        Log.e("new", "服务销毁");
        super.onDestroy();
    }

    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
