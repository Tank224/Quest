package com.example.quest;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;

public class Achievement extends AppCompatActivity {
    Achive achive1;
    Achive achive2;
    Achive achive3;
    Achive achive4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        achive1 = new Achive(false, imageView1, R.drawable.corona_black, R.drawable.corona);
        achive2 = new Achive(false, imageView2, R.drawable.corona_black, R.drawable.corona);
        achive3 = new Achive(false, imageView3, R.drawable.corona_black, R.drawable.corona);
        achive4 = new Achive(false, imageView4, R.drawable.corona_black, R.drawable.corona);

        Update(achive1);
        Update(achive2);
        Update(achive3);
        Update(achive4);



    }

    public void Update(Achive achive)
    {
       boolean Status = achive.getStatus();
       ImageView ImVi = achive.getImageView();
       int picture_do = achive.getIDDO();
       int picture_bet = achive.getIDBET();
       if(Status)
       {
           ImVi.setBackgroundResource(R.drawable.paper);
           ImVi.setImageResource(picture_bet);
       }
       else
       {
           ImVi.setBackgroundResource(R.drawable.paper);
           ImVi.setImageResource(picture_do);
       }

    }



    public void onBackClick(View view) {
        finish();
    }

    public void Change1(View view){
        achive1.Status = true;
        Update(achive1);
    }

    public void Change2(View view){
        achive2.Status = true;
        Update(achive2);
    }
    public void Change3(View view){
        achive3.Status = true;
        Update(achive3);
    }
    public void Change4(View view){
        achive4.Status = true;
        Update(achive4);
    }
}
