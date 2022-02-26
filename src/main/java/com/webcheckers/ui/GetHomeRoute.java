package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.util.CurrentUser;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class GetHomeRoute implements Route {

  /**
   * Creates a log for information.
   */
   static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  /**
   * Holds the welcome message.
   */
  static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  /**
   * Holds the current playerLobby.
   */
  private final PlayerLobby playerLobby;
  /**
   * Holds the current templateEngine.
   */
  private final TemplateEngine templateEngine;

  static final String TITLE = "Welcome!";
  static final String TITLE_GAME = "In a game";

  static final String TITLE_ATTR = "title";
  static final String PLAYER_ONLINE_ATTR = "playersOnline";
  static final String MESS_ATTR = "message";
  static final String SIGNIN_ATTR = "signedIn";
  static final String USERNAME_ATTR = "username";
  static final String USER_ATTR = "currentUser";
  static final String LOBBY_ATTR = "names";
  static final String VIEW_ATTR = "viewMode";
  static final String RED_ATTR = "redPlayer";
  static final String WHITE_ATTR = "whitePlayer";
  static final String ACTIVE_ATTR = "activeColor";
  static final String BOARD_ATTR = "board";

  static final String VIEW_NAME = "home.ftl";
  static final String GAME_VIEW_NAME = "game.ftl";


  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine.
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {

    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request the HTTP request.
   * @param response the HTTP response.
   * @return the rendered HTML for the Home page.
   */
  @Override
  public String handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    Map<String, Object> vm = new HashMap<>();
    final Session httpSession = request.session();
    final Player playerServices = httpSession.attribute(PostSignInRoute.PLAYERSERVICES_KEY);
    if(playerServices != null) {
      if(playerServices.isInGame() && playerServices.getGame().getWinner() == null) {
        Game game = playerServices.getGame();
        vm.put(TITLE_ATTR, TITLE_GAME );
        vm.put(USER_ATTR, new CurrentUser(playerServices.getName()));
        vm.put(VIEW_ATTR, "PLAY");
        vm.put(RED_ATTR, game.getRedPlayer());
        vm.put(WHITE_ATTR, game.getWhitePlayer());
        vm.put(ACTIVE_ATTR, game.getActiveColor());
        vm.put(BOARD_ATTR, game.getBoard());
        response.redirect(WebServer.GAME_URL);
        return templateEngine.render(new ModelAndView(vm, GAME_VIEW_NAME));
      }
      else {
        String name = playerServices.getName();
        vm.put(SIGNIN_ATTR, "Yes");
        vm.put(USERNAME_ATTR, name);
        vm.put(USER_ATTR, new CurrentUser(name));
        vm.put(LOBBY_ATTR, playerLobby.getPlayers());
      }
    }
    vm.put(PLAYER_ONLINE_ATTR, playerLobby.getNumPlayersOnline());
    vm.put(TITLE_ATTR, TITLE);
    vm.put(MESS_ATTR, WELCOME_MSG);
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
