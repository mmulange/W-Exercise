package com.example.admin.wiproexercise.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.admin.wiproexercise.model.Row;

import java.util.List;

@Dao
public interface FeedsDao {

    @Query("SELECT * FROM feeds")
    List<Row> getAll();

    @Insert
    void insertAll(List<Row> feeds);

    @Query("DELETE FROM feeds")
    void delete();

    @Update
    void update(Row row);
}
