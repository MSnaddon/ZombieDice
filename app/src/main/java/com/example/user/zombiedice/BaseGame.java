package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 03/09/2016.
 */
public class BaseGame extends Game {


    public BaseGame(int numberOfPlayers){
        super(numberOfPlayers);
        initializeDiceAndCounters();
    }

    public void removeScoreDice(){
        // change to replace entries with dummys (null)
        for (int i=2; i > -1; i--){
            if (!(previousRollOutcome.get(i).equals(Side.FOOTSTEP))) {
                playDice.set(i,null);
            }
        }
    }





    public HashMap playSubTurn(){
        previousDice.clear();
        previousRollOutcome.clear();
        HashMap turnStats = new HashMap();
        ArrayList<Side> outcome = currentPlayer.rollDice(playDice);
        for (Side side : outcome){
            previousRollOutcome.add(side); //Make clone of outcomes
            switch (side){
                case SHOTGUN: shotgunCounter ++;
                    break;
                case BRAIN: brainCounter ++;
                    break;
            }
        }
        turnStats.put("Shotgun", shotgunCounter);
        turnStats.put("Brain", brainCounter);
        turnStats.put("Footsteps", 3 - brainCounter - shotgunCounter);
        previousDice = (ArrayList<Dice>)playDice.clone();
        removeScoreDice();
        baseDiceBag.dealToThree(playDice);
        return turnStats;
    }
    public boolean isNextRound(){
        // adding score if not dead
        if (shotgunCounter <3){
            currentPlayer.addToScore(brainCounter);
        }

        // Set up for next player
        initializeDiceAndCounters();
        turnCounter ++;
        currentPlayer = players.get( turnCounter%players.size() );

        // checking if the game is over (can refactor to instance variable if want to)
        boolean nextRound = true;
        if (turnCounter%players.size() == 0) {
            for (Player player : players){
                if (player.getScore()>12){
                    nextRound = false;
                    break;
                }
            }
        }
        return nextRound;
    }

    //helper methods
    public void initializeDiceAndCounters(){
        baseDiceBag.genDice();
        playDice.clear();
        baseDiceBag.dealToThree(playDice);
        resetPointCounters();
    }
    public void resetPointCounters(){
        this.brainCounter = 0;
        this.shotgunCounter = 0;
    }

}