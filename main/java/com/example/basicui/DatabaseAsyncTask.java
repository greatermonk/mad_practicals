package com.example.basicui;

import android.os.AsyncTask;
import android.util.Log;
import java.lang.ref.WeakReference;

public class DatabaseAsyncTask extends AsyncTask<Object, Void, Object> {
    private static final String TAG = "DatabaseAsyncTask";
    // Operation types
    public static final int OPERATION_INSERT = 1;
    public static final int OPERATION_QUERY_ALL = 2;
    public static final int OPERATION_UPDATE = 3;
    public static final int OPERATION_DELETE = 4;

    private final WeakReference<DatabaseOperationListener> listenerRef;
    private final int operationType;
    private final DatabaseHelper dbHelper;

    public DatabaseAsyncTask(DatabaseOperationListener listener, DatabaseHelper dbHelper, int operationType) {
        this.listenerRef = new WeakReference<>(listener);
        this.dbHelper = dbHelper;
        this.operationType = operationType;
    }

    @Override
    protected void onPreExecute() {
        DatabaseOperationListener listener = listenerRef.get();
        if (listener != null) {
            listener.onDatabaseOperationStart();
        }
    }

    @Override
    protected Object doInBackground(Object... params) {
        try {
            switch (operationType) {
                case OPERATION_INSERT:
                    if (params.length >= 2 && params[0] instanceof String && params[1] instanceof String) {
                        return dbHelper.addTask((String) params[0], (String) params[1]);
                    }
                    break;

                case OPERATION_QUERY_ALL:
                    return dbHelper.getAllTasks();

                case OPERATION_UPDATE:
                    if (params.length >= 2 && params[0] instanceof Integer && params[1] instanceof Boolean) {
                        return dbHelper.updateTaskCompleted((Integer) params[0], (Boolean) params[1]);
                    }
                    break;

                case OPERATION_DELETE:
                    if (params.length >= 1 && params[0] instanceof Integer) {
                        dbHelper.deleteTask((Integer) params[0]);
                        return true;
                    }
                    break;
            }
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error in database operation", e);
            return e;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        DatabaseOperationListener listener = listenerRef.get();
        if (listener != null) {
            if (result instanceof Exception) {
                listener.onDatabaseOperationError((Exception) result);
            } else {
                listener.onDatabaseOperationComplete(operationType, result);
            }
        }
    }

    // Interface for callbacks
    public interface DatabaseOperationListener {
        void onDatabaseOperationStart();
        void onDatabaseOperationComplete(int operationType, Object result);
        void onDatabaseOperationError(Exception e);
    }
}