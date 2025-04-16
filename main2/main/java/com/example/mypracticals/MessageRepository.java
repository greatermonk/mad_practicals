package com.example.mypracticals;


import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class MessageRepository {
    private final MessageDao messageDao;
    private final LiveData<List<Message>> allMessages;

    public MessageRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        messageDao = database.messageDao();
        allMessages = messageDao.getAllMessages();
    }

    public void insert(Message message) {
        new InsertMessageAsyncTask(messageDao).execute(message);
    }

    public LiveData<List<Message>> getAllMessages() {
        return allMessages;
    }

    private static class InsertMessageAsyncTask extends AsyncTask<Message, Void, Void> {
        private final MessageDao messageDao;

        InsertMessageAsyncTask(MessageDao messageDao) {
            this.messageDao = messageDao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            messageDao.insert(messages[0]);
            return null;
        }
    }
}