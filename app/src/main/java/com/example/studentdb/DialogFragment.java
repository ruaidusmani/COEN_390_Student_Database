package com.example.studentdb;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogFragment extends AppCompatDialogFragment  {
    private EditText editTextSurname;
    private EditText editTextFirstName;
    private EditText editTextID;
    private EditText editTextGPA;
    private DialogListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_fragment, null);

        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

//        builder.setMessage("Enter the details of the student");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //set EditText to string
                String surname = editTextSurname.getText().toString();
                String firstName = editTextFirstName.getText().toString();
                String ID = editTextID.getText().toString();
                String GPA = editTextGPA.getText().toString();
                //pass the string to the main activity
                DialogListener listener = (DialogListener) getActivity();
                listener.applyTexts(surname, firstName, ID, GPA);

            }
        });

        editTextSurname = view.findViewById(R.id.editText_student_surname);
        editTextFirstName = view.findViewById(R.id.editText_student_firstName);
        editTextID = view.findViewById(R.id.editText_student_ID);
        editTextGPA = view.findViewById(R.id.editText_student_GPA);

        return builder.create();
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


    public interface DialogListener{
        void applyTexts(String surname, String firstName, String ID, String GPA);
    }
}