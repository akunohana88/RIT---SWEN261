package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * Signs out the player from the session and player lobby.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class PostSignOutRoute implements Route{
    static final String HOME_VIEW = "home.ftl";
    static final String TITLE = "title";
    static final String TITLE_MESSAGE = "Welcome!";

    /**
     * The key string for the player class.
     */
    static String PLAYERSERVICES_KEY = "playerServices";

    /** Fields. */
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    /**
     * Creates a PostSignOutRoute instance.
     *
     * @param playerLobby a PlayerLobby that the site is using.
     * @param templateEngine a templateEngine that the site is using.
     */
    PostSignOutRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby must not be null");
    }

    /**
     * Handles the actions of PostSignOut by removing the player from the
     * playerLobby and deleting the instance of the player associated with
     * the current session.
     *
     * @param request http request spark object.
     * @param response response spark object.
     * @return home page where user signed out.
     */
    @Override
    public String handle(Request request, Response response) {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PLAYERSERVICES_KEY);
        playerLobby.signOutPlayer(playerServices.getName());
        httpSession.attribute(PLAYERSERVICES_KEY, null);

        vm.put(TITLE, TITLE_MESSAGE);
        vm.put("playersOnline", playerLobby.getNumPlayersOnline());
        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm, HOME_VIEW));
    }
}
