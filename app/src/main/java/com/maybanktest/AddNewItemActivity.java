package com.maybanktest;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewItemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText start_date, end_date, txt_title;
    private Button btn_create;
    private boolean isNew = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        getSupportActionBar().hide();


        // To distinguish between new data and update data
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ini();
            setInitialValue();
        }else {
            isNew = true;
            ini();
        }

    }

    private void ini() {
        //Initialize attribute

        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        txt_title = findViewById(R.id.txt_title);
        btn_create = findViewById(R.id.btn_create);

        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        btn_create.setOnClickListener(this);

        if(isNew){
            btn_create.setText("Create Now");
        }else {
            btn_create.setText("Update");
        }
    }

    private void setInitialValue() {
        txt_title.setText(getIntent().getExtras().getString("txt_title"));
        start_date.setText(getIntent().getExtras().getString("txt_start_date"));
        end_date.setText(getIntent().getExtras().getString("txt_end_date"));
    }

    public void AddData(TaskModel taskModel){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean insertData = databaseHelper.addData(taskModel);

        if(insertData){
            Toast.makeText(this, "Task add successful", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else {
            Toast.makeText(this, "Task add fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(TaskModel taskModel){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean updateData = databaseHelper.updateData(taskModel);

        if(updateData){
            Toast.makeText(this, "Task Update successful", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else {
            Toast.makeText(this, "Task Update fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.start_date:
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String a = dayOfMonth + " " + Helper.getMonth(monthOfYear + 1) + " " + year;
                        start_date.setText(a);
                    }
                };
                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(AddNewItemActivity.this, dateSetListener, date.year, date.month, date.monthDay);
                d.show();
                break;
            case R.id.end_date:
                DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String a = dayOfMonth + " " + Helper.getMonth(monthOfYear + 1) + " " + year;
                        end_date.setText(a);
                    }
                };
                Time date2 = new Time();
                DatePickerDialog d2 = new DatePickerDialog(AddNewItemActivity.this, dateSetListener2, date2.year, date2.month, date2.monthDay);
                d2.show();
                break;
            case R.id.btn_create:
                if(isNew){
                    if (!TextUtils.isEmpty(txt_title.getText().toString()) && !TextUtils.isEmpty(start_date.getText().toString()) && !TextUtils.isEmpty(end_date.getText().toString())) {
                        TaskModel taskModel = new TaskModel(null, txt_title.getText().toString(), start_date.getText().toString(), end_date.getText().toString(), false);
                        AddData(taskModel);

                    } else {
                        Toast.makeText(this, "Please fill all form", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (!TextUtils.isEmpty(txt_title.getText().toString()) && !TextUtils.isEmpty(start_date.getText().toString()) && !TextUtils.isEmpty(end_date.getText().toString())) {
                        TaskModel taskModel = new TaskModel(getIntent().getExtras().getString("id"), txt_title.getText().toString(), start_date.getText().toString(), end_date.getText().toString(), false);
                        updateData(taskModel);

                    } else {
                        Toast.makeText(this, "Please fill all form", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }
}
