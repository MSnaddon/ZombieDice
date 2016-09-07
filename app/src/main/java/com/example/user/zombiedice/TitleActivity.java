package com.example.user.zombiedice;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by user on 01/09/2016.
 */
public class TitleActivity extends AppCompatActivity {

    ImageView mSplashArt;
    EditText mNumbersOfPlayers;
    Button mStartVanilla;
    Button mStartExpansion;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_activity);

        mSplashArt = (ImageView) findViewById(R.id.splashImage);
        mNumbersOfPlayers = (EditText) findViewById(R.id.numberOfPlayers);
        mStartVanilla = (Button) findViewById(R.id.start_vanilla);
        mStartExpansion = (Button)findViewById(R.id.start_expansion);



        mStartVanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberOfPlayersInput = mNumbersOfPlayers.getText().toString();

                if (!numberOfPlayersInput.isEmpty()) {
                    MediaPlayer mp = MediaPlayer.create(TitleActivity.this, R.raw.start_moan2);
                    mp.start();
                    Intent intent = new Intent(TitleActivity.this, GameActivity.class);
                    int players = Integer.parseInt(numberOfPlayersInput);
                    Log.d("TitleActivity", "Number of players is " + ((Integer) players).toString());
                    intent.putExtra("NumberOfPlayers", players);
                    startActivity(intent);
                }

            }
        });

        mStartExpansion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String numberOfPlayersInput = mNumbersOfPlayers.getText().toString();
                if (!numberOfPlayersInput.isEmpty()) {
                    MediaPlayer mp = MediaPlayer.create(TitleActivity.this, R.raw.start_moan);
                    mp.start();
                    Intent intent = new Intent(TitleActivity.this, GameActivityHH.class);
                    int players = Integer.parseInt(numberOfPlayersInput);
                    Log.d("TitleActivity", "Number of players is " + ((Integer) players).toString());
                    intent.putExtra("NumberOfPlayers", players);
                    startActivity(intent);
                }
            }
        });


    }
}
