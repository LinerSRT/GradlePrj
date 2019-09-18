package com.kct.sports.bll;

import android.content.Context;
import java.util.Map;

public interface ISportsBLL {
    void clearDate();

    double getAltitude();

    double getAltitudeDown();

    double getAltitudeUp();

    int getAvePace();

    double getAveSpeed();

    double getAveStepWidth();

    double getCalorie();

    double getCurrentSecondTime();

    double getDistance();

    int getHeartRate();

    double getHorizontalDistance();

    double getHorizontalSpeed();

    Map<Integer, Integer> getMaxPaceAndKm();

    Map<Integer, Integer> getMinPaceAndKm();

    boolean getOneKm();

    int getPace();

    double getSpeed();

    int getStepCount();

    int getStepSpeed();

    String getTrainingIntensity();

    double getVerticalDistance();

    double getVerticalSpeed();

    void initParam(Context context);

    boolean isInit();

    double needO2Max();
}
