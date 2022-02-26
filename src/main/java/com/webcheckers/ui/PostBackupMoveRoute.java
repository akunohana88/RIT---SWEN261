package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;

/**
 * The UI Controller to undo a move if available.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class PostBackupMoveRoute implements Route {

    /**
     * Holds the current gameCenter.
     */
    private final GameCenter gamecenter;

    /**
     * Holds the current gson.
     */
    private final Gson gson;

    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    /**
     * Creates a PostBackupMoveRoute instance.
     *
     * @param gson the gson the site is using.
     * @param gameCenter the gameCenter that the site is using.
     * @param templateEngine the template engine that the site is using.
     */
    public PostBackupMoveRoute(final Gson gson, final GameCenter gameCenter, final TemplateEngine templateEngine) {

        this.gamecenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    }

    /**
     * Undoes the turn that the user created.
     *
     * @param request the spark request.
     * @param response the spark response.
     * @return a message as an json.
     */
    @Override
    public String handle(Request request, Response response) {
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PostSignInRoute.PLAYERSERVICES_KEY);
        Game game = playerServices.getGame();

        String message = game.backupMove();
        if(message.startsWith("ERROR")) {
            return gson.toJson(Message.error(message.substring(7)));
        }
        return gson.toJson(Message.info(message));

    }
}
