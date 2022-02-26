package com.webcheckers.model;

import java.util.Objects;

/**
 * Creates a player based on a username assigned.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class Player {

    /**
     * The key part of the player class, this defines the player in total.
     */
    private final String name;

    /**
     * Holds whether the player is red or white.
     */
    private Color playerColor;

    /**
     * Holds the current game the player is in.
     */
    private Game currentGame;

    /**
     * Holds a boolean of whether the player is online.
     */
    private boolean online;

    /**
     * Assigns username to player.
     *
     * @param name a string representing the players name.
     */
    public Player(String name) {
        this.name = name;
        this.online = true;
    }

    /**
     * Sets the player as online.
     */
    public void setOnline() {
        online = true;
    }

    /**
     * Sets the player as offline.
     */
    public void setOffline() {
        online = false;
    }

    /**
     * Gets if the player is online or not.
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Gets player's username.
     *
     * @return the player's username.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets player's game.
     *
     * @param game the game the player is currently involved in.
     */
    public void setGame(Game game){

        this.currentGame = game;
    }

    /**
     * Get player's game.
     *
     * @return The game the current player is playing.
     */
    public Game getGame(){
        return currentGame;
    }

    /**
     * Sets player's color. //For spectator
     *
     * @param color what side the player is on.
     */
    public void setPlayerColor(Color color){
        this.playerColor = color;
    }

    /**
     * Gets player's color. //For spectator
     *
     * @return what side the player is on.
     */
    public Color getPlayerColor(){
        return playerColor;
    }

    /**
     * Checks if player is in a game.
     *
     * @return true if player is in a game false if not.
     */
    public boolean isInGame(){
        return (currentGame != null);
    }

    /**
     * Declares winner for game
     * @return whether winner for a game was declared
     */
    public boolean declareWinner() {
        if(currentGame != null) {
            return currentGame.declareWinner(this);
        }
        return false;
    }

    /**
     * If players are the same
     * @param other player being compared
     * @return if players are the same
     */
    @Override
    public boolean equals(Object other) {
        if(Objects.nonNull(other) && other.getClass() == Player.class) {
            return name.equals(((Player) other).name);
        }
        return false;
    }
}
