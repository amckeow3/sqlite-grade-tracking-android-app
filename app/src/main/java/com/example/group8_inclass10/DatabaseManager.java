package com.example.group8_inclass10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    Context mContext;
    SQLiteDatabase db;
    DatabaseHelper dbOpenHelper;
    GradesDAO gradesDAO;

    public DatabaseManager(Context context) {
        this.mContext = context;
        dbOpenHelper = new DatabaseHelper(mContext);
        // Get database instance. Triggers the created of the database - if the database exists it is going to open
        // If the database DOES NOT exist, onCreate() is going to be called
        // if db exists but the versions are different, onUpgrade is going to be called then onCreate
        db = dbOpenHelper.getWritableDatabase();
        gradesDAO = new GradesDAO(db);
    }

    public GradesDAO getGradesDAO() {
        return gradesDAO;
    }

    public void setGradesDAO(GradesDAO gradesDAO) {
        this.gradesDAO = gradesDAO;
    }
}
