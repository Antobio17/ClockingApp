package com.example.clockingapp.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.clockingapp.model.ScheduleDao;
import com.example.clockingapp.model.Schedule;

@Database(
        entities = {Schedule.class, Worker.class},
        version = 4,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ScheduleDao scheduleDao();
    public abstract WorkerDao workerDao();
}


