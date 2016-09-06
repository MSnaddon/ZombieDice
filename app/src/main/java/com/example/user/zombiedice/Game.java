package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by user on 03/09/2016.
 */
public class Game {

    private ArrayList<Dice> playDice;
    private ArrayList<Side> previousRollOutcome;
    private ArrayList<Player> players;
    private int turnCounter;
    private Player currentPlayer;
    private int brainCounter;
    private int shotgunCounter;
    private DiceBag diceBag;



    public Game(int numberOfPlayers){
        //setup players
        diceBag = new DiceBag();
        playDice = new ArrayList<Dice>(3);
        players = new ArrayList<Player>();
        for (Integer i=0 ; i < numberOfPlayers;) {
            i++;
            players.add(new Player("Player " + i.toString()));
        }
        currentPlayer = players.get(0);
        //set turn counter and brain/shotgun counters for first turn.
        turnCounter = 0;
        previousRollOutcome = new ArrayList<Side>(3);
        initializeCounters();
    }

    public Player getPlayer(int index){
        return players.get(index);
    }

    public int getBrainCounter(){
        return this.brainCounter;
    }

    public int getShotgunCounter(){
        return this.shotgunCounter;
    }


    public String getPreviousRollString(){
        String output = "";
        if (previousRollOutcome != null) {
            for (Side side : previousRollOutcome) {
                output += side.valueOf() + "  ";
            }
            previousRollOutcome.clear();
        }

        return output;

    }

    public ArrayList<Dice> getDice(){
        return this.playDice;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    public void removeBrainsAndShotguns(){
        // change to replace entries with dummys (null)

        for (int i=2; i > -1; i--){
            if (!(previousRollOutcome.get(i).equals(Side.FOOTSTEP))) {
                playDice.set(i,null);
            }
        }
    }

    public void resetPointCounters(){
        this.brainCounter = 0;
        this.shotgunCounter = 0;
    }

    public HashMap playSubTurn(){
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
        removeBrainsAndShotguns();
        diceBag.dealToThree(playDice);
        return turnStats;
    }

    public void initializeCounters(){
        diceBag.genDice();
        playDice.clear();
        diceBag.dealToThree(playDice);
        resetPointCounters();
    }

    public boolean isNextRound(){
        if (shotgunCounter <3){
            currentPlayer.addToScore(brainCounter);
        }
        initializeCounters();
        turnCounter ++;
        currentPlayer = players.get( turnCounter%players.size() );
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

    public ArrayList<Player> getStandings(){
        ArrayList<Player> sortedPlayers = new ArrayList<>(players);
        Collections.sort(sortedPlayers);
        return sortedPlayers;
    }
}
