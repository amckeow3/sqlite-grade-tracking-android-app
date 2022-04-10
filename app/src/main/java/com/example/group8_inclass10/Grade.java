package com.example.group8_inclass10;

public class Grade {
    long _id;
    String courseNumber;
    String course;
    String creditHours;
    String grade;

    public Grade() {

    }

    public Grade(long _id, String courseNumber, String course, String creditHours, String grade) {
        this._id = _id;
        this.courseNumber = courseNumber;
        this.course = course;
        this.creditHours = creditHours;
        this.grade = grade;
    }

    public Grade(String courseNumber, String course, String creditHours, String grade) {
        this.courseNumber = courseNumber;
        this.course = course;
        this.creditHours = creditHours;
        this.grade = grade;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "_id=" + _id +
                ", courseNumber='" + courseNumber + '\'' +
                ", course='" + course + '\'' +
                ", creditHours='" + creditHours + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
