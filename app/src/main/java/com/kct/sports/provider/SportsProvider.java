package com.kct.sports.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.kct.sports.db.DBUtil;
import com.kct.sports.db.DatabaseHelper;
import com.kct.sports.utils.StringUtils;
import java.util.HashMap;

public class SportsProvider extends ContentProvider {
    private static HashMap<String, String> mSportsProjectionMap = new HashMap();
    private static final UriMatcher sUriMatcher = new UriMatcher(-1);
    private DBUtil DBdatas = null;
    private DatabaseHelper mHelper = null;

    static {
        sUriMatcher.addURI("com.kct.sports.provider.sportsMainDate", "step_count_detail", 1);
        sUriMatcher.addURI("com.kct.sports.provider.sportsMainDate", "step_count_detail/#", 2);
        mSportsProjectionMap.put("_id", "_id");
        mSportsProjectionMap.put("item_id", "item_id");
        mSportsProjectionMap.put("start_time", "start_time");
        mSportsProjectionMap.put("get_time", "get_time");
        mSportsProjectionMap.put("time_take", "time_take");
        mSportsProjectionMap.put("pause_time", "pause_time");
        mSportsProjectionMap.put("pause_times", "pause_times");
        mSportsProjectionMap.put("step_count", "step_count");
        mSportsProjectionMap.put("max_step_width", "max_step_width");
        mSportsProjectionMap.put("min_step_width", "min_step_width");
        mSportsProjectionMap.put("ave_step_width", "ave_step_width");
        mSportsProjectionMap.put("speed", "speed");
        mSportsProjectionMap.put("max_speed", "max_speed");
        mSportsProjectionMap.put("min_speed", "min_speed");
        mSportsProjectionMap.put("vertical_speed", "vertical_speed");
        mSportsProjectionMap.put("horizontal_speed", "horizontal_speed");
        mSportsProjectionMap.put("step_speed", "step_speed");
        mSportsProjectionMap.put("max_step_speed", "max_step_speed");
        mSportsProjectionMap.put("min_step_speed", "min_step_speed");
        mSportsProjectionMap.put("distance", "distance");
        mSportsProjectionMap.put("heart_rate", "heart_rate");
        mSportsProjectionMap.put("max_heart_rate", "max_heart_rate");
        mSportsProjectionMap.put("min_heart_rate", "min_heart_rate");
        mSportsProjectionMap.put("altitude", "altitude");
        mSportsProjectionMap.put("kcal", "kcal");
        mSportsProjectionMap.put("space", "space");
        mSportsProjectionMap.put("best_space", "best_space");
        mSportsProjectionMap.put("best_space_distance", "best_space_distance");
        mSportsProjectionMap.put("lowest_space", "lowest_space");
        mSportsProjectionMap.put("lowest_space_distance", "lowest_space_distance");
        mSportsProjectionMap.put("sum_up", "sum_up");
        mSportsProjectionMap.put("sum_down", "sum_down");
        mSportsProjectionMap.put("o2_need", "o2_need");
        mSportsProjectionMap.put("training_intensity", "training_intensity");
    }

    public boolean onCreate() {
        if (this.DBdatas == null) {
            this.DBdatas = new DBUtil(getContext());
            this.mHelper = this.DBdatas.getDbHelper();
        }
        return this.DBdatas != null;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String orderBy;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case 1:
                qb.setTables("step_count_detail");
                qb.setProjectionMap(mSportsProjectionMap);
                break;
            case 2:
                qb.setTables("step_count_detail");
                qb.setProjectionMap(mSportsProjectionMap);
                qb.appendWhere("_id=" + ((String) uri.getPathSegments().get(1)));
                break;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        if (StringUtils.isEmpty(sortOrder)) {
            orderBy = "get_time";
        } else {
            orderBy = sortOrder;
        }
        return qb.query(this.mHelper.getReadableDatabase(), projection, selection, null, null, null, orderBy);
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
