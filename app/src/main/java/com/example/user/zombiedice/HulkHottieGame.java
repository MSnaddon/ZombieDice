package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 06/09/2016.
 */
public class HulkHottieGame extends Game {

    public HulkHottieGame(int numberOfPlayers){
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







//    protected ArrayList<Dice> playDice;
//    protected ArrayList<Side> previousRollOutcome;
//    protected ArrayList<Player> players;
//    protected int turnCounter;
//    protected Player currentPlayer;
//    protected int brainCounter;
//    protected int shotgunCounter;
//    protected BaseDiceBag baseDiceBag;
//
//    protected Game(int numberOfPlayers){
//        baseDiceBag = new BaseDiceBag();
//        playDice = new ArrayList<Dice>(3);
//        players = new ArrayList<Player>();
//        for (Integer i=0 ; i < numberOfPlayers;) {
//            i++;
//            players.add(new Player("Player " + i.toString()));
//        }
//        currentPlayer = players.get(0);
//        turnCounter = 0;
//        previousRollOutcome = new ArrayList<Side>(3);
//    };
//
//    //getters
//    public Player getPlayer(int index){
//        return players.get(index);
//    }
//    public Player getCurrentPlayer(){
//        return this.currentPlayer;
//    }
//    public ArrayList<Player> getPlayers(){
//        return this.players;
//    }
//    public int getBrainCounter(){
//        return this.brainCounter;
//    }
//    public int getShotgunCounter(){
//        return this.shotgunCounter;
//    }
//    public ArrayList<Dice> getDice(){
//        return this.playDice;
//    }
//
//    //pseudo Getters
//    public ArrayList<Player> getStandings(){
//        ArrayList<Player> sortedPlayers = new ArrayList<>(players);
//        Collections.sort(sortedPlayers);
//        return sortedPlayers;
//    }


}
