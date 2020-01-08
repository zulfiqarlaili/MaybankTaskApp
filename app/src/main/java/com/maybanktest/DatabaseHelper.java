package com.maybanktest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_START = "startDate";
    public static final String COLUMN_END = "endDate";
    public static final String COLUMN_ISCOMPLETED = "isCompleted";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,1);
    }

    //when initialize this class, it will go to onUpgrade first to check any database has been create then go to onCreate to create database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TITLE + " TEXT," +
                        COLUMN_START + " TEXT," +
                        COLUMN_END + " TEXT," +
                        COLUMN_ISCOMPLETED + " INTEGER)";
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // check whether database has been create
        String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }

    public boolean addData(TaskModel task){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,task.getTitle());
        contentValues.put(COLUMN_START,task.getStart_date());
        contentValues.put(COLUMN_END,task.getEnd_date());
        contentValues.put(COLUMN_ISCOMPLETED,task.getIsCompleted());


        Log.d(TAG,"addData: Adding "+ task.getTitle() +" to "+TABLE_NAME);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean updateData(TaskModel task){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,task.getTitle());
        contentValues.put(COLUMN_START,task.getStart_date());
        contentValues.put(COLUMN_END,task.getEnd_date());
        contentValues.put(COLUMN_ISCOMPLETED,task.getIsCompleted());


        Log.d(TAG,"updateData: Updating "+ task.getTitle() +" to "+TABLE_NAME);
        long result = db.update(TABLE_NAME, contentValues, "id = ?", new String[]{task.getId()});

        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id}) > 0;
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
