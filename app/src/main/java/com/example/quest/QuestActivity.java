package com.example.quest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class QuestActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private List<Button> buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        buttons = new ArrayList<>();

        // Пример создания экрана квеста
        createQuestScreen(R.drawable.example_image, "Вы встретили монстра. Что будете делать?",
                new String[]{"Атаковать", "Убежать", "Поговорить"});
    }

    private void createQuestScreen(int imageResource, String text, String[] options) {
        // Устанавливаем картинку
        imageView.setImageResource(imageResource);

        // Устанавливаем текст
        textView.setText(text);

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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedOption = (int) v.getTag();
                    handleOptionSelection(selectedOption);
                }
            });

            // Добавляем кнопку в макет
            ((ViewGroup) textView.getParent()).addView(button);
            buttons.add(button);
        }
    }

    private void handleOptionSelection(int selectedOption) {
        // Обработка выбора пользователя
        switch (selectedOption) {
            case 0:
                createQuestScreen(R.drawable.attack_image, "Вы атаковали монстра и победили!", new String[]{"Продолжить"});
                break;
            case 1:
                createQuestScreen(R.drawable.run_image, "Вы убежали от монстра, но потеряли все вещи.", new String[]{"Продолжить"});
                break;
            case 2:
                createQuestScreen(R.drawable.talk_image, "Вы поговорили с монстром, и он стал вашим другом.", new String[]{"Продолжить"});
                break;
            default:
                break;
        }
    }
}