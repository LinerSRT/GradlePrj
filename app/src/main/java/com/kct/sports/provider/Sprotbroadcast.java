package com.kct.sports.provider;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.System;
import android.util.Log;
import com.kct.sports.Trace.LocationSvc;
import com.kct.sports.utils.Utils;

public class Sprotbroadcast extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ContentResolver resolver = context.getContentResolver();
        if ("com.szkct.Battery_CHANGED_ACTION".equals(action)) {
            String extra = intent.getStringExtra("target");
            if (extra.equals("batterydown")) {
                Utils.LOWBATT_STATUS = true;
                Log.e("sport", "进入低电量模式");
                saveSportData(context, resolver);
            } else if (extra.equals("batteryup")) {
                Utils.LOWBATT_STATUS = false;
            }
        } else if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
            if (System.getString(context.getContentResolver(), "airplane_mode_on").equals("1")) {
            }
        } else if (action.equals("android.intent.action.ACTION_SHUTDOWN")) {
            Utils.LOWBATT_STATUS = true;
            try {
                saveSportData(context, resolver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveSportData(Context context, ContentResolver resolver) {
        //System.putInt(resolver, "isRunning", 0);
        context.sendBroadcast(new Intent("com.kct.sports.start.run"));
        context.stopService(new Intent(context, DateProviderService.class));
        context.stopService(new Intent(context, LocationSvc.class));
        turnGPSOff(context);
        Intent intent2 = new Intent("com.kct.spots.timerEnd");
        if (enoughDate(resolver)) {
            intent2.putExtra("end", true);
        } else {
            intent2.putExtra("end", false);
        }
        context.sendBroadcast(intent2);
    }

    public boolean enoughDate(ContentResolver resolver) {
        if (Float.valueOf(System.getFloat(resolver, "distance", 0.0f)).floatValue() <= 20.0f) {
            return false;
        }
        return true;
    }

    public void turnGPSOff(Context context) {
    }
}
