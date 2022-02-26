package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.model.Game;
import com.webcheckers.util.CurrentUser;
import com.webcheckers.util.Message;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Game page.
 *
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class GetGameRoute implements Route {
    /**
     * Constants.
     */
    final static String TITLE = "title";
    final static String TITLE_IN_GAME = "In a game";
    final static String TITLE_NOT_IN_GAME = "Welcome!";
    final static String VIEW_MODE = "viewMode";
    final static String GAME_VIEW = "game.ftl";
    final static String HOME_VIEW = "home.ftl";
    final static String RED_PLAYER = "redPlayer";
    final static String WHITE_PLAYER = "whitePlayer";
    final static String ACTIVE_COLOR = "activeColor";
    final static String BOARD = "board";
    final static String CURRENT_USER = "currentUser";
    final static String SIGNED_IN = "signedIn";
    final static String USERNAME = "username";
    final static String LIST_OF_NAMES = "names";
    final static String PLAYERS_ONLINE = "playersOnline";
    final static String MODE_AS_JSON = "modeOptionsAsJSON";
    final static String IS_GAME_OVER = "isGameOver";
    final static String GAME_OVER_MESS = "gameOverMessage";
    final static String MESSAGE = "message";
    final static Message PLAYER_NOT_AVAL = Message.info("That Player is not available to play");

    /**
     * Creates a log for information.
     */
    static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    /**
     * Holds the current playerLobby.
     */
    private final PlayerLobby playerLobby;

    private final GameCenter gameCenter;

    private Gson gson;
    /**
     * Holds the current templateEngine.
     */
    private final TemplateEngine templateEngine;

    public GetGameRoute(final Gson gson, final PlayerLobby playerLobby, final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
        LOG.config("GetHomeRoute is initialized.");
    }

    /**
     * Render the WebCheckers Game page.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @return the rendered HTML for the Game page.
     */
    @Override
    public String handle(Request request, Response response) {

        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        final Player playerServices = httpSession.attribute(PostSignInRoute.PLAYERSERVICES_KEY);

        String name = playerServices.getName();
        vm.put(CURRENT_USER, new CurrentUser(name));

        Game game = null;
        if (!playerServices.isInGame()) {
            String opponent = request.queryParams("playerNames");
            game = gameCenter.newGame(playerServices, playerLobby.getPlayer(opponent));
            if (game == null) {
                vm.put(SIGNED_IN, "Yes");
                vm.put(USERNAME, name);
                vm.put(LIST_OF_NAMES, playerLobby.getPlayers());
                vm.put(PLAYERS_ONLINE, playerLobby.getNumPlayersOnline());
                vm.put(TITLE, TITLE_NOT_IN_GAME);
                vm.put(MESSAGE, PLAYER_NOT_AVAL);
                return templateEngine.render(new ModelAndView(vm, HOME_VIEW));
            }
        }
        if (Objects.isNull(game)) {
            game = playerServices.getGame();
        }

        if(!playerServices.equals(game.getRedPlayer()) && !playerServices.equals(game.getWhitePlayer())){
            vm.put(TITLE, TITLE_IN_GAME);
            vm.put(VIEW_MODE, "SPECTATOR");
            vm.put(RED_PLAYER, game.getRedPlayer());
            vm.put(WHITE_PLAYER, game.getWhitePlayer());
            vm.put(ACTIVE_COLOR, game.getActiveColor());
            vm.put(BOARD, game.getBoard());

            return templateEngine.render(new ModelAndView(vm, GAME_VIEW));
        }

        vm.put(TITLE, TITLE_IN_GAME);
        vm.put(VIEW_MODE, "PLAY");
        vm.put(RED_PLAYER, game.getRedPlayer());
        vm.put(WHITE_PLAYER, game.getWhitePlayer());
        vm.put(ACTIVE_COLOR, game.getActiveColor());
        vm.put(BOARD, game.getBoard());
        game.noMoreMovesLeft();
        if (game.getWinner() != null){
            final Map<String, Object> modeOptions = new HashMap<>(2);
            playerServices.setGame(null);
            modeOptions.put(IS_GAME_OVER, true);
            modeOptions.put(GAME_OVER_MESS, String.format("Congratulation, %s won!✌ by %s", game.getWinner().getName(), game.getWhyGameOver()));
            vm.put(MODE_AS_JSON, gson.toJson(modeOptions));
        }

        return templateEngine.render(new ModelAndView(vm, GAME_VIEW));
    }
}