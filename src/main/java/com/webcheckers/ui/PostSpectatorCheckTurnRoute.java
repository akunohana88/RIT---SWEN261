package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
/**
 * Updates the spectator view upon a move being made.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class PostSpectatorCheckTurnRoute implements Route {

    final static String JOINED_MESS = "You are spectating successfully";

    /**
     * Holds the current gameCenter.
     */
    private final GameCenter gameCenter;

    private Gson gson;
    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    public PostSpectatorCheckTurnRoute(final Gson gson, final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
    }

    /**
     * Checks whose turn it is.
     *
     * @param request a spark request the site is using.
     * @param response a spark response the site is using.
     * @return a message as a json.
     */
    @Override
    public String handle(Request request, Response response) {
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PostSignInRoute.PLAYERSERVICES_KEY);
        Game game = playerServices.getGame();
        Color current = game.getActiveColor();

        if(playerServices.getPlayerColor() == null){
            playerServices.setPlayerColor(current);
            return gson.toJson(Message.info(JOINED_MESS));
        }
        else if(playerServices.getPlayerColor().equals(current)){
            return gson.toJson(Message.info("false"));
        }
        else{
            playerServices.setPlayerColor(current);
            return gson.toJson(Message.info("true"));
        }

    }


}
