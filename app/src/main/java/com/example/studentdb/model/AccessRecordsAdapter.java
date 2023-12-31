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

public class AccessRecordsAdapter extends RecyclerView.Adapter<AccessRecordsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView access_record; // holds access record

        public ViewHolder(View itemView) {
            super(itemView);
            access_record = (TextView) itemView.findViewById(R.id.access_record_placeholder);
        }
    }

    List<String> AccessRecords = new ArrayList<String>(); // holds access records of profile

    public AccessRecordsAdapter(List<String> AccessRecords_list) {
        AccessRecords.addAll(AccessRecords_list); //copy items to class-specific array
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View counterView = inflater.inflate(R.layout.access_record_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(counterView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String access_record_string = AccessRecords.get(position);
        TextView textView = holder.access_record;
        textView.setText(access_record_string);
    }

    @Override
    public int getItemCount() {
        return AccessRecords.size();
    }
}

