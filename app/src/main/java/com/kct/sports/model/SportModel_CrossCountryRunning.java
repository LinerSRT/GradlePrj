package com.kct.sports.model;

public class SportModel_CrossCountryRunning extends SportModel {
    public SportModel_CrossCountryRunning() {
        this.model = 5;
        String[][] r0 = new String[5][];
        r0[0] = new String[]{"mileage", "altitude", "pace"};
        r0[1] = new String[]{"mileage", "time", "pace"};
        r0[2] = new String[]{"stepcount", "heartrate", "calorie"};
        r0[3] = new String[]{"altitude", "cumulativeclimb", "cumulativedecrease"};
        r0[4] = new String[]{"horizontalvelocity", "verticalvelocity", "averagespeed"};
        this.viewIndexes = r0;
    }
}
