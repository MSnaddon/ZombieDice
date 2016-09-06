package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 06/09/2016.
 */
public abstract class Game {

    protected ArrayList<Dice> playDice;
    protected ArrayList<Dice> previousDice;
    protected ArrayList<Side> previousRollOutcome;
    protected ArrayList<Player> players;
    protected int turnCounter;
    protected Player currentPlayer;
    protected int brainCounter;
    protected int shotgunCounter;
    protected DiceBag diceBag;

    protected Game(int numberOfPlayers){
        playDice = new ArrayList<Dice>(3);
        players = new ArrayList<Player>();
        for (Integer i=0 ; i < numberOfPlayers;) {
            i++;
            players.add(new Player("Player " + i.toString()));
        }
        currentPlayer = players.get(0);
        turnCounter = 0;
        previousDice = new ArrayList<Dice>(3);
        previousRollOutcome = new ArrayList<Side>(3);
    };

    //getters
    public Player getPlayer(int index){
        return players.get(index);
    }
    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    public int getBrainCounter(){
        return this.brainCounter;
    }
    public int getShotgunCounter(){
        return this.shotgunCounter;
    }
    public ArrayList<Dice> getDice(){
        return this.playDice;
    }
    public ArrayList<Dice> getPreviousDice(){
        return this.previousDice;
    }
    public ArrayList<Side> getPreviousOutcome(){
        return this.previousRollOutcome;
    }


    // Functional methods
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

    //pseudo Getters
    public ArrayList<Player> getStandings(){
        ArrayList<Player> sortedPlayers = new ArrayList<>(players);
        Collections.sort(sortedPlayers);
        return sortedPlayers;
    }

    //Generic Helper Methods
    public void removeScoreDice(){
        // change to replace entries with dummys (null)
        for (int i=2; i > -1; i--){
            if (!(previousRollOutcome.get(i).equals(Side.FOOTSTEP))) {
                playDice.set(i,null);
            }
        }
    }
    public void clearPrevious(){
        previousDice.clear();
        previousRollOutcome.clear();
    }
    public void initializeDiceAndCounters(){
        diceBag.genDice();
        playDice.clear();
        diceBag.dealToThree(playDice);
        resetPointCounters();
    }

    public abstract void resetPointCounters();


}
