package com.example.basicui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COMPLETED = "completed";

    // Create table query
    private static final String CREATE_TABLE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_COMPLETED + " INTEGER DEFAULT 0)";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is a simple implementation that drops and recreates the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
    // Add a new task
    public long addTask(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESCRIPTION, description);
        // Insert the row
        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    // Get all tasks
    @OptIn(markerClass = UnstableApi.class)
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        try (db; Cursor cursor = db.rawQuery(selectQuery, null)) {
            if (cursor.moveToFirst()) {
                //Get the index of each column
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int completedIndex = cursor.getColumnIndex(COLUMN_COMPLETED);
                do {
                    Task task = new Task();
                    //Check if the index is valid
                    if (idIndex >= 0) {
                        task.setId(cursor.getInt(idIndex));
                    } else {
                        androidx.media3.common.util.Log.e("DatabaseHelper", "Column " + COLUMN_ID + " not found.");
                    }
                    if (titleIndex >= 0) {
                        task.setTitle(cursor.getString(titleIndex));
                    } else {
                        androidx.media3.common.util.Log.e("DatabaseHelper", "Column " + COLUMN_TITLE + " not found.");
                    }
                    if (descriptionIndex >= 0) {
                        task.setDescription(cursor.getString(descriptionIndex));
                    } else {
                        androidx.media3.common.util.Log.e("DatabaseHelper", "Column " + COLUMN_DESCRIPTION + " not found.");
                    }
                    if (completedIndex >= 0) {
                        task.setCompleted(cursor.getInt(completedIndex) == 1);
                    } else {
                        androidx.media3.common.util.Log.e("DatabaseHelper", "Column " + COLUMN_COMPLETED + " not found.");
                    }
                    taskList.add(task);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            androidx.media3.common.util.Log.e("DatabaseHelper", "Exception getting tasks: ", e);
        }
        return taskList;
    }


    // Mark task as completed
    public int updateTaskCompleted(int id, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPLETED, completed ? 1 : 0);
        // Update the row
        int result = db.update(TABLE_TASKS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return result;
    }
    // Delete a task
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}