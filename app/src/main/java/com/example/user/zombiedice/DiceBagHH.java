package com.example.user.zombiedice;

/**
 * Created by user on 06/09/2016.
 */
public class DiceBagHH extends DiceBag {

    public DiceBagHH(){
        super();
    }

    public void genDice() {
        for (int i = 0; i<2; i++){dice.add(new RedDice());}
        for (int i = 0; i<3; i++){dice.add(new YellowDice());}
        for (int i = 0; i<6; i++){dice.add(new RedDice());}
        placeHunkAndHottie();
    }

    public void placeHunkAndHottie(){
        dice.add(new HottieDice());
        dice.add(new HunkDice());
    }
}
