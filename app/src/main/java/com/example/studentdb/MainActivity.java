package com.example.studentdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InsertProfile.DialogListener{

    Vibrator vibrate;

    RecyclerView students_list;

    FloatingActionButton insert_profile;

    DBHelper dbHelper;
    ArrayList<Student> students = new ArrayList<Student>();

    boolean toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE); // vibrate service
        insert_profile = (FloatingActionButton) findViewById(R.id.insert_profile_button); // insert profile button

        dbHelper = new DBHelper(MainActivity.this); // database helper

        toggle = false;



        Toolbar activity_main_menu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Main Activity");

        //Toolbar items
        activity_main_menu.showOverflowMenu();

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


    public void openDialog(){
        vibrate.vibrate(50);
        Log.d("TAG", "FAB pressed!");
        InsertProfile dialog = new InsertProfile();
        dialog.show(getSupportFragmentManager(), "InsertProfile");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main_menu, menu); // to show the menu items
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.activity_main_menu){ // toggle menu item
            vibrate.vibrate(50);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadData() {
        List<Student> students_db = dbHelper.getAllStudents();


        Log.d("Load", students_db.get(0).getFirstName());
        Log.d("Load", students_db.get(0).getSurname());
        Log.d("Load", String.valueOf(students_db.get(0).getID()));
        Log.d("Load", "Size of db = " + String.valueOf(students_db.size()));

        students.addAll(students_db); //copy items to class-specific array
        Log.d("Load", "Size of students = " + String.valueOf(students.size()));
    }

    public void display_items(){
        Log.d("Status", "display_items");
        Log.d("Student added", String.valueOf(students.size()));
        students_list = findViewById(R.id.students_list);
        StudentAdapter adapter = new StudentAdapter(students, toggle);
        adapter.notifyDataSetChanged(); // if toggle is set
        students_list.setAdapter(adapter);
        students_list.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void applyTexts() {

    }
}