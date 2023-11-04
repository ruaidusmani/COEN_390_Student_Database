package com.example.studentdb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context = null;
    public static final int DATABASE_VERSION = 1;


    public DBHelper(@Nullable Context context) {
        super(context, Config.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create Student Profile Table
        String profile_query = "CREATE TABLE " + Config.PROFILE_TABLE_NAME +
                " (" + Config.COLUMN_SURNAME + " TEXT NOT NULL, " +
                Config.COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                Config.COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Config.COLUMN_GPA + " REAL);";

        db.execSQL(profile_query);

        //Create Access Profile Table
        String access_query = "CREATE TABLE " + Config.ACCESS_TABLE_NAME +
                " (" + Config.COLUMN_ACCESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Config.COLUMN_ACCESS_PROFILE_ID + " INTEGER, " +  // Remove PRIMARY KEY
                Config.COLUMN_ACCESS_TYPE + " TEXT NOT NULL, " +
                Config.COLUMN_ACCESS_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
//        " +
//                "FOREIGN KEY (" + Config.COLUMN_PROFILE_ID + ") REFERENCES " + Config.PROFILE_TABLE_NAME + "(" + Config.COLUMN_PROFILE_ID + "));";

        db.execSQL(access_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.PROFILE_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS "+ Config.ACCESS_TABLE_NAME);
        onCreate(db);
    }

    void addStudent(Student Student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_SURNAME,Student.getSurname());
        cv.put(Config.COLUMN_FIRSTNAME,Student.getFirstName());
        cv.put(Config.COLUMN_PROFILE_ID,Student.getID());
        cv.put(Config.COLUMN_GPA,Student.getGPA());

        long result = db.insert(Config.PROFILE_TABLE_NAME, null, cv);


        if (result == -1){
            Toast.makeText(context, "Failed to add Student", Toast.LENGTH_SHORT).show();
        }
        else{
//            Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();

        insertAccessRecord(Student.getID(), "Created");
    }

    public void insertAccessRecord(int student_id, String access_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Config.COLUMN_ACCESS_PROFILE_ID, student_id);
        cv.put(Config.COLUMN_ACCESS_TYPE, access_type);

        long result = db.insert(Config.ACCESS_TABLE_NAME, null, cv);

//        if (result == -1){
//            Toast.makeText(context, "Failed to add Access Record", Toast.LENGTH_SHORT).show();
//        }
//        else{}
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + Config.PROFILE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public Student getSelectedStudent(int student_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                Config.COLUMN_PROFILE_ID,
                Config.COLUMN_SURNAME,
                Config.COLUMN_FIRSTNAME,
                Config.COLUMN_GPA
        };

        String selection = Config.COLUMN_PROFILE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(student_id) };

        Cursor cursor = db.query(
                Config.PROFILE_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Student student = null;

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SURNAME));
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FIRSTNAME));
            @SuppressLint("Range") int ID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PROFILE_ID));
            @SuppressLint("Range") float GPA = cursor.getFloat(cursor.getColumnIndex(Config.COLUMN_GPA));


            student = new Student(surname, firstName, ID, GPA);
            cursor.close();
        }

        db.close();

        return student;
    }

    public List<String> getSelectedStudentAccessRecords(int student_id){


        SQLiteDatabase db = this.getReadableDatabase();

        Log.d("DBHELPER ACCESS RECORDS", "First Log");
        String[] columns = {
                Config.COLUMN_ACCESS_ID,
                Config.COLUMN_ACCESS_PROFILE_ID,
                Config.COLUMN_ACCESS_TYPE,
                Config.COLUMN_ACCESS_TIMESTAMP
        };

        String selection = Config.COLUMN_ACCESS_PROFILE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(student_id) };

        Cursor cursor = db.query(
                Config.ACCESS_TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List <String> accessRecords = new ArrayList<>();


        if (cursor != null && cursor.moveToFirst()) {

            // TODO: Add all access records to list, it is only adding creation

            @SuppressLint("Range") String timestamp = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_TIMESTAMP));
            @SuppressLint("Range") String access_type = cursor.getString(cursor.getColumnIndex(Config.COLUMN_ACCESS_TYPE));

            accessRecords.add(timestamp + " " + access_type);
            Log.d("DBHELPER ACCESS RECORDS", "getSelectedStudentAccessRecords: " + timestamp + " " + access_type);
            cursor.close();
        }

        for (String record : accessRecords){
            Log.d("DBHELPER ACCESS RECORDS", "getSelectedStudentAccessRecords: " + record);
        }
        db.close();

        return accessRecords;

    }



    public List<Student> getAllStudents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Student> students = new ArrayList<>();

        try{
            cursor = db.query(Config.PROFILE_TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SURNAME));
                        @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FIRSTNAME));
                        @SuppressLint("Range") int ID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_PROFILE_ID));
                        @SuppressLint("Range") float GPA = cursor.getFloat(cursor.getColumnIndex(Config.COLUMN_GPA));

                        Student student = new Student(surname, firstName, ID, GPA);
                        students.add(student);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e){
            Toast.makeText(context, "DB get failed: "+ e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            db.close();
        }
        return students;
    }
}
