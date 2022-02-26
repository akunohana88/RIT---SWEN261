package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import spark.Request;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the GetSpectatorStopWatchingRoute component.
 *
 * @author Matthew Simoni
 */
@Tag("UI-tier")
public class GetSpectatorStopWatchingRouteTest {


    //Friendly objects
    private Gson gson;
    private PlayerLobby playerLobby;
    private Player currentPlayer;

    //Component under test
    private GetSpectatorStopWatchingRoute CuT;

    //Mock data
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;



    /**
     Test to see if the player leaves and is sent home
     **/
    @Test
    public void homePageAfterLeaving(){
        //mock versions
        request = mock(Request.class);
        session = mock(Session.class);

        //On call then return session
        when(request.session()).thenReturn(session);

        //mock versions
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby();
        gson = new Gson();

        CuT = new GetSpectatorStopWatchingRoute(gson, playerLobby, templateEngine);

        currentPlayer = playerLobby.newPlayer("current");
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(currentPlayer);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        currentPlayer.setGame(new Game(currentPlayer, new Player("Tom")));
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        String name = currentPlayer.getName();

        assertNull(currentPlayer.getGame(), "Player should have left");
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_ONLINE_ATTR, playerLobby.getNumPlayersOnline());
        testHelper.assertViewModelAttribute(GetHomeRoute.MESS_ATTR, GetHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(GetHomeRoute.SIGNIN_ATTR, "Yes");
        testHelper.assertViewModelAttribute(GetHomeRoute.USERNAME_ATTR, name);
        testHelper.assertViewModelAttribute(GetHomeRoute.LOBBY_ATTR, playerLobby.getPlayers());

        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }
}
