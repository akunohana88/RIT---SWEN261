package com.webcheckers.application;

import com.webcheckers.model.Player;
import com.webcheckers.ui.GetHomeRoute;

import java.util.*;
import java.util.logging.Logger;

/**
 * The object which coordinates player login in and games for the web application
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */

public class PlayerLobby{

    /**
     * Creates a log for information.
     */
     static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    /**
     * Holds all of the players and the names associated with them.
    */
    private final Map<String, Player> players;

    /**
     * Creates and adds a new player to the player lobby.
     *
     * @param name The username submitted by the user
     * @return A valid player or null if player is not valid
     */
    public Player newPlayer(String name) {
        if(addPlayer(name)) {
            return getPlayer(name);
        }
        return null;
    }

    /**
     * Create place to store players.
     */
    public PlayerLobby() {
        players = new HashMap<>();
    }

    /**
     * Adds players to the list of players signed in.
     * Checks that username is acceptable(only alphanumeric characters and optional spaces).
     *
     * @param username player's username.
     * @return boolean true if player has been added to the list.
     */
    private boolean addPlayer(String username) {
        if(!(username.replaceAll("[^A-Za-z0-9]", "").length() >= 1)) {
            return false;
        }
        else if(username.replaceAll("[^A-Za-z0-9 ]", "").length() != username.length()){
            return false;
        }
        else if(players.containsKey(username)) {
            if(players.get(username).isOnline()) {
                return false;
            }
            else {
                players.get(username).setOnline();
                LOG.fine("Player logged on.");
                return true;
            }
        }
        else {
            Player newPlayer = new Player(username);
            LOG.fine("New player services instance created.");
            players.put(username, newPlayer);
            return true;
        }
    }

    /**
     * Signs out the player when called from the GetSignOutRoute.
     *
     * @param username a string representing the players name.
     */
    public void signOutPlayer(String username) {
        players.get(username).setOffline();
    }

    /**
     * Gets the number of players currently signed in.
     *
     * @return number of players signed in.
     */
    public int getNumPlayersOnline(){
        return getPlayers().size();
    }

    /**
     * Gets the names of players signed in and if they are in a game.
     *
     * @return names of players signed in with game status.
     */
    public List<Player> getPlayers(){
        Set<String> setOfNames = players.keySet();
        ArrayList<Player> setOfPlayers = new ArrayList<>();
        for (String names : setOfNames) {
            if(players.get(names).isOnline())
                setOfPlayers.add(players.get(names));
        }
        return setOfPlayers;
    }

    /**
     * Return player with username.
     *
     * @param username player's username.
     * @return player with the username in parameter.
     */
    public Player getPlayer(String username) {
        return players.get(username);
    }


}
