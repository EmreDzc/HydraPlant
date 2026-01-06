package com.example.hydraplant;

import android.os.Bundle;

import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.hydraplant.data.entity.Plant;
import com.example.hydraplant.data.viewmodel.PlantViewModel;

public class MainActivity extends AppCompatActivity {

    // 1. Ekran Elemanları (XML'deki ID'lerle eşleşecek)
    private TextView tvLevel, tvHpLabel, tvXpLabel;
    private ProgressBar pbHealth, pbExp;
    private ImageView ivPlant;
    private Button btnDrinkWater;

    // 2. Kaptan (ViewModel)
    private PlantViewModel viewModel;

    // 3. Geçici Hafıza (O anki bitkiyi burada tutacağız)
    private Plant currentPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // A. KABLOLARI BAĞLA (XML -> Java)
        tvLevel = findViewById(R.id.tvLevel);
        tvHpLabel = findViewById(R.id.tvHpLabel); // İleride HP: 80/100 yazdırmak için
        pbHealth = findViewById(R.id.pbHealth);
        pbExp = findViewById(R.id.pbExp);
        ivPlant = findViewById(R.id.ivPlant);
        btnDrinkWater = findViewById(R.id.btnDrinkWater);

        // B. GARSONU ÇAĞIR (ViewModel Başlat)
        // Bu kod, "Bana bu Activity için uygun olan PlantViewModel'i getir" der.
        viewModel = new ViewModelProvider(this).get(PlantViewModel.class);

        // C. GÖZLEM KULESİ (LiveData Observe)
        // Burası uygulamanın kalbi. Veritabanında yaprak kımıldasa burası çalışır.
        viewModel.getPlant().observe(this, new Observer<Plant>() {
            @Override
            public void onChanged(Plant plant) {
                // Veritabanından yeni bitki bilgisi geldi!
                if (plant != null) {
                    currentPlant = plant; // Gelen bitkiyi hafızaya al (Su içerken lazım olacak)
                    updateUI(plant);      // Ekranı güncelle (Aşağıda yazacağız)
                }
            }
        });

        // D. BUTON AKSİYONU (Su İçme)
        btnDrinkWater.setOnClickListener(v -> {
            drinkWater(); // Aşağıda yazacağız
        });
    }

    private void updateUI(Plant plant) {
        // 1. Level Yazısını Güncelle
        tvLevel.setText("Level " + plant.getLevel() + ": " + getPlantName(plant.getLevel()));

        // 2. Can Barını Güncelle
        pbHealth.setProgress(plant.getHp());
        tvHpLabel.setText("Can: " + plant.getHp() + "/100");

        // 3. XP Barını Güncelle
        pbExp.setProgress(plant.getXp());

        // 4. Bitki Görselini Level'a Göre Değiştir (Şimdilik renk değiştiriyoruz)
        // İleride buraya gerçek resimler koyacağız.
        if (plant.getLevel() == 1) {
            ivPlant.setImageResource(R.drawable.ic_launcher_foreground); // Tohum
        } else {
            ivPlant.setImageResource(R.mipmap.ic_launcher_background);
        }
    }

    // Basit bir yardımcı: Level 1 ise "Tohum", 2 ise "Filiz" döndürür.
    private String getPlantName(int level) {
        switch (level) {
            case 1: return "Tohum";
            case 2: return "Filiz";
            case 3: return "Fidan";
            default: return "Ağaç";
        }
    }

    private void drinkWater() {
        if (currentPlant == null) return; // Bitki henüz yüklenmediyse işlem yapma

        // 1. Değerleri Değiştir
        int newHp = Math.min(100, currentPlant.getHp() + 10); // Canı 10 artır (Max 100)
        int newXp = currentPlant.getXp() + 20; // XP'yi 20 artır

        // Level Atlama Mantığı (Basitçe: XP 100 olunca Level atla)
        if (newXp >= 100) {
            newXp = 0; // XP sıfırla
            currentPlant.setLevel(currentPlant.getLevel() + 1); // Level artır
            Toast.makeText(this, "TEBRİKLER! LEVEL ATLADIN!", Toast.LENGTH_LONG).show();
        }

        // 2. Yeni değerleri nesneye yaz
        currentPlant.setHp(newHp);
        currentPlant.setXp(newXp);

        // 3. Garsona ver, veritabanını güncellesin
        viewModel.update(currentPlant);
    }
}