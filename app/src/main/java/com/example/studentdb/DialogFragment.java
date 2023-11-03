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

public class DialogFragment extends AppCompatDialogFragment  {
    private EditText editTextSurname;
    private EditText editTextFirstName;
    private EditText editTextID;
    private EditText editTextGPA;
    private DialogListener listener;

    Vibrator vibrate;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_fragment, null);


        vibrate = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE); // vibrate service
        editTextSurname = view.findViewById(R.id.editText_student_surname);
        editTextFirstName = view.findViewById(R.id.editText_student_firstName);
        editTextID = view.findViewById(R.id.editText_student_ID);
        editTextGPA = view.findViewById(R.id.editText_student_GPA);

        builder.setView(view);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrate.vibrate(50);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrate.vibrate(50);
                if (editTextSurname.getText().toString().isEmpty() & editTextFirstName.getText().toString().isEmpty() & editTextID.getText().toString().isEmpty() & editTextGPA.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Please enter a value", Toast.LENGTH_SHORT).show();
                }
                else{
                    //set EditText to string
                    String surname = editTextSurname.getText().toString();
                    String firstName = editTextFirstName.getText().toString();
                    int ID = Integer.parseInt(editTextID.getText().toString());
                    float GPA = Float.parseFloat(editTextGPA.getText().toString());


                    //pass the string to the main activity
                    DialogListener listener = (DialogListener) getActivity();
                    listener.applyTexts(surname, firstName, ID, GPA);

                }
            }
        });
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
        void applyTexts(String surname, String firstName, int ID, float GPA);
    }
}