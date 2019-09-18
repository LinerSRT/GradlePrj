package com.kct.sports.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent intent_alarm = new Intent(context, TimeToRunActivity.class);
        intent_alarm.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent_alarm);
    }
}
