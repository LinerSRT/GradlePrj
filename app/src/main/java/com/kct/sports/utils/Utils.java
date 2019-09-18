package com.kct.sports.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps2d.model.Marker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Utils {
    public static boolean LOWBATT_STATUS = false;
    private static ArrayList<Marker> markers = new ArrayList();
    private static SimpleDateFormat sdf = null;
    private static float variance = 1.0f;

    public static synchronized String getLocationStr(AMapLocation location) {
        synchronized (Utils.class) {
            if (location == null) {
                return null;
            }
            StringBuffer sb = new StringBuffer();
            if (location.getErrorCode() == 0) {
                sb.append("定位成功\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");
                if (location.getProvider().equalsIgnoreCase("gps")) {
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                } else {
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss:sss") + "\n");
                }
            } else {
                sb.append("定位失败\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
            }
            sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss:sss") + "\n");
            String stringBuffer = sb.toString();
            return stringBuffer;
        }
    }

    public static double decimalTo2(double f, int weishu) {
        try {
            return new BigDecimal(f).setScale(weishu, 4).doubleValue();
        } catch (Exception e) {
            return f;
        }
    }

    public static synchronized String formatUTC(long l, String strPattern) {
        String str;
        synchronized (Utils.class) {
            if (TextUtils.isEmpty(strPattern)) {
                strPattern = "yyyy-MM-dd HH:mm:ss";
            }
            if (sdf == null) {
                try {
                    sdf = new SimpleDateFormat(strPattern, Locale.ENGLISH);
                } catch (Throwable th) {
                }
            } else {
                sdf.applyPattern(strPattern);
            }
            if (l <= 0) {
                l = System.currentTimeMillis();
            }
            if (sdf == null) {
                str = "NULL";
            } else {
                str = sdf.format(Long.valueOf(l));
            }
        }
        return str;
    }

    public static String getCurrentLauguage() {
        return Locale.getDefault().getLanguage();
    }

    public static boolean isGooglePlayServiceAvailable(Context context) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
            Log.e("YXH", "GooglePlayServicesUtil service is available.");
            return true;
        }
        Log.e("YXH", "GooglePlayServicesUtil service is NOT available.");
        return false;
    }
}
