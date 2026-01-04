package com.example.hydraplant.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hydraplant.data.entity.Plant;
import com.example.hydraplant.data.entity.WaterLog;

import java.util.List;

@Dao
public interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlant(Plant plant);

    @Update
    void updatePlant(Plant plant);

    @Query("SELECT * FROM plant_table LIMIT 1")
    LiveData<Plant> getPlant();

    @Query("SELECT * FROM plant_table LIMIT 1")
    Plant getPlantSync();

    @Insert
    void insertLog(WaterLog log);

    @Query("SELECT * FROM water_log_table ORDER BY timestamp DESC")
    LiveData<List<WaterLog>> getAllLogs();
}