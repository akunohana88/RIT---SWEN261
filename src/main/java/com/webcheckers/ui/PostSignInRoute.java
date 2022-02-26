package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.CurrentUser;
import com.webcheckers.util.Message;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

/**
 * Signs in the player into the session and player lobby.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class PostSignInRoute implements Route{
    static final String HOME_VIEW = "home.ftl";
    static final String SIGNIN_VIEW = "signin.ftl";

    /**
     * Values used in the view-model map for rendering the sign in view.
     */
    static final String USERNAME = "username";
    static final String MESSAGE = "message";
    private static final Message PROMPT_MSG = Message.info("Invalid sign in name, try again");

    /**
     * Key used in the session attribute map for those signed in.
     */
    static final String PLAYERSERVICES_KEY = "playerServices";

    /**
     * Fields for the class.
     */
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;

    /**
     * Creates a PostSignInRoute instance.
     *
     * @param playerLobby PlayerLobby that the site is using.
     * @param templateEngine TemplateEngine that site is using.
     */
    PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine){
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.templateEngine = templateEngine;
    }

    /**
     * Allows the player to make a sign in attempt and informs the user of
     * the outcome.
     *
     * @param request http request spark object.
     * @param response response spark object.
     * @return home page where user signed in.
     */
    @Override
    public String handle(Request request, Response response) {
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        vm.put("title", "Welcome");
        if(httpSession.attribute(PLAYERSERVICES_KEY) == null) {
            final String playerName = request.queryParams(USERNAME);
            final Player playerService = playerLobby.newPlayer(playerName);
            if(playerService == null){
                vm.put(MESSAGE, PROMPT_MSG);
                vm.put("signedIn", "No");
                return templateEngine.render(new ModelAndView(vm, SIGNIN_VIEW));
            }
            vm.put("signedIn", "Yes");
            httpSession.attribute(PLAYERSERVICES_KEY, playerService);
            vm.put(USERNAME, playerName);
            vm.put("playersOnline", playerLobby.getNumPlayersOnline());
            vm.put("names" , playerLobby.getPlayers());
            vm.put("currentUser", new CurrentUser(playerName));
            response.redirect(WebServer.HOME_URL);
            return templateEngine.render(new ModelAndView(vm, HOME_VIEW));
        }
        else {
            vm.put("signedIn", "Yes");
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }
}
