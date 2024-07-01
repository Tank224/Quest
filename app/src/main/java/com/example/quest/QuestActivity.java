package com.example.quest;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private List<Button> buttons;
    List<Scene> scenes = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        View oView = findViewById(R.id.questLayout);
        oView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        buttons = new ArrayList<>();
        //createQuestScreen(R.drawable.susanin_home, "Вы встретили монстра. Что будете делать?",
                //new String[]{"Атаковать", "Убежать", "Поговорить"});
        // Пример создания экрана квеста

        startQuest();
    }
    private void startQuest() {
        if (MainActivity.quest.equalsIgnoreCase("Сусанин")) {
            susaninQuest();
        } else {
            dolgorukiyQuest();
        }
    }
    private void susaninQuest(){
        scenes.add(new Scene("Начало", R.drawable.susanin_home, "Польско-литовский отряд, " +
                "стремящийся помешать воцарению Михаила Романова, пришел к тебе за информацией о " +
                "местонахождении царя. Они требуют выдать, где прячется молодой Романов.",
                new String[]{"Согласиться помочь врагу и показать ему правильный путь",
                        "Согласиться помочь, но увести их в противоположную сторону", "Попытаться убежать"}));
        scenes.add(new Scene("Согласиться помочь врагу и показать ему правильный путь", R.drawable.susanin_home, "Ты решаешь предать царя и показать польско-литовскому отряду, где он прячется.", new String[]{}));
        scenes.add(new Scene("Согласиться помочь, но увести их в противоположную сторону", R.drawable.susanin_home, "В", new String[]{"Атаковать"}));
        scenes.add(new Scene("Попытаться убежать", R.drawable.died, "Ты решаешь не " +
                "помогать полякам и пытаешься убежать. Они настигают тебя.",
                new String[]{}));
        scenes.add(new Scene("", R.drawable.susanin_home, "В", new String[]{"Атаковать"}));
        createQuestScreen(scenes.get(0));
    }
    private void dolgorukiyQuest(){
        scenes.add(new Scene("", R.drawable.susanin_home, "В", new String[]{"Атаковать"}));
        scenes.add(new Scene("", R.drawable.susanin_home, "В", new String[]{"Атаковать"}));
        createQuestScreen(scenes.get(0));
    }
    private void createQuestScreen(Scene scene) {
        // Устанавливаем картинку
        imageView.setImageResource(scene.getImageResource());

        // Устанавливаем текст
        textView.setText(scene.getText());
        String[] options = scene.getOptions();

        // Удаляем старые кнопки
        for (Button button : buttons) {
            ((ViewGroup) button.getParent()).removeView(button);
        }
        buttons.clear();
            // Создаем новые кнопки для вариантов ответа
            for (int i = 0; i < options.length; i++) {
                Button button = new Button(this);
                button.setText(options[i]);
                button.setTag(i);
                button.setBackgroundColor(Color.parseColor("#E6CF9D"));
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params1.setMargins(0, 0, 0, 20);
                button.setLayoutParams(params1);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedOption = (int) v.getTag();
                        handleOptionSelection(button.getText().toString());
                    }
                });

                // Добавляем кнопку в макет
                ((ViewGroup) textView.getParent()).addView(button);
                buttons.add(button);
            }
    }

    private void handleOptionSelection(String selectedOption) {
        for (Scene i : scenes){
            if (i.getName().equals(selectedOption)){
                createQuestScreen(i);
                break;
            }
        }
        // Обработка выбора пользователя
//        switch (selectedOption) {
//            case 0:
//                createQuestScreen(R.drawable.attack_image, "Вы атаковали монстра и победили!", new String[]{"Продолжить"});
//                break;
//            case 1:
//                createQuestScreen(R.drawable.run_image, "Вы убежали от монстра, но потеряли все вещи.", new String[]{"Продолжить"});
//                break;
//            case 2:
//                createQuestScreen(R.drawable.talk_image, "Вы поговорили с монстром, и он стал вашим другом.", new String[]{"Продолжить"});
//                break;
//            default:
//                break;
//        }
    }
}