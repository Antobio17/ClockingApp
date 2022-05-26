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

    @Query("SELECT * FROM schedule WHERE checking_in > :dayStart and checking_in < :dayEnd")
    public List<Schedule> findAllByDate(String dayStart, String dayEnd);

    @Query("SELECT * FROM schedule ORDER BY id DESC LIMIT 1")
    public Schedule findLast();

    @Query("SELECT * FROM schedule WHERE worker_id = :workerID AND checking_out is NULL ORDER BY id DESC LIMIT 1")
    public Schedule findLastCheckingInByWorker(Integer workerID);

    @Query("SELECT * FROM schedule WHERE worker_id = :workerID AND checking_in > :dayStart and checking_in < :dayEnd is NOT NULL ORDER BY id DESC LIMIT 1")
    public Schedule findCheckingInAtDayByWorker(Integer workerID, String dayStart, String dayEnd);
}
