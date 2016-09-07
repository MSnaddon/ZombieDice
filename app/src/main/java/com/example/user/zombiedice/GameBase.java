package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 03/09/2016.
 */
public class GameBase extends Game {

    DiceBag diceBag;

    public GameBase(int numberOfPlayers){
        super(numberOfPlayers);
        diceBag = new DiceBagBase();
        initializeDiceAndCounters();

    }

    public void playerRollDice() {
        ArrayList<Side> outcome = currentPlayer.rollDice(playDice);
        for (Side side : outcome) {
            previousRollOutcome.add(side); //Make clone of outcomes
            switch (side) {
                case SHOTGUN:
                    shotgunCounter++;
                    break;
                case BRAIN:
                    brainCounter++;
                    break;
            }
        }
    }

    public HashMap playSubTurn(){
        //update previous dice with current
        clearPreviousDice();
        previousDice = (ArrayList<Dice>)playDice.clone();

        //player rolls dice (outcomes saved)
        playerRollDice();


        // Hottie hunk logic


        //generate output info
        HashMap turnStats = new HashMap();
        turnStats.put("Shotgun", shotgunCounter);
        turnStats.put("Brain", brainCounter);

        //sorting dice
        removeScoreDice();
        diceBag.dealToThree(playDice);

        return turnStats;
    }

    public void resetPointCounters(){
        this.brainCounter = 0;
        this.shotgunCounter = 0;
    }

    public void initializeDiceAndCounters(){
        diceBag.genDice();
        playDice.clear();
        diceBag.dealToThree(playDice);
        resetPointCounters();
    }
}

