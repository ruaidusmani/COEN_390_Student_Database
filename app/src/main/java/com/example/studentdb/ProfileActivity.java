package com.example.studentdb;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewHeader_Surname, textViewHeader_firstName, textViewHeader_ID, textViewHeader_GPA;
    TextView textView_Surname, textView_firstName, textView_ID, textView_GPA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initialize TextViews
        textViewHeader_Surname = (TextView) findViewById(R.id.textViewHeader_Surname);
        textViewHeader_firstName = (TextView) findViewById(R.id.textViewHeader_firstName);
        textViewHeader_ID = (TextView) findViewById(R.id.textViewHeader_ID);
        textViewHeader_GPA = (TextView) findViewById(R.id.textViewHeader_GPA);

        textView_Surname = (TextView) findViewById(R.id.textView_Surname);
        textView_firstName = (TextView) findViewById(R.id.textView_firstName);
        textView_ID = (TextView) findViewById(R.id.textView_ID);
        textView_GPA = (TextView) findViewById(R.id.textView_GPA);


        Toolbar activity_main_menu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Profile Activity");


    }
}