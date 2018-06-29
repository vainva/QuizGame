package com.example.valtteri.quizgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        TextView totalScoreLabel = (TextView) findViewById(R.id.totalScoreLabel);

        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);

        SharedPreferences setting = getSharedPreferences("quizGame", Context.MODE_PRIVATE);
        int totalScore = setting.getInt("totalScore", 0);
        totalScore += score;

        resultLabel.setText(score + " / 5");
        totalScoreLabel.setText("total score: " + totalScore);

        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("totalScore", totalScore);
        editor.commit();

        Button backButton;
        backButton = (Button) findViewById(R.id.back);

        backButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent nextPage = new Intent(ResultActivity.this, StartActivity.class);

                startActivity(nextPage);

            }

        });

    }

}
