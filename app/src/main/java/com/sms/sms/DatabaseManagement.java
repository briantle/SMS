package com.sms.sms;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManagement extends SQLiteOpenHelper
{

    public static final String DATABASE_NAME = "SMSUsers.db";
    public static final String TABLE_NAME = "UserInfo";
    public static final String COL_USERID = "ID";
    public static final String COL_USERNM = "userNm";
    public static final String COL_PASSWORD = "pw";
    public static final String COL_EMAIL = "email";


    public DatabaseManagement(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // Writes to database
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
        // create table sql query
        String SQLQuery = "CREATE TABLE " + TABLE_NAME + "(" +
                           COL_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                           COL_USERNM + " VARCHAR(50) NOT NULL, " +
                           COL_PASSWORD + " VARCHAR(50) NOT NULL," +
                           COL_EMAIL + " VARCHAR(100) NOT NULL " + ");";
        // create the table
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // create the tables again
        onCreate(db);
    }
    /******************
     *          createUser
     *
     *********************/
    public void createUser(String userName, String password, String email){
        // Writes to database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // Get row values
        values.put(COL_USERNM, userName);
        values.put(COL_PASSWORD, password);
        values.put(COL_EMAIL, email);

        // Insert row into table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
