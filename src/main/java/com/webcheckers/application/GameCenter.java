package com.webcheckers.application;

import com.webcheckers.model.Player;
import com.webcheckers.model.Game;
import com.webcheckers.ui.GetHomeRoute;

import java.util.logging.Logger;

/**
 * The game center for the site that makes games.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class GameCenter {

    /**
     * Creates a log for information.
     */
    static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final PlayerLobby playerLobby;

    /**
     * Creates the game center with an associated player lobby.
     *
     * @param playerLobby a player lobby the site is using.
     */
    public GameCenter(PlayerLobby playerLobby) {
        this.playerLobby = playerLobby;
    }

    /**
     * Returns the player lobby.
     *
     * @return the playerLobby.
     */
    public PlayerLobby getPlayerLobby(){
        return playerLobby;
    }

    /**
     * Creates a new game for players and also checks for a desyncs or if the user needs to be a spectator.
     *
     * @param user1 the first user, the one who initiated the action.
     * @param user2 the second user, the one who is the subject of the action.
     * @return the game that was created or null if a player was in a game.
     */
    public Game newGame(Player user1, Player user2) {
        LOG.fine("New game instance created.");
        if (user1.isInGame()) {
            return null;
        } else if(!user1.isInGame() && user2.isInGame()) {
            user1.setGame(user2.getGame());
            return user2.getGame();
        } else if ((user1.isInGame() && user1.getGame().getWinner() == null) || (user2.isInGame() && user2.getGame().getWinner() == null)){
            return null;
       } else {
            Game startGame = new Game(user1, user2);
            user1.setGame(startGame);
            user2.setGame(startGame);
            return startGame;
        }
    }
}
