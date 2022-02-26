package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;


/**
 * The unit test suite for the PostCheckTurn component.
 *
 * @author Matthew Simoni
 */
@Tag("UI-tier")
public class PostCheckTurnRouteTest {

    //Friendly objects
    private Gson gson;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Player opponent;
    private Player currentPlayer;

    //Component under test
    private PostCheckTurnRoute CuT;

    //Mock data
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;
    private GetGameRoute GGR;


    /**
     * Starts the mock session and handles the mock versions that can be referenced later
     */
    @BeforeEach
    public void setup() {
        //mock versions
        request = mock(Request.class);
        session = mock(Session.class);

        //On call then return session
        when(request.session()).thenReturn(session);

        //mock versions
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);


        //Creates a player lobby and player to test login
        playerLobby = mock(PlayerLobby.class);
        gson = new Gson();
        gameCenter = mock(GameCenter.class);
        currentPlayer = new Player("user");
        opponent = new Player("The Enemy");

        game = new Game(currentPlayer, opponent);
        currentPlayer.setGame(game);
        opponent.setGame(game);
        //when(currentPlayer.getGame()).thenReturn(game);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        GGR = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);
        CuT = new PostCheckTurnRoute(gson , gameCenter, templateEngine);
    }

    /**
     Test to see if active turn is true
     **/
    @Test
    public void activeTurn() throws Exception {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);
        //This doesn't actually make the game yet.

        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(message.getText(),"true", "Should return true if it's their turn");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }

    /**
     Test to see if active turn is true after a swap in game
     **/
    @Test
    public void activeTurnAfterSwap() throws Exception {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(opponent);
        //This doesn't actually make the game yet.
        game.swapActiveColor();
        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(message.getText(),"true", "Should return true if it's their turn");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }

    /**
     Test to see if active turn is false after a swap in game
     **/
    @Test
    public void activeTurnAfterSwapIssue() throws Exception {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);
        //This doesn't actually make the game yet.

        game.swapActiveColor();
        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);

        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals("false", message.getText(), "Should return true if it's their turn");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }
}
