package com.example.studentdb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    Vibrator vibrate;
    TextView textViewHeader_Surname, textViewHeader_firstName, textViewHeader_ID, textViewHeader_GPA,
            textViewHeader_ProfileCreated,
            textView_Surname, textView_firstName, textView_ID, textView_GPA, textView_ProfileCreated;
    DBHelper dbHelper;

    RecyclerView AccessHistory; // list for showing access records of profile

    ArrayList<String> access_records = new ArrayList<String>(); // holds access records of profile

    Student selected_student; // holds selected student profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE); // vibrate service

        // Toolbar
        Toolbar profile_toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(profile_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Profile Activity");

        //Toolbar items
        profile_toolbar.showOverflowMenu();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // up navigation
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialize TextViews
        textViewHeader_Surname = (TextView) findViewById(R.id.textViewHeader_Surname);
        textViewHeader_firstName = (TextView) findViewById(R.id.textViewHeader_firstName);
        textViewHeader_ID = (TextView) findViewById(R.id.textViewHeader_ID);
        textViewHeader_GPA = (TextView) findViewById(R.id.textViewHeader_GPA);
        textViewHeader_ProfileCreated = (TextView) findViewById(R.id.textViewHeader_ProfileCreated);

        textView_Surname = (TextView) findViewById(R.id.textView_Surname);
        textView_firstName = (TextView) findViewById(R.id.textView_firstName);
        textView_ID = (TextView) findViewById(R.id.textView_ID);
        textView_GPA = (TextView) findViewById(R.id.textView_GPA);
        textView_ProfileCreated = (TextView) findViewById(R.id.textView_ProfileCreated);

        // initialize selected student with the information from the Intent's extras
        selected_student= new Student(
                getIntent().getStringExtra("Surname"),
                getIntent().getStringExtra("FirstName"),
                getIntent().getIntExtra("student_id", -1),
                getIntent().getFloatExtra("student_gpa", -1)
        );

        //???
        //int student_id = (getIntent().getIntExtra("student_id", -1)); // Replace "key" with the key you used
        dbHelper = new DBHelper(ProfileActivity.this);
//        selected_student = dbHelper.getSelectedStudent(student_id);
        loadAccessRecords(selected_student.getID());

        Log.d("Profile Created Access Record", access_records.get(access_records.size()-1));
        Log.d("Selected Student", selected_student.getSurname() + " " + selected_student.getFirstName() + " " + selected_student.getID() + " " + selected_student.getGPA());

        set_StudentInfo(selected_student, access_records.get(access_records.size()-1));
        display_records();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.profile_main_menu){ // deleting student profile from Profile Table
            vibrate.vibrate(50);
            dbHelper.insertAccessRecord(selected_student.getID(), "Deleted");
            loadAccessRecords(selected_student.getID());
            display_records();
            dbHelper.deleteStudent(selected_student.getID());

            Log.d("Deleted", "Deleted Student");
            return true;
        }

        else if (id == android.R.id.home){
            vibrate.vibrate(50);
            dbHelper.insertAccessRecord(selected_student.getID(), "Closed");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void set_StudentInfo(Student student, String profile_creation_string){
        textView_Surname.setText(student.getSurname());
        textView_firstName.setText(student.getFirstName());
        textView_ID.setText(Integer.toString(student.getID()));
        textView_GPA.setText(Float.toString(student.getGPA()));
        textView_ProfileCreated.setText(profile_creation_string);
    }

    public void loadAccessRecords(int student_id) {
        List<String> access_records_db = dbHelper.getSelectedStudentAccessRecords(student_id);

        // remove all entries of arraylist
        access_records.clear();
        access_records.addAll(access_records_db); //copy items to class-specific array
        Collections.reverse(access_records);

        Log.d("Printing Access Records", "Printing Access Records");
        for (int i=0; i<access_records_db.size(); i++){
            Log.d("AccessRecords", access_records_db.get(i));
        }
    }

    public void display_records(){
        AccessHistory = findViewById(R.id.AccessHistory);
        AccessRecordsAdapter adapter = new AccessRecordsAdapter(access_records);
        adapter.notifyDataSetChanged(); // refreshes the list if a record is added to Access Tables
        AccessHistory.setAdapter(adapter);
        AccessHistory.setLayoutManager(new LinearLayoutManager(this));
    }
}