package com.example.user.zombiedice;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by user on 03/09/2016.
 */
public class PlayerTest {

    Player player;
    ArrayList<Dice> diceArray;

    @Before
    public void Before(){
        player = new Player("Brian");
        diceArray = new ArrayList<Dice>();
        for (int i=0; i<3;i++) {
            diceArray.add(new Dice());
        }
    }

    @Test
    public void playerHasName(){
        String name = player.getName();
        assertEquals("Brian", name);
    }

    @Test
    public void playerHasScore(){
        int score = player.getScore();
        assertEquals(score,0);
    }

    @Test
    public void playerCanRollDice(){
        ArrayList<Side> outcomes = player.rollDice(diceArray);
            for (Side side : outcomes) {
                assertTrue(Side.BRAIN.equals(side) || Side.SHOTGUN.equals(side));
            }
    }

    @Test
    public void canAddToPlayerScore(){
        player.addToScore(5);
        assertEquals(5, player.getScore());
    }

    @Test
    public void canCompare(){
        Player player2 = new Player("Angus");
        player2.addToScore(3);
        assertEquals(-1, player.compareTo(player2));
        player.addToScore(5);
        assertEquals(1, player.compareTo(player2));
        player2.addToScore(2);
        assertEquals(0, player.compareTo(player2));


    }
}
