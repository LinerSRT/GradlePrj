package com.kct.sports.utils;

import android.content.Context;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class SportDataUtils {
    private Context mContext;

    public SportDataUtils(Context context) {
        this.mContext = context;
    }

    public int DoubToInt(double f) {
        try {
            return Integer.valueOf(new DecimalFormat("######0", new DecimalFormatSymbols(Locale.ENGLISH)).format(f)).intValue();
        } catch (Exception e) {
            return (int) f;
        }
    }

    public int getAltitudeUp(ArrayList<Integer> mAltitude) {
        int up = 0;
        int down = 0;
        for (int i = 1; i < mAltitude.size(); i++) {
            int c = ((Integer) mAltitude.get(i)).intValue() - ((Integer) mAltitude.get(i - 1)).intValue();
            if (c > 0) {
                up += c;
            } else if (c < 0) {
                down += c;
            }
        }
        if (up == 0) {
            return 0;
        }
        try {
            return Integer.valueOf(String.valueOf(up)).intValue();
        } catch (Exception e) {
            return up;
        }
    }

    public static int getAltitudeDown(ArrayList<Integer> mAltitude) {
        int up = 0;
        int down = 0;
        for (int i = 1; i < mAltitude.size(); i++) {
            int c = ((Integer) mAltitude.get(i)).intValue() - ((Integer) mAltitude.get(i - 1)).intValue();
            if (c > 0) {
                up += c;
            } else if (c < 0) {
                down += Math.abs(c);
            }
        }
        if (down == 0) {
            return 0;
        }
        try {
            return Integer.valueOf(String.valueOf(down)).intValue();
        } catch (Exception e) {
            return down;
        }
    }
}
