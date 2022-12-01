package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private LinearLayout gameLayout;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLayout = findViewById(R.id.game_layout);
        gameView = new GameView(this);
        gameLayout.addView(gameView);
    }

    public void onStartButtonClick(View view) {
        gameView.eraseCells();
    }
}