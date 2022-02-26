package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;

import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.model.ValidateMove;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;


/**
 * The UI Controller to check a move for validity.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class PostValidateMoveRoute implements Route {

    /**
     * Holds the current playerLobby.
     */
    private final GameCenter gamecenter;

    private final Gson gson;

    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    /**
     * Creates a PostValidateMoveRoute instance.
     *
     * @param gson the gson the site is using.
     * @param gameCenter the gameCenter that the site is using.
     * @param templateEngine the template engine that the site is using.
     */
    public PostValidateMoveRoute(final Gson gson, final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gamecenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "Gson is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    }

    /**
     * Checks that the move is valid.
     *
     * @param request the spark request.
     * @param response the spark response.
     * @return A message as a json.
     */
    @Override
    public String handle(Request request, Response response) {
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PostSignInRoute.PLAYERSERVICES_KEY);

        Game game = playerServices.getGame();


        String jsonMove = request.queryParams("actionData");
        Move move = gson.fromJson(jsonMove, Move.class);
        System.out.println(jsonMove);

        String message = game.takeTurn(move);
        if(message.startsWith("ERROR")){
            return gson.toJson(Message.error(message.substring(7)));
        }
        return gson.toJson(Message.info(message));
    }
}
