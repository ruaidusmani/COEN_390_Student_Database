package com.example.studentdb;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView student_information; // holds name or student ID

        public ViewHolder(View itemView) {
            super(itemView);
            student_information = (TextView) itemView.findViewById(R.id.student_info_placeholder);
        }
    }

    List<Student> Student_List = new ArrayList<Student>();
    private final boolean toggle; // false = student name, true = student ID

    public StudentAdapter(List<Student> studentinfo_list, boolean toggle) {
        Student_List.addAll(studentinfo_list); //copy items to class-specific array
        this.toggle = toggle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View counterView = inflater.inflate(R.layout.student_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(counterView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = Student_List.get(position);
        Log.d("Issue", student.getSurname());
        TextView textView = holder.student_information;


        if (!toggle){ // counter_name or counter_number
            textView.setText(student.getSurname() + ", " + student.getFirstName());
        }
        else{
            textView.setText(String.valueOf(student.getID()));
        }
    }

    @Override
    public int getItemCount() {
        return Student_List.size();
    }
}

