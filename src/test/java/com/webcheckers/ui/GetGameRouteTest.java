package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.*;
import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.Request;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This class test the GetGameRoute in the UI tier.
 *
 * @author Nicholas Deary
 */
@Tag("UI-tier")
public class GetGameRouteTest {
    private GetGameRoute CuT;

    /** Friendly Objects */
    private Game game;
    private Player currentPlayer;
    private Player opponent;

    /** Mock Objects */
    private GameCenter gameCenter;
    private PlayerLobby playerLobby;
    private Gson gson;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;

    /**
     * Executes before each test.
     */
    @BeforeEach
    public void beforeEach() {
        request = mock(Request.class);
        session = mock(Session.class);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);

        gson = new Gson();

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);
    }

    /**
     * Tests that the user can start a game.
     */
    @Test
    public void userStartsGame() {
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        currentPlayer = new Player("user");
        opponent = new Player("The Enemy");

        CuT = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);

        game = new Game(currentPlayer, opponent);
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);
        when(request.queryParams("playerNames")).thenReturn("The Enemy");
        //This doesn't actually make the game yet.
        when(gameCenter.newGame(currentPlayer, opponent)).thenReturn(game);
        when(playerLobby.getPlayer("The Enemy")).thenReturn(opponent);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
    }

    /**
     * Tests that the server rejects attempts to join a user in a game.
     */
    @Test
    public void startGameWithUserInGameRejects() {
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        currentPlayer = new Player("user");
        opponent = new Player("The Enemy");
        opponent.setGame(new Game(new Player("rando"), opponent));
        CuT = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);

        game = new Game(currentPlayer, opponent);
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);
        when(request.queryParams("playerNames")).thenReturn("The Enemy");
        when(gameCenter.newGame(currentPlayer, opponent)).thenReturn(null);
        when(playerLobby.getPlayer("The Enemy")).thenReturn(opponent);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_NOT_IN_GAME);
        testHelper.assertViewName(GetGameRoute.HOME_VIEW);
        testHelper.assertViewModelAttribute(GetGameRoute.USERNAME, currentPlayer.getName());
    }

    /**
     * Tests that the server puts you in your game if already in a game.
     */
    @Test
    public void startGameWhenAlreadyInGame() {
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        currentPlayer = new Player("user");
        currentPlayer.setGame(new Game(new Player("rando"), currentPlayer));
        CuT = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, "PLAY");
    }

    /**
     * Tests that the server puts you in your game if already in a game.
     */
    @Test
    public void gameWhenThereIsWinner() {
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        currentPlayer = new Player("user");
        opponent = new Player("The Enemy");

        game = new Game(currentPlayer, opponent);
        currentPlayer.setGame(game);
        opponent.setGame(game);
        game.declareWinner(currentPlayer);
        CuT = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);


        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put(GetGameRoute.IS_GAME_OVER, true);
        modeOptions.put(GetGameRoute.GAME_OVER_MESS, "Congratulation, user won!✌ by null");

        testHelper.assertViewModelAttribute(GetGameRoute.MODE_AS_JSON, gson.toJson(modeOptions));
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, "PLAY");
    }

    /**
     * Tests that the user can watch a game.
     */
    @Test
    public void userSpectatesGame() {
        gameCenter = mock(GameCenter.class);
        playerLobby = mock(PlayerLobby.class);

        currentPlayer = new Player("user");
        Player redPlayer = new Player("redPlayer");
        Player whitePlayer = new Player("whitePlayer");

        CuT = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);

        game = new Game(redPlayer, whitePlayer);
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);
        when(request.queryParams("playerNames")).thenReturn("redPlayer");
        //This doesn't actually make the game yet.
        when(gameCenter.newGame(currentPlayer, redPlayer )).thenReturn(game);
        when(playerLobby.getPlayer("redPlayer")).thenReturn(redPlayer);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, "SPECTATOR");
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, whitePlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, redPlayer);
    }
}
