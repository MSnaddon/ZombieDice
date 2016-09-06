package com.example.user.zombiedice;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by user on 03/09/2016.
 */
public class BaseGameTest {

    BaseGame baseGame;

    @Before
    public void Before(){
        baseGame = new BaseGame(2);
    }

    @Test
    public void gameHasPlayers(){
        Player player = baseGame.getPlayer(0);
        assertEquals("Player 1", player.getName());
    }

    @Test
    public void canGetPlayers(){
        ArrayList<Player> players = baseGame.getPlayers();
        assertNotNull(players);
    }

    @Test
    public void gameHasCurrentPlayer(){
        assertEquals("Player 1", baseGame.getCurrentPlayer().getName());
    }

    @Test
    public void canGetNextDice(){
        assertEquals(new YellowDice().getClass(), baseGame.getDice().get(2).getClass());
    }

    @Test
    public void canPlaySubTurn(){
        HashMap turnStats = baseGame.playSubTurn();
        assertNotNull(turnStats.get("Brain"));
        assertNotNull(turnStats.get("Footsteps"));
        assertNotNull(turnStats.get("Shotgun"));
    }

    @Test
    public void gameHasNextTurn(){
        Player player = baseGame.getCurrentPlayer();
        player.addToScore(13);
        boolean over = baseGame.isNextRound();
        assertEquals(true, over);
        over = baseGame.isNextRound();
        assertEquals(false, over);
    }

    @Test
    public void gameSortsStandings(){
        BaseGame baseGame2 = new BaseGame(3);
        baseGame2.getPlayer(0).addToScore(2);
        baseGame2.getPlayer(2).addToScore(1);
        baseGame2.getPlayer(1).addToScore(3);
        ArrayList<Player> sortedPlayers = baseGame2.getStandings();
        assertEquals(sortedPlayers.get(0), baseGame2.getPlayer(1));
        assertEquals(sortedPlayers.get(1), baseGame2.getPlayer(0));
        assertEquals(sortedPlayers.get(2), baseGame2.getPlayer(2));
    }
}
