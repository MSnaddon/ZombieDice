package com.example.user.zombiedice;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by user on 03/09/2016.
 */
public class DiceTest {

    Dice mDice;

    @Before
    public void Before(){
        mDice = new Dice();
    }

    @Test
    public void rollTest(){
        Side result = mDice.roll();
        assertTrue(result == Side.BRAIN || result == Side.SHOTGUN);

    }

}


