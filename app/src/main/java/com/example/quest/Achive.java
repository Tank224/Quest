package com.example.quest;

import static com.example.quest.R.style.Theme_Quest;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
public class Achive extends AppCompatActivity {

    public boolean Status;
    private ImageView ImageView;
    private int IDDO;
    private int IDBET;

    public int getIDDO() {
        return IDDO;
    }
    public int getIDBET() {
        return IDBET;
    }

    public Achive(boolean state, ImageView imageView, int idDO, int idBet) {
        this.Status = state;
        this.ImageView = imageView;
        this.IDDO = idDO;
        this.IDBET = idBet;
    }

    public android.widget.ImageView getImageView() {
        return ImageView;
    }

    public boolean getStatus() {
        return Status;
    }

}
