package com.example.user.zombiedice;

import java.util.ArrayList;

/**
 * Created by user on 02/09/2016.
 */
public class Player implements Comparable<Player>{

    private String name;
    private int score;

    public Player(String name){
        this.name = name;
        this.score = 0;
    }

    @Override
    public int compareTo(Player player){
        return -1*((Integer) this.score).compareTo(player.getScore());
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }
    public void addToScore(int roundScore){
        this.score += roundScore;
    }

    public ArrayList<Side> rollDice(ArrayList<Dice> playDice){
        ArrayList<Side> outcomes = new ArrayList<Side>();
            for (Dice dice : playDice){
                outcomes.add(dice.roll());
            }
        return outcomes;
    }
}
