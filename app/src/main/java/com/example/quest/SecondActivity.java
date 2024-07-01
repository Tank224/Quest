package com.example.quest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SecondActivity extends AppCompatActivity {
    private ImageView nachatimage;
    private ImageView soundImage;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nachatimage = findViewById(R.id.nachatimage);
        soundImage = findViewById(R.id.soundImage);
        if (MainActivity.quest.equals("Сусанин")){textView.setText("В 1613 году после множества " +
                "трагических событий на очередном Земском соборе дворяне избрали царем Михаила " +
                "Федоровича Романова.\nНа тот момент ему было 17 лет. Юный царь вместе с матерью " +
                "находился «на Костроме», куда искать его отправился польско-литовский отряд – " +
                "они хотели убить Михаила Федоровича.\nТы — Иван Сусанин, вотчинный староста деревни Домнино.");}
        else {textView.setText("awda");}
    }
    public void onBackButtonClick(View view) {
        finish();
    }
    public void onNachatClick(View view) {
        Intent intent = new Intent(SecondActivity.this, QuestActivity.class);
        startActivity(intent);
    }
    public void onSoundClick(View view) {
        // Обработка нажатия на buttonImage2
    }
}

