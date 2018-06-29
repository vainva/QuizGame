package com.example.valtteri.quizgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBt1;
    private Button answerBt2;
    private Button answerBt3;
    private Button answerBt4;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 5;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    private DatabaseHelper dbHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countLabel = (TextView) findViewById(R.id.countLabel);
        questionLabel = (TextView) findViewById(R.id.questionLabel);
        answerBt1 = (Button) findViewById(R.id.answerBt1);
        answerBt2 = (Button) findViewById(R.id.answerBt2);
        answerBt3 = (Button) findViewById(R.id.answerBt3);
        answerBt4 = (Button) findViewById(R.id.answerBt4);

        // Valmistellaan database
        dbHelper = new DatabaseHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String table = "quiz";
        String[] columns = {"*"};  
        String orderBy = "RANDOM()";
        String limit = String.valueOf(QUIZ_COUNT);

        Cursor cursor;


            cursor = db.query(table, columns, null, null, null, null, orderBy, limit);


        while (cursor.moveToNext()) {
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(cursor.getString(0));
            tmpArray.add(cursor.getString(1));
            tmpArray.add(cursor.getString(2));
            tmpArray.add(cursor.getString(3));
            tmpArray.add(cursor.getString(4));


            quizArray.add(tmpArray);
        }


        showNextQuiz();

    }

    public void showNextQuiz() {

        // Päivitetään countLabel

        countLabel.setText("Question " + quizCount);

        // Luodaan satunnainen numero

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // Valitaan yksi quiz

        ArrayList<String> quiz =  quizArray.get(randomNum);

        // Asetetaan kysymys ja oikea vastaus

        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        // Asetetaan valinnat

        answerBt1.setText(quiz.get(0));
        answerBt2.setText(quiz.get(1));
        answerBt3.setText(quiz.get(2));
        answerBt4.setText(quiz.get(3));

        quizArray.remove(randomNum);
    }

    public void checkAnswer(View view) {

        // kun painat nappia

        Button answerBt = (Button) findViewById(view.getId());
        String btText = answerBt.getText().toString();

        String alertTitle;

        if (btText.equals(rightAnswer)) {
            // Vastaus oikein
            alertTitle = "Correct!";
            rightAnswerCount++;
        }
        else{
            // väärin
            alertTitle = "Wrong!";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // Näytä tulokset

                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);

                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();

    }

}
