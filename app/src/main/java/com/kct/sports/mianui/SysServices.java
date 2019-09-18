package com.kct.sports.mianui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings.System;

public class SysServices {
    public static PackageManager getPkMgr(Context context) {
        return context.getPackageManager();
    }

    public static int getSystemSettingInt(Context context, String name, int defalutValue) {
        return System.getInt(context.getContentResolver(), name, defalutValue);
    }

    public static void setSystemSettingInt(Context context, String name, int value) {
        //System.putInt(context.getContentResolver(), name, value);
    }
}
