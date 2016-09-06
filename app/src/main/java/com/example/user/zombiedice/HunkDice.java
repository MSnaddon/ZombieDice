package com.example.user.zombiedice;

/**
 * Created by user on 06/09/2016.
 */
public class HunkDice extends Dice {

    @Override
    public String getType() {
        return "Hunk";
    }

    @Override
    public Side roll(){
        int roll = (int)(Math.random()*6);
        switch (roll){
            case 0:
                return Side.DOUBLEBRAIN;
            case 1:
                return Side.DOUBLESHOTGUN;
            case 2: case 3:
                return Side.SHOTGUN;
            default:
                return Side.FOOTSTEP;
        }
    }
}


