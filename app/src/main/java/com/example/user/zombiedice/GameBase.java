package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 03/09/2016.
 */
public class GameBase extends Game {



    public GameBase(int numberOfPlayers){
        super(numberOfPlayers);
        diceBag = new DiceBagBase();
        initializeDiceAndCounters();
    }

    public HashMap playSubTurn(){
        clearPrevious();
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
        previousDice = (ArrayList<Dice>)playDice.clone(); //Make clone of outcomes
        removeScoreDice();
        diceBag.dealToThree(playDice);
        return turnStats;
    }


}
