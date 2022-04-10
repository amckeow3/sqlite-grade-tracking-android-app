

// In Class 10
// Group8_InClass10
// Adrianna McKeown

package com.example.group8_inclass10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements GradesFragment.GradesFragmentListener, AddCourseFragment.AddCourseFragmentListener {

    private static final String TAG = "main activity";
    Grade grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new GradesFragment(), "grades-fragment")
                .commit();
    }

    @Override
    public void goToAddCourse() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddCourseFragment(), "add-course-fragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteAndRefresh() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new GradesFragment(), "add-course-fragment")
                .commit();
    }

    @Override
    public void cancelNewCourse() {
        getSupportFragmentManager()
                .popBackStack();
    }

    @Override
    public void addNewCourseAndRefresh() {
        getSupportFragmentManager()
                .popBackStack();
    }
}