package com.sms.sms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManagement extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "SMSUsers.db";
    public static final String TABLE_NAME = "UserInfo";
    public static final String COL_1 = "userNm";
    public static final String COL_2 = "pw";
    public static final String COL_3 = "email";


    public DatabaseManagement(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    /******************************************************
     *              onCreate
     *
     *
     * Notes:
     *      Usernames can have a maximum of 50 characters
     *      Password can have a maximum of 50 characters
     *      Email can be 100 characters long
     ******************************************************/
    public void onCreate(SQLiteDatabase db) {
        String SQLQuery = "create table " + DATABASE_NAME + " (userNm VARCHAR(50) NOT NULL,"
                                                          +  "pw VARCHAR(50) NOT NULL,"
                                                          +  "email VARCHAR(100) NOT NULL,"
                                                          +  "PRIMARY KEY (usrNm, email))";
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
