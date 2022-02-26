package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.CurrentUser;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.GetHomeRoute.*;

public class GetSpectatorStopWatchingRoute implements Route {
    /**
     * Holds the current playerLobby.
     *
     * @author Jack Irish <jti2576@rit.edu>
     * @author Matthew Simoni <mss9774@g.rit.edu>
     */

    private final PlayerLobby playerLobby;

    private Gson gson;
    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    public GetSpectatorStopWatchingRoute(final Gson gson, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
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
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PostSignInRoute.PLAYERSERVICES_KEY);
        playerServices.setGame(null);

        String name = playerServices.getName();
        vm.put(SIGNIN_ATTR, "Yes");vm.put(USERNAME_ATTR, name);
        vm.put(USER_ATTR, new CurrentUser(name));
        vm.put(LOBBY_ATTR, playerLobby.getPlayers());
        vm.put(PLAYER_ONLINE_ATTR, playerLobby.getNumPlayersOnline());
        vm.put(TITLE_ATTR, TITLE);
        vm.put(MESS_ATTR, WELCOME_MSG);

        response.redirect(WebServer.HOME_URL);
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }


}
