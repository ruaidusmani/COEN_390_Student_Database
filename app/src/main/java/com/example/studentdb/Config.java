// Has all the names of the Database, its tables and their columns

package com.example.studentdb;

public class Config {
    public static final String DATABASE_NAME = "Student.db";

    // Profile Table
    public static final String PROFILE_TABLE_NAME = "student_profile";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_FIRSTNAME = "firstName";
    public static final String COLUMN_PROFILE_ID = "_id";
    public static final String COLUMN_GPA = "gpa";

    // Access Table
    public static final String ACCESS_TABLE_NAME = "access_profile";
    public static final String COLUMN_ACCESS_ID = "_id";
    public static final String COLUMN_ACCESS_PROFILE_ID = "profile_id";
    public static final String COLUMN_ACCESS_TYPE = "type";
    public static final String COLUMN_ACCESS_TIMESTAMP = "timestamp";
}
