package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 05/09/2016.
 */
public class DiceBag {

    private ArrayList<Dice> dice;

    public DiceBag(){
        dice = new ArrayList<Dice>();
        genDice();
    }

    public void genDice(){
        dice.clear();
        for (int i=0; i<3;i++){dice.add(new RedDice());}
        for (int i=0; i<4;i++){dice.add(new YellowDice());}
        for (int i=0; i<6;i++){dice.add(new GreenDice());}
        Collections.shuffle(dice);
    }

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
