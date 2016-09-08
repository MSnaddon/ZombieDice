package com.example.user.zombiedice;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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
public class GameActivityHH extends AppCompatActivity {

    TextView mPlayerName;
    TextView mTotalScore;
    TextView mTurnScoreBox;
    TextView mCurrentDice;
    ArrayList<TextView> mPreviousRolls;
    ArrayList<ImageView> mPreviousRollsImage;
    Button mContinueTurn;
    Button mEndTurn;
    GameHH gameHH;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        Bundle extras = getIntent().getExtras();
        int playerNum = extras.getInt("NumberOfPlayers");
        Log.d("GameActivity", "Number of players " + ((Integer) playerNum).toString());
        gameHH = new GameHH(playerNum);

        mPlayerName = (TextView) findViewById(R.id.player_name);
        mTotalScore = (TextView)findViewById(R.id.player_score);
        mTurnScoreBox = (TextView) findViewById(R.id.turn_score_box);
        mCurrentDice = (TextView) findViewById((R.id.current_dice));
        mContinueTurn = (Button) findViewById(R.id.continue_turn);
        mEndTurn = (Button) findViewById(R.id.end_turn);

        mPreviousRolls = new ArrayList<TextView>();
        mPreviousRolls.add((TextView) findViewById(R.id.previous_roll1));
        mPreviousRolls.add((TextView) findViewById(R.id.previous_roll2));
        mPreviousRolls.add((TextView) findViewById(R.id.previous_roll3));

        mPreviousRollsImage = new ArrayList<ImageView>();
        mPreviousRollsImage.add((ImageView) findViewById(R.id.result1));
        mPreviousRollsImage.add((ImageView) findViewById(R.id.result2));
        mPreviousRollsImage.add((ImageView) findViewById(R.id.result3));

        // Initial Page setup
        setupPage();

        mContinueTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameHH.playSubTurn();
                if (gameHH.getShotgunCounter() > 2) {
                    MediaPlayer mp = MediaPlayer.create(GameActivityHH.this, R.raw.shot_dead);
                    mp.start();
                    Toast.makeText(GameActivityHH.this, "You have been shot " + ((Integer) gameHH.getShotgunCounter()).toString() + " times", Toast.LENGTH_SHORT).show();
                    checkForNextTurn();
                    clearPreviousRolls();
                } else {
                    displayPreviousRolls();
                }

                setupPage();
            }
        });

        mEndTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaPlayer mp = MediaPlayer.create(GameActivityHH.this, R.raw.nom_nom_nom);
                mp.start();

                //check to see if game is over.
                checkForNextTurn();
                setupPage();
                Toast.makeText(GameActivityHH.this, "They live... for now", Toast.LENGTH_SHORT).show();
                clearPreviousRolls();
            }
        });
    }

    private void displayPreviousRolls() {
        int index = 0;
        for (Dice dice : gameHH.getPreviousDice()) {
            TextView view = mPreviousRolls.get(index);
            ImageView imageView = mPreviousRollsImage.get(index);
            Side outcome = gameHH.getPreviousOutcome().get(index);
            setColour(view, dice);
            setDiceImages(imageView, dice, outcome);

            String outputText = dice.getType() + "\n" + outcome.valueOf();
            view.setText(outputText);
            index++;
        }
    }

    private void clearPreviousRolls() {
        for (TextView view : mPreviousRolls) {
            view.setText("");
        }
        for (ImageView view : mPreviousRollsImage) {
            view.setImageResource(R.drawable.placeholder);
        }
    }

    private void setupPage() {

        String player = gameHH.getCurrentPlayer().getName();
        mPlayerName.setText(player);

        String score = "Total Score\n" + ((Integer)gameHH.getCurrentPlayer().getScore()).toString();
        mTotalScore.setText(score);

        String scoreBoxText = "Brains :" + ((Integer) gameHH.getBrainCounter()).toString() + " Shotguns: " + ((Integer) gameHH.getShotgunCounter()).toString();
        mTurnScoreBox.setText(scoreBoxText);

        String currentDiceString = "";
        for (Dice dice : gameHH.getDice()) {
            currentDiceString += dice.getType() + "  ";
        }
        mCurrentDice.setText(currentDiceString);
        // end of page setup
    }

    public void checkForNextTurn() {
        boolean nextRound = gameHH.isNextRound();
        //if game is over, go to game over activity
        if (!nextRound) {
            Intent intent = new Intent(GameActivityHH.this, GameFinishedActivity.class);
            int index = 0;
            int arraySize = gameHH.getPlayers().size();
            String[] playerNames = new String[arraySize];
            int[] playerScores = new int[arraySize];
            for (Player player : gameHH.getStandings()) {
                playerScores[index] = player.getScore();
                playerNames[index] = player.getName();
                index++;
            }
            intent.putExtra("Players", playerNames);
            intent.putExtra("Scores", playerScores);
            startActivity(intent);
        }
    }

    private void setColour(TextView view, Dice dice) {
        switch (dice.getType()) {
            case "Red":
                view.setTextColor(Color.RED);
                return;
            case "Yellow":
                view.setTextColor(0xff898900);
                return;
            case "Green":
                view.setTextColor(0xff008800);
                return;
        }
        //for HH game
        switch (dice.getType()) {
            case "Hottie":
                view.setTextColor(0xffdf61da);
                return;
            case "Hunk":
                view.setTextColor(0xff632f00);
        }
    }

    //Could refactor to use concatinated strings and resource finder
    private void setDiceImages(ImageView view, Dice dice, Side side){
        String type = dice.getType();
        switch (type) {
            case "Green": {
                switch (side) {
                    case BRAIN: {
                        view.setImageResource(R.drawable.green_brain);
                        return;
                    }
                    case SHOTGUN: {
                        view.setImageResource(R.drawable.green_shotgun);
                        return;
                    }
                    case FOOTSTEP: {
                        view.setImageResource(R.drawable.green_footsteps);
                        return;
                    }
                }
            }
            case "Yellow": {
                switch (side) {
                    case BRAIN: {
                        view.setImageResource(R.drawable.yellow_brain);
                        return;
                    }
                    case SHOTGUN: {
                        view.setImageResource(R.drawable.yellow_shotgun);
                        return;
                    }
                    case FOOTSTEP: {
                        view.setImageResource(R.drawable.yellow_footsteps);
                        return;
                    }
                }
            }
            case "Red": {
                switch (side) {
                    case BRAIN: {
                        view.setImageResource(R.drawable.red_brain);
                        return;
                    }
                    case SHOTGUN: {
                        view.setImageResource(R.drawable.red_shotgun);
                        return;
                    }
                    case FOOTSTEP: {
                        view.setImageResource(R.drawable.red_footsteps);
                        return;
                    }
                }
            }
            case "Hunk": {
                switch (side) {
                    case DOUBLEBRAIN: {
                        view.setImageResource(R.drawable.hunk_doublebrain);
                        return;
                    }
                    case SHOTGUN: {
                        view.setImageResource(R.drawable.hunk_shotgun);
                        return;
                    }
                    case DOUBLESHOTGUN: {
                        view.setImageResource(R.drawable.hunk_doubleshotgun);
                        return;
                    }
                    case FOOTSTEP: {
                        view.setImageResource(R.drawable.hunk_footsteps);
                        return;
                    }
                }
            }
            case "Hottie": {
                switch (side) {
                    case BRAIN: {
                        view.setImageResource(R.drawable.hottie_brain);
                        return;
                    }
                    case SHOTGUN: {
                        view.setImageResource(R.drawable.hottie_shotgun);
                        return;
                    }
                    case FOOTSTEP: {
                        view.setImageResource(R.drawable.hottie_footsteps);
                    }
                }
            }
        }
    }
}