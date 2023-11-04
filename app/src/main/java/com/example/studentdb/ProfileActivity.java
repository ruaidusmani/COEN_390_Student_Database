package com.example.studentdb;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewHeader_Surname, textViewHeader_firstName, textViewHeader_ID, textViewHeader_GPA;
    TextView textView_Surname, textView_firstName, textView_ID, textView_GPA;
    DBHelper dbHelper;

    RecyclerView AccessHistory;

    ArrayList<String> access_records = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar activity_main_menu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Profile Activity");

        //Initialize TextViews
        textViewHeader_Surname = (TextView) findViewById(R.id.textViewHeader_Surname);
        textViewHeader_firstName = (TextView) findViewById(R.id.textViewHeader_firstName);
        textViewHeader_ID = (TextView) findViewById(R.id.textViewHeader_ID);
        textViewHeader_GPA = (TextView) findViewById(R.id.textViewHeader_GPA);

        textView_Surname = (TextView) findViewById(R.id.textView_Surname);
        textView_firstName = (TextView) findViewById(R.id.textView_firstName);
        textView_ID = (TextView) findViewById(R.id.textView_ID);
        textView_GPA = (TextView) findViewById(R.id.textView_GPA);


        int student_id = (getIntent().getIntExtra("student_id", -1)); // Replace "key" with the key you used
        dbHelper = new DBHelper(ProfileActivity.this);
        Student selected_student = dbHelper.getSelectedStudent(student_id);
        set_StudentInfo(selected_student);

        loadAccessRecords(student_id);
        display_records();


    }

    public void set_StudentInfo(Student student){
        textView_Surname.setText(student.getSurname());
        textView_firstName.setText(student.getFirstName());
        textView_ID.setText(Integer.toString(student.getID()));
        textView_GPA.setText(Double.toString(student.getGPA()));

        //TODO: set Profile Creation Date
    }


    public void loadAccessRecords(int student_id) {
        List<String> access_records_db = dbHelper.getSelectedStudentAccessRecords(student_id);


        // remove all entries of arraylist
        access_records.clear();
        access_records.addAll(access_records_db); //copy items to class-specific array

        Log.d("Printing Access Records", "Printing Access Records");
        for (int i=0; i<access_records_db.size(); i++){
            Log.d("AccessRecords", access_records_db.get(i));
        }
    }


    public void display_records(){
        AccessHistory = findViewById(R.id.AccessHistory);
        AccessRecordsAdapter adapter = new AccessRecordsAdapter(access_records);
        adapter.notifyDataSetChanged(); // if toggle is set
        AccessHistory.setAdapter(adapter);
        AccessHistory.setLayoutManager(new LinearLayoutManager(this));
    }
}