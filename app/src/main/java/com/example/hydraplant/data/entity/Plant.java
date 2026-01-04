package com.example.hydraplant.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plant_table")
public class Plant {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int level;
    public int currentHp;
    public int maxHp;
    public int currentXp;
    public int targetXp;
    public boolean isAlive;

    public Plant() {
        this.level = 1;
        this.currentHp = 100;
        this.maxHp = 100;
        this.currentXp = 0;
        this.targetXp = 100;
        this.isAlive = true;
    }
}
