package com.kct.sports.base;

public class Trace {
    double Latitude;
    double Longitude;
    double altitude;
    int item_id;

    public Trace(double latitude, double longitude) {
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public double getLatitude() {
        return this.Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return this.Longitude;
    }

    public void setLongitude(double longitude) {
        this.Longitude = longitude;
    }

    public int getItem_id() {
        return this.item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Trace)) {
            return false;
        }
        Trace trace = (Trace) obj;
        return trace.altitude == this.altitude && trace.Latitude == this.Latitude && trace.Longitude == this.Longitude;
    }
}
