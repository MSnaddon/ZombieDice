package com.example.user.zombiedice;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by user on 03/09/2016.
 */
public class GameTest {

    Game game;

    @Before
    public void Before(){
        game = new Game(2);
    }

    @Test
    public void gameHasPlayers(){
        Player player = game.getPlayer(0);
        assertEquals("Player 1", player.getName());
    }

    @Test
    public void canGetPlayers(){
        ArrayList<Player> players = game.getPlayers();
        assertNotNull(players);
    }

    @Test
    public void gameHasCurrentPlayer(){
        assertEquals("Player 1", game.getCurrentPlayer().getName());
    }

    @Test
    public void canGetNextDice(){
        assertEquals(new Dice().getClass(), game.getDice().get(2).getClass());
    }

    @Test
    public void canPlaySubTurn(){
        HashMap turnStats = game.playSubTurn();
        assertNotNull(turnStats.get("Brain"));
        assertNotNull(turnStats.get("Footsteps"));
        assertNotNull(turnStats.get("Shotgun"));
    }

    @Test
    public void gameHasNextTurn(){
        Player player = game.getCurrentPlayer();
        player.addToScore(13);
        boolean over = game.isNextRound();
        assertEquals(true, over);
        over = game.isNextRound();
        assertEquals(false, over);
    }

    @Test
    public void gameSortsStandings(){
        Game game2 = new Game(3);
        game2.getPlayer(0).addToScore(2);
        game2.getPlayer(2).addToScore(1);
        game2.getPlayer(1).addToScore(3);
        ArrayList<Player> sortedPlayers = game2.getStandings();
        assertEquals(sortedPlayers.get(0), game2.getPlayer(1));
        assertEquals(sortedPlayers.get(1), game2.getPlayer(0));
        assertEquals(sortedPlayers.get(2), game2.getPlayer(2));
    }
}
