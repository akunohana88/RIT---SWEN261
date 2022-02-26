package com.webcheckers.application;


import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the PlayerLobby component.
 *
 * @author Matthew Simoni
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    /**
     * Looks at how the class returns players
     */
    @Test
    public void gettingPlayers(){
        //The component under testing
        final PlayerLobby CuT = new PlayerLobby();

        //Creates a new player
        Player first = CuT.newPlayer("first");

        //Test that the players are added and that they can be returned properly
        assertNull(CuT.getPlayer("second"), "The attempt to get a non-existent player didn't fail");
        assertTrue(CuT.getPlayers().size() == 1, "The list does not add people properly");
        assertEquals(CuT.getPlayer("first"), first, "The getPlayer() method doesn't work");
    }

    /**
     * Looks at how the class adds players to the lobby and making sure players are valid
     */
    @Test
    public void addingPlayerTest(){
        final PlayerLobby CuT = new PlayerLobby();

        //Valid player
        Player first = CuT.newPlayer("test");
        //Invalid based on non-alphanumerically characters
        Player second = CuT.newPlayer("$%%%^&");
        //Invalid based on repeat
        Player third = CuT.newPlayer("test");
        //Invalid based on one character issue
        Player forth = CuT.newPlayer("test%");
        //Valid because of signing in again.
        first.setOffline();
        Player fifth = CuT.newPlayer("test");

        //Looks at how the players were added
        assertEquals(first, CuT.getPlayer("test"), "The username is invalid");
        assertNull(second, "The username is valid with non-alphanumeric symbols when it's not supposed to be");
        assertNull(third, "This username is valid even though its a repeat");
        assertNull(forth, "This username shouldn't be valid because of one character");
        assertEquals(fifth, CuT.getPlayer("test"), "The username is invalid");
    }

    /**
     * Checks that the lobby value changes to match the players in lobby
     */
    @Test
    public void howManyPlayers(){
        final PlayerLobby CuT = new PlayerLobby();

        //Checks that there is no players
        assertEquals(CuT.getNumPlayersOnline(),0, "Default should be 0");

        //Valid player
        Player first = CuT.newPlayer("first");

        //Checks that there is a player
        assertEquals(CuT.getNumPlayersOnline(),1, "There is not one player");

        Player second = CuT.newPlayer("second");
        Player third = CuT.newPlayer("third");

        //Checks if more players are added and registered
        assertEquals(CuT.getNumPlayersOnline(),3, "There is not three players");
    }

    /**
     * Checks that the player is removed from the lobby when prompted
     */
    @Test
    public void removingPlayers(){
        final PlayerLobby CuT = new PlayerLobby();

        //A player is added properly
        Player first = CuT.newPlayer("first");
        assertNotNull(CuT.getPlayer("first"), "The person added does not appear");

        //A player is signed out properly
        CuT.signOutPlayer("first");
        assertFalse(CuT.getPlayer("first").isOnline(), "The player not removed properly");
    }

}
