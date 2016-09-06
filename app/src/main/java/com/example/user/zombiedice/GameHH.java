package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public class GameHH extends Game {

    public GameHH(int numberOfPlayers) {
        super(numberOfPlayers);
        diceBag = new DiceBagHH();
        initializeDiceAndCounters();
    }

    public HashMap playSubTurn(){
        clearPrevious(); //clears previous dice and results

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

        HashMap turnStats = new HashMap();
        turnStats.put("Shotgun", shotgunCounter);
        turnStats.put("Brain", brainCounter);
        turnStats.put("Footsteps", 3 - brainCounter - shotgunCounter);
        previousDice = (ArrayList<Dice>)playDice.clone(); //Make clone of outcomes
        removeScoreDice();
        diceBag.dealToThree(playDice);
        return turnStats;
    }


    public void resetPointCounters(){
        this.brainCounter = 0;
        this.shotgunCounter = 0;
    }





}