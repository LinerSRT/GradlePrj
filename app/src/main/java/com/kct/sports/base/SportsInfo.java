package com.kct.sports.base;

import com.kct.sports.utils.StringUtils;
import java.io.Serializable;

public class SportsInfo implements Serializable {
    private int BestPace;
    private int BestPace_distance;
    private int LowestPace;
    private int LowestPace_distance;
    private double altitude;
    private double aveStepWidth;
    private int averagePace;
    private int averageSpeed;
    private int averageStepSpeed;
    private double distance;
    private long getTime;
    private int heatRate;
    private double horizontalSpeed;
    private int id;
    private double kcal;
    private int maxHeatRate;
    private double maxSpeed;
    private int maxStepSpeed;
    private double maxStepWidth;
    private int minHeatRate;
    private double minSpeed;
    private int minStepSpeed;
    private double minStepWidth;
    private int model;
    private double needO2Max;
    private int pace;
    private int pauseTime;
    private int pauseTimes;
    private double speed;
    private long startTime;
    private int stepCount;
    private int stepSpeed;
    private double sumDown;
    private double sumUp;
    private long timeTake;
    private String trainingIntensity;
    private double verticalSpeed;

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getTimeTake() {
        return this.timeTake;
    }

    public void setTimeTake(long timeTake) {
        this.timeTake = timeTake;
    }

    public long getGetTime() {
        return this.getTime;
    }

    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getHeatRate() {
        return this.heatRate;
    }

    public void setHeatRate(int heatRate) {
        this.heatRate = heatRate;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getKcal() {
        return this.kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public int getPace() {
        return this.pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = (double) speed;
    }

    public int getStepSpeed() {
        return this.stepSpeed;
    }

    public void setStepSpeed(int stepSpeed) {
        this.stepSpeed = stepSpeed;
    }

    public void setAverageStepSpeed(int averageStepSpeed) {
        this.averageStepSpeed = averageStepSpeed;
    }

    public void setVerticalSpeed(double verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public String toString() {
        return StringUtils.getStrDateTime(this.getTime) + "|" + String.valueOf(this.stepCount) + "|" + String.valueOf(this.distance) + "|" + String.valueOf(this.heatRate) + "|" + String.valueOf(this.altitude) + "|" + String.valueOf(this.kcal);
    }

    public int getModel() {
        return this.model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public int getBestPace() {
        return this.BestPace;
    }

    public void setBestPace(int bestPace) {
        this.BestPace = bestPace;
    }

    public int getLowestPace() {
        return this.LowestPace;
    }

    public void setLowestPace(int lowestPace) {
        this.LowestPace = lowestPace;
    }

    public int getBestPace_distance() {
        return this.BestPace_distance;
    }

    public void setBestPace_distance(int bestPace_distance) {
        this.BestPace_distance = bestPace_distance;
    }

    public int getLowestPace_distance() {
        return this.LowestPace_distance;
    }

    public void setLowestPace_distance(int lowestPace_distance) {
        this.LowestPace_distance = lowestPace_distance;
    }

    public int getMaxHeatRate() {
        return this.maxHeatRate;
    }

    public void setMaxHeatRate(int maxHeatRate) {
        this.maxHeatRate = maxHeatRate;
    }

    public int getMinHeatRate() {
        return this.minHeatRate;
    }

    public void setMinHeatRate(int minHeatRate) {
        this.minHeatRate = minHeatRate;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMinSpeed() {
        return this.minSpeed;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getMaxStepSpeed() {
        return this.maxStepSpeed;
    }

    public void setMaxStepSpeed(int maxStepSpeed) {
        this.maxStepSpeed = maxStepSpeed;
    }

    public int getMinStepSpeed() {
        return this.minStepSpeed;
    }

    public void setMinStepSpeed(int minStepSpeed) {
        this.minStepSpeed = minStepSpeed;
    }

    public double getMaxStepWidth() {
        return this.maxStepWidth;
    }

    public void setMaxStepWidth(double maxStepWidth) {
        this.maxStepWidth = maxStepWidth;
    }

    public void setAveStepWidth(double aveStepWidth) {
        this.aveStepWidth = aveStepWidth;
    }

    public String getTrainingIntensity() {
        return this.trainingIntensity;
    }

    public void setTrainingIntensity(String trainingIntensity) {
        this.trainingIntensity = trainingIntensity;
    }

    public double getNeedO2Max() {
        return this.needO2Max;
    }

    public void setNeedO2Max(double needO2Max) {
        this.needO2Max = needO2Max;
    }

    public void setSumUp(double sumUp) {
        this.sumUp = sumUp;
    }

    public void setSumDown(double sumDown) {
        this.sumDown = sumDown;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    public void setPauseTimes(int pauseTimes) {
        this.pauseTimes = pauseTimes;
    }

    public void setHorizontalSpeed(double horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public double getMinStepWidth() {
        return this.minStepWidth;
    }

    public void setMinStepWidth(double minStepWidth) {
        this.minStepWidth = minStepWidth;
    }
}
