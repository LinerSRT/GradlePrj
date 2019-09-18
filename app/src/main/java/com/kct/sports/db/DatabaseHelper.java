package com.kct.sports.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "kct_sports.db", null, 2);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS step_count_detail (_id INTEGER PRIMARY KEY,item_id INTEGER,get_time LONG NOT NULL,start_time LONG NOT NULL,time_take LONG NOT NULL,pause_time INTEGER,pause_times INTEGER,step_count INTEGER DEFAULT 100,max_step_width DOUBLE,min_step_width DOUBLE,ave_step_width DOUBLE,speed DOUBLE,max_speed DOUBLE,min_speed DOUBLE,vertical_speed DOUBLE,horizontal_speed DOUBLE,step_speed INTEGER,max_step_speed INTEGER,min_step_speed INTEGER,distance DOUBLE,heart_rate INTEGER,max_heart_rate INTEGER,min_heart_rate INTEGER,altitude DOUBLE,kcal DOUBLE,space INTEGER,best_space INTEGER,best_space_distance INTEGER,lowest_space INTEGER,lowest_space_distance INTEGER,sum_up DOUBLE,sum_down DOUBLE,o2_need DOUBLE,training_intensity STRING,ave_step_speed INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS trace (trace_id INTEGER PRIMARY KEY,Latitude DOUBLE,Longitude DOUBLE,altitude DOUBLE,start_time LONG NOT NULL,get_time LONG NOT NULL,item_id INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS heartRate_velocity_stepSpeed (_id INTEGER PRIMARY KEY,item_id INTEGER,start_time LONG NOT NULL,get_time LONG NOT NULL,heart_rate INTEGER,speed DOUBLE,step_speed INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS pace (_id INTEGER PRIMARY KEY,item_id INTEGER,start_time LONG NOT NULL,get_time LONG NOT NULL,space INTEGER);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS step_count_detail");
        db.execSQL("DROP TABLE IF EXISTS trace");
        db.execSQL("DROP TABLE IF EXISTS heartRate_velocity_stepSpeed");
        db.execSQL("DROP TABLE IF EXISTS pace");
        onCreate(db);
    }
}
