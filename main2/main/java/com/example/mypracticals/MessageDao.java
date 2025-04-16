package com.example.mypracticals;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Message message);

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    LiveData<List<Message>> getAllMessages();

    @Query("DELETE FROM messages WHERE id = :id")
    void deleteById(long id);
}

