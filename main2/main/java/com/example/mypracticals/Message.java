package com.example.mypracticals;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "messages")
public class Message {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String phoneNumber;
    private String content;
    private long timestamp;
    private boolean isSent;

    public Message(long id, String phoneNumber, String content, long timestamp, boolean isSent) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.content = content;
        this.timestamp = timestamp;
        this.isSent = isSent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}