package com.kct.sports.utils;

import android.content.Context;
import com.kct.sports.R;

public class ShowStringUitls {
    public static String formatTimer(int time) {
        String hh = time / 3600 > 9 ? (time / 3600) + "" : "0" + (time / 3600);
        return hh + ":" + ((time % 3600) / 60 > 9 ? ((time % 3600) / 60) + "" : "0" + ((time % 3600) / 60)) + ":" + ((time % 3600) % 60 > 9 ? ((time % 3600) % 60) + "" : "0" + ((time % 3600) % 60));
    }

    public static String formatPace(int pace) {
        return (pace / 60 > 9 ? (pace / 60) + "" : "0" + (pace / 60)) + "'" + ((pace % 3600) % 60 > 9 ? ((pace % 3600) % 60) + "" : "0" + ((pace % 3600) % 60)) + "''";
    }

    public static String formatTimes(int times) {
        String hundred = times / 100 > 9 ? (times / 100) + "" : "0" + (times / 100);
        return hundred + ":" + ((times % 100) / 10 > 9 ? ((times % 100) / 10) + "" : "0" + ((times % 100) / 10)) + ":" + ((times % 100) % 10 > 9 ? ((times % 100) % 10) + "" : "0" + ((times % 100) % 10));
    }

    public static String getModel(Context mContext, int model) {
        switch (model) {
            case 1:
                return mContext.getResources().getString(R.string.walking);
            case 2:
                return mContext.getResources().getString(R.string.outdoor_running);
            case 3:
                return mContext.getResources().getString(R.string.indoor_running);
            case 4:
                return mContext.getResources().getString(R.string.mountaineering);
            case 5:
                return mContext.getResources().getString(R.string.cc_run2);
            case 6:
                return mContext.getResources().getString(R.string.half_marathon);
            case 7:
                return mContext.getResources().getString(R.string.full_marathon);
            case 11:
                return mContext.getResources().getString(R.string.riding);
            default:
                return mContext.getResources().getString(R.string.outdoor_running);
        }
    }
}
