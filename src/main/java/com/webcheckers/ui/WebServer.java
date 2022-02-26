package com.webcheckers.ui;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;

import spark.TemplateEngine;
import static spark.Spark.*;

/**
 * The server that initializes the set of HTTP request handlers. This defines the web application interface for this
 * WebCheckers application.
 *
 * @author Bryan Basham <bdbvse@rit.edu>
 * @author Jack Irish <jti2576@rit.edu>
 * @author Nicholas Deary <njd5368@rit.edu>
 * @author Hana Ho <hth2539@rit.edu>
 * @author Matthew Simoni <mss9774@g.rit.edu>
 */
public class WebServer {
  private static final Logger LOG = Logger.getLogger(WebServer.class.getName());

  /**
   * The URL pattern to request the Home page.
   */
  public static final String HOME_URL = "/";
  public static final String SIGN_IN_URL = "/signin";
  public static final String SIGN_IN_OUT = "/signout";
  public static final String GAME_URL = "/game";
  public static final String VALIDATE_MOVE_URL = "/validateMove";
  public static final String SUBMIT_TURN_URL = "/submitTurn";
  public static final String BACKUP_MOVE_URL = "/backupMove";
  public static final String CHECK_TURN_URL = "/checkTurn";
  public static final String RESIGN_URL = "/resignGame";
  public static final String SPEC_CHECK_URL = "/spectator/checkTurn";
  public static final String SPEC_STOP_WATCH_URL = "/spectator/stopWatching";


  private final PlayerLobby playerLobby;
  private final GameCenter gameCenter;
  private final TemplateEngine templateEngine;
  private final Gson gson;


  /**
   * The constructor for the Web Server.
   *
   * @param templateEngine
   *    The default {@link TemplateEngine} to render page-level HTML views.
   * @param gson
   *    The Google JSON parser object used to render Ajax responses.
   *
   * @throws NullPointerException
   *    If any of the parameters are {@code null}.
   */
  public WebServer(final PlayerLobby playerLobby,final GameCenter gameCenter, final TemplateEngine templateEngine, final Gson gson) {
    // validation
    Objects.requireNonNull(templateEngine, "templateEngine must not be null");
    Objects.requireNonNull(gson, "gson must not be null");
    //
    this.playerLobby = playerLobby;
    this.gameCenter = gameCenter;
    this.templateEngine = templateEngine;
    this.gson = gson;
  }


  /**
   * Initialize all of the HTTP routes that make up this web application.
   *
   * <p>
   * Initialization of the web server includes defining the location for static
   * files, and defining all routes for processing client requests. The method
   * returns after the web server finishes its initialization.
   * </p>
   */
  public void initialize() {

    staticFileLocation("/public");


    /*
     * Shows the Checkers game Home page.
     */
    get(HOME_URL, new GetHomeRoute(playerLobby, templateEngine));

    /*
     * Shows the sign in page.
     */
    get(SIGN_IN_URL, new GetSignInRoute(templateEngine));

    /*
     * Post the sign in page.
     */
    post(SIGN_IN_URL, new PostSignInRoute(playerLobby, templateEngine));

    /*
     * Post the sign out request.
     */
     post(SIGN_IN_OUT, new PostSignOutRoute(playerLobby, templateEngine));

    /*
     * Gets the game route.
     */
    get(GAME_URL, new GetGameRoute(gson, playerLobby, gameCenter, templateEngine));

    /*
     * Posts the resign game request.
     */
    post(RESIGN_URL, new PostResignGameRoute(gson, playerLobby, templateEngine));

    /*
     * Post the sign out request.
     */
    post(SUBMIT_TURN_URL, new PostSubmitTurnRoute(gson, gameCenter, templateEngine));

    /*
     * Post the backup request.
     */
    post(BACKUP_MOVE_URL, new PostBackupMoveRoute(gson, gameCenter, templateEngine));

    /*
     * Post the validate move request.
     */
    post(VALIDATE_MOVE_URL, new PostValidateMoveRoute(gson, gameCenter, templateEngine));

    /*
     * Post the check turn request.
     */
    post(CHECK_TURN_URL, new PostCheckTurnRoute(gson, gameCenter, templateEngine));

    /*
     * Post the spectator check move request.
     */
    post(SPEC_CHECK_URL, new PostSpectatorCheckTurnRoute(gson, gameCenter, templateEngine));

    /*
     * Get the homepage after leaving.
     */
    get(SPEC_STOP_WATCH_URL, new GetSpectatorStopWatchingRoute(gson, playerLobby, templateEngine));



    //get(GAME_URL, new GetGameRoute(templateEngine));

    LOG.config("WebServer is initialized.");
  }

}