package com.example.user.zombiedice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by user on 02/09/2016.
 */
public class GameActivity extends AppCompatActivity {

    TextView mPlayerName;
    TextView mTurnScoreBox;
    TextView mCurrentDice;
    TextView mPreviousRoll;
    Button mContinueTurn;
    Button mEndTurn;
    Game game;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Bundle extras = getIntent().getExtras();
        int playerNum = extras.getInt("NumberOfPlayers");
        Log.d("GameActivity","Number of players " + ((Integer)playerNum).toString());
        game = new Game(playerNum);

        mPlayerName = (TextView)findViewById(R.id.player_name);
        mTurnScoreBox = (TextView)findViewById(R.id.turn_score_box);
        mCurrentDice = (TextView)findViewById((R.id.current_dice));
        mPreviousRoll = (TextView)findViewById(R.id.previous_roll);
        mContinueTurn = (Button)findViewById(R.id.continue_turn);
        mEndTurn = (Button)findViewById(R.id.end_turn);

        // Initial Page setup
        setupPage();

        mContinueTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                game.playSubTurn();
                String outcome = game.getPreviousRollString();

                if (game.getShotgunCounter() > 2) {
                    outcome = "-Next Player-";
                    Toast.makeText(GameActivity.this,  "You have been shot " + ((Integer)game.getShotgunCounter()).toString() + " times" , Toast.LENGTH_SHORT).show();
                    checkForNextTurn();
                }
                mPreviousRoll.setText(outcome);
                setupPage();
            }
        });

        mEndTurn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //check to see if game is over.
                checkForNextTurn();
                setupPage();
                mPreviousRoll.setText("-Next Player-");
            }
        });
    }

    private void setupPage(){

        String playerAndScore = game.getCurrentPlayer().getName() + "                Score: " + ((Integer) game.getCurrentPlayer().getScore()).toString();
        mPlayerName.setText(playerAndScore);

        String scoreBoxText = "Brains :" + ((Integer)game.getBrainCounter()).toString() + " Shotguns: " + ((Integer)game.getShotgunCounter()).toString();
        mTurnScoreBox.setText(scoreBoxText);

        String currentDiceString = "";
        for (Dice dice : game.getDice()) {
            currentDiceString += dice.getType() + "  ";
        }
        mCurrentDice.setText(currentDiceString);
        // end of page setup
    }

    public void checkForNextTurn(){
        boolean nextRound = game.isNextRound();
        //if game is over, go to game over activity
        if (!nextRound){
            Intent intent = new Intent(GameActivity.this, GameFinishedActivity.class);
            int index = 0;
            int arraySize = game.getPlayers().size();
            String[] playerNames = new String[arraySize];
            int[] playerScores = new int[arraySize];
            for (Player player : game.getStandings()) {
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


