package com.example.hydraplant.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.hydraplant.data.HydraPlantDatabase;
import com.example.hydraplant.data.dao.PlantDao;
import com.example.hydraplant.data.entity.Plant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlantRepository {
    private PlantDao plantDao;
    private ExecutorService executorService;

    public PlantRepository(Application application){
        HydraPlantDatabase db = HydraPlantDatabase.getDatabase(application);
        plantDao = db.plantDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Plant plant){
        executorService.execute(() -> {
            plantDao.insertPlant(plant);
        });
    }

    public void update(Plant plant) {
        executorService.execute(() -> {
            plantDao.updatePlant(plant);
        });
    }

    public LiveData<Plant> getPlant() {
        return plantDao.getPlant();
    }
}
