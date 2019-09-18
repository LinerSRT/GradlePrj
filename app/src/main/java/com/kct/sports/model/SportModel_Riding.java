package com.kct.sports.model;

public class SportModel_Riding extends SportModel {
    public SportModel_Riding() {
        this.model = 11;
        String[][] r0 = new String[4][];
        r0[0] = new String[]{"mileage", "averagepace", "pace"};
        r0[1] = new String[]{"mileage", "time", "pace"};
        r0[2] = new String[]{"heartrate", "calorie", "speed"};
        r0[3] = new String[]{"cadence", "averagepace", "averagespeed"};
        this.viewIndexes = r0;
    }
}
