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
    protected BaseDiceBag baseDiceBag;

    protected Game(int numberOfPlayers){
        baseDiceBag = new BaseDiceBag();
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

    //pseudo Getters
    public ArrayList<Player> getStandings(){
        ArrayList<Player> sortedPlayers = new ArrayList<>(players);
        Collections.sort(sortedPlayers);
        return sortedPlayers;
    }


}
