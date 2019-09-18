package com.kct.sports.utils;

import com.amap.api.maps2d.model.LatLng;

public class KalmanFilter {
    private int MIN_ACCURACY = 1;
    private double latitude;
    private double longitude;
    private long timeStamp = 0;
    private float variance = 0.0f;

    public LatLng progressWithPoint(LatLng latLng, float speed, long newTimeStamp) {
        if (this.variance < 0.0f) {
            this.variance = -1.0f;
            return latLng;
        }
        if (speed < 1.0f) {
            speed = 1.0f;
        }
        long duration = newTimeStamp - this.timeStamp;
        if (duration > 0) {
            this.variance += ((((float) duration) * speed) * speed) / 1000.0f;
            this.timeStamp = newTimeStamp;
        }
        float k = this.variance / (this.variance + ((float) (this.MIN_ACCURACY * this.MIN_ACCURACY)));
        this.latitude += ((double) k) * (latLng.latitude - this.latitude);
        this.longitude += ((double) k) * (latLng.longitude - this.longitude);
        this.variance = (1.0f - k) * this.variance;
        return new LatLng(this.latitude, this.longitude);
    }
}
