package com.example.user.zombiedice;

/**
 * Created by user on 02/09/2016.
 */
public class YellowDice extends Dice {

    @Override
    public String getType(){
            return "Normal";
    }

    @Override
    public Side roll(){
        int roll = (int)(Math.random()*6);
        if (roll > 3){
            return Side.BRAIN;
        }else if(roll<2) {
            return Side.SHOTGUN;
        }
        return Side.FOOTSTEP;

    }
}
