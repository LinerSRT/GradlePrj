package com.kct.sports.provider;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import java.util.List;

public class StepCountService extends Service {
    public boolean first = true;
    /* access modifiers changed from: private */
    public Sensor mHeartRateSensor;
    /* access modifiers changed from: private */
    public SensorManager mSensorManager;
    private StepSensorListen mStepSensorListen;
    public int step = 0;

    private class StepSensorListen implements SensorEventListener {
        private List<Sensor> mSensors;
        int mystep;

        /* synthetic */ StepSensorListen(StepCountService this$0, StepSensorListen stepSensorListen) {
            this();
        }

        private StepSensorListen() {
            this.mystep = 0;
        }

        /* access modifiers changed from: private */
        public void startListen(Context context) {
            this.mSensors = StepCountService.this.mSensorManager.getSensorList(19);
            for (int i = 0; i < this.mSensors.size(); i++) {
            }
            StepCountService.this.mHeartRateSensor = StepCountService.this.mSensorManager.getDefaultSensor(21);
            Log.e("kctsports", "mHeartRateSensor____" + StepCountService.this.mHeartRateSensor.getName());
            StepCountService.this.mSensorManager.registerListener(this, StepCountService.this.mHeartRateSensor, 0);
        }

        public void stopListen() {
            StepCountService.this.mSensorManager.unregisterListener(this);
        }

        public void onSensorChanged(SensorEvent event) {
            Log.e("kctsports", " onSensorChanged：event.sensor.getType()=" + event.sensor.getType());
            switch (event.sensor.getType()) {
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
        Log.e("kctsports", "启动服务");
    }

    public void onDestroy() {
        this.mStepSensorListen.stopListen();
        Log.e("kctsports", "服务销毁");
        super.onDestroy();
    }

    public boolean stopService(Intent name) {
        return super.stopService(name);
    }
}
