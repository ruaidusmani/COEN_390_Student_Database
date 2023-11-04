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

    Vibrator vibrate;

    TextView status;
    RecyclerView students_list;
    FloatingActionButton insert_profile;
    DBHelper dbHelper;
    ArrayList<Student> students = new ArrayList<Student>();
    boolean toggle = false;
    int student_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize components
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE); // vibrate service
        insert_profile = (FloatingActionButton) findViewById(R.id.insert_profile_button); // insert profile button
        status = (TextView) findViewById (R.id.textView_status); // status textview

        Toolbar activity_main_menu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Main Activity");
        activity_main_menu.showOverflowMenu(); // Toolbar items

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

        if (id == R.id.activity_main_menu){ // toggle menu item
            toggle = !(toggle);
            loadData();
            display_items();
            vibrate.vibrate(50);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDialog(){
        vibrate.vibrate(50);
        Log.d("TAG", "FAB pressed!");
        InsertProfile dialog = new InsertProfile();
        dialog.show(getSupportFragmentManager(), "InsertProfile");
    }


    public void loadData() {
        List<Student> students_db = dbHelper.getAllStudents();

        Log.d("Load", students_db.get(0).getFirstName());
        Log.d("Load", students_db.get(0).getSurname());
        Log.d("Load", String.valueOf(students_db.get(0).getID()));
        Log.d("Load", "Size of db = " + String.valueOf(students_db.size()));

        // remove all entries of arraylist
        students.clear();
        students.addAll(students_db); //copy items to class-specific array
        student_count= students.size();

        sort_students(students, toggle);
        //TODO: sort the arraylist function

        Log.d("Load", "Size of students = " + String.valueOf(students.size()));
    }

    public void sort_students(ArrayList<Student> students, boolean toggle){
        //sort arraylist based off surname alphabetically increasing order

        if (!toggle){
            Comparator<Student> SurnameComparator = Comparator.comparing(Student::getSurname);

            // Sort the ArrayList using the custom comparator
            Collections.sort(students,SurnameComparator);
        }
        else{
            Comparator<Student> IDComparator = Comparator.comparing(Student::getID);

            // Sort the ArrayList using the custom comparator
            Collections.sort(students,IDComparator);
        }

        // Print the sorted list
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void display_items(){
        String display_status = String.valueOf(student_count) + " profiles,";
        if (!toggle){
            display_status += " by Surname.";
        }
        else{
            display_status += " by ID.";
        }

        status.setText(display_status);
        Log.d("Status", "display_items");
        Log.d("Student added", String.valueOf(students.size()));
        students_list = findViewById(R.id.students_list);
        StudentAdapter adapter = new StudentAdapter(students, toggle, this);
        adapter.notifyDataSetChanged(); // if toggle is set
        students_list.setAdapter(adapter);
        students_list.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onItemClick(int position){
        // Get the selected student
        Student selectedStudent = students.get(position);

        // Create an intent to navigate to the ProfileActivity
        Intent intent = new Intent(this, ProfileActivity.class);

        // Pass the selected student's data to the ProfileActivity
        intent.putExtra("Surname", selectedStudent.getSurname());
        intent.putExtra("FirstName", selectedStudent.getFirstName());
        intent.putExtra("student_id", selectedStudent.getID());
        intent.putExtra("student_gpa", selectedStudent.getGPA());

        // Start the ProfileActivity
        startActivity(intent);
    }


    @Override
    public void applyTexts() {

    }
}