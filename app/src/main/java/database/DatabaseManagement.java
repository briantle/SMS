package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;

/******************************************************************************
 *
 *
 * Notes:
 *      this.getWritableDatabase - this opens the database to be written to
 *      this.getReadableDatabase - this opens the database to be read from
 ****************************************************************************/
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
    /**************************************************************
     *
     *
     *
     *
     *******************************************************************/
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

    /**
     *
     * @param username
     * @return
     */
    public boolean doesUserExist(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String[] selectionArgs = {username};
        String sqlQuery = "SELECT userNm from " + TABLE_NAME + " WHERE userNm = ?";
        cursor = db.rawQuery(sqlQuery, selectionArgs);

        int count = cursor.getCount();
        cursor.close();

        if (count == 0)
            return false;
        return true;
    }
    public boolean doesEmailExist(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String[] selectionArgs = {email};
        String sqlQuery = "SELECT email from " + TABLE_NAME + " WHERE email = ?";
        cursor = db.rawQuery(sqlQuery, selectionArgs);

        int count = cursor.getCount();
        cursor.close();

        if (count == 0)
            return false;
        return true;
    }
    public boolean findUser(NestedScrollView nestedScrollView, String username, String password, String errMsg){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selectionArgs = {username, password};
        String sqlQuery = "SELECT userNm, pw from " + TABLE_NAME + " WHERE userNm = ? and pw = ?";
        Cursor cursor = db.rawQuery(sqlQuery, selectionArgs);

        int count = cursor.getCount();
        cursor.close();

        // If the query returned no results, that means that either the user doesn't exist
        // or the user passed in wrong information
        if (count == 0) {
            Snackbar.make(nestedScrollView, errMsg, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
