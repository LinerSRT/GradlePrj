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

public class HVSProvider extends ContentProvider {
    private static HashMap<String, String> mTraceProjectionMap = new HashMap();
    private static final UriMatcher sUriMatcher = new UriMatcher(-1);
    private DBUtil DBdatas = null;
    private DatabaseHelper mHelper = null;

    static {
        sUriMatcher.addURI("com.kct.sports.provider.sportsHVSDate", "heartRate_velocity_stepSpeed", 1);
        sUriMatcher.addURI("com.kct.sports.provider.sportsHVSDate", "heartRate_velocity_stepSpeed/#", 2);
        mTraceProjectionMap.put("_id", "_id");
        mTraceProjectionMap.put("item_id", "item_id");
        mTraceProjectionMap.put("get_time", "get_time");
        mTraceProjectionMap.put("heart_rate", "heart_rate");
        mTraceProjectionMap.put("speed", "speed");
        mTraceProjectionMap.put("step_speed", "step_speed");
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
                qb.setTables("heartRate_velocity_stepSpeed");
                qb.setProjectionMap(mTraceProjectionMap);
                break;
            case 2:
                qb.setTables("heartRate_velocity_stepSpeed");
                qb.setProjectionMap(mTraceProjectionMap);
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
