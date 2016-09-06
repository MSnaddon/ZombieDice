package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 06/09/2016.
 */
public abstract class DiceBag {

    protected ArrayList<Dice> dice;

    protected DiceBag(){
        dice = new ArrayList<Dice>();
        genDice();
    }

    public abstract void genDice();

    public Dice getDie(){
        if (dice.size()<3){
            genDice();
            Collections.shuffle(dice);
        }
        return dice.remove(0);
    }

    public void dealToThree(ArrayList<Dice> playDice){
        //check if empty
        if (playDice.isEmpty()){
            for (int i = 0 ; i < 3 ; i ++){
                playDice.add(getDie());
            }
            return;
        }
        //replace all nulls with dice
        for (int i = 0 ; i < 3 ; i ++){
            if (playDice.get(i) == null){
                playDice.set(i, getDie());
            }
        }
    }
}
