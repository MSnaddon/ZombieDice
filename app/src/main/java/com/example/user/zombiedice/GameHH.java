package com.example.user.zombiedice;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public class GameHH extends Game {


    private boolean hunkBrain;
    private boolean hottieBrain;
    private boolean hunkShotgunned;
    private boolean hottieShotgunned;
    private boolean hunkHottieShotgunned;

    DiceBagHH diceBag;


    public GameHH(int numberOfPlayers) {
        super(numberOfPlayers);
        diceBag = new DiceBagHH();
        initializeDiceAndCounters();
        hottieHunkSaved();
    }

    public boolean[] hasShotgunned(){


        return new boolean[2];
    }

    public void playerRollDice() {
        ArrayList<Side> outcome = currentPlayer.rollDice(playDice);
        int index = 0;
        for (Side side : outcome) {
            previousRollOutcome.add(side);
            String correspondingDiceType = playDice.get(index).getType();

            // adjusting counters
            switch (side) {
                case SHOTGUN:
                    if (correspondingDiceType.equals("Hottie")){
                        hottieShotgunned = true;
                        hunkHottieShotgunned = true;
                    }
                    if(correspondingDiceType.equals("Hunk")){
                        hunkShotgunned = true;
                        hunkHottieShotgunned = true;
                    }
                    shotgunCounter++;
                    break;
                case BRAIN:
                    if (correspondingDiceType.equals("Hottie")){
                        hottieBrain = true;
                    }
                    brainCounter++;
                    break;
                case DOUBLEBRAIN:
                    hunkBrain = true;
                    brainCounter += 2;
                    break;
                case DOUBLESHOTGUN:
                    hunkHottieShotgunned = true;
                    shotgunCounter += 2;
                    break;
            }
            index ++;
        }
    }

    public HashMap playSubTurn(){
        //update previous dice with current
        clearPreviousDice();
        previousDice = (ArrayList<Dice>)playDice.clone();

        //player rolls dice (outcomes saved)
        playerRollDice();

        Log.d("GameHH", "The brain counter was " + ((Integer) brainCounter).toString());
        //Hottie Hunk Logic
        if (hunkHottieShotgunned && hottieBrain || hunkBrain){
            Log.d("GameHH", "Condition has passed");
            if (hunkBrain){brainCounter -= 2; Log.d("GameHH","Hunk rescued");}
            if (hottieBrain){brainCounter --;Log.d("GameHH","Hottie rescued");}
            hottieHunkSaved();
        }

        Log.d("GameHH", "The brain counter is " + ((Integer) brainCounter).toString());
        Log.d("GameHH", "Hottie is " + hottieBrain + "| Hunky is " + hunkBrain);
        Log.d("GameHH", "Shotgun stuff is " + hunkHottieShotgunned);

        hunkHottieShotgunned = false;

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
        this.hottieBrain = false;
        this.hunkBrain = false;
    }
    public void initializeDiceAndCounters(){
        diceBag.genDice();
        hottieHunkSaved();
        playDice.clear();
        diceBag.dealToThree(playDice);
        resetPointCounters();
    }

    public void hottieHunkSaved(){
        Log.d("GameHH", "Heroes Saved");
        this.hottieBrain = false;
        this.hunkBrain = false;
        this.diceBag.placeHunkAndHottie();
    }






}