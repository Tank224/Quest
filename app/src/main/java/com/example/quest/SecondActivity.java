package com.example.quest;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity {
    private ImageView nachatimage;
    private ImageView soundImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nachatimage = findViewById(R.id.nachatimage);
        soundImage =findViewById(R.id.soundImage);
    }
    public void onBackButtonClick(View view) {
        finish();
    }
    public void onNachatClick(View view) {
        // Обработка нажатия на buttonImage2
    }
    public void onSoundClick(View view) {
        // Обработка нажатия на buttonImage2
    }
}

