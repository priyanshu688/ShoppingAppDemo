package com.example.coertutorial.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.ActionBar;

import com.example.coertutorial.model.User;

// we need to extends SQLiteOpenHelper class
public class MyLocalDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shopping";

    private static final String COL_NAME = "name";
    private static final String COL_MOBILE = "mobile";
    private static final String COL_PASSWORD = "pass";
    private static final String COL_STATE = "state";
    private static final String COL_EMAIL = "email";
    private static final String TABLE_NAME = "users";

    private static final int DATABASE_VERSION = 1;

    public MyLocalDatabase(Context context) {// loose coupling
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //table creation code here
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (" + COL_NAME + " varchar(30), " + COL_MOBILE + " long(10) , " + COL_PASSWORD + " varchar(15), " + COL_STATE + " varchar(20)," + COL_EMAIL + " varchar(35) primary key);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // table upgrade code here
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME + "");
        onCreate(sqLiteDatabase);
    }

    public long insert(User user){
        ContentValues cv=new ContentValues();
        cv.put(COL_NAME,user.getName());
        cv.put(COL_EMAIL,user.getEmail());
        cv.put(COL_PASSWORD,user.getPass());
        cv.put(COL_STATE,user.getState());
        cv.put(COL_MOBILE,user.getMobile());
        return getWritableDatabase().insert(TABLE_NAME,null,cv);
    }

    public boolean login(String email,String pass){
        Cursor cursor = getReadableDatabase().query(TABLE_NAME,null,"" + COL_EMAIL + "=? and " + COL_PASSWORD + "=?",new String[]{email , pass},null,null,null);
       return cursor.moveToNext();
    }

    public User getUserByEmail(String email){
        User user = null;
      Cursor cursor = getReadableDatabase().query(TABLE_NAME,null,""+COL_EMAIL+ " = ?",new String[]{email},null,null,null,null);
        if (cursor.moveToNext()){
            user = new User();
            user.setName(cursor.getString(0));
            user.setMobile(cursor.getLong(1));
            user.setPass(cursor.getString(2));
            user.setState(cursor.getString(3));
            user.setEmail(cursor.getString(4));
        }
        return user;
    }
    public long updateUser(User user){
        ContentValues cv=new ContentValues();
        cv.put(COL_NAME,user.getName());
        cv.put(COL_EMAIL,user.getEmail());
        cv.put(COL_PASSWORD,user.getPass());
        cv.put(COL_STATE,user.getState());
        cv.put(COL_MOBILE,user.getMobile());
        return getWritableDatabase().update(TABLE_NAME,cv,"" + COL_EMAIL + "=?",new String[]{user.getEmail()});
    }

    public long deleteAccount(String email){
       return getWritableDatabase().delete(TABLE_NAME,"" + COL_EMAIL + "=?",new String[]{email});
    }
}
