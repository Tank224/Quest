package com.example.quest;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class SecondActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private ImageView nachatimage;
    private ImageView soundImage;
    private TextView textView;
    private TextToSpeech mTextToSpeech;
    private boolean mIsInit, stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nachatimage = findViewById(R.id.nachatimage);
        soundImage = findViewById(R.id.soundImage);
        textView = findViewById(R.id.textView);
        View oView = findViewById(R.id.secondLayout);
        oView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        mTextToSpeech = new TextToSpeech(this, this);
        if (MainActivity.quest.equals("Сусанин")){textView.setText("В 1613 году после множества " +
                "трагических событий на очередном Земском соборе дворяне избрали царем Михаила " +
                "Федоровича Романова. На тот момент ему было 17 лет. Юный царь вместе с матерью " +
                "находился «на Костроме», куда искать его отправился польско-литовский отряд – " +
                "они хотели убить Михаила Федоровича. Ты — Иван Сусанин, вотчинный староста " +
                "деревни Домнино.");}
        else {textView.setText("Ты — Юрий Долгорукий, князь Владимиро-Суздальский. Тебе предстоит основать новый город на берегу Волги и построить детинец, чтобы защититься от волжских булгар и мордвы и контролировать значимую для всей Северо-Восточной Руси речную торговлю по верхнему течению реки. Ваши решения определят будущее города.");}
    }
    public void onBackButtonClick(View view) {
        stop = false;
        mTextToSpeech.stop();
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void onNachatClick(View view) {
        stop = false;
        mTextToSpeech.stop();
        Intent intent = new Intent(SecondActivity.this, QuestActivity.class);
        startActivity(intent);

    }
    public void onSoundClick(View view) {
        if (mIsInit) {
            if (stop == false) {
                String textToSpeech = textView.getText().toString();
                mTextToSpeech.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, "id1");
                stop = true;
                setupUtteranceProgressListener();
            }
            else
            {
                stop = false;
                mTextToSpeech.stop();
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Locale locale = new Locale("ru");
            int result = mTextToSpeech.setLanguage(locale);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                mIsInit = false;
            } else {
                mIsInit = true;
            }
        } else {
            mIsInit = false;
        }
    }
    private void setupUtteranceProgressListener() {
        mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {
                // Действие при начале воспроизведения
            }

            @Override
            public void onDone(String utteranceId) {
                // Действие после окончания воспроизведения
                runOnUiThread(() -> {
                    stop = false;
                });
            }

            @Override
            public void onError(String utteranceId) {
                // Действие при ошибке воспроизведения
            }
        });
    }
}

