package com.example.studentdb;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class InsertProfile extends AppCompatDialogFragment  {
    Vibrator vibrate; // button press effect
    private EditText editTextSurname, editTextFirstName, editTextID, editTextGPA;
    private DialogListener listener; // interface

    private AlertDialog dialog;
    DBHelper dbHelper;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_fragment, null);

        // initialize components
        vibrate = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE); // vibrate service
        editTextSurname = view.findViewById(R.id.editText_student_surname);
        editTextFirstName = view.findViewById(R.id.editText_student_firstName);
        editTextID = view.findViewById(R.id.editText_student_ID);
        editTextGPA = view.findViewById(R.id.editText_student_GPA);

        // Database
        dbHelper = new DBHelper(getActivity());

        builder.setView(view);

        setNegativeButton(builder); // cancel button
        setPositiveButton(builder); // save button

        builder.create();
        return builder.create();

    }

    private void setNegativeButton(AlertDialog.Builder builder){
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrate.vibrate(50);
            }
        });
    }

    private void setPositiveButton(AlertDialog.Builder builder){
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrate.vibrate(50);
                if (!input_validity()){
                    Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
                }
                else{
                    saveStudent(); // write student to database

                    // reload data and display
                    ((MainActivity) getActivity()).loadData();
                    ((MainActivity) getActivity()).display_items();

                    dialog.dismiss();
                }
            }
        });
    }

    public void saveStudent(){
        String surname = editTextSurname.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        int ID = Integer.parseInt(editTextID.getText().toString());
        float GPA = Float.parseFloat(editTextGPA.getText().toString());

        dbHelper.addStudent(new Student(surname, firstName, ID, GPA));
        Toast.makeText(getActivity(), "Student added", Toast.LENGTH_SHORT).show();
    }

    private boolean empty_text(EditText text){
        if (text.getText().toString().isEmpty()){
            return true;
        }
        return false;
    }

    private boolean valid_numbers(EditText text){
        boolean valid_number = true;
        String tag = (String) text.getTag(); // tags for editTexts are in their .xml
        switch(tag){
            case "ID":
                // check if ID is 8 digits long and if it already exists in database
                if ((int) ((Math.log10((Integer.parseInt(text.getText().toString())))) + 1) != 8){
                    valid_number = false;
                } else if (dbHelper.checkDuplicateID((int) Integer.parseInt(text.getText().toString()))){
                    valid_number = false;
                }
                break;
            case "GPA":
                // check if GPA is between 0.00 and 4.30
                if (Float.parseFloat(text.getText().toString()) > 4.30F || Float.parseFloat(text.getText().toString()) < 0.00F){
                    text.setError("Please enter a valid GPA");
                    valid_number = false;
                }
                break;
        }
        return valid_number;
    }

    private boolean input_validity() {
        boolean validity = true;

        // checking if any editText is empty and then check if ID and GPA input are valid
        if (empty_text(editTextSurname) || empty_text(editTextFirstName) || empty_text(editTextID) || empty_text(editTextGPA)) {
            validity = false;
        } else if (!valid_numbers(editTextID) || !valid_numbers(editTextGPA)) {
            validity = false;
        }

        return validity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context; // context is the activity
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    // interface to implement DialogListener. Kept empty.
    public interface DialogListener{
        void applyTexts();
    }
}