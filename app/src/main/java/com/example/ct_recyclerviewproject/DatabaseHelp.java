package com.example.ct_recyclerviewproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ct_recyclerviewproject.Model.DatabaseModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelp extends SQLiteOpenHelper{

    // Database Info
    private static final String DATABASE_NAME = "MyWeatherDb_test";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "weather_data_1";

    private static final String COLUMN_ID="id";
    private static final String COLUMN_DATE="date";
    private static final String COLUMN_MIN_TEMP ="min_temp";
    private static final String COLUMN_MAX_TEMP ="max_temp";
    private static final String COLUMN_HUMIDITY ="humidity";
    private static final String COLUMN_PRESSURE="pressure";
    private static final String COLUMN_DESCRIPTION="description";
    private static final String COLUMN_ICON_TEXT="icon";


    public DatabaseHelp(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME +
                " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_MIN_TEMP + " TEXT, " +
                COLUMN_MAX_TEMP + " TEXT, " +
                COLUMN_HUMIDITY + " TEXT, " +
                COLUMN_PRESSURE+ " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_ICON_TEXT + " TEXT " +
                " ) ";

        System.out.println("Test  "+CREATE_QUERY);
        db.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void insertUserDetail(DatabaseModel weatherData) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, weatherData.getDate());
            values.put(COLUMN_MIN_TEMP, weatherData.getMinTemp());
            values.put(COLUMN_MAX_TEMP, weatherData.getMaxTemp());
            values.put(COLUMN_HUMIDITY, weatherData.getHumidity());
            values.put(COLUMN_PRESSURE, weatherData.getPressure());
            values.put(COLUMN_DESCRIPTION, weatherData.getDescription());
            values.put(COLUMN_ICON_TEXT, weatherData.getIcon());

            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Test  "+e.toString());
        } finally {
            System.out.println("Test  ");
            db.endTransaction();
        }
    }

    public List<DatabaseModel> getAllUser() {
        List<DatabaseModel> usersdetail = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                    System.out.println("Test  "+date);
                    String min_temp = cursor.getString(cursor.getColumnIndex(COLUMN_MIN_TEMP));
                    String max_temp = cursor.getString(cursor.getColumnIndex(COLUMN_MAX_TEMP));
                    String humidity = cursor.getString(cursor.getColumnIndex(COLUMN_HUMIDITY));
                    String pressure = cursor.getString(cursor.getColumnIndex(COLUMN_PRESSURE));
                    String desc = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                    String icon = cursor.getString(cursor.getColumnIndex(COLUMN_ICON_TEXT));
                    DatabaseModel userData = new DatabaseModel(date,min_temp,max_temp,pressure,humidity,desc,icon);

                    usersdetail.add(userData);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            System.out.println("Test  "+e.toString());
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return usersdetail;

    }

}
