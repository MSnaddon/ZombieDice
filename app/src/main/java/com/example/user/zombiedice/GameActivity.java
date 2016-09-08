package com.example.user.zombiedice;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 02/09/2016.
 */
public class GameActivity extends AppCompatActivity {

    TextView mPlayerName;
    TextView mTotalScore;
    TextView mTurnScoreBox;
    TextView mCurrentDice;
    ImageView mImageView1;
    ImageView mImageView2;
    ImageView mImageView3;
    ArrayList<TextView> mPreviousRolls;
    Button mContinueTurn;
    Button mEndTurn;
    GameBase gameBase;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Bundle extras = getIntent().getExtras();
        int playerNum = extras.getInt("NumberOfPlayers");
        Log.d("GameActivity","Number of players " + ((Integer)playerNum).toString());
        gameBase = new GameBase(playerNum);

        mPlayerName = (TextView)findViewById(R.id.player_name);
        mTotalScore = (TextView)findViewById(R.id.player_score);
        mTurnScoreBox = (TextView)findViewById(R.id.turn_score_box);
        mCurrentDice = (TextView)findViewById((R.id.current_dice));
        mContinueTurn = (Button)findViewById(R.id.continue_turn);
        mEndTurn = (Button)findViewById(R.id.end_turn);

        mImageView1 = (ImageView)findViewById(R.id.result1);
        mImageView2 = (ImageView)findViewById(R.id.result2);
        mImageView3 = (ImageView)findViewById(R.id.result3);
        mImageView1.setVisibility(View.GONE);
        mImageView2.setVisibility(View.GONE);
        mImageView3.setVisibility(View.GONE);

        mPreviousRolls = new ArrayList<TextView>();
        mPreviousRolls.add((TextView)findViewById(R.id.previous_roll1));
        mPreviousRolls.add((TextView)findViewById(R.id.previous_roll2));
        mPreviousRolls.add((TextView)findViewById(R.id.previous_roll3));

        // Initial Page setup
        setupPage();

        mContinueTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBase.playSubTurn();
                if (gameBase.getShotgunCounter() > 2) {
                    MediaPlayer mp = MediaPlayer.create(GameActivity.this, R.raw.shot_dead);
                    mp.start();
                    Toast.makeText(GameActivity.this, "You have been shot " + ((Integer) gameBase.getShotgunCounter()).toString() + " times", Toast.LENGTH_SHORT).show();
                    checkForNextTurn();
                    clearPreviousRolls();
                }else{
                    displayPreviousRolls();
                }

                setupPage();
            }
        });

        mEndTurn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                MediaPlayer mp =  MediaPlayer.create(GameActivity.this,R.raw.nom_nom_nom);
                mp.start();

                //check to see if game is over.
                checkForNextTurn();
                setupPage();
                Toast.makeText(GameActivity.this, "They live... for now", Toast.LENGTH_SHORT).show();
                clearPreviousRolls();
            }
        });
    }

    private void setColour(TextView view, Dice dice){
        switch (dice.getType()) {
            case "Red": view.setTextColor(Color.RED);
                return;
            case "Yellow": view.setTextColor(0xffbdbd00);
                return;
            case "Green":view.setTextColor(0xff00bb00);
                return;
        }


    }

    private void displayPreviousRolls() {
        int index = 0;
        for (Dice dice : gameBase.getPreviousDice()) {
            TextView view = mPreviousRolls.get(index);
            Side outcome = gameBase.getPreviousOutcome().get(index);
            setColour(view, dice);
            String outputText = dice.getType() + "\n" + outcome.valueOf();
            view.setText(outputText);
            index++;
        }
    }

    private void clearPreviousRolls(){
        for (TextView view : mPreviousRolls){view.setText("");}
    }

    private void setupPage(){

        String player = gameBase.getCurrentPlayer().getName();
        mPlayerName.setText(player);

        String score = "Total Score\n" + ((Integer)gameBase.getCurrentPlayer().getScore()).toString();
        mTotalScore.setText(score);

        String scoreBoxText = "Brains :" + ((Integer) gameBase.getBrainCounter()).toString() + " Shotguns: " + ((Integer) gameBase.getShotgunCounter()).toString();
        mTurnScoreBox.setText(scoreBoxText);

        String currentDiceString = "";
        for (Dice dice : gameBase.getDice()) {
            currentDiceString += dice.getType() + "  ";
        }
        mCurrentDice.setText(currentDiceString);
        // end of page setup
    }

    public void checkForNextTurn(){
        boolean nextRound = gameBase.isNextRound();
        //if game is over, go to game over activity
        if (!nextRound){
            Intent intent = new Intent(GameActivity.this, GameFinishedActivity.class);
            int index = 0;
            int arraySize = gameBase.getPlayers().size();
            String[] playerNames = new String[arraySize];
            int[] playerScores = new int[arraySize];
            for (Player player : gameBase.getStandings()) {
                playerScores[index] = player.getScore();
                playerNames[index] = player.getName();
                index ++;
            }
            intent.putExtra("Players", playerNames);
            intent.putExtra("Scores", playerScores);
            startActivity(intent);
        }
    }
}


