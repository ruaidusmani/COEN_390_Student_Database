package com.example.studentdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements DialogFragment.DialogListener{

    Vibrator vibrate;

    TextView textViewSurname;
    TextView textViewFirstName;
    TextView textViewID;
    TextView textViewGPA;

    FloatingActionButton insert_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSurname = (TextView) findViewById(R.id.textView_surname);
        textViewFirstName = (TextView) findViewById(R.id.textView_firstName);
        textViewID = (TextView) findViewById(R.id.textView_ID);
        textViewGPA = (TextView) findViewById(R.id.textView_GPA);


        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE); // vibrate service
        insert_profile = (FloatingActionButton) findViewById(R.id.insert_profile_button); // insert profile button

        Toolbar activity_main_menu = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(activity_main_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Main Activity");

        //Toolbar items
        activity_main_menu.showOverflowMenu();

        //Floating action button
        insert_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrate.vibrate(50);
                OpenDialog();
            }
        });

    }

    public void OpenDialog(){
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "Dialog Fragment");
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

    @Override
    public void applyTexts(String surname, String firstName, String ID, String GPA) {
        textViewSurname.setText(surname);
        textViewFirstName.setText(firstName);
        textViewID.setText(ID);
        textViewGPA.setText(GPA);

    }
}