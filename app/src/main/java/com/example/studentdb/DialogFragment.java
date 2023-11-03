package com.example.studentdb;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragment extends AppCompatDialogFragment {
    private EditText editTextSurname;
    private EditText editTextFirstName;
    private EditText editTextID;
    private EditText editTextGPA;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_fragment, null);

        builder.setView(view);
        builder.setTitle("Insert Profile");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

//        builder.setMessage("Enter the details of the student");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Strin
            }
        });

        editTextSurname = view.findViewById(R.id.editText_student_surname);
        editTextFirstName = view.findViewById(R.id.editText_student_firstName);
        editTextID = view.findViewById(R.id.editText_student_ID);
        editTextGPA = view.findViewById(R.id.editText_student_GPA);

        return builder.create();
    }

    public interface DialogListener{
        void applyTexts(String name, String roll, String branch, String year);
    }
}