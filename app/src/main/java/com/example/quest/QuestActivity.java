package com.example.quest;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.MapKitFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private List<Button> buttons;
    List<Scene> scenes = new ArrayList<> ();
    static SharedPreferences sharedPref;
    static char[] char_array;
    static String Key_string;
    final String SAVED_TEXT = "saved_text";

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
        scenes.add(new Scene(
                "Начало",
                R.drawable.susanin_home,
                "Польско-литовский отряд, стремящийся помешать воцарению Михаила Романова, пришел к тебе за информацией о " +
                "местонахождении царя. Они требуют выдать, где прячется молодой Романов.",
                new String[]{"Согласиться помочь врагу и показать ему правильный путь",
                        "Согласиться помочь, но увести их в противоположную сторону", "Попытаться убежать"}));
        scenes.add(new Scene("Согласиться помочь врагу и показать ему правильный путь",
                R.drawable.ipatiy, "Ты предаешь царя и показываеь польско-литовскому " +
                "отряду, где он прячется.", new String[]{}));
        scenes.add(new Scene("Согласиться помочь, но увести их в противоположную сторону",
                R.drawable.forest1, "Ты решил помочь полякам, но увести их в противоположную" +
                " сторону. Теперь ты ведешь их через лес.", new String[]{"Выбрать опасный путь " +
                "через болото","Выбрать длинный, но безопасный путь через чащу леса"}));
        scenes.add(new Scene("Попытаться убежать", R.drawable.died, "Ты решаешь не " +
                "помогать полякам и пытаешься убежать. Они настигают тебя.", new String[]{}));
        scenes.add(new Scene(
                "Выбрать опасный путь через болото",
                R.drawable.boloto,
                "Ты выбрал опасный путь через болота. Польско-литовский отряд начинает сомневаться в" +
                " твоих намерениях, но ты продолжаешь вести их вперед.",
                new String[]{"Продолжить путь, игнорируя недоверие", "\"Это правильный путь, нужно только обойти болото\""}));
        scenes.add(new Scene("Выбрать длинный, но безопасный путь через чащу леса",
                R.drawable.forest2, "Ты выбрал путь через чащу леса. Польско-литовский отряд" +
                " начинает терять терпение.",
                new String[]{"Завести врага в дебри", "Попытаться увести врага в болото"}));
        scenes.add(new Scene(
                "Продолжить путь, игнорируя недоверие",
                R.drawable.died,
                "Польско-литовский отряд понял, что ты их обманул. Ты не смог спасти царя",
                new String[]{}));
        scenes.add(new Scene(
                "\"Это правильный путь, нужно только обойти болото\"",
                R.drawable.book,
                "Ты начинаешь все дальше уводить врага в болото. В какой-то момент польско-литовский отряд понимает, что ты их обманул, но уже поздно. Вы тонете в болоте. Жертвуя собой ты спасаешь будущего царя.",
                new String[]{}));
        scenes.add(new Scene(
                "Предать царя",
                R.drawable.ipatiy,
                "Ты решаешь предать царя и показать полякам правильный путь.",
                new String[]{}));
        scenes.add(new Scene(
                "\"Вам уже не найти царя!\"",
                R.drawable.book,
                "Под пытками ты не выдаешь местонахождение Михаила Фёдоровича. Жертвуя собой ты спасаешь будущего царя.",
                new String[]{}));
        scenes.add(new Scene(
                "Завести врага в дебри",
                R.drawable.forest3,
                "Польско-литовский отряд понял, что ты их обманул. Они собираються вытянуть из тебя информацию пытками.",
                new String[]{"Предать царя", "\"Вам уже не найти царя!\""}));
        scenes.add(new Scene(
                "Попытаться увести врага в болото",
                R.drawable.died,
                "Польско-литовский отряд понял, что ты их обманул.",
                new String[]{}));
        //scenes.add(new Scene("", R.drawable.susanin_home, "В", new String[]{"Атаковать"}));

        createQuestScreen(scenes.get(0));
    }
    private void dolgorukiyQuest(){
        scenes.add(new Scene(
                "Начало",
                R.drawable.rivers,
                "Ты прибыл на пересечение двух рек Волги и Костромы.",
                new String[]{"Начать строительство немедленно", "Сначала провести разведку местности"}));
        scenes.add(new Scene(
                "Начать строительство немедленно",
                R.drawable.died,
                "На вас напали кочевники!",
                new String[]{}));
        scenes.add(new Scene(
                "Сначала провести разведку местности",
                R.drawable.holm,
                "Ты решил провести разведку местности. Разведчики вернулись изучив рельеф и " +
                        "обнаружив поблизости лагерь волжских булгар.",
                new String[]{"Начать строительство в низине у слияния рек Волга и Кострома","Начать строительство на холме на берегу Волги"}));
        scenes.add(new Scene(
                "Начать строительство в низине у слияния рек Волга и Кострома",
                R.drawable.flame_city,
                "Вы не заметили приближние врага. Детинец был разрушен.",
                new String[]{}));
        scenes.add(new Scene(
                "Начать строительство на холме на берегу Волги",
                R.drawable.flame_vilage,
                "Во время строительства приходит новость о нападении кочевников на окрестные деревни. Строительство детинца под угрозой.",
                new String[]{"Потратить ресурсы на строительство временных укреплений", "Собрать отряд и попытаться разбить кочевников"}));
        scenes.add(new Scene(
                "Собрать отряд и попытаться разбить кочевников",
                R.drawable.died,
                "Кочевников оказалось слишком много. Ваше войско разбито.",
                new String[]{}));
        scenes.add(new Scene(
                "Потратить ресурсы на строительство временных укреплений",
                R.drawable.wall,
                "Место выбрано, а земляные валы и деревянные стены построены. Что " +
                        "будет первым построено внутри?",
                new String[]{"Ремесленные мастерские", "Княжеский терем"}));
        scenes.add(new Scene(
                "Княжеский терем",
                R.drawable.bund,
                "Экономика города в упадке. Жители устроили бунт!",
                new String[]{}));
        scenes.add(new Scene(
                "Ремесленные мастерские",
                R.drawable.city,
                "Город разрастается. К детинцу прилегают дома и усадьбы. Работает кузня и " +
                        "ювелирная мастерская. Ведется активная торговля. Кострома " +
                        "станет надежным форпостом на северо-восточных рубежах твоих владений.",
                new String[]{}));
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
                button.setTextColor(Color.parseColor("#2a2a2a"));
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
    public void onBackClick(View view) {
        finish();
//        Intent intent = new Intent(QuestActivity.this, MainActivity.class);
//        startActivity(intent);
    }

    private void handleOptionSelection(String selectedOption) {
        for (Scene i : scenes){
            if (i.getName().equals(selectedOption)){
                createQuestScreen(i);
                break;
            }
        }
        if (selectedOption.equals("Ремесленные мастерские"))
        {
                Toast.makeText(QuestActivity.this, "Вы получили новое достижение!",
                        Toast.LENGTH_SHORT).show();
                sharedPref = getSharedPreferences(SAVED_TEXT, MODE_PRIVATE);
                Key_string = sharedPref.getString(SAVED_TEXT, "");
                char_array = Key_string.toCharArray();
                char_array[1] = '1';
                String key = String.valueOf(char_array);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SAVED_TEXT, key);
                editor.apply();
        }
        if(selectedOption.equals("\"Вам уже не найти царя!\"") || selectedOption.equals("\"Это " +
                "правильный путь, нужно только обойти болото\""))
        {
                Toast.makeText(QuestActivity.this, "Вы получили новое достижение!",
                        Toast.LENGTH_SHORT).show();
                sharedPref = getSharedPreferences(SAVED_TEXT, MODE_PRIVATE);
                Key_string = sharedPref.getString(SAVED_TEXT, "");
                char_array = Key_string.toCharArray();
                char_array[0] = '1';
                String key = String.valueOf(char_array);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(SAVED_TEXT, key);
                editor.apply();
        }

    }
}