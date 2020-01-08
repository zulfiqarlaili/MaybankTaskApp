package com.maybanktest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private List<TaskModel> list_task;
    private Context context;
    private DatabaseHelper databaseHelper;
    View itemView;

    public TaskAdapter(List<TaskModel> list_bill, Context context) {
        this.list_task = list_bill;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_title,txt_start_date,txt_end_date,txt_time_left,txt_status;
        public CheckBox cb_completed;
        public ImageView iv_delete;
        public MyViewHolder(@NonNull View view) {
            super(view);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_start_date = itemView.findViewById(R.id.txt_start_date);
            txt_end_date = itemView.findViewById(R.id.txt_end_date);
            txt_time_left = itemView.findViewById(R.id.txt_time_left);
            txt_status = itemView.findViewById(R.id.txt_status);
            cb_completed = itemView.findViewById(R.id.cb_completed);
            iv_delete = itemView.findViewById(R.id.iv_delete);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        databaseHelper = new DatabaseHelper(context);
        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final TaskModel task = list_task.get(position);
        holder.txt_title.setText(task.getTitle());
        holder.txt_start_date.setText(task.getStart_date());
        holder.txt_end_date.setText(task.getEnd_date());
        try {
            holder.txt_time_left.setText(Helper.CalculateTimeLeft(task.getStart_date(),task.getEnd_date()).replace("-","")+" hrs");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(task.getIsCompleted()) holder.txt_status.setText("Complete");else holder.txt_status.setText("Incomplete");
        if(task.getIsCompleted()) holder.cb_completed.setChecked(true);else holder.cb_completed.setChecked(false);


        holder.cb_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.cb_completed.isChecked()) holder.txt_status.setText("Complete");else holder.txt_status.setText("Incomplete");
                task.setIsCompleted(holder.cb_completed.isChecked());
                databaseHelper.updateData(task);

            }
        });


        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Delete "+task.getTitle()+" from the list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.deleteData(task.getId());
                                ((MainActivity)context).refreshList(position);
                            }
                        }).setNegativeButton("No", null).show();
            }
        });


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNewItemActivity.class);
                intent.putExtra("id", task.getId());
                intent.putExtra("txt_title", task.getTitle());
                intent.putExtra("txt_start_date", task.getStart_date());
                intent.putExtra("txt_end_date", task.getEnd_date());
                intent.putExtra("txt_isCompleted", task.getIsCompleted());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_task.size();
    }

    public List<TaskModel> getList_task(){
        return list_task;
    }
}
