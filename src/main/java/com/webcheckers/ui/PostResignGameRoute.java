package com.webcheckers.ui;

import java.util.*;

import com.google.gson.Gson;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;

import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * Fulfills the post request for resign.
 *
 * @author Nicholas Deary
 */
public class PostResignGameRoute implements Route {
    /**
     * The key string for the player class.
     */
    static String PLAYERSERVICES_KEY = "playerServices";

    /** Fields. */
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private Gson gson;

    /**
     * Constructor
     */
    public PostResignGameRoute(Gson gson, PlayerLobby playerLobby, TemplateEngine templateEngine) {
        Objects.requireNonNull(playerLobby);
        Objects.requireNonNull(templateEngine);
        Objects.requireNonNull(gson);
        this.gson = gson;
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    @Override
    public String handle(Request request, Response response) {
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PLAYERSERVICES_KEY);
        Player opponent = playerServices.getGame().getOpponent(playerServices);
        //Opponent wins when the player resigns. Probably need to simplify
        if(opponent.declareWinner()) {
            playerServices.getGame().setWhyGameOver("Resignation");
            playerServices.setGame(null); // They left, They get sent home
            final Message message = Message.info("You have resigned.");
            return gson.toJson(message);
        }
        final Message message = Message.error("Not able to resign.");
        return gson.toJson(message);
    }
}
