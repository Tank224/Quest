package com.example.quest;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageStat;
    private TextView textView2;
    private ImageView clickableImageView2;
    private ImageView anotherSmallImage;
    private ImageView centerImageView;
    private LinearLayout interactionLayout;
    private ImageView buttonImage1;
    private ImageView clickableAchievements;
    private ImageView buttonImage2;
    private TextView textView;
    private ImageView bigpaperImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickableImageView2 = findViewById(R.id.clickableImageView2);
        anotherSmallImage = findViewById(R.id.anotherSmallImage);
        centerImageView = findViewById(R.id.centerImageView);
        interactionLayout = findViewById(R.id.interactionLayout);
        buttonImage1 = findViewById(R.id.buttonImage1);
        buttonImage2 = findViewById(R.id.buttonImage2);
        textView = findViewById(R.id.textView);
        bigpaperImageView = findViewById(R.id.bigpaperImageView);
        textView2 = findViewById(R.id.textView2);
        imageStat=findViewById(R.id.imageStat);
        clickableAchievements=findViewById(R.id.clickableAchievements);

        anotherSmallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSmallImageClick2(v);
            }
        });

        clickableImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnotherSmallImageClick(v);
            }
        });
    }

    public void onSmallImageClick2(View view) {
        anotherSmallImage.setVisibility(View.INVISIBLE);
        animateImageToCenter("Жизнь состоит из одних\n вопросов,а хочется,\n чтобы она состояла \nиз одних ответов");//меняется текст на квесте
        textView2.setText("Квест 1");
        imageStat.setImageResource(R.drawable.statdolgorukiy2);//меняется картинка на статуе
    }

    public void onAnotherSmallImageClick(View view) {
        clickableImageView2.setVisibility(View.INVISIBLE);
        animateImageToCenter("Жить, как говорится, хорошо!\nА хорошо жить еще лучше!");
        textView2.setText("Квест 2");
        imageStat.setImageResource(R.drawable.statsusanin);//меняется картинка на статуе
    }
    public void onAchievementsClick(View view) {
        // Обработка нажатия на clickableAchievements
    }
    private void animateImageToCenter(String text) {
        ObjectAnimator moveX = ObjectAnimator.ofFloat(clickableImageView2, "translationX", 0f);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(clickableImageView2, "translationY", 0f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(moveX, moveY);
        set.setDuration(500);
        set.addListener(new AnimatorSet.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                clickableImageView2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                clickableImageView2.setVisibility(View.VISIBLE);
                interactionLayout.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                buttonImage1.setVisibility(View.VISIBLE);
                buttonImage2.setVisibility(View.VISIBLE);
                imageStat.setVisibility(View.VISIBLE);
                clickableImageView2.setClickable(false);
                bigpaperImageView.setVisibility(View.VISIBLE);
                textView.setText(text); // Устанавливаем текст в textView
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        set.start();
    }

    public void onButtonImage1Click(View view) {
        resetToInitialState();
    }

    public void onButtonImage2Click(View view) {
        // Обработка нажатия на buttonImage2
    }

    private void resetToInitialState() {
        ObjectAnimator moveX = ObjectAnimator.ofFloat(clickableImageView2, "translationX", 0f);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(clickableImageView2, "translationY", 0f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(moveX, moveY);
        set.setDuration(500);
        set.addListener(new AnimatorSet.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                interactionLayout.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                buttonImage1.setVisibility(View.GONE);
                buttonImage2.setVisibility(View.GONE);
                imageStat.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                clickableImageView2.setClickable(true);
                clickableImageView2.setVisibility(View.VISIBLE);
                anotherSmallImage.setVisibility(View.VISIBLE);
                bigpaperImageView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        set.start();
    }
}