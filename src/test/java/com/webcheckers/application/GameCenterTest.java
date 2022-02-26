package com.webcheckers.application;


import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the GameCenter component.
 *
 * @author Matthew Simoni
 */
@Tag("Application-tier")
public class GameCenterTest {

    //The component under testing
    private GameCenter CuT;

    private PlayerLobby playerLobby;

    /**
     * Looks at how the class returns PlayerLobby
     */
    @Test
    public void gettingPlayerLobby(){
        playerLobby = new PlayerLobby();
        CuT = new GameCenter(playerLobby);

        assertEquals(playerLobby, CuT.getPlayerLobby(), "The player lobby is not returned");
    }

    /**
     * Checks to see if a valid game is made
     */
    @Test
    public void validGame(){
        playerLobby = new PlayerLobby();
        CuT = new GameCenter(playerLobby);

        Player first = playerLobby.newPlayer("first");
        Player second = playerLobby.newPlayer("second");
        Player third = playerLobby.newPlayer("third");

        Game game1 = CuT.newGame(first, second);
        Game game2 = CuT.newGame(third, first);

        assertNotNull(game1, "Game wasn't created");
        assertTrue(first.isInGame(), "The first player did not join a game");
        assertTrue(second.isInGame(), "The second player did not join a game");
        assertEquals(second.getGame(), first.getGame(), "They are not in the same game");
        assertNotNull(game2);
    }
    /**
     * Checks to see if a invalid game gets rejected
     */
    @Test
    public void invalidGame(){
        playerLobby = new PlayerLobby();
        CuT = new GameCenter(playerLobby);

        Player first = playerLobby.newPlayer("first");
        Player second = playerLobby.newPlayer("second");

        Game game = CuT.newGame(first, second);

        assertNotNull(game, "Game wasn't created");

        Game secondGame = CuT.newGame(first, second);

        assertNull(secondGame, "The second game started when there shouldn't have been one");

    }



}
