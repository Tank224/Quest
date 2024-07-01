package com.example.quest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.yandex.mapkit.geometry.Point;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ImageView clickableImageView2;
    private ImageView anotherSmallImage;
    private ImageView centerImageView;
    private LinearLayout interactionLayout;
    private ImageView buttonImage1;
    private ImageView buttonImage2;
    private TextView textView;
    private ImageView bigpaperImageView;
    public static Point point1 = new Point(57.766977559205536, 40.92477980384792);
    public static Point point2 = new Point(57.766603637743245, 40.929353020483006);

    public static String quest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View oView = findViewById(R.id.mainLayout);
        oView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        clickableImageView2 = findViewById(R.id.clickableImageView2);
        anotherSmallImage = findViewById(R.id.anotherSmallImage);
        centerImageView = findViewById(R.id.centerImageView);
        interactionLayout = findViewById(R.id.interactionLayout);
        buttonImage1 = findViewById(R.id.buttonImage1);
        buttonImage2 = findViewById(R.id.buttonImage2);
        textView = findViewById(R.id.textView);
        bigpaperImageView = findViewById(R.id.bigpaperImageView);
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            Toast.makeText(MainActivity.this, "Доступ запрещен\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    TedPermission.create()
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("Для использования приложения необходимо предоставить доступ к местоположению\n\nTo use the application, you must provide location access")
                            .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                            .check();
                }
            }
        };
        thread.start();
    }
    public void onSmallImageClick2(View view) {
        anotherSmallImage.setVisibility(View.INVISIBLE);
        animateImageToCenter();
    }

    public void onAnotherSmallImageClick(View view) {
        clickableImageView2.setVisibility(View.INVISIBLE);
        animateImageToCenter();
    }

    private void animateImageToCenter() {
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
                buttonImage1.setVisibility(View.VISIBLE);
                buttonImage2.setVisibility(View.VISIBLE);
                clickableImageView2.setClickable(false);
                bigpaperImageView.setVisibility(View.VISIBLE); // Сделать bigpaperImageView видимой
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
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
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
                clickableImageView2.setClickable(true);
                clickableImageView2.setVisibility(View.VISIBLE);
                anotherSmallImage.setVisibility(View.VISIBLE);
                bigpaperImageView.setVisibility(View.GONE); // Скрыть bigpaperImageView
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        set.start();
    }
}