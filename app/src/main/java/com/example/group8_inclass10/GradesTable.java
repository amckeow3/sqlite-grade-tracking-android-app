package com.example.group8_inclass10;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GradesTable {
    static final String TABLE_NAME = "grades";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_COURSE_NUMBER = "courseNumber";
    static final String COLUMN_COURSE = "course";
    static final String COLUMN_CREDIT_HOURS = "creditHours";
    static final String COLUMN_GRADE = "grade";


    // Called whenever you want to create the table itself
    // Where schema creation code for this table goes -- use string builder
    static public void onCreate(SQLiteDatabase db) {
        StringBuilder sb = new StringBuilder();

        //CREATE TABLE grades (_id integer primary key autoincrement, course text not null, credit_hours integer not null, grade text not null, grade_points integer not null)
        sb.append("CREATE TABLE " + GradesTable.TABLE_NAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_COURSE_NUMBER + " text not null, ");
        sb.append(COLUMN_COURSE + " text not null, ");
        sb.append(COLUMN_CREDIT_HOURS + " text not null, ");
        sb.append(COLUMN_GRADE + " text not null);");


        // Need try catch because db.execSQL can cause an exception if the SQL string is invalid.
        try {
            db.execSQL(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + GradesTable.TABLE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
