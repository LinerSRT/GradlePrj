package com.kct.sports.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.kct.sports.base.SportsInfo;
import com.kct.sports.base.Trace;
import com.kct.sports.utils.LogUtil;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    private DatabaseHelper dbHelper;

    public DBUtil(Context context) {
        if (this.dbHelper == null) {
            this.dbHelper = new DatabaseHelper(context);
        }
    }

    public long insertDate(long starttime, int stepCount, double maxStepWidth, double minStepWidth, double aveStepWidth, double speed, double maxSpeed, double minSpeed, double verticalSpeed, double horizontalSpeed, int stepSpeed, int maxStepSpeed, int minStepSpeed, int itemId, long recordHour, long timeTake, int pauseTime, int pauseTimes, double distance, int heatRate, int maxHeartRate, int minHeartRate, double altitude, double kcal, int pace, int bestPace, int bestPaceDistance, int lowestPace, int lowestPaceDistance, double sumUp, double sum_Down, double o2, String trainingIntensity, int aveStepSpeed) {
        long insertResult = -1;
        if (this.dbHelper == null) {
            return -1;
        }
        try {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "存入数据库!");
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("start_time", Long.valueOf(starttime));
            values.put("item_id", Integer.valueOf(itemId));
            values.put("get_time", Long.valueOf(recordHour));
            values.put("time_take", Long.valueOf(timeTake));
            values.put("pause_time", Integer.valueOf(pauseTime));
            values.put("pause_times", Integer.valueOf(pauseTimes));
            values.put("step_count", Integer.valueOf(stepCount));
            values.put("max_step_width", Double.valueOf(maxStepWidth));
            values.put("min_step_width", Double.valueOf(minStepWidth));
            values.put("ave_step_width", Double.valueOf(aveStepWidth));
            values.put("speed", Double.valueOf(speed));
            values.put("max_speed", Double.valueOf(maxSpeed));
            values.put("min_speed", Double.valueOf(minSpeed));
            values.put("vertical_speed", Double.valueOf(verticalSpeed));
            values.put("horizontal_speed", Double.valueOf(horizontalSpeed));
            values.put("step_speed", Integer.valueOf(stepSpeed));
            values.put("max_step_speed", Integer.valueOf(maxStepSpeed));
            values.put("min_step_speed", Integer.valueOf(minStepSpeed));
            values.put("distance", Double.valueOf(distance));
            values.put("heart_rate", Integer.valueOf(heatRate));
            values.put("max_heart_rate", Integer.valueOf(maxHeartRate));
            values.put("min_heart_rate", Integer.valueOf(minHeartRate));
            values.put("altitude", Double.valueOf(altitude));
            values.put("kcal", Double.valueOf(kcal));
            values.put("space", Integer.valueOf(pace));
            values.put("best_space", Integer.valueOf(bestPace));
            values.put("best_space_distance", Integer.valueOf(bestPaceDistance));
            values.put("lowest_space", Integer.valueOf(lowestPace));
            values.put("lowest_space_distance", Integer.valueOf(lowestPaceDistance));
            values.put("sum_up", Double.valueOf(sumUp));
            values.put("sum_down", Double.valueOf(sum_Down));
            values.put("o2_need", Double.valueOf(o2));
            values.put("training_intensity", trainingIntensity);
            values.put("ave_step_speed", Integer.valueOf(aveStepSpeed));
            insertResult = db.insert("step_count_detail", null, values);
            db.close();
            return insertResult;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "找不到数据库" + e);
            return insertResult;
        }
    }

    public int deleteDate(int id) {
        int date_id = 1;
        if (this.dbHelper == null) {
            return 1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            date_id = db.delete("step_count_detail", "_id = ? ", new String[]{id + ""});
            db.close();
            return date_id;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "删除失败" + e);
            return date_id;
        }
    }

    public ArrayList<SportsInfo> queryAllInDB() {
        ArrayList<SportsInfo> sportsInfoArrayList = new ArrayList();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.query("step_count_detail", null, null, null, null, null, null);
        SportsInfo sportsStepCountInfo = null;
        while (cursor != null && !cursor.isClosed() && cursor.moveToNext()) {
            SportsInfo sportsStepCountInfo2;
            try {
                sportsStepCountInfo2 = new SportsInfo();
                try {
                    sportsStepCountInfo2.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    sportsStepCountInfo2.setModel(cursor.getInt(cursor.getColumnIndex("item_id")));
                    sportsStepCountInfo2.setGetTime(cursor.getLong(cursor.getColumnIndex("get_time")));
                    sportsStepCountInfo2.setStartTime(cursor.getLong(cursor.getColumnIndex("start_time")));
                    sportsStepCountInfo2.setPauseTime(cursor.getInt(cursor.getColumnIndex("pause_time")));
                    sportsStepCountInfo2.setPauseTimes(cursor.getInt(cursor.getColumnIndex("pause_times")));
                    sportsStepCountInfo2.setMaxStepWidth(cursor.getDouble(cursor.getColumnIndex("max_step_width")));
                    sportsStepCountInfo2.setAveStepWidth(cursor.getDouble(cursor.getColumnIndex("ave_step_width")));
                    sportsStepCountInfo2.setSpeed(cursor.getDouble(cursor.getColumnIndex("speed")));
                    sportsStepCountInfo2.setMaxSpeed(cursor.getDouble(cursor.getColumnIndex("max_speed")));
                    sportsStepCountInfo2.setMinSpeed(cursor.getDouble(cursor.getColumnIndex("min_speed")));
                    sportsStepCountInfo2.setVerticalSpeed(cursor.getDouble(cursor.getColumnIndex("vertical_speed")));
                    sportsStepCountInfo2.setHorizontalSpeed(cursor.getDouble(cursor.getColumnIndex("horizontal_speed")));
                    sportsStepCountInfo2.setStepSpeed(cursor.getInt(cursor.getColumnIndex("step_speed")));
                    sportsStepCountInfo2.setMaxStepSpeed(cursor.getInt(cursor.getColumnIndex("max_step_speed")));
                    sportsStepCountInfo2.setMinStepSpeed(cursor.getInt(cursor.getColumnIndex("min_step_speed")));
                    sportsStepCountInfo2.setPace(cursor.getInt(cursor.getColumnIndex("space")));
                    sportsStepCountInfo2.setBestPace(cursor.getInt(cursor.getColumnIndex("best_space")));
                    sportsStepCountInfo2.setLowestPace(cursor.getInt(cursor.getColumnIndex("lowest_space")));
                    sportsStepCountInfo2.setBestPace_distance(cursor.getInt(cursor.getColumnIndex("best_space_distance")));
                    sportsStepCountInfo2.setLowestPace_distance(cursor.getInt(cursor.getColumnIndex("lowest_space_distance")));
                    sportsStepCountInfo2.setTimeTake(cursor.getLong(cursor.getColumnIndex("time_take")));
                    sportsStepCountInfo2.setStepCount(cursor.getInt(cursor.getColumnIndex("step_count")));
                    sportsStepCountInfo2.setDistance(cursor.getDouble(cursor.getColumnIndex("distance")));
                    sportsStepCountInfo2.setHeatRate(cursor.getInt(cursor.getColumnIndex("heart_rate")));
                    sportsStepCountInfo2.setMaxHeatRate(cursor.getInt(cursor.getColumnIndex("max_heart_rate")));
                    sportsStepCountInfo2.setMinHeatRate(cursor.getInt(cursor.getColumnIndex("min_heart_rate")));
                    sportsStepCountInfo2.setAltitude(cursor.getDouble(cursor.getColumnIndex("altitude")));
                    sportsStepCountInfo2.setKcal(cursor.getDouble(cursor.getColumnIndex("kcal")));
                    sportsStepCountInfo2.setNeedO2Max(cursor.getDouble(cursor.getColumnIndex("o2_need")));
                    sportsStepCountInfo2.setSumUp(cursor.getDouble(cursor.getColumnIndex("sum_up")));
                    sportsStepCountInfo2.setSumDown(cursor.getDouble(cursor.getColumnIndex("sum_down")));
                    sportsStepCountInfo2.setTrainingIntensity(cursor.getString(cursor.getColumnIndex("training_intensity")));
                    sportsStepCountInfo2.setAverageStepSpeed(cursor.getColumnIndex("ave_step_speed"));
                    sportsStepCountInfo2.setMinStepWidth(cursor.getDouble(cursor.getColumnIndex("min_step_width")));
                    sportsInfoArrayList.add(sportsStepCountInfo2);
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                sportsStepCountInfo2 = sportsStepCountInfo;
            }
            sportsStepCountInfo = sportsStepCountInfo2;
        }
        cursor.close();
        db.close();
        return sportsInfoArrayList;
    }

    public long insertTrace(Trace trace, long startTime, long recordTime) {
        long insertResult = -1;
        if (this.dbHelper == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Latitude", Double.valueOf(trace.getLatitude()));
            values.put("Longitude", Double.valueOf(trace.getLongitude()));
            values.put("altitude", Double.valueOf(trace.getAltitude()));
            values.put("item_id", Integer.valueOf(trace.getItem_id()));
            values.put("start_time", Long.valueOf(startTime));
            values.put("get_time", Long.valueOf(recordTime));
            insertResult = db.insert("trace", null, values);
            Log.e("kctsport", "currentTime==" + recordTime);
            db.close();
            return insertResult;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "找不到trace数据库" + e);
            return insertResult;
        }
    }

    public List<Trace> getTrace(long startTime, long endtime) {
        Exception e;
        List<Trace> traces_points = new ArrayList();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM trace where start_time >= ?  AND get_time <= ?  order by trace_id ASC ", new String[]{String.valueOf(startTime), String.valueOf(endtime)});
        Trace trace = null;
        while (cursor != null && !cursor.isClosed()) {
            if (!cursor.moveToNext()) {
                break;
            }
            Trace trace2;
            try {
                trace2 = new Trace(startTime, endtime);
                try {
                    trace2.setLatitude(cursor.getDouble(cursor.getColumnIndex("Latitude")));
                    trace2.setLongitude(cursor.getDouble(cursor.getColumnIndex("Longitude")));
                    trace2.setAltitude(cursor.getDouble(cursor.getColumnIndex("altitude")));
                    trace2.setItem_id(cursor.getInt(cursor.getColumnIndex("item_id")));
                    if (trace2.getLatitude() == -1.0d && trace2.getLongitude() == -1.0d) {
                        break;
                    }
                    traces_points.add(trace2);
                    Log.e("sportTract", trace2.toString());
                    trace = trace2;
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    trace = trace2;
                }
            } catch (Exception e3) {
                e = e3;
                trace2 = trace;
                e.printStackTrace();
                trace = trace2;
            }
        }
        cursor.close();
        db.close();
        return traces_points;
    }

    public void updateTraceList(long startTime, long gettime) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("get_time", Long.valueOf(gettime));
        db.update("trace", values, "start_time=?", new String[]{String.valueOf(startTime)});
        db.close();
    }

    public int deleteTraceDate(long startTime, long endtime) {
        int date_id = 1;
        if (this.dbHelper == null) {
            return 1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            date_id = db.delete("trace", "start_time >=?  AND get_time <=? ", new String[]{String.valueOf(startTime), String.valueOf(endtime)});
            db.close();
            return date_id;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "删除轨迹数据失败" + e);
            return date_id;
        }
    }

    public long insertHVS(SportsInfo sportsInfo) {
        long insertResult = -1;
        if (this.dbHelper == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("heart_rate", Integer.valueOf(sportsInfo.getHeatRate()));
            values.put("speed", Double.valueOf(sportsInfo.getSpeed()));
            values.put("step_speed", Integer.valueOf(sportsInfo.getStepSpeed()));
            values.put("start_time", Long.valueOf(sportsInfo.getStartTime()));
            values.put("get_time", Long.valueOf(sportsInfo.getGetTime()));
            values.put("item_id", Integer.valueOf(sportsInfo.getModel()));
            insertResult = db.insert("heartRate_velocity_stepSpeed", null, values);
            LogUtil.getInstance();
            LogUtil.e("kctsports", "插入hvs数据库成功");
            db.close();
            return insertResult;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "找不到hvs数据库" + e);
            return insertResult;
        }
    }

    public List<SportsInfo> getHVS(long startTime, long endTime) {
        List<SportsInfo> sportsInfosList = new ArrayList();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM heartRate_velocity_stepSpeed where start_time >= ?  AND get_time <=? ", new String[]{String.valueOf(startTime), String.valueOf(endTime)});
        SportsInfo mSportsInfo = null;
        while (cursor != null && !cursor.isClosed() && cursor.moveToNext()) {
            SportsInfo mSportsInfo2;
            try {
                mSportsInfo2 = new SportsInfo();
                try {
                    mSportsInfo2.setHeatRate(cursor.getInt(cursor.getColumnIndex("heart_rate")));
                    mSportsInfo2.setSpeed(cursor.getInt(cursor.getColumnIndex("speed")));
                    mSportsInfo2.setStepSpeed(cursor.getInt(cursor.getColumnIndex("step_speed")));
                    mSportsInfo2.setModel(cursor.getInt(cursor.getColumnIndex("item_id")));
                    sportsInfosList.add(mSportsInfo2);
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                mSportsInfo2 = mSportsInfo;
            }
            mSportsInfo = mSportsInfo2;
        }
        cursor.close();
        db.close();
        return sportsInfosList;
    }

    public void updateHVSList(long startTime, long gettime) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("get_time", Long.valueOf(gettime));
        db.update("heartRate_velocity_stepSpeed", values, "start_time=?", new String[]{String.valueOf(startTime)});
        db.close();
    }

    public int deleteHVSDate(long startTime, long endTime) {
        int date_id = 1;
        if (this.dbHelper == null) {
            return 1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            date_id = db.delete("heartRate_velocity_stepSpeed", "start_time >=?  AND get_time <=? ", new String[]{String.valueOf(startTime), String.valueOf(endTime)});
            db.close();
            return date_id;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "删除hvs数据失败" + e);
            return date_id;
        }
    }

    public long insertPace(SportsInfo sportsInfo) {
        long insertResult = -1;
        if (this.dbHelper == null) {
            return -1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("space", Integer.valueOf(sportsInfo.getPace()));
            values.put("start_time", Long.valueOf(sportsInfo.getStartTime()));
            values.put("get_time", Long.valueOf(sportsInfo.getGetTime()));
            values.put("item_id", Integer.valueOf(sportsInfo.getModel()));
            insertResult = db.insert("pace", null, values);
            LogUtil.getInstance();
            LogUtil.e("kctsports", "插入配速数据库成功");
            db.close();
            return insertResult;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "找不到配速数据库" + e);
            return insertResult;
        }
    }

    public void updatePaceList(long startTime, long gettime) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("get_time", Long.valueOf(gettime));
        db.update("pace", values, "start_time=?", new String[]{String.valueOf(startTime)});
        db.close();
    }

    public int deletePaceDate(long startTime, long endTime) {
        int date_id = 1;
        if (this.dbHelper == null) {
            return 1;
        }
        try {
            SQLiteDatabase db = this.dbHelper.getWritableDatabase();
            date_id = db.delete("pace", "start_time >=?  AND get_time <=? ", new String[]{String.valueOf(startTime), String.valueOf(endTime)});
            db.close();
            return date_id;
        } catch (Exception e) {
            LogUtil.getInstance();
            LogUtil.e("kctsports", "删除配速数据失败" + e);
            return date_id;
        }
    }

    public DatabaseHelper getDbHelper() {
        return this.dbHelper;
    }
}
