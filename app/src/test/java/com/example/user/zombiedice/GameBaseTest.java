package com.example.user.zombiedice;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by user on 03/09/2016.
 */
public class GameBaseTest {

    GameBase gameBase;

    @Before
    public void Before(){
        gameBase = new GameBase(2);
    }

    @Test
    public void gameHasPlayers(){
        Player player = gameBase.getPlayer(0);
        assertEquals("Player 1", player.getName());
    }

    @Test
    public void canGetPlayers(){
        ArrayList<Player> players = gameBase.getPlayers();
        assertNotNull(players);
    }

    @Test
    public void gameHasCurrentPlayer(){
        assertEquals("Player 1", gameBase.getCurrentPlayer().getName());
    }

    @Test
    public void canGetNextDice(){
        assertEquals(new YellowDice().getClass(), gameBase.getDice().get(2).getClass());
    }

    @Test
    public void canPlaySubTurn(){
        HashMap turnStats = gameBase.playSubTurn();
        assertNotNull(turnStats.get("Brain"));
        assertNotNull(turnStats.get("Footsteps"));
        assertNotNull(turnStats.get("Shotgun"));
    }

    @Test
    public void gameHasNextTurn(){
        Player player = gameBase.getCurrentPlayer();
        player.addToScore(13);
        boolean over = gameBase.isNextRound();
        assertEquals(true, over);
        over = gameBase.isNextRound();
        assertEquals(false, over);
    }

    @Test
    public void gameSortsStandings(){
        GameBase gameBase2 = new GameBase(3);
        gameBase2.getPlayer(0).addToScore(2);
        gameBase2.getPlayer(2).addToScore(1);
        gameBase2.getPlayer(1).addToScore(3);
        ArrayList<Player> sortedPlayers = gameBase2.getStandings();
        assertEquals(sortedPlayers.get(0), gameBase2.getPlayer(1));
        assertEquals(sortedPlayers.get(1), gameBase2.getPlayer(0));
        assertEquals(sortedPlayers.get(2), gameBase2.getPlayer(2));
    }
}
