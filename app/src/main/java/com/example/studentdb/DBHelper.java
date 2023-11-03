package com.example.studentdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

//        db.insert(Config.TABLE_NAME, null, cv);
        long result = db.insert(Config.TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Failed to add Student", Toast.LENGTH_SHORT).show();
        }
        else{
//            Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show();
        }


    }
}
