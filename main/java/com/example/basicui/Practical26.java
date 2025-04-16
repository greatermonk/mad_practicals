package com.example.basicui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Practical26 extends AppCompatActivity implements DatabaseAsyncTask.DatabaseOperationListener {

    private EditText editTextTitle, editTextDescription;
    private ProgressBar progressBar;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<Task> adapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical26);

        // Initialize UI components
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        ListView listViewTasks = findViewById(R.id.listViewTasks);
        progressBar = findViewById(R.id.progressBar);
        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList) {
            @Override
            public Task getItem(int position) {
                return taskList.get(position);
            }
        };
        listViewTasks.setAdapter(adapter);

        // Set click listeners
        buttonAddTask.setOnClickListener(v -> addTask());
        listViewTasks.setOnItemClickListener((parent, view, position, id) -> toggleTaskComplete(position));
        listViewTasks.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteTask(position);
            return true;
        });
        // Load tasks asynchronously
        loadTasks();
    }
    private void addTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        // Execute async task for insertion
        new DatabaseAsyncTask(this, dbHelper, DatabaseAsyncTask.OPERATION_INSERT)
                .execute(title, description);
    }

    private void loadTasks() {
        // Execute async task for querying all tasks
        new DatabaseAsyncTask(this, dbHelper, DatabaseAsyncTask.OPERATION_QUERY_ALL)
                .execute();
    }

    private void toggleTaskComplete(int position) {
        Task task = taskList.get(position);
        boolean newStatus = !task.isCompleted();

        // Execute async task for updating task status
        new DatabaseAsyncTask(this, dbHelper, DatabaseAsyncTask.OPERATION_UPDATE)
                .execute(task.getId(), newStatus);
    }

    private void deleteTask(int position) {
        Task task = taskList.get(position);

        // Execute async task for deleting
        new DatabaseAsyncTask(this, dbHelper, DatabaseAsyncTask.OPERATION_DELETE).execute(task.getId());
    }

    @Override
    public void onDatabaseOperationStart() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDatabaseOperationComplete(int operationType, Object result) {
        progressBar.setVisibility(View.GONE);

        switch (operationType) {
            case DatabaseAsyncTask.OPERATION_INSERT:
                if (result instanceof Long) {
                    // Clear input fields
                    editTextTitle.setText("");
                    editTextDescription.setText("");
                    // Reload tasks to show the new one
                    loadTasks();
                    Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                }
                break;

            case DatabaseAsyncTask.OPERATION_QUERY_ALL:
                if (result instanceof List) {
                    taskList.clear();
                    taskList.addAll((List<Task>) result);
                    adapter.notifyDataSetChanged();
                }
                break;

            case DatabaseAsyncTask.OPERATION_UPDATE:
                if (result != null && (Integer) result > 0) {
                    // Reload to show updated status
                    loadTasks();
                    Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
                }
                break;

            case DatabaseAsyncTask.OPERATION_DELETE:
                if (result != null && (Boolean) result) {
                    // Reload to show current list
                    loadTasks();
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onDatabaseOperationError(Exception e) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}