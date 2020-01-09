package com.maybanktest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<TaskModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialize list
        recyclerView = findViewById(R.id.recycle_view);

        // Initialize Action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddNewItemActivity.class));
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GetList();
    }

    private void GetList() {
        taskList = new ArrayList<>();
        TextView lb_indicator = findViewById(R.id.lb_indicator);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor data = databaseHelper.getData();
        while (data.moveToNext()) {
            boolean isCompleted = false;
            if (data.getString(4).equalsIgnoreCase("1")) isCompleted = true;
            TaskModel taskModel = new TaskModel(data.getString(0), data.getString(1), data.getString(2), data.getString(3), isCompleted);
            taskList.add(taskModel);
        }
        if (taskList.isEmpty()) {
            recyclerView.setVisibility(View.INVISIBLE);
            lb_indicator.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            lb_indicator.setVisibility(View.INVISIBLE);
        }
        PrepareList();
    }

    private void PrepareList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerView.setAdapter(taskAdapter);
    }

    public void refreshList(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}
