package com.example.valtteri.quizgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final private String DBNAME = "quiz.sqlite";
    static final private int VERSION = 1;

    String quizData[][] = {{"Who had a 1973 hit with the song Hocus Pocus", "Focus", "AC/DC", "Kansas", "Oasis"},
    {"What is the capital of Peru?", "Lima", "London", "Manila", "Turku"},
    {"In which 1973 film does Yul Brynner play a robotic cowboy ?", "Westworld", "The Terminator", "Blade Runner", "American History X"},
    {"What is the defining characteristic of someone who is described as hirsute?", "Hairy", "Loud", "Funny", "Rude"},
    {"The idea of Socialism was articulated and advanced by whom?", "Karl Marx", "Vladimir Lenin", "Joseph Stalin", "Vladimir Putin"},
    {"When did Spanish Peninsular War start?", "1808", "1891", "1888", "1801"},
    {"What is the only state in America that does not have a flag in a shape with 4 edges?", "Ohio", "Florida", "Idaho", "Texas"},
    {"Which of the following former Yugoslavian states is landlocked?", "Serbia", "Bosnia and Herzegovina", "Montenegro", "Croatia"},
    {"The F1 season of 1994 is remembered for what tragic event?", "Death of Ayrton Senna (San Marino)","The Showdown (Australia)", "Verstappen on Fire (Germany)", "Schumachers Ban (Britain)"},
    {"In web design, what does CSS stand for?", "Cascading Style Sheet", "Counter Strike: Source", "Corrective Style Sheet", "Computer Style Sheet"},
    {"What is the capital of the US state Nevada?", "Carson City", "Las Vegas", "Henderson", "Reno"},
    {"What is the name of the corgi in Cowboy Bebop?", "Einstein", "Edward", "Rocket", "Joel"},
    {"What was the first Disney movie to use CGI?", "The Black Cauldron", "Tron", "Toy Story", "Fantasia"},
    {"Bohdan Khmelnytsky was which of the following?", "Leader of the Ukrainian Cossacks", "General Secretary of the Communist Party of the USSR", "Prince of Wallachia", "Grand Prince of Novgorod"},
    {"Which of these is NOT a Humongous Entertainment game franchise?", "Commander Keen", "Pajama Sam", "Putt-Putt", "Freddi Fish"}

    };

    public DatabaseHelper (Context context) {
        super(context, DBNAME, null, VERSION );
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    sqLiteDatabase.execSQL("CREATE TABLE quiz (" +
            "question TEXT PRIMARY KEY, answer TEXT, choice1 TEXT," +
            "choice2 TEXT, choice3 TEXT)");

    sqLiteDatabase.beginTransaction();
    try {
        SQLiteStatement sql = sqLiteDatabase.compileStatement(
                "INSERT INTO quiz(question, answer, choice1, choice2, choice3)"
                + "VALUES(?,?,?,?,?)"
        );

        //lisätään quizdata

        for(int i = 0; i < quizData.length; i++){
            sql.bindString(1, quizData[i][0]); // Kysymys
            sql.bindString(2, quizData[i][1]); // Oikea vastaus
            sql.bindString(3, quizData[i][2]); // Vaihtoehto 1
            sql.bindString(4, quizData[i][3]); // Vaihtoehto 2
            sql.bindString(5, quizData[i][4]); // Vaihtoehto 3

            sql.executeInsert();
        }
        sqLiteDatabase.setTransactionSuccessful();

    }catch (SQLiteException e) {

        e.printStackTrace();

    } finally {
        sqLiteDatabase.endTransaction();
    }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
