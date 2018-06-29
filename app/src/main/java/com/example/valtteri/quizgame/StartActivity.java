package com.example.valtteri.quizgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startGame;
        startGame = (Button) findViewById(R.id.startGame);

        startGame.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent nextPage = new Intent(StartActivity.this, MainActivity.class);
                startActivity(nextPage);
            }

        });

    }
}
