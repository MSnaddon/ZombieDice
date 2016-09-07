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
//    private boolean hunkHottieShotgunned;

    DiceBagHH diceBag;


    public GameHH(int numberOfPlayers) {
        super(numberOfPlayers);
        diceBag = new DiceBagHH();
        initializeDiceAndCounters();
        hottieHunkSaved();
    }

    public void playerRollDice() {
        ArrayList<Side> outcome = currentPlayer.rollDice(playDice);
        int index = 0;
        for (Side side : outcome) {
            previousRollOutcome.add(side);
            String correspondingDiceType = playDice.get(index).getType();
            // adjusting counters
            //should refactor to account for hunk/hottie shotgunning (much better that way)
            switch (side) {
                case SHOTGUN:
                    if (correspondingDiceType.equals("Hottie") && hunkBrain){
                        hunkBrain = false;
                        brainCounter -= 2;
                    }
                    if(correspondingDiceType.equals("Hunk") && hottieBrain){
                        hottieBrain = false;
                        brainCounter --;
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
                    if (hottieBrain){brainCounter --;}
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


//        //Hottie Hunk Logic
//        Log.d("GameHH", "The brain counter is " + ((Integer) brainCounter).toString());
//        Log.d("GameHH", "Hottie is " + hottieBrain + "| Hunky is " + hunkBrain);
//        Log.d("GameHH", "Hunk or hottie has shotgun " + hunkHottieShotgunned);
//        hunkHottieShotgunned = false;

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