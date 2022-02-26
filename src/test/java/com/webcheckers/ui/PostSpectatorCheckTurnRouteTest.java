package com.webcheckers.ui;

import static com.webcheckers.ui.PostSpectatorCheckTurnRoute.JOINED_MESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Color;
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
 * The unit test suite for the PostSpectatorCheckTurn component.
 *
 * @author Matthew Simoni
 */
@Tag("UI-tier")
public class PostSpectatorCheckTurnRouteTest {

    //Friendly objects
    private Gson gson;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Player opponent;
    private Player secondPlayer;
    private Player currentPlayer;

    //Component under test
    private PostSpectatorCheckTurnRoute CuT;

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
        gameCenter = new GameCenter(playerLobby);
        currentPlayer = new Player("user");
        secondPlayer = new Player( "secondUser");
        opponent = new Player("The Enemy");
        game = gameCenter.newGame(secondPlayer, opponent);

        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        GGR = new GetGameRoute(gson, playerLobby, gameCenter, templateEngine);
        CuT = new PostSpectatorCheckTurnRoute(gson , gameCenter, templateEngine);
    }

    /**
     Test to see if player started spectating
     **/
    @Test
    public void startingToSpectate() {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        when(request.session()).thenReturn(session);



        currentPlayer.setGame(game);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(message.getText(), JOINED_MESS, "The person is not spectating properly");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, secondPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, "SPECTATOR");
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }

    /**
     Test to see if player is watching the players move
     **/
    @Test
    public void updateTurn() {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        when(request.session()).thenReturn(session);


        currentPlayer.setPlayerColor(Color.WHITE);
        currentPlayer.setGame(game);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(message.getText(), "true", "The person is not updating to see new person");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, secondPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, "SPECTATOR");
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }

    /**
     Test to see if player is watching the players move
     **/
    @Test
    public void updateTurnIncorrectly() {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        when(request.session()).thenReturn(session);


        currentPlayer.setPlayerColor(Color.RED);
        currentPlayer.setGame(game);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        assertEquals(message.getText(), "false", "The person is updating board when they shouldn't");
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, secondPlayer);
        testHelper.assertViewModelAttribute(GetGameRoute.VIEW_MODE, "SPECTATOR");
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }
}
