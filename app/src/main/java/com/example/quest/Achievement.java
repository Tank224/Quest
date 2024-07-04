package com.example.quest;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class Achievement extends AppCompatActivity {
    SharedPreferences sharedPref;
    char[] char_array;
    String Key_string;
    Achive achive1;
    Achive achive2;
    Achive achive3;
    Achive achive4;
    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.achievement_main);
            View oView = findViewById(R.id.AchiveLayout);
            oView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN);

            ImageView imageView1 = findViewById(R.id.Achiven1);
            ImageView imageView2 = findViewById(R.id.Achiven2);
            ImageView imageView3 = findViewById(R.id.Achiven3);
            ImageView imageView4 = findViewById(R.id.Achiven4);

            sharedPref = getSharedPreferences(SAVED_TEXT, MODE_PRIVATE);

            achive1 = new Achive(false, imageView1, R.drawable.corona_black, R.drawable.corona);
            achive2 = new Achive(false, imageView2, R.drawable.corona_black, R.drawable.corona);
            achive3 = new Achive(false, imageView3, R.drawable.corona_black, R.drawable.corona);
            achive4 = new Achive(false, imageView4, R.drawable.corona_black, R.drawable.corona);

            //Save_0();   //------ использовать для очистки данных очивок.
            //загрузили - сразу сбрасываете приложение и комментите метод
            Key_string = sharedPref.getString(SAVED_TEXT, "");

            if (Key_string.equals("")) {
                Save_0();
                new AlertDialog.Builder(this)
                        .setTitle("Проверка")
                        .setMessage("0000 - " + Key_string)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } else {
                char_array = Key_string.toCharArray();
                Check_state(achive1, char_array[0]);
                Check_state(achive2, char_array[1]);
                Check_state(achive3, char_array[2]);
                Check_state(achive4, char_array[3]);
            }


            Update(achive1);
            Update(achive2);
            Update(achive3);
            Update(achive4);


        } catch (Exception e) {
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }
    }

    public void Update(Achive achive) {
        boolean Status = achive.getStatus();
        ImageView ImVi = achive.getImageView();
        int picture_do = achive.getIDDO();
        int picture_bet = achive.getIDBET();
        if (Status) {
            ImVi.setBackgroundResource(R.drawable.paper);
            ImVi.setImageResource(picture_bet);
        } else {
            ImVi.setBackgroundResource(R.drawable.paper);
            ImVi.setImageResource(picture_do);
        }

    }


    public void Check_state(Achive achive, char i) {
        if (i == '0') {
            achive.Status = false;
        } else if (i == '1') {
            achive.Status = true;
        }
    }

    void Save() {
        String key = String.valueOf(char_array);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SAVED_TEXT, key);
        editor.apply();
    }

    void Save_0() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SAVED_TEXT, "0000");
        editor.apply();
    }

    public void onBackClick(View view) {
        Save();
        new AlertDialog.Builder(this)
                .setTitle("Save")
                .setMessage(Arrays.toString(char_array))
                .setPositiveButton(android.R.string.ok, null)
                .show();
        finish();
    }

    public void Change1(View view) {
        achive1.Status = true;
        char_array[0] = '1';
        Update(achive1);
    }

    public void Change2(View view) {
        achive2.Status = true;
        char_array[1] = '1';
        Update(achive2);
    }

    public void Change3(View view) {
        achive3.Status = true;
        char_array[2] = '1';
        Update(achive3);
    }

    public void Change4(View view) {
        achive4.Status = true;
        char_array[3] = '1';
        Update(achive4);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Save();
    }

}
