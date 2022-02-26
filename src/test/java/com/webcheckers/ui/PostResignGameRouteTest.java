package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.Request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * The unit test suite for the PostResignGameRouteTest component.
 *
 * @author Matthew Simoni
 */
@Tag("UI-tier")
public class PostResignGameRouteTest {
    //Friendly objects
    private Gson gson;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Game game;
    private Player opponent;
    private Player currentPlayer;

    //Component under test
    private PostResignGameRoute CuT;

    //Mock data
    private spark.Request request;
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
        CuT = new PostResignGameRoute(gson , playerLobby, templateEngine);
    }

    /**
     Test to see if the player resigned and was sent home
     **/
    @Test
    public void playerResigned() throws Exception {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_NOT_IN_GAME);
        assertEquals("You have resigned.", message.getText(), "Should say they have resigned");

        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_ONLINE_ATTR, playerLobby.getNumPlayersOnline());
        testHelper.assertViewModelAttribute(GetHomeRoute.MESS_ATTR, GetGameRoute.PLAYER_NOT_AVAL);

        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     Test to see if the player wasn't able to resign and stayed in game
     **/
    @Test
    public void playerResignedImproperly() throws Exception {
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);


        opponent.setGame(null);

        GGR.handle(request,response);
        String result = CuT.handle(request, response);
        Message message = gson.fromJson(result, Message.class);
        System.out.println(result);
        GGR.handle(request,response);


        //Checks attributes to see if they load
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        assertEquals("Not able to resign.", message.getText(), "Should say they haven't resigned");
        testHelper.assertViewModelAttribute(GetGameRoute.TITLE, GetGameRoute.TITLE_IN_GAME);
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
        testHelper.assertViewModelAttribute(GetGameRoute.WHITE_PLAYER, opponent);
        testHelper.assertViewModelAttribute(GetGameRoute.RED_PLAYER, currentPlayer);
    }



}
