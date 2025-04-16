package com.example.mypracticals;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class SMSViewModel extends AndroidViewModel {
    private final MessageRepository repository;
    private final LiveData<List<Message>> messages;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public SMSViewModel(@NonNull Application application) {
        super(application);
        repository = new MessageRepository(application);
        messages = repository.getAllMessages();
        isLoading.setValue(false);
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    public void addSentMessage(String phoneNumber, String content) {
        Message message = new Message(
                0,                          // Auto-generated ID
                phoneNumber,                // Phone number
                content,                    // Message content
                System.currentTimeMillis(), // Current timestamp
                true                        // Sent message
        );
        repository.insert(message);
    }
}