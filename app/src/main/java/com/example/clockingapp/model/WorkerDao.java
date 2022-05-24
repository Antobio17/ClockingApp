package com.example.clockingapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WorkerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertWorkers(Worker... workers);

    @Update
    public void updateWorkers(Worker... workers);

    @Delete
    public void deleteWorkers(Worker... workers);

    @Query("SELECT * FROM worker")
    public List<Worker> findAll();

    @Query("SELECT * FROM worker WHERE code_value = :codeValue")
    public Worker findOneByCode(Integer codeValue);

}
