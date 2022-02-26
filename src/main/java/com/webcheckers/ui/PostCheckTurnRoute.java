package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;

import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

/**
 * The UI Controller to check whose turn it is.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class PostCheckTurnRoute implements Route {

    /**
     * Holds the current gamecenter.
     */
    private final GameCenter gamecenter;

    /**
     * Holds the gson
     */
    private final Gson gson;

    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    /**
     * Creates a PostCheckTurnRoute instance.
     *
     * @param gson the gson the site is using.
     * @param gameCenter the gameCenter that the site is using.
     * @param templateEngine the template engine that the site is using.
     */
    public PostCheckTurnRoute(final Gson gson, final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gamecenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
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
        if(playerServices.equals(game.getRedPlayer()) && current.equals(Color.RED)){
            return gson.toJson(Message.info("true"));
        }
        else if((playerServices.equals(game.getWhitePlayer()) && current.equals(Color.WHITE))){
            return gson.toJson(Message.info("true"));
        }
        return gson.toJson(Message.info("false"));
    }
}
