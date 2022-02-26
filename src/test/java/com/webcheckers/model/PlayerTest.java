package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Tests the Player class in the model tier.
 *
 * @author Nicholas Deary
 * @author Hana Ho
 */
@Tag("Model-Tier")
public class PlayerTest {
    /** Fields for consistency */
    private final String name = "insert professional name here";

    /** CuT for these tests */
    Player CuT;

    /**
     * Tests that a player can be made.
     */
    @Test
    public void makePlayerTest() {
        CuT = new Player(name);
        assertEquals(CuT.getName(), name);
    }

    /**
     * Tests that the player can be assigned games and associated methods work.
     */
    @Test
    public void setGameTests() {
        CuT = new Player(name);
        assertEquals(CuT.getName(), name);
        assertFalse(CuT.isInGame());
        Game game = mock(Game.class);
        CuT.setGame(game);
        assertEquals(CuT.getGame(), game);
        assertTrue(CuT.isInGame());
    }

    /**
     * Tests that the equals method works.
     */
    @Test
    public void playerEqualsTests() {
        CuT = new Player(name);
        Player alt = new Player(name);
        assertEquals(CuT, alt);
        alt = new Player(name + "now it isnt the same player");
        assertNotEquals(CuT, alt);
        assertNotEquals(CuT, "Not a player object");
    }

    /**
     * Checks that player can be declared a winner.
     */
    @Test
    public void player() {
        Player player = new Player("hahaha");
        Game game = new Game(player, new Player("blah"));
        player.setGame(game);
        assertTrue(player.declareWinner());
        game = null;
        player.setGame(game);
        assertEquals(player.getPlayerColor(), Color.RED);
        assertFalse(player.declareWinner());
    }

    @Test
    public void testOnlineOffline() {
        Player player = new Player("hahaha");
        player.setOnline();
        assertTrue(player.isOnline());
        player.setOffline();
        assertFalse(player.isOnline());

    }
}
