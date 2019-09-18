package com.kct.sports.mianui;

import com.kct.sports.R;
import java.util.ArrayList;

public class SportListUtil {
    private static int[] bg_images = {R.drawable.sport_bg_walking, R.drawable.sport_bg_outdoor, R.drawable.sport_bg_indoor, R.drawable.sport_bg_mountain, R.drawable.sport_bg_cross_country, R.drawable.sport_bg_marathon_half, R.drawable.sport_bg_marathon_full, R.drawable.sport_bg_cycling, R.drawable.sport_bg_setting};
    private static int[] bg_images_bw = {R.drawable.sport_bg_walking_bw, R.drawable.sport_bg_outdoor_bw, R.drawable.sport_bg_indoor_bw, R.drawable.sport_bg_mountain_bw, R.drawable.sport_bg_cross_country_bw, R.drawable.sport_bg_marathon_half_bw, R.drawable.sport_bg_marathon_full_bw, R.drawable.sport_bg_cycling_bw, R.drawable.sport_bg_setting_bw};
    private static int[] images = {R.drawable.sport_icon_walking, R.drawable.sport_run_outdoor, R.drawable.sport_run_indoor, R.drawable.sport_icon_mountain, R.drawable.sport_icon_cross_country, R.drawable.sport_icon_marathon_half, R.drawable.sport_icon_marathon_full, R.drawable.sport_icon_cycling, R.drawable.sport_icon_setting};
    private static String[] intents = {"kct.intent.action.SearchGPSActivity#1", "kct.intent.action.SearchGPSActivity#2", "kct.intent.action.SearchGPSActivity#3", "kct.intent.action.SearchGPSActivity#4", "kct.intent.action.SearchGPSActivity#5", "kct.intent.action.SearchGPSActivity#6", "kct.intent.action.SearchGPSActivity#7", "kct.intent.action.SearchGPSActivity#11", "com.kct.sports.setting.SportsSettings"};
    private static ArrayList<AppMenu> mSportList = new ArrayList();
    private static int[] names = {R.string.sport_walking, R.string.sport_run_outdoor, R.string.sport_run_indoor, R.string.sport_mountain, R.string.sport_cross_country, R.string.sport_marathon_half, R.string.sport_marathon_full, R.string.sport_run_ride, R.string.sport_setting};

    static {
        for (int i = 0; i < images.length; i++) {
            AppMenu menu = new AppMenu();
            menu.setCustomImageRes(images[i]);
            menu.setCustomNameRes(names[i]);
            menu.setBgImageRes(bg_images[i]);
            menu.setBgImageBwRes(bg_images_bw[i]);
            String[] intent = intents[i].split("#");
            menu.setIntent(intent[0]);
            if (intent.length == 2) {
                menu.setIntentData(Integer.parseInt(intent[1]));
            }
            mSportList.add(menu);
        }
    }

    public static ArrayList<AppMenu> getSportList() {
        if (mSportList == null) {
            mSportList = new ArrayList<>();
        }
        return mSportList;
    }
}
