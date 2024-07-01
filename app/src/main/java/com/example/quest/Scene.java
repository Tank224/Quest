package com.example.quest;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Scene {
    private String name;
    private int imageResource;
    private String text;
    private String[] options;
    public Scene(String name, int imageResource, String text, String[] options){
        this.name = name;
        this.imageResource = imageResource;
        this.text = text;
        this.options = options;
    }
    public String getName(){
        return name;
    }
    public int getImageResource(){
        return imageResource;
    }
    public String getText(){
        return text;
    }
    public String[] getOptions(){
        return options;
    }
}
