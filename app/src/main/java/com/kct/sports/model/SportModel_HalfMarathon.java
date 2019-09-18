package com.kct.sports.model;

import com.kct.sports.utils.Utils;

public class SportModel_HalfMarathon extends SportModel {
    public SportModel_HalfMarathon() {
        this.model = 6;
        String[][] r0 = new String[4][];
        r0[0] = new String[]{"remainingmileage", "stepcount", "pace"};
        r0[1] = new String[]{"remainingmileage", "time", "pace"};
        r0[2] = new String[]{"heartrate", "calorie", "speed"};
        r0[3] = new String[]{"cadence", "averagepace", "averagespeed"};
        this.viewIndexes = r0;
    }

    public String getRemainingmileage() {
        double a = 21098.0d - getSportsBLL().getDistance();
        if (a < 0.0d) {
            a = 0.0d;
        }
        return String.valueOf(Utils.decimalTo2(0.001d * a, 2));
    }
}
