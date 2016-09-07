package com.example.user.zombiedice;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user on 05/09/2016.
 */
public class DiceBagBase extends DiceBag {

    public DiceBagBase(){
        super();
    }

    public void genDice(){
        dice.clear();
        for (int i=0; i<3;i++){dice.add(new RedDice());}
        for (int i=0; i<4;i++){dice.add(new YellowDice());}
        for (int i=0; i<6;i++){dice.add(new GreenDice());}
        Collections.shuffle(dice);
    }
}
