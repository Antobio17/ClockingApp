package com.example.clockingapp.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertSchedules(Schedule... exercises);

    @Update
    public void updateSchedules(Schedule... exercises);

    @Delete
    public void deleteSchedules(Schedule... exercises);

    @Query("SELECT * FROM schedule")
    public List<Schedule> findAll();

    @Query("SELECT * FROM schedule ORDER BY id DESC LIMIT 1")
    public Schedule findLast();
}
