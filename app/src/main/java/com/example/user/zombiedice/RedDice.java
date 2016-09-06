package com.example.user.zombiedice;

/**
 * Created by user on 05/09/2016.
 */
public class RedDice extends Dice {

    @Override
    public String getType(){
        return "Strong";
    }

    @Override
    public Side roll(){
        int roll = (int)(Math.random()*6);
        if (roll > 4){
            return Side.BRAIN;
        }else if(roll<3) {
            return Side.SHOTGUN;
        }
        return Side.FOOTSTEP;

    }
}
