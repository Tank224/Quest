package com.example.quest;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Achievement extends AppCompatActivity {

    private ImageView imageView1;
    private boolean flag = false;
    private Resources r;
    private Drawable[] layers = new Drawable[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement_main);
        View oView = findViewById(R.id.AchiveLayout);
        oView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        imageView1 = (ImageView) findViewById(R.id.Achive1);
        r = getResources();
        LayerDrawable layerDrawable;
        layers[0] = r.getDrawable(R.drawable.paper);
        if (flag == false)
        {
            layers[1] = r.getDrawable(R.drawable.corona_black);
        }
        else
        {
            layers[1] = r.getDrawable(R.drawable.corona);
        }
        layerDrawable = new LayerDrawable(layers);
        imageView1.setImageDrawable(layerDrawable);
    }
    public void onBackClick(View view) {
        finish();
    }
    public void Change(View view){
        flag = true;
        r = getResources();
        layers[1] = r.getDrawable(R.drawable.corona);
        LayerDrawable layerDraw = new LayerDrawable(layers);
        imageView1.setImageDrawable(layerDraw);
    }

}
