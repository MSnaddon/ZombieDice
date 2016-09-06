package com.example.user.zombiedice;

/**
 * Created by user on 05/09/2016.
 */
public class GreenDice extends Dice {

    @Override
    public String getType(){
        return "Green";
    }

    @Override
    public Side roll(){
        int roll = (int)(Math.random()*6);
        if (roll > 2){
            return Side.BRAIN;
        }else if(roll<1) {
            return Side.SHOTGUN;
        }
        return Side.FOOTSTEP;

    }
}
