package com.example.hydraplant.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plant_table")
public class Plant {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int level;
    private int hp;
    private int xp;

    // --- Constructor (Kurucu) ---
    public Plant(int level, int hp, int xp) {
        this.level = level;
        this.hp = hp;
        this.xp = xp;
    }

    // --- Getter ve Setter Metotları (Eksik Olanlar Bunlardı) ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}