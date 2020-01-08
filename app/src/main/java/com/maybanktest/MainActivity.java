package com.maybanktest;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TextView lb_indicator;
    private ArrayList<TaskModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Initialize list
        recyclerView = findViewById(R.id.recycle_view);

        // Initialize Action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddNewItemActivity.class));
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        GetList();
    }

    private void GetList() {
        taskList = new ArrayList();
        lb_indicator = findViewById(R.id.lb_indicator);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor data = databaseHelper.getData();
        while (data.moveToNext()){
            boolean isCompleted = false;
            if(data.getString(4).equalsIgnoreCase("1")) isCompleted = true;
            TaskModel taskModel = new TaskModel(data.getString(0),data.getString(1),data.getString(2),data.getString(3),isCompleted);
            taskList.add(taskModel);
        }
        if(taskList.isEmpty()){
            recyclerView.setVisibility(View.INVISIBLE);
            lb_indicator.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            lb_indicator.setVisibility(View.INVISIBLE);
        }
        PrepareList();
    }

    private void PrepareList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TaskAdapter(taskList,this);
        recyclerView.setAdapter(taskAdapter);
    }

    public void refreshList(int position){
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}
