package com.example.studentdb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
        String query = "CREATE TABLE " + Config.TABLE_NAME +
                " (" + Config.COLUMN_SURNAME + " TEXT NOT NULL, " +
                Config.COLUMN_FIRSTNAME + " TEXT NOT NULL, " +
                Config.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Config.COLUMN_GPA + " REAL);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_NAME);
        onCreate(db);
    }

    void addStudent(Student Student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(Config.COLUMN_SURNAME,Student.getSurname());
        cv.put(Config.COLUMN_FIRSTNAME,Student.getFirstName());
        cv.put(Config.COLUMN_ID,Student.getID());
        cv.put(Config.COLUMN_GPA,Student.getGPA());

        long result = db.insert(Config.TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed to add Student", Toast.LENGTH_SHORT).show();
        }
        else{
//            Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show();
        }
    }


    Cursor readAllData(){
        String query = "SELECT * FROM " + Config.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }



    public List<Student> getAllStudents(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Student> students = new ArrayList<>();

        try{
            cursor = db.query(Config.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do{
                        @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(Config.COLUMN_SURNAME));
                        @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(Config.COLUMN_FIRSTNAME));
                        @SuppressLint("Range") int ID = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_ID));
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
