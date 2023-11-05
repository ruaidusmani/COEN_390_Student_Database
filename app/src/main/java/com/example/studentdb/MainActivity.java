package com.example.studentdb;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InsertProfile.DialogListener, StudentAdapter.OnItemClickListener {

    Vibrator vibrate; // button press effect

    TextView status; // holds profile numbers and sort type
    RecyclerView students_list;
    FloatingActionButton insert_profile;
    DBHelper dbHelper;
    ArrayList<Student> students = new ArrayList<Student>(); // holds student profiles to list
    boolean toggle = false; // toggle for profile name or ID
    int student_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize components
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE); // vibrate service
        insert_profile = (FloatingActionButton) findViewById(R.id.insert_profile_button); // insert profile button
        status = (TextView) findViewById (R.id.textView_status); // status textview

        // Toolbar
        Toolbar activity_main_menu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Main Activity");
        activity_main_menu.showOverflowMenu(); // Toolbar items

        // Database
        dbHelper = new DBHelper(MainActivity.this); // database helper

        // First load and display
        loadData(); // load data from database
        display_items();

        //Floating action button
        insert_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
                loadData();
                display_items();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main_menu, menu); // to show the menu items
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.activity_main_menu){ // toggle between profile name or ID
            vibrate.vibrate(50);
            toggle = !(toggle);
            loadData();
            display_items();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // opens InsertProfile Dialog
    public void openDialog(){
        vibrate.vibrate(50);
        InsertProfile dialog = new InsertProfile();
        dialog.show(getSupportFragmentManager(), "InsertProfile");
    }


    public void loadData() {
        List<Student> students_db = dbHelper.getAllStudents();

        // remove all entries of list array and re-add to update the list
        students.clear();
        students.addAll(students_db); //copy items to Activity-specific array
        student_count= students.size();

        sort_students(students, toggle); // sort the arraylist based off toggle option

        Log.d("Load", "Size of students = " + String.valueOf(students.size()));
    }

    // sorts profile name by surname alphabetically or by increasing ID
    public void sort_students(ArrayList<Student> students, boolean toggle){
        if (!toggle){
            Comparator<Student> SurnameComparator = Comparator.comparing(Student::getSurname);
            Collections.sort(students,SurnameComparator);
        }
        else{
            Comparator<Student> IDComparator = Comparator.comparing(Student::getID);
            Collections.sort(students,IDComparator);
        }
    }

    // displays the list of profiles
    public void display_items(){
        // Generating status text based off toggle
        String display_status = student_count + " profiles,";

        if (!toggle) { display_status += " by Surname."; }
        else { display_status += " by ID."; }

        status.setText(display_status);

        // populate the list
        students_list = findViewById(R.id.students_list);
        StudentAdapter adapter = new StudentAdapter(students, toggle, this);
        adapter.notifyDataSetChanged(); // if toggle is set
        students_list.setAdapter(adapter);
        students_list.setLayoutManager(new LinearLayoutManager(this));
    }

    // Navigate to ProfileActivity if a profile is clicked from the list
    public void onItemClick(int position){
        vibrate.vibrate(50);

        // Get the selected student
        Student selectedStudent = students.get(position);
        dbHelper.insertAccessRecord(selectedStudent.getID(), "Opened");

        // Start activity with student data in Intent
        Intent intent = new Intent(this, ProfileActivity.class);

        intent.putExtra("Surname", selectedStudent.getSurname());
        intent.putExtra("FirstName", selectedStudent.getFirstName());
        intent.putExtra("student_id", selectedStudent.getID());
        intent.putExtra("student_gpa", selectedStudent.getGPA());

        startActivity(intent);
    }

    // empty function to be able to use DialogFragment interface
    @Override
    public void applyTexts() {}
}