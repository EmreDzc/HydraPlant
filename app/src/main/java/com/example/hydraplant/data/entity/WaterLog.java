package com.example.hydraplant.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_log_table")
public class WaterLog {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public long timestamp;
    public int amountMl;

    public WaterLog(long timestamp, int amountMl) {
        this.timestamp = timestamp;
        this.amountMl = amountMl;
    }

    public WaterLog(){
    }
}
