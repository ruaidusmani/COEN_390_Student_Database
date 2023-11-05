package com.example.studentdb.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentdb.R;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView student_information; // holds name or student ID
        public TextView line_number; // holds line number

        public ViewHolder(View itemView) {
            super(itemView);
            student_information = (TextView) itemView.findViewById(R.id.student_info_placeholder);
            line_number = (TextView) itemView.findViewById(R.id.line_number);

            //initialize click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (clickListener != null && position != RecyclerView.NO_POSITION)
                        clickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    List<Student> Student_List = new ArrayList<Student>();
    private boolean toggle; // false = student name, true = student ID
    private OnItemClickListener clickListener;

    public StudentAdapter(List<Student> studentinfo_list, boolean toggle,  OnItemClickListener clickListener) {
        Student_List.addAll(studentinfo_list); //copy items to class-specific array
        this.toggle = toggle;
        this.clickListener = clickListener;
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
        // set line number
        int lineNumber = position + 1;
        TextView line_number = holder.line_number;
        line_number.setText(lineNumber + ".");

        // set student information
        Student student = Student_List.get(position);
        TextView student_information = holder.student_information;

        if (!toggle){ // student surname
            student_information.setText(student.getSurname() + ", " + student.getFirstName());
        }
        else{ // student ID
            student_information.setText(String.valueOf(student.getID()));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    @Override
    public int getItemCount() {
        return Student_List.size();
    }
}

