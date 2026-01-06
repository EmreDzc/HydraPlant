package com.example.hydraplant.data.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.hydraplant.data.entity.Plant;
import com.example.hydraplant.data.repository.PlantRepository;

public class PlantViewModel extends AndroidViewModel {

    private PlantRepository repository;
    private LiveData<Plant> plant;

    public PlantViewModel(@NonNull Application application){
        super(application);
        repository = new PlantRepository(application);
        plant = repository.getPlant();
    }

    public void insert(Plant plant) {
        repository.insert(plant);
    }

    public void update(Plant plant) {
        repository.update(plant);
    }

    public LiveData<Plant> getPlant() {
        return plant;
    }
}
