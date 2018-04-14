package com.example.owner.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Owner on 21/03/2018.
 */

public class PropertyDatabase {
    //tag for logcat output
    private static final String TAG="PropertyDatabase";
    //name of the database
    private static final String DATABASE_NAME="PropertyDatabase";
    //database version increment it every time you upgrade your database
    private static final int DATABASE_VERSION=6;
    //connection to the database
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;
    private final Context mCtx;

    public PropertyDatabase(Context context) {
        this.mCtx=context;
    }
    public SQLiteDatabase openDatabase(){
        mDbHelper=new DatabaseHelper(mCtx);
        mDb= mDbHelper.getWritableDatabase();
        return mDb;
    }

    public void closeDatabase(){
        mDb.close();
        mDb=null;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(PropertyTable.CREATE_STATEMENT);
            sqLiteDatabase.execSQL(PropertyTable.CREATE_STATEMENT_BOUGHT);
            Log.d(TAG,"database created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PropertyTable.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +PropertyTable.TABLE_NAME_BOUGHT);
            onCreate(sqLiteDatabase);
            Log.d(TAG,"sqlight upgraded");
        }
    }


}
