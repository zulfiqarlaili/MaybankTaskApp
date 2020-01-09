package com.maybanktest;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;


public class AddNewItemActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText start_date, end_date, txt_title;
    private boolean isNew = false;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        Objects.requireNonNull(getSupportActionBar()).hide();

        bundle = getIntent().getExtras();

        // To distinguish between new data and update data
        Bundle extras = getIntent().getExtras();
        ini();
        isNew = extras == null;
        setInitialValue();
    }

    private void ini() {
        //Initialize attribute

        start_date = findViewById(R.id.start_date);
        end_date = findViewById(R.id.end_date);
        txt_title = findViewById(R.id.txt_title);
        Button btn_create = findViewById(R.id.btn_create);

        start_date.setOnClickListener(this);
        end_date.setOnClickListener(this);
        btn_create.setOnClickListener(this);

        if (isNew) {
            btn_create.setText(getString(R.string.create_now));
        } else {
            btn_create.setText(getString(R.string.update));
        }
    }

    private void setInitialValue() {
        if (bundle != null) {
            txt_title.setText(bundle.getString("txt_title", ""));
            start_date.setText(bundle.getString("txt_start_date", ""));
            end_date.setText(bundle.getString("txt_end_date", ""));
        }
    }

    public void addData(TaskModel taskModel) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean insertData = databaseHelper.addData(taskModel);

        if (insertData) {
            Toast.makeText(this, "Task add successful", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(this, "Task add fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateData(TaskModel taskModel) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean updateData = databaseHelper.updateData(taskModel);

        if (updateData) {
            Toast.makeText(this, "Task Update successful", Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else {
            Toast.makeText(this, "Task Update fail", Toast.LENGTH_SHORT).show();
        }
    }

    private DatePickerDialog datePickerDialogInstance(final boolean isStartDate) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String a = dayOfMonth + " " + Helper.getMonth(monthOfYear) + " " + year;
                final EditText outputText = isStartDate ? start_date : end_date;
                outputText.setText(a);
            }
        };

        Calendar date = Calendar.getInstance();
        return new DatePickerDialog(AddNewItemActivity.this, dateSetListener, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
    }

    private void updateOrCreateData() {
        final boolean isTitleEmpty = TextUtils.isEmpty(txt_title.getText().toString());
        final boolean isStartDateEmpty = TextUtils.isEmpty(start_date.getText().toString());
        final boolean isEndDateEmpty = TextUtils.isEmpty(end_date.getText().toString());

        final boolean isFormCompleted = !isTitleEmpty && !isStartDateEmpty && !isEndDateEmpty;

        if (isFormCompleted) {
            final String taskId = isNew ? null : bundle.getString("id");
            TaskModel taskModel = new TaskModel(taskId, txt_title.getText().toString(), start_date.getText().toString(), end_date.getText().toString(), false);
            if (isNew) {
                addData(taskModel);
            } else {
                updateData(taskModel);
            }
        } else {
            Toast.makeText(this, "Please fill all form", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_date:
            case R.id.end_date:
                DatePickerDialog datePickerDialog = datePickerDialogInstance(view.getId() == R.id.start_date);
                datePickerDialog.show();
                break;
            case R.id.btn_create:
                updateOrCreateData();
                break;
        }

    }
}
