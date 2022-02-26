package com.webcheckers.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;


/**
 * The unit test suite for the GetHomeRoute component.
 *
 * @author Matthew Simoni
 */
@Tag("UI-tier")
public class GetHomeRouteTest {

    //Friendly objects
    private Player player;
    private PlayerLobby playerLobby;

    //Component under test
    private GetHomeRoute CuT;

    //Mock data
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine templateEngine;


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
        playerLobby = new PlayerLobby();
        player = playerLobby.newPlayer("first");
        CuT = new GetHomeRoute(playerLobby, templateEngine);
    }

    /**
     Test to see how the non-signed in page runs
     **/
    @Test
    public void nonSignedInHomePage(){
        //Used to aid the template engine
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Runs a request using mock objects
        CuT.handle(request, response);

        //Do they exist and start properly
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //Checks attributes to see if they load
        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_ONLINE_ATTR, playerLobby.getNumPlayersOnline());
        testHelper.assertViewModelAttribute(GetHomeRoute.MESS_ATTR, GetHomeRoute.WELCOME_MSG);

        //View name actually starts
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     Test to see how the signed in page runs
     **/
    @Test
    public void signedInHomePage(){
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(player);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        //Gets name
        String name = player.getName();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE);
        testHelper.assertViewModelAttribute(GetHomeRoute.PLAYER_ONLINE_ATTR, playerLobby.getNumPlayersOnline());
        testHelper.assertViewModelAttribute(GetHomeRoute.MESS_ATTR, GetHomeRoute.WELCOME_MSG);
        testHelper.assertViewModelAttribute(GetHomeRoute.SIGNIN_ATTR, "Yes");
        testHelper.assertViewModelAttribute(GetHomeRoute.USERNAME_ATTR, name);
        testHelper.assertViewModelAttribute(GetHomeRoute.LOBBY_ATTR, playerLobby.getPlayers());

        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }

    /**
     Test to see how the home page changes to a game when the game starts
     **/
    @Test
    public void goingToGame(){
        when(session.attribute(PostSignInRoute.PLAYERSERVICES_KEY)).thenReturn(player);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //Mock opponent and game to allow for the player to be in a game
        Player opponent = new Player("opponent");
        Game game = new Game(player, opponent);
        player.setGame(game);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute(GetHomeRoute.TITLE_ATTR, GetHomeRoute.TITLE_GAME);
        testHelper.assertViewModelAttribute(GetHomeRoute.VIEW_ATTR, "PLAY");
        testHelper.assertViewModelAttribute(GetHomeRoute.RED_ATTR, game.getRedPlayer());
        testHelper.assertViewModelAttribute(GetHomeRoute.WHITE_ATTR, game.getWhitePlayer());
        testHelper.assertViewModelAttribute(GetHomeRoute.ACTIVE_ATTR, game.getActiveColor());
        testHelper.assertViewModelAttribute(GetHomeRoute.BOARD_ATTR, game.getBoard());

        //Checks to see if url changes
        verify(response).redirect(WebServer.GAME_URL);

        testHelper.assertViewName(GetHomeRoute.GAME_VIEW_NAME);
    }


}
