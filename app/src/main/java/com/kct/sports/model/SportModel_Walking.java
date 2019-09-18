package com.kct.sports.model;

public class SportModel_Walking extends SportModel {
    public SportModel_Walking() {
        this.model = 1;
        String[][] r0 = new String[3][];
        r0[0] = new String[]{"mileage", "stepcount", "pace"};
        r0[1] = new String[]{"mileage", "time", "calorie"};
        r0[2] = new String[]{"heartrate", "speed", "averagespeed"};
        this.viewIndexes = r0;
    }
}
