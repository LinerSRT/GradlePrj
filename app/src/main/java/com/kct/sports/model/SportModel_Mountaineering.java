package com.kct.sports.model;

public class SportModel_Mountaineering extends SportModel {
    public SportModel_Mountaineering() {
        this.model = 4;
        String[][] r0 = new String[5][];
        r0[0] = new String[]{"cadence", "altitude", "verticalvelocity"};
        r0[1] = new String[]{"time", "horizontaldisplacement", "verticaldisplacement"};
        r0[2] = new String[]{"averagepace", "heartrate", "calorie"};
        r0[3] = new String[]{"altitude", "cumulativeclimb", "cumulativedecrease"};
        r0[4] = new String[]{"horizontalvelocity", "verticalvelocity", "averagespeed"};
        this.viewIndexes = r0;
    }
}
