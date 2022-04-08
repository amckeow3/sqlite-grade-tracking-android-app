package com.example.group8_inclass10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "grades.db";
    final static int DATABASE_VERSION = 1;
    private static final String TAG = "database helper";

    public DatabaseHelper(@Nullable Context mContext) {
        super(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "onOpen: ");
    }

    // Called if database has not yet been created (there is no db file on the system)
    // On start, tries to find "grades.db" file and calls create when it cannot find it.
    // Point where tables are created/initialized
    @Override
    public void onCreate(SQLiteDatabase db) {
        GradesTable.onCreate(db);
        Log.d(TAG, "onCreate: ");
    }

    // When there is a "grades.db" file in the system but it has a version that does not equal the
    // version that is listed above.
    // Allows you to backup your db before a new version of the db is created.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        GradesTable.onUpgrade(db, oldVersion, newVersion);
        Log.d(TAG, "onUpgrade: ");
    }
}
