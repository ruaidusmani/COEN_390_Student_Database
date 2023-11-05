package com.example.studentdb;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
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

        dbHelper = new DBHelper(getActivity());

        builder.setView(view);

//      Save and Cancel Buttons
        setNegativeButton(builder);
        setPositiveButton(builder);

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
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vibrate.vibrate(50);
                if (!input_validity()){
                    Log.d("Valid", String.valueOf(input_validity()));
                    Toast.makeText(getActivity(), "Invalid input", Toast.LENGTH_SHORT).show();
                }
                else{
                    //set EditText to string
                    Log.d("Valid", String.valueOf(input_validity()));
                    String surname = editTextSurname.getText().toString();
                    String firstName = editTextFirstName.getText().toString();
                    int ID = Integer.parseInt(editTextID.getText().toString());
                    float GPA = Float.parseFloat(editTextGPA.getText().toString());

                    dbHelper.addStudent(new Student(surname, firstName, ID, GPA));
                    dialog.dismiss();

                }
            }
        });
    }

//    private void setOnShowListenerForDialog(final AlertDialog dialog) {
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//                positiveButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!input_validity()) {
//                            //dialog.dismiss();
//                        }
//                        else {
//                            String surname = editTextSurname.getText().toString();
//                            String firstName = editTextFirstName.getText().toString();
//                            int ID = Integer.parseInt(editTextID.getText().toString());
//                            float GPA = Float.parseFloat(editTextGPA.getText().toString());
//
//                            dbHelper.addStudent(new Student(surname, firstName, ID, GPA));
//                        }
//                    }
//                });
//            }
//        });
//    }

    private boolean input_validity(){
        boolean validity = true;

        // checking if surname is empty
        if (editTextSurname.getText().toString().isEmpty()){
            editTextSurname.setError("Please enter a surname");
            validity = false;
        }

        // checking if first name is empty
        if(editTextFirstName.getText().toString().isEmpty()){
            editTextFirstName.setError("Please enter a first name");
            validity = false;
        }

        Log.d("ID", String.valueOf((int) ((Math.log10((Integer.parseInt(editTextID.getText().toString())))) + 1)));
        // checking if ID is empty, the ID format is invalid or if the ID already exists
        if (editTextID.getText().toString().isEmpty()){
            editTextID.setError("Please enter an ID");
            validity = false;
        }

        else if ((int) ((Math.log10((Integer.parseInt(editTextID.getText().toString())))) + 1) != 8){
            Log.d("ID", String.valueOf((int) ((Math.log10((Integer.parseInt(editTextID.getText().toString())))) + 1)));
            editTextID.setError("Please enter a valid ID between 11111111 - 99999999");
            validity = false;
        }
        else if (dbHelper.checkDuplicateID((int) Integer.parseInt(editTextID.getText().toString()))){
            Log.d("DUPLICATE ID", String.valueOf(dbHelper.checkDuplicateID((int) Integer.parseInt(editTextID.getText().toString()))));
            editTextID.setError("ID already exists");
            validity = false;
        }

        // checking if GPA is empty or the GPA format is invalid
        if (editTextGPA.getText().toString().isEmpty()){
            editTextGPA.setError("Please enter a GPA");
            validity = false;
        }

        else if (Float.parseFloat(editTextGPA.getText().toString()) > 4.30F || Float.parseFloat(editTextGPA.getText().toString()) < 0.00F){
            editTextGPA.setError("Please enter a valid GPA");
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