package com.kct.sports.base;

import android.app.Activity;
import android.app.Application;
import android.graphics.Typeface;
import android.app.Activity;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.List;

public class NewApplication extends Application {
    public static int height;
    private static NewApplication instance;
    public static Typeface mTypeface_DIN1451EF = null;
    public static Typeface mTypeface_date = null;
    public static Typeface mTypeface_date_name = null;
    public static int width;
    private List<Activity> activitys;

    public NewApplication() {
        this.activitys = null;
        this.activitys = new LinkedList();
    }

    public void onCreate() {
        mTypeface_date = Typeface.createFromAsset(getAssets(), "fonts/BEBAS___.TTF");
        mTypeface_date_name = Typeface.createFromAsset(getAssets(), "fonts/LanTingBlack_YS_GB18030.TTF");
        mTypeface_DIN1451EF = Typeface.createFromAsset(getAssets(), "fonts/DIN1451EF-EngNeu.otf");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        super.onCreate();
    }

    public static NewApplication getInstance() {
        if (instance == null) {
            instance = new NewApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (this.activitys == null || this.activitys.size() <= 0) {
            this.activitys.add(activity);
        } else if (!this.activitys.contains(activity)) {
            this.activitys.add(activity);
        }
    }

    public void exit() {
        if (this.activitys != null && this.activitys.size() > 0) {
            for (Activity activity : this.activitys) {
                activity.finish();
            }
        }
    }
}
