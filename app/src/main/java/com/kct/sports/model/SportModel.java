package com.kct.sports.model;

import android.content.Context;
import com.kct.sports.R;
import com.kct.sports.bll.ISportsBLL;
import com.kct.sports.bll.SportsBLL;
import com.kct.sports.utils.ShowStringUitls;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class SportModel {
    Context context;
    int model;
    String[][] viewIndexes;

    public static SportModel getSportModel(Context context, int mode) {
        SportModel mSportModel = null;
        switch (mode) {
            case 1:
                mSportModel = new SportModel_Walking();
                break;
            case 2:
                mSportModel = new SportModel_OutdoorRunning();
                break;
            case 3:
                mSportModel = new SportModel_IndoorRun();
                break;
            case 4:
                mSportModel = new SportModel_Mountaineering();
                break;
            case 5:
                mSportModel = new SportModel_CrossCountryRunning();
                break;
            case 6:
                mSportModel = new SportModel_HalfMarathon();
                break;
            case 7:
                mSportModel = new SportModel_FullMarathon();
                break;
            case 11:
                mSportModel = new SportModel_Riding();
                break;
        }
        mSportModel.context = context;
        return mSportModel;
    }

    public String getNameFromViewIndexes(String key) {
        if (key.equals("cadence")) {
            return this.context.getResources().getString(R.string.stride_frequency);
        }
        if (key.equals("averagepace")) {
            return this.context.getResources().getString(R.string.average_pace);
        }
        if (key.equals("pace")) {
            return this.context.getResources().getString(R.string.pace);
        }
        if (key.equals("heartrate")) {
            return this.context.getResources().getString(R.string.heart_rate);
        }
        if (key.equals("calorie")) {
            return this.context.getResources().getString(R.string.energy_cost);
        }
        if (key.equals("altitudechange")) {
            return this.context.getResources().getString(R.string.altitude_change);
        }
        if (key.equals("verticalvelocity")) {
            return this.context.getResources().getString(R.string.vertical_velocity);
        }
        if (key.equals("remainingmileage")) {
            return this.context.getResources().getString(R.string.remaining_mileage);
        }
        if (key.equals("mileage")) {
            return this.context.getResources().getString(R.string.distance);
        }
        if (key.equals("time")) {
            return this.context.getResources().getString(R.string.timing);
        }
        if (key.equals("horizontaldisplacement")) {
            return this.context.getResources().getString(R.string.horizontal_displacement);
        }
        if (key.equals("verticaldisplacement")) {
            return this.context.getResources().getString(R.string.vertica_displacement);
        }
        if (key.equals("speed")) {
            return this.context.getResources().getString(R.string.velocity);
        }
        if (key.equals("averagespeed")) {
            return this.context.getResources().getString(R.string.average_speed);
        }
        if (key.equals("altitude")) {
            return this.context.getResources().getString(R.string.altitude);
        }
        if (key.equals("cumulativeclimb")) {
            return this.context.getResources().getString(R.string.stored_climbing);
        }
        if (key.equals("cumulativedecrease")) {
            return this.context.getResources().getString(R.string.stored_decline);
        }
        if (key.equals("horizontalvelocity")) {
            return this.context.getResources().getString(R.string.horizontal_velocity);
        }
        if (key.equals("stepcount")) {
            return this.context.getResources().getString(R.string.step_count);
        }
        return null;
    }

    public String getValueFromViewIndexes(String key) {
        if (key.equals("cadence")) {
            return getCadence();
        }
        if (key.equals("averagepace")) {
            return getAveragepace();
        }
        if (key.equals("pace")) {
            return getPace();
        }
        if (key.equals("heartrate")) {
            return getHeartrate();
        }
        if (key.equals("calorie")) {
            return getCalorie();
        }
        if (key.equals("altitudechange")) {
            return getAltitudechange();
        }
        if (key.equals("verticalvelocity")) {
            return getVerticalvelocity();
        }
        if (key.equals("remainingmileage")) {
            return getRemainingmileage();
        }
        if (key.equals("mileage")) {
            return getMileage();
        }
        if (key.equals("time")) {
            return getTime();
        }
        if (key.equals("horizontaldisplacement")) {
            return getHorizontaldisplacement();
        }
        if (key.equals("verticaldisplacement")) {
            return getVerticaldisplacement();
        }
        if (key.equals("speed")) {
            return getSpeed();
        }
        if (key.equals("averagespeed")) {
            return getAveragespeed();
        }
        if (key.equals("altitude")) {
            return getAltitude();
        }
        if (key.equals("cumulativeclimb")) {
            return getCumulativeclimb();
        }
        if (key.equals("cumulativedecrease")) {
            return getCumulativedecrease();
        }
        if (key.equals("horizontalvelocity")) {
            return getHorizontalvelocity();
        }
        if (key.equals("stepcount")) {
            return getTotalStep();
        }
        return null;
    }

    public String getUnitFromViewIndexes(String key) {
        if (key.equals("cadence")) {
            return this.context.getResources().getString(R.string.step_per_minuter);
        }
        if (key.equals("averagepace")) {
            return this.context.getResources().getString(R.string.per_km);
        }
        if (key.equals("pace")) {
            return this.context.getResources().getString(R.string.per_km);
        }
        if (key.equals("heartrate")) {
            return this.context.getResources().getString(R.string.times_per_minute);
        }
        if (key.equals("calorie")) {
            return this.context.getResources().getString(R.string.kcal);
        }
        if (key.equals("altitudechange")) {
            return "";
        }
        if (key.equals("verticalvelocity")) {
            return this.context.getResources().getString(R.string.km_per_hours);
        }
        if (key.equals("remainingmileage")) {
            return this.context.getResources().getString(R.string.kilometer);
        }
        if (key.equals("mileage")) {
            return this.context.getResources().getString(R.string.kilometer);
        }
        if (key.equals("time")) {
            return "";
        }
        if (key.equals("horizontaldisplacement")) {
            return this.context.getResources().getString(R.string.meter);
        }
        if (key.equals("verticaldisplacement")) {
            return this.context.getResources().getString(R.string.meter);
        }
        if (key.equals("speed")) {
            return this.context.getResources().getString(R.string.km_per_hours);
        }
        if (key.equals("averagespeed")) {
            return this.context.getResources().getString(R.string.km_per_hours);
        }
        if (key.equals("altitude")) {
            return this.context.getResources().getString(R.string.meter);
        }
        if (key.equals("cumulativeclimb")) {
            return this.context.getResources().getString(R.string.meter);
        }
        if (key.equals("cumulativedecrease")) {
            return this.context.getResources().getString(R.string.meter);
        }
        if (key.equals("horizontalvelocity")) {
            return this.context.getResources().getString(R.string.km_per_hours);
        }
        return null;
    }

    public ISportsBLL getSportsBLL() {
        if (!SportsBLL.getInstance().isInit()) {
            SportsBLL.getInstance().initParam(this.context);
        }
        return SportsBLL.getInstance();
    }

    public String getHeartrate() {
        return String.valueOf(getSportsBLL().getHeartRate());
    }

    public String getCadence() {
        return String.valueOf(getSportsBLL().getStepSpeed());
    }

    public String getTotalStep() {
        return String.valueOf(getSportsBLL().getStepCount());
    }

    public String getCalorie() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getCalorie()));
    }

    public String getAveragepace() {
        return String.valueOf(ShowStringUitls.formatPace(getSportsBLL().getAvePace()));
    }

    public String getPace() {
        return ShowStringUitls.formatPace(getSportsBLL().getPace());
    }

    public String getAltitudechange() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getAltitude()));
    }

    public String getVerticalvelocity() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getVerticalSpeed()));
    }

    public String getRemainingmileage() {
        return String.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format((21098.0d - getSportsBLL().getDistance()) * 0.001d));
    }

    public String getMileage() {
        return String.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getDistance() * 0.001d));
    }

    public String getTime() {
        return ShowStringUitls.formatTimer((int) getSportsBLL().getCurrentSecondTime());
    }

    public String getHorizontaldisplacement() {
        return String.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getHorizontalDistance()));
    }

    public String getVerticaldisplacement() {
        return String.valueOf(new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getVerticalDistance()));
    }

    public String getSpeed() {
        DecimalFormat df = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH));
        df.setRoundingMode(RoundingMode.HALF_UP);
        return String.valueOf(df.format(getSportsBLL().getSpeed()));
    }

    public String getAveragespeed() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getAveSpeed()));
    }

    public String getAltitude() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getAltitude()));
    }

    public String getCumulativeclimb() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getAltitudeUp()));
    }

    public String getCumulativedecrease() {
        return String.valueOf(new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH)).format(getSportsBLL().getAltitudeDown()));
    }

    public String getHorizontalvelocity() {
        DecimalFormat df = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.ENGLISH));
        df.setRoundingMode(RoundingMode.HALF_UP);
        return String.valueOf(df.format(getSportsBLL().getHorizontalSpeed()));
    }

    public String[][] getViewIndexes() {
        return this.viewIndexes;
    }
}
