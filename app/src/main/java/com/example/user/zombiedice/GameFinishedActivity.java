package com.example.user.zombiedice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 04/09/2016.
 */
public class GameFinishedActivity extends AppCompatActivity {

    TextView mWinner;
    TextView mStandings;
    Button mAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        mWinner = (TextView)findViewById(R.id.winner);
        mStandings = (TextView)findViewById(R.id.standings);
        mAgain = (Button)findViewById(R.id.restart_game);

        Bundle extras = getIntent().getExtras();
        String[] players = extras.getStringArray("Players");
        int[] scores = extras.getIntArray("Scores");
        String winner = "Winner is : " + players[0] + " : " + ((Integer)scores[0]).toString();
        mWinner.setText(winner);
        String standings = "";
        for (int i=0; i < scores.length;i++){
            standings += players[i] + " : " + ((Integer)scores[i]).toString() + "\n";
        }
        mStandings.setText(standings);

        mAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameFinishedActivity.this, TitleActivity.class);
                startActivity(intent);

            }
        });

    }
}
