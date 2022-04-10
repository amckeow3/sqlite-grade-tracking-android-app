package com.example.group8_inclass10;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GradesDAO {
    private SQLiteDatabase db;

    public GradesDAO(SQLiteDatabase db) {
        this.db = db;
    }

    //CRUD Operations
    // CREATE, READ, UPDATE, DELETE

    public long save(Grade grade) {
        ContentValues values = new ContentValues();
        values.put(GradesTable.COLUMN_COURSE_NUMBER, grade.getCourseNumber());
        values.put(GradesTable.COLUMN_COURSE, grade.getCourse());
        values.put(GradesTable.COLUMN_CREDIT_HOURS, grade.getCreditHours());
        values.put(GradesTable.COLUMN_GRADE, grade.getGrade());


        return db.insert(GradesTable.TABLE_NAME, null, values);
    }

    public boolean update(Grade grade) {
        ContentValues values = new ContentValues();
        values.put(GradesTable.COLUMN_COURSE_NUMBER, grade.getCourseNumber());
        values.put(GradesTable.COLUMN_COURSE, grade.getCourse());
        values.put(GradesTable.COLUMN_CREDIT_HOURS, grade.getCreditHours());
        values.put(GradesTable.COLUMN_GRADE, grade.getGrade());


        return db.update(GradesTable.TABLE_NAME, values, GradesTable.COLUMN_ID + " = ?", new String[]{String.valueOf(grade.get_id())}) > 0;
    }

    public boolean delete(Grade grade) {
        return db.delete(GradesTable.TABLE_NAME, GradesTable.COLUMN_ID + " = ?", new String[]{String.valueOf(grade.get_id())}) > 0;
    }

    public boolean delete(long id) {
        return db.delete(GradesTable.TABLE_NAME, GradesTable.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public Grade get(long id) {
        Grade grade = null;
        Cursor cursor = db.query(GradesTable.TABLE_NAME,
                new String[]{GradesTable.COLUMN_ID, GradesTable.COLUMN_COURSE_NUMBER, GradesTable.COLUMN_COURSE, GradesTable.COLUMN_CREDIT_HOURS, GradesTable.COLUMN_GRADE},
                GradesTable.COLUMN_COURSE + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            grade = buildGradeFromCursor(cursor);

        }
        return grade;
    }

    public ArrayList<Grade> getAll() {
        ArrayList<Grade> grades = new ArrayList<>();
        Cursor cursor = db.query(GradesTable.TABLE_NAME,
                new String[]{GradesTable.COLUMN_ID, GradesTable.COLUMN_COURSE_NUMBER, GradesTable.COLUMN_COURSE, GradesTable.COLUMN_CREDIT_HOURS, GradesTable.COLUMN_GRADE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Grade grade = buildGradeFromCursor(cursor);
            grades.add(grade);
        }

        return grades;
    }

    private Grade buildGradeFromCursor(Cursor cursor) {
        Grade grade = new Grade();
        grade.set_id(cursor.getLong(0));
        grade.setCourseNumber(cursor.getString(1));
        grade.setCourse(cursor.getString(2));
        grade.setCreditHours(cursor.getString(3));
        grade.setGrade(cursor.getString(4));
        return grade;
    }
}
