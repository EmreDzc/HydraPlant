package com.example.hydraplant.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.hydraplant.data.dao.PlantDao;
import com.example.hydraplant.data.entity.Plant;
import com.example.hydraplant.data.entity.WaterLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { Plant.class, WaterLog.class }, version = 1, exportSchema = false)
public abstract class HydraPlantDatabase extends RoomDatabase{

    public abstract PlantDao plantDao();
    private static volatile HydraPlantDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    // Arka plan işlemleri için thread havuzu (UI donmasın diye yazma işlemlerini burada yapacağız)
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static HydraPlantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HydraPlantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    HydraPlantDatabase.class, "hydra_plant_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@androidx.annotation.NonNull androidx.sqlite.db.SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Veritabanı işlemleri ana thread'de yapılamaz, arka plana atıyoruz.
            databaseWriteExecutor.execute(() -> {
                // Veritabanı örneğini al
                PlantDao dao = INSTANCE.plantDao();

                // Level 1, Can 100, XP 0 olarak başlatıyoruz
                Plant initialPlant = new Plant(1, 100, 0);

                // Veritabanına kaydet
                dao.insertPlant(initialPlant);
            });
        }
    };
}
